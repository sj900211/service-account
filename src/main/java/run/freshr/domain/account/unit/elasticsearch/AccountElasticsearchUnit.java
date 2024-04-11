package run.freshr.domain.account.unit.elasticsearch;

import org.springframework.data.domain.Page;
import run.freshr.common.data.CursorData;
import run.freshr.common.extensions.unit.UnitDocumentDefaultExtension;
import run.freshr.domain.account.entity.Account;
import run.freshr.domain.account.vo.AccountSearch;

/**
 * 사용자 계정 정보 관리 elasticsearch unit
 *
 * @author FreshR
 * @apiNote 사용자 계정 정보 관리 elasticsearch unit
 * @since 2024. 4. 3. 오후 4:36:50
 */
public interface AccountElasticsearchUnit extends UnitDocumentDefaultExtension<Account, String> {

  /**
   * 페이징 데이터 조회
   *
   * @param search Get Parameter VO
   * @return page
   * @apiNote 페이징 데이터 조회
   * @author FreshR
   * @since 2024. 4. 3. 오후 4:36:50
   */
  Page<Account> getPage(AccountSearch search);

  /**
   * Cursor 페이징 데이터 조회
   *
   * @param search Get Parameter VO
   * @return page
   * @apiNote Cursor 페이징 데이터 조회
   * @author FreshR
   * @since 2024. 4. 3. 오후 4:36:50
   */
  CursorData<Account> getCursor(AccountSearch search);

}
