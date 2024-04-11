package run.freshr.controller;

import static run.freshr.domain.auth.enumerations.Role.Secured.ANONYMOUS;
import static run.freshr.domain.auth.enumerations.Role.Secured.MANAGER_MAJOR;
import static run.freshr.domain.auth.enumerations.Role.Secured.MANAGER_MINOR;
import static run.freshr.domain.auth.enumerations.Role.Secured.USER;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import run.freshr.common.configurations.URIConfiguration;
import run.freshr.common.utils.RestUtil;
import run.freshr.domain.account.dto.request.AccountJoinRequest;
import run.freshr.domain.account.validator.AccountValidator;
import run.freshr.domain.account.vo.AccountSearch;
import run.freshr.service.AccountService;

/**
 * 사용자 관리 controller
 *
 * @author FreshR
 * @apiNote 사용자 관리 controller
 */
@RestController
@RequiredArgsConstructor
public class AccountController {

  private final AccountService service;
  private final AccountValidator validator;

  @Secured(ANONYMOUS)
  @PostMapping(URIConfiguration.uriAccountJoin)
  public ResponseEntity<?> join(@RequestBody @Valid AccountJoinRequest dto) {
    return service.join(dto);
  }

  @Secured({MANAGER_MAJOR, MANAGER_MINOR, USER, ANONYMOUS})
  @GetMapping(URIConfiguration.uriAccount)
  public ResponseEntity<?> getAccountPage(@ModelAttribute @Valid AccountSearch search,
      Errors errors) {
    validator.getAccountPage(search, errors);

    if (errors.hasErrors()) {
      return RestUtil.error(errors);
    }

    return service.getAccountPage(search);
  }

  @Secured({MANAGER_MAJOR, MANAGER_MINOR, USER, ANONYMOUS})
  @GetMapping(URIConfiguration.uriAccountCursor)
  public ResponseEntity<?> getAccountCursor(@ModelAttribute @Valid AccountSearch search,
      Errors errors) {
    validator.getAccountCursor(search, errors);

    if (errors.hasErrors()) {
      return RestUtil.error(errors);
    }

    return service.getAccountCursor(search);
  }

  @Secured({MANAGER_MAJOR, MANAGER_MINOR, USER, ANONYMOUS})
  @GetMapping(URIConfiguration.uriAccountId)
  public ResponseEntity<?> getAccount(@PathVariable String id) {
    return service.getAccount(id);
  }

}
