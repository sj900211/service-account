package run.freshr.service;

import static run.freshr.common.utils.CryptoUtil.decryptRsa;
import static run.freshr.common.utils.MapperUtil.map;
import static run.freshr.common.utils.StringUtil.padding;
import static run.freshr.domain.account.enumerations.AccountStatus.ACTIVE;
import static run.freshr.domain.auth.enumerations.Privilege.USER;

import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.common.data.CursorData;
import run.freshr.common.data.EntityData;
import run.freshr.common.utils.RestUtil;
import run.freshr.common.utils.StringUtil;
import run.freshr.domain.account.dto.request.AccountJoinRequest;
import run.freshr.domain.account.dto.response.AccountListResponse;
import run.freshr.domain.account.dto.response.AccountResponse;
import run.freshr.domain.account.entity.Account;
import run.freshr.domain.account.unit.elasticsearch.AccountElasticsearchUnit;
import run.freshr.domain.account.unit.jpa.AccountUnit;
import run.freshr.domain.account.vo.AccountSearch;
import run.freshr.domain.auth.redis.RsaPair;
import run.freshr.domain.auth.unit.redis.RsaPairUnit;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

  private final AccountUnit accountUnit;

  private final AccountElasticsearchUnit accountElasticsearchUnit;

  private final RsaPairUnit rsaPairUnit;

  private final PasswordEncoder passwordEncoder;

  private final EntityData entityData;

  /**
   * 회원 가입
   *
   * @param dto dto
   * @return response entity
   * @apiNote 회원 가입
   * @author FreshR
   * @since 2024. 4. 3. 오후 4:48:17
   */
  @Override
  @Transactional
  public ResponseEntity<?> join(AccountJoinRequest dto) {
    Long rsaTtl = entityData.getRsaTtl();
    String encodePublicKey = dto.getRsa();

    // RSA 유효 기간 체크
    if (!rsaPairUnit.checkRsa(encodePublicKey, rsaTtl)) {
      return RestUtil.error(RestUtil.getExceptions().getAccessDenied());
    }

    RsaPair redis = rsaPairUnit.get(encodePublicKey);
    String encodePrivateKey = redis.getPrivateKey();
    String username = decryptRsa(dto.getUsername(), encodePrivateKey);

    // 요청 정보로 데이터가 있는지 체크
    if (accountUnit.exists(username)) {
      return RestUtil.error(RestUtil.getExceptions().getDuplicate());
    }

    Long sequence = accountUnit.getSequence();
    String padding = padding(sequence, 9);
    String uuid = StringUtil.uuidWithoutHyphen();
    long now = ZonedDateTime.now().toInstant().toEpochMilli();
    String id = "A-AI-" + uuid + "-" + now + "-" + padding;
    Account entity = Account.builder()
        .id(id)
        .privilege(USER)
        .status(ACTIVE)
        .gender(dto.getGender())
        .username(username)
        .nickname(decryptRsa(dto.getNickname(), encodePrivateKey))
        .password(passwordEncoder.encode(decryptRsa(dto.getPassword(), encodePrivateKey)))
        .build();

    accountUnit.create(entity);
    accountElasticsearchUnit.save(entity);

    rsaPairUnit.delete(encodePublicKey);

    return RestUtil.ok(RestUtil.buildId(id));
  }

  /**
   * 사용자 계정 정보 조회 - Page
   *
   * @param search search
   * @return account page
   * @apiNote 사용자 계정 정보 조회 - Page
   * @author FreshR
   * @since 2024. 4. 3. 오후 4:48:17
   */
  @Override
  public ResponseEntity<?> getAccountPage(AccountSearch search) {
    Page<AccountListResponse> page = accountElasticsearchUnit.getPage(search)
        .map(item -> map(item, AccountListResponse.class));

    return RestUtil.ok(page);
  }

  /**
   * 사용자 계정 정보 조회 - Cursor Page
   *
   * @param search search
   * @return account cursor
   * @apiNote 사용자 계정 정보 조회 - Cursor Page
   * @author FreshR
   * @since 2024. 4. 3. 오후 4:48:17
   */
  @Override
  public ResponseEntity<?> getAccountCursor(AccountSearch search) {
    CursorData<AccountListResponse> page = accountElasticsearchUnit.getCursor(search)
        .map(item -> map(item, AccountListResponse.class));

    return RestUtil.ok(page);
  }

  /**
   * 사용자 계정 정보 조회
   *
   * @param id id
   * @return account
   * @apiNote 사용자 계정 정보 조회
   * @author FreshR
   * @since 2024. 4. 3. 오후 4:48:17
   */
  @Override
  public ResponseEntity<?> getAccount(String id) {
    Boolean valid = accountUnit.exists(id);

    if (!valid) {
      return RestUtil.error(RestUtil.getExceptions().getEntityNotFound());
    }

    Account entity = accountUnit.get(id);

    valid = !entity.getDeleteFlag() && entity.getUseFlag() && entity.getStatus().equals(ACTIVE);

    if (!valid) {
      return RestUtil.error(RestUtil.getExceptions().getEntityNotFound());
    }

    AccountResponse data = map(entity, AccountResponse.class);

    return RestUtil.ok(data);
  }

}
