package run.freshr.domain.account.enumerations;

import static java.util.List.of;
import static run.freshr.domain.account.entity.QAccount.account;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.StringPath;
import java.util.List;
import lombok.Getter;
import run.freshr.common.extensions.enumerations.SearchEnumExtension;
import run.freshr.common.utils.QueryUtil;

/**
 * 계정 검색 조건 데이터 관리
 *
 * @author FreshR
 * @apiNote 계정 검색 조건 데이터 관리
 * @since 2024. 4. 3. 오후 2:13:27
 */
public enum AccountSearchKeys implements SearchEnumExtension {

  ALL("전체", of(account.username, account.nickname)),
  USERNAME("아이디", of(account.username)),
  NICKNAME("닉네임", of(account.nickname));

  private final String value;
  @Getter
  private final List<StringPath> paths;

  AccountSearchKeys(String value, List<StringPath> paths) {
    this.value = value;
    this.paths = paths;
  }

  @Override
  public String getKey() {
    return name();
  }

  @Override
  public String getValue() {
    return value;
  }

  /**
   * 검색 Query 작성
   *
   * @param word 검색어
   * @return boolean builder
   * @apiNote 검색어로 조회 조건 Query 를 작성
   * @author FreshR
   * @since 2024. 4. 3. 오후 2:15:22
   */
  @Override
  public BooleanBuilder search(String word) {
    return QueryUtil.searchKeyword(word, paths);
  }

}
