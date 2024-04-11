package run.freshr.domain.account.unit.jpa;

import run.freshr.common.extensions.unit.UnitDefaultExtension;
import run.freshr.domain.account.entity.Account;

/**
 * 사용자 계정 정보 관리 unit
 *
 * @author FreshR
 * @apiNote 사용자 계정 정보 관리 unit
 * @since 2024. 4. 3. 오후 4:36:50
 */
public interface AccountUnit extends UnitDefaultExtension<Account, String> {

  /**
   * 순환 시퀀스 조회
   *
   * @return sequence
   * @apiNote 순환 시퀀스 조회
   * @author FreshR
   * @since 2024. 4. 3. 오후 5:19:17
   */
  Long getSequence();

  /**
   * 데이터 존재 여부 by Username
   *
   * @param username username
   * @return boolean
   * @apiNote 데이터 존재 여부 by Username
   * @author FreshR
   * @since 2024. 4. 3. 오후 4:36:50
   */
  Boolean existsByUsername(String username);

  /**
   * 데이터 조회 by Username
   *
   * @param username username
   * @return by username
   * @apiNote 데이터 조회 by Username
   * @author FreshR
   * @since 2024. 4. 3. 오후 4:36:50
   */
  Account getByUsername(String username);

}
