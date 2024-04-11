package run.freshr.domain.account.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.freshr.common.enumerations.Gender;

/**
 * 회원 가입 request DTO
 *
 * @author FreshR
 * @apiNote 회원 가입 request DTO
 * @since 2024. 4. 3. 오후 2:08:55
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountJoinRequest {

  @NotEmpty
  private String rsa;

  @NotEmpty
  private String username;

  @NotEmpty
  private String password;

  @NotEmpty
  private String nickname;

  @NotNull
  private Gender gender;

}
