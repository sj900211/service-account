package run.freshr.domain.account.repository.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import run.freshr.domain.account.entity.Account;

/**
 * 사용자 계정 정보 관리 권한 elasticsearch repository
 *
 * @author FreshR
 * @apiNote 사용자 계정 정보 관리 권한 elasticsearch repository
 * @since 2024. 4. 3. 오후 2:32:24
 */
public interface AccountElasticsearchRepository extends ElasticsearchRepository<Account, String>,
    AccountElasticsearchRepositoryCustom {

}
