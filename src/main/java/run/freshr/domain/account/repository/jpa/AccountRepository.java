package run.freshr.domain.account.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import run.freshr.domain.account.entity.Account;

/**
 * 사용자 계정 정보 관리 권한 repository
 *
 * @author FreshR
 * @apiNote 사용자 계정 정보 관리 권한 repository
 * @since 2024. 4. 3. 오후 2:17:11
 */
public interface AccountRepository extends JpaRepository<Account, String> {

  /**
   * 순환 시퀀스 조회
   *
   * @return sequence
   * @apiNote 순환 시퀀스 조회
   * @author FreshR
   * @since 2024. 4. 3. 오후 5:18:42
   */
  @Query(value = "SELECT NEXTVAL('\"account\".seq_account')")
  Long getSequence();

  /**
   * 데이터 존재 여부 by Username
   *
   * @param username username
   * @return boolean
   * @apiNote 데이터 존재 여부 by Username
   * @author FreshR
   * @since 2024. 3. 28. 오후 5:15:13
   */
  Boolean existsByUsername(String username);

  /**
   * 데이터 조회 by Username
   *
   * @param username username
   * @return by username
   * @apiNote 데이터 조회 by Username
   * @author FreshR
   * @since 2024. 3. 28. 오후 5:15:13
   */
  Account findByUsername(String username);

}
