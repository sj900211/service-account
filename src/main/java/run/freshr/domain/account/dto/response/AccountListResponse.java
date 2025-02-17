package run.freshr.domain.account.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import run.freshr.common.enumerations.Gender;
import run.freshr.common.extensions.response.ResponseLogicalExtension;

/**
 * 사용자 계정 목록 response DTO
 *
 * @author FreshR
 * @apiNote 사용자 계정 목록 response DTO
 * @since 2024. 4. 3. 오후 2:10:54
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AccountListResponse extends ResponseLogicalExtension<String> {

  private String username;

  private String nickname;

  private Gender gender;

  private LocalDateTime signAt;

}
