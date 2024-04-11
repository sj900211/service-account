package run.freshr.domain.account.unit.jpa;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.common.annotations.Unit;
import run.freshr.domain.account.entity.Account;
import run.freshr.domain.account.repository.jpa.AccountRepository;

@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountUnitImpl implements AccountUnit {

  private final AccountRepository repository;

  /**
   * 순환 시퀀스 조회
   *
   * @return sequence
   * @apiNote 순환 시퀀스 조회
   * @author FreshR
   * @since 2024. 4. 3. 오후 5:19:17
   */
  @Override
  public Long getSequence() {
    return repository.getSequence();
  }

  @Override
  @Transactional
  public String create(Account entity) {
    return repository.save(entity).getId();
  }

  @Override
  public Boolean exists(String id) {
    return repository.existsById(id);
  }

  /**
   * 데이터 존재 여부 by Username
   *
   * @param username username
   * @return boolean
   * @apiNote 데이터 존재 여부 by Username
   * @author FreshR
   * @since 2024. 4. 3. 오후 4:36:50
   */
  @Override
  public Boolean existsByUsername(String username) {
    return repository.existsByUsername(username);
  }

  @Override
  public Account get(String id) {
    return repository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  /**
   * 데이터 조회 by Username
   *
   * @param username username
   * @return by username
   * @apiNote 데이터 조회 by Username
   * @author FreshR
   * @since 2024. 4. 3. 오후 4:36:50
   */
  @Override
  public Account getByUsername(String username) {
    return repository.findByUsername(username);
  }

}
