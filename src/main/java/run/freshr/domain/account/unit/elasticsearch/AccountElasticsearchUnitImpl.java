package run.freshr.domain.account.unit.elasticsearch;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.common.annotations.Unit;
import run.freshr.common.data.CursorData;
import run.freshr.domain.account.entity.Account;
import run.freshr.domain.account.repository.elasticsearch.AccountElasticsearchRepository;
import run.freshr.domain.account.vo.AccountSearch;

@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountElasticsearchUnitImpl implements AccountElasticsearchUnit {

  private final AccountElasticsearchRepository repository;

  @Override
  @Transactional
  public void save(Account document) {
    repository.save(document);
  }

  @Override
  public Boolean exists(String id) {
    return repository.existsById(id);
  }

  @Override
  public Account get(String id) {
    return repository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  @Transactional
  public void delete(String id) {
    repository.deleteById(id);
  }

  /**
   * 페이징 데이터 조회
   *
   * @param search Get Parameter VO
   * @return page
   * @apiNote 페이징 데이터 조회
   * @author FreshR
   * @since 2024. 4. 3. 오후 4:36:50
   */
  @Override
  public Page<Account> getPage(AccountSearch search) {
    return repository.getPage(search);
  }

  /**
   * Cursor 페이징 데이터 조회
   *
   * @param search Get Parameter VO
   * @return page
   * @apiNote Cursor 페이징 데이터 조회
   * @author FreshR
   * @since 2024. 4. 3. 오후 4:36:50
   */
  @Override
  public CursorData<Account> getCursor(AccountSearch search) {
    return repository.getCursor(search);
  }

}
