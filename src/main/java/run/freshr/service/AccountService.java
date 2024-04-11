package run.freshr.service;

import org.springframework.http.ResponseEntity;
import run.freshr.domain.account.dto.request.AccountJoinRequest;
import run.freshr.domain.account.vo.AccountSearch;

/**
 * 사용자 관리 service
 *
 * @author FreshR
 * @apiNote 사용자 관리 service
 * @since 2024. 4. 3. 오후 4:48:17
 */
public interface AccountService {

  /**
   * 회원 가입
   *
   * @param dto dto
   * @return response entity
   * @apiNote 회원 가입
   * @author FreshR
   * @since 2024. 4. 3. 오후 4:48:17
   */
  ResponseEntity<?> join(AccountJoinRequest dto);

  /**
   * 사용자 계정 정보 조회 - Page
   *
   * @param search search
   * @return account page
   * @apiNote 사용자 계정 정보 조회 - Page
   * @author FreshR
   * @since 2024. 4. 3. 오후 4:48:17
   */
  ResponseEntity<?> getAccountPage(AccountSearch search);

  /**
   * 사용자 계정 정보 조회 - Cursor Page
   *
   * @param search search
   * @return account cursor
   * @apiNote 사용자 계정 정보 조회 - Cursor Page
   * @author FreshR
   * @since 2024. 4. 3. 오후 4:48:17
   */
  ResponseEntity<?> getAccountCursor(AccountSearch search);

  /**
   * 사용자 계정 정보 조회
   *
   * @param id id
   * @return account
   * @apiNote 사용자 계정 정보 조회
   * @author FreshR
   * @since 2024. 4. 3. 오후 4:48:17
   */
  ResponseEntity<?> getAccount(String id);

}
