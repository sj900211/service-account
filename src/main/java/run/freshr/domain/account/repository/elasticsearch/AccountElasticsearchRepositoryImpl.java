package run.freshr.domain.account.repository.elasticsearch;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;
import static org.springframework.data.domain.PageRequest.of;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.data.elasticsearch.annotations.DateFormat.date_hour_minute_second_millis;
import static org.springframework.util.StringUtils.hasLength;
import static run.freshr.common.utils.EnumUtil.find;
import static run.freshr.common.utils.StringUtil.uuidWithoutHyphen;
import static run.freshr.domain.account.entity.QAccount.account;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.json.JsonData;
import io.jsonwebtoken.Claims;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import run.freshr.common.data.CursorData;
import run.freshr.common.exceptions.ParameterException;
import run.freshr.common.utils.JwtUtil;
import run.freshr.common.utils.MapperUtil;
import run.freshr.common.utils.RestUtil;
import run.freshr.domain.account.entity.Account;
import run.freshr.domain.account.enumerations.AccountSearchKeys;
import run.freshr.domain.account.vo.AccountSearch;

/**
 * 사용자 계정 정보 관리 권한 elasticsearch custom repository 구현체
 *
 * @author FreshR
 * @apiNote 사용자 계정 정보 관리 권한 elasticsearch custom repository 구현체
 * @since 2024. 4. 3. 오후 2:35:15
 */
@RequiredArgsConstructor
public class AccountElasticsearchRepositoryImpl implements AccountElasticsearchRepositoryCustom {

  private final ElasticsearchOperations elasticsearchOperations;

  /**
   * 조회 - Page
   *
   * @param search search
   * @return page
   * @apiNote 조회 - Page
   * @author FreshR
   * @since 2024. 4. 3. 오후 2:34:23
   */
  @Override
  public Page<Account> getPage(AccountSearch search) {
    List<Query> boolList = new ArrayList<>();

    keyword(boolList, search.getKey(), search.getWord());

    int page = ofNullable(search.getPage()).orElse(1) - 1;
    Query query = BoolQuery.of(bool -> bool.must(boolList))._toQuery();
    NativeQueryBuilder nativeQuery = NativeQuery.builder().withQuery(query);
    long totalElements = elasticsearchOperations.count(nativeQuery.build(), Account.class);
    PageRequest pageRequest = of(page, search.getSize());
    List<Account> list = elasticsearchOperations.search(
            nativeQuery.withSort(Sort.by(DESC, RestUtil.field(account.createAt)))
                .withPageable(pageRequest)
                .build(),
            Account.class)
        .stream()
        .map(SearchHit::getContent)
        .toList();

    return new PageImpl<>(list, pageRequest, totalElements);
  }

  /**
   * 조회 - Cursor Page
   *
   * @param search search
   * @return cursor
   * @apiNote 조회 - Cursor Page
   * @author FreshR
   * @since 2024. 4. 3. 오후 2:34:23
   */
  @Override
  public CursorData<Account> getCursor(AccountSearch search) {
    String pageToken = search.getPageToken();
    int page, size;
    String cursorAt, key, word;

    if (hasLength(pageToken)) {
      Claims claims = JwtUtil.get(pageToken);
      Object claimsData = claims.get("data");

      if (isNull(claimsData)) {
        throw new ParameterException("not found page token data");
      }

      HashMap<?, ?> data = MapperUtil.map(claimsData, HashMap.class);

      page = Integer.parseInt(data.get("page").toString());
      size = Integer.parseInt(data.get("size").toString());
      cursorAt = data.get("cursorAt").toString();
      key = isNull(data.get("key")) ? "" : data.get("key").toString();
      word = isNull(data.get("word")) ? "" : data.get("word").toString();
    } else {
      page = 0;
      size = search.getSize();
      cursorAt = LocalDateTime.now()
          .format(DateTimeFormatter.ofPattern(date_hour_minute_second_millis.getPattern()));
      key = search.getKey();
      word = search.getWord();
    }

    List<Query> boolList = new ArrayList<>();

    boolList.add(QueryBuilders.range(range ->
        range.field(RestUtil.field(account.createAt)).lte(JsonData.of(cursorAt))));

    keyword(boolList, key, word);

    NativeQueryBuilder nativeQuery = NativeQuery.builder()
        .withQuery(BoolQuery.of(bool -> bool.must(boolList))._toQuery());
    long totalElements = elasticsearchOperations.count(nativeQuery.build(), Account.class);
    PageRequest pageRequest = of(page, size);

    List<Account> list = elasticsearchOperations.search(
            nativeQuery.withSort(Sort.by(DESC, RestUtil.field(account.createAt)))
                .withPageable(pageRequest).build(),
            Account.class)
        .stream()
        .map(SearchHit::getContent)
        .toList();

    HashMap<String, Object> nextData = new HashMap<>(){{
      put("page", page + 1);
      put("size", size);
      put("cursorAt", cursorAt);
      put("key", key);
      put("word", word);
    }};

    String nextPageToken = JwtUtil.generate(uuidWithoutHyphen(), new HashMap<>() {{
      put("data", nextData);
    }});

    return new CursorData<>(list, pageRequest, totalElements, nextPageToken);
  }

  private void keyword(List<Query> boolList, String key, String word) {
    if (hasLength(word)) {
      AccountSearchKeys keys = find(AccountSearchKeys.class, key, AccountSearchKeys.ALL);

      String wildWord = "*" + word + "*";
      boolList.addAll(keys.getPaths().stream()
          .map(path -> QueryBuilders.wildcard(wildcard ->
              wildcard.field(RestUtil.field(path)).wildcard(wildWord)))
          .toList());
    }
  }

}
