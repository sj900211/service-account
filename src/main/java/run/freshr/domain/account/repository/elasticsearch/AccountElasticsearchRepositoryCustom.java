package run.freshr.domain.account.repository.elasticsearch;

import org.springframework.data.domain.Page;
import run.freshr.common.data.CursorData;
import run.freshr.domain.account.entity.Account;
import run.freshr.domain.account.vo.AccountSearch;

/**
 * 사용자 계정 정보 관리 권한 elasticsearch custom repository
 *
 * @author FreshR
 * @apiNote 사용자 계정 정보 관리 권한 elasticsearch custom repository
 * @since 2024. 4. 3. 오후 2:32:24
 */
public interface AccountElasticsearchRepositoryCustom {

  /**
   * 조회 - Page
   *
   * @param search search
   * @return page
   * @apiNote 조회 - Page
   * @author FreshR
   * @since 2024. 4. 3. 오후 2:34:23
   */
  Page<Account> getPage(AccountSearch search);

  /**
   * 조회 - Cursor Page
   *
   * @param search search
   * @return cursor
   * @apiNote 조회 - Cursor Page
   * @author FreshR
   * @since 2024. 4. 3. 오후 2:34:23
   */
  CursorData<Account> getCursor(AccountSearch search);

}
