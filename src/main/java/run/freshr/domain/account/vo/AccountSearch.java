package run.freshr.domain.account.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import run.freshr.common.annotations.SearchClass;
import run.freshr.common.annotations.SearchComment;
import run.freshr.common.enumerations.Gender;
import run.freshr.common.extensions.request.SearchExtension;
import run.freshr.domain.account.enumerations.AccountStatus;

/**
 * 사용자 계정 정보 필터 VO
 *
 * @author FreshR
 * @apiNote 사용자 계정 정보 필터 VO
 * @since 2024. 4. 3. 오후 2:19:52
 */
@Data
@SearchClass
@EqualsAndHashCode(callSuper = true)
public class AccountSearch extends SearchExtension<String> {

  /**
   * 계정 상태
   *
   * @apiNote 계정 상태
   * @since 2024. 4. 3. 오후 2:19:52
   */
  @SearchComment("상태")
  private AccountStatus status;

  /**
   * 계정 성별
   *
   * @apiNote 계정 성별
   * @since 2024. 4. 3. 오후 2:19:52
   */
  @SearchComment("성별")
  private Gender gender;

}
