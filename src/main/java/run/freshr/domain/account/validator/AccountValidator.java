package run.freshr.domain.account.validator;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.hasLength;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import run.freshr.common.utils.RestUtil;
import run.freshr.domain.account.vo.AccountSearch;

/**
 * 사용자 계정 정보 관리 요청 정보 검증
 *
 * @author FreshR
 * @apiNote 사용자 계정 정보 관리 요청 정보 검증
 * @since 2024. 4. 3. 오후 4:43:44
 */
@Component
@RequiredArgsConstructor
public class AccountValidator {

  /**
   * 조회 - Page 검증
   *
   * @param search search
   * @param errors errors
   * @apiNote 조회 - Page 검증
   * @author FreshR
   * @since 2024. 4. 3. 오후 4:46:18
   */
  public void getAccountPage(AccountSearch search, Errors errors) {
    Integer size = search.getSize();

    if (isNull(size)) {
      RestUtil.rejectRequired("size", errors);
    }
  }

  /**
   * 조회 - Cursor Page 검증
   *
   * @param search search
   * @param errors errors
   * @apiNote 조회 - Cursor Page 검증
   * @author FreshR
   * @since 2024. 4. 3. 오후 4:46:18
   */
  public void getAccountCursor(AccountSearch search, Errors errors) {
    String pageToken = search.getPageToken();
    Integer size = search.getSize();

    if (isNull(size) && !hasLength(pageToken)) {
      RestUtil.rejectRequired("size or page-token", errors);
    }
  }

}
