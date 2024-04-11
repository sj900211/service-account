package run.freshr.controller;

import static java.lang.System.lineSeparator;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static run.freshr.TestRunner.userIdList;
import static run.freshr.common.utils.CryptoUtil.encryptRsa;
import static run.freshr.common.utils.ThreadUtil.threadPublicKey;
import static run.freshr.domain.auth.enumerations.Role.ROLE_ANONYMOUS;
import static run.freshr.domain.auth.enumerations.Role.ROLE_MANAGER_MAJOR;
import static run.freshr.domain.auth.enumerations.Role.ROLE_MANAGER_MINOR;
import static run.freshr.domain.auth.enumerations.Role.ROLE_USER;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import run.freshr.common.configurations.URIConfiguration;
import run.freshr.common.enumerations.Gender;
import run.freshr.common.extensions.TestExtension;
import run.freshr.domain.account.AccountDocs;
import run.freshr.domain.account.dto.request.AccountJoinRequest;
import run.freshr.domain.account.vo.AccountSearch;
import run.freshr.domain.auth.AuthDocs;

@DisplayName("사용자 관리")
public class AccountControllerTest extends TestExtension {

  @Test
  @DisplayName("회원 가입")
  public void join() throws Exception {
    setAnonymous();
    setRsa();

    apply();

    String rsa = threadPublicKey.get();

    POST_BODY(
        URIConfiguration.uriAccountJoin,
        AccountJoinRequest
            .builder()
            .rsa(rsa)
            .username(encryptRsa("input-username", rsa))
            .password(encryptRsa("input-password", rsa))
            .nickname(encryptRsa("input-nickname", rsa))
            .gender(Gender.OTHERS)
            .build()
    ).andDo(print())
        .andDo(docs(ResourceSnippetParameters
            .builder()
            .summary("회원 가입")
            .description(AuthDocs.Data.descriptionRsa()
                + lineSeparator() + lineSeparator()
                + AuthDocs.Data.descriptionRole(ROLE_ANONYMOUS))
            .requestFields(AccountDocs.Request.join())
            .responseFields(AccountDocs.Response.join())
            .build()))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("사용자 조회 - Page")
  public void getAccountPage() throws Exception {
    setSignedUser();

    apply();

    AccountSearch search = new AccountSearch();

    search.setPage(2);
    search.setSize(5);

    GET_PARAM(URIConfiguration.uriAccount, search)
        .andDo(print())
        .andDo(docs(ResourceSnippetParameters
            .builder()
            .summary("사용자 조회 - Page")
            .description(AuthDocs.Data.descriptionAuthorizationAccess()
                + lineSeparator() + lineSeparator()
                + AuthDocs.Data.descriptionRole(ROLE_MANAGER_MAJOR, ROLE_MANAGER_MINOR,
                ROLE_USER, ROLE_ANONYMOUS))
            .queryParameters(AccountDocs.Request.getAccountPage())
            .responseFields(AccountDocs.Response.getAccountPage())
            .build()))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("사용자 조회 - Cursor Page")
  public void getAccountCursor() throws Exception {
    setSignedUser();

    apply();

    AccountSearch search = new AccountSearch();

    search.setSize(5);

    GET_PARAM(URIConfiguration.uriAccountCursor, search)
        .andDo(print())
        .andDo(docs(ResourceSnippetParameters
            .builder()
            .summary("사용자 조회 - Cursor Page")
            .description(AuthDocs.Data.descriptionAuthorizationAccess()
                + lineSeparator() + lineSeparator()
                + "#### Cursor 페이징" + lineSeparator() + lineSeparator()
                + "Cursor 페이징은 중복되지 않는 데이터를 제공한다." + lineSeparator()
                + "요청 데이터 중 `size` 와 `page-token` 둘 중 하나는 필수로 작성해야한다."
                + lineSeparator()
                + "`size` 를 작성해서 요청한다면 첫 번째 페이지로 조회된다." + lineSeparator()
                + "`page-token` 를 작성해서 요청한다면 이전에 검색 조건을 기준으로 다음 페이지가 조회된다."
                + lineSeparator()
                + "`page-token` 의 값은 반환 데이터 중 `nextPageToken` 를 사용해서 작성한다."
                + lineSeparator() + lineSeparator()
                + AuthDocs.Data.descriptionRole(ROLE_MANAGER_MAJOR, ROLE_MANAGER_MINOR,
                ROLE_USER, ROLE_ANONYMOUS))
            .queryParameters(AccountDocs.Request.getAccountCursor())
            .responseFields(AccountDocs.Response.getAccountCursor())
            .build()))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("사용자 조회")
  public void getAccountByManager() throws Exception {
    setSignedUser();

    apply();

    GET(URIConfiguration.uriAccountId, userIdList.get(1))
        .andDo(print())
        .andDo(docs(ResourceSnippetParameters
            .builder()
            .summary("사용자 조회")
            .description(AuthDocs.Data.descriptionAuthorizationAccess()
                + lineSeparator() + lineSeparator()
                + AuthDocs.Data.descriptionRole(ROLE_MANAGER_MAJOR, ROLE_MANAGER_MINOR,
                ROLE_USER, ROLE_ANONYMOUS))
            .pathParameters(AccountDocs.Request.getAccount())
            .responseFields(AccountDocs.Response.getAccount())
            .build()))
        .andExpect(status().isOk());
  }

}
