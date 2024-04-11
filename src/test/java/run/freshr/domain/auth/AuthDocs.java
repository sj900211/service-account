package run.freshr.domain.auth;

import static java.lang.System.lineSeparator;
import static java.util.Arrays.stream;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static run.freshr.domain.account.entity.QAccount.account;

import java.util.List;
import org.springframework.restdocs.payload.FieldDescriptor;
import run.freshr.common.docs.ResponseDocs;
import run.freshr.common.utils.PrintUtil;
import run.freshr.domain.auth.enumerations.Role;

public class AuthDocs {

  public static class Request {
  }

  public static class Response {
  }

  public static class Data {
    public static String descriptionAuthorizationAccess() {
      return descriptionAuthorization(true);
    }

    public static String descriptionAuthorizationRefresh() {
      return descriptionAuthorization(false);
    }

    public static String descriptionAuthorization(Boolean isAccess) {
      return """
          ##### Authorization
          > Bearer 유형으로 값을 설정
          ```json
          {
            "Authorization": "Bearer {{%s JWT Token}}"
          }
          ```
          """.formatted(isAccess ? "Access" : "Refresh");
    }

    public static String descriptionRole(Role... roles) {
      StringBuilder description = new StringBuilder();

      description.append("##### 허용 권한").append(lineSeparator());

      stream(roles).forEach(role ->
          description.append("- ").append(role.getValue()).append(lineSeparator()));

      return description.toString();
    }

    public static String descriptionRsa() {
      return """
          #### RSA
          `GET /auth/crypto` 를 통해 발급받은 RSA 공개키를 사용하여 암호화해야할 항목들을 암호화 후 요청
          """;
    }
  }

}
