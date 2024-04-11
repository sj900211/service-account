package run.freshr.domain.account;

import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static run.freshr.domain.account.entity.QAccount.account;

import java.util.List;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;
import run.freshr.common.docs.ResponseDocs;
import run.freshr.common.utils.PrintUtil;
import run.freshr.domain.account.vo.SAccountSearch;

public class AccountDocs {

  public static class Request {
    public static List<FieldDescriptor> join() {
      return PrintUtil
          .builder()

          .field("rsa", "RSA 공개키", STRING)

          .prefixDescription("사용자")

          .field(account.username, "고유 아이디 - 이메일 [RSA 암호화]")
          .field(account.password, "비밀번호 [RSA 암호화]")
          .field(account.nickname, "닉네임 [RSA 암호화]")

          .field(account.gender)

          .build()
          .getFieldList();
    }

    public static ParameterDescriptor[] getAccountPage() {
      return PrintUtil
          .builder()

          .parameter(SAccountSearch.page, SAccountSearch.size)

          .prefixOptional()
          .parameter(SAccountSearch.key, SAccountSearch.word)

          .clear()
          .build()
          .getParameters();
    }

    public static ParameterDescriptor[] getAccountCursor() {
      return PrintUtil
          .builder()

          .prefixOptional()
          .parameter(SAccountSearch.size, SAccountSearch.pageToken,
              SAccountSearch.key, SAccountSearch.word)

          .clear()
          .build()
          .getParameters();
    }

    public static ParameterDescriptor[] getAccount() {
      return PrintUtil
          .builder()

          .prefixDescription("사용자")

          .parameter(account.id)

          .build()
          .getParameters();
    }
  }

  public static class Response {
    public static List<FieldDescriptor> join() {
      return ResponseDocs
          .data()

          .prefixDescription("사용자")

          .field(account.id)

          .clear()
          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> getAccountPage() {
      return ResponseDocs
          .page()

          .prefixDescription("사용자")

          .field(account.id, account.username, account.nickname,
              account.gender, account.createAt, account.updateAt)

          .prefixOptional()

          .field(account.signAt)

          .clear()
          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> getAccountCursor() {
      return ResponseDocs
          .cursor()

          .prefixDescription("사용자")

          .field(account.id, account.username, account.nickname,
              account.gender, account.createAt, account.updateAt)

          .prefixOptional()

          .field(account.signAt)

          .clear()
          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> getAccount() {
      return ResponseDocs
          .data()

          .prefixDescription("사용자")

          .field(account.id, account.username, account.nickname,
              account.gender, account.createAt, account.updateAt)

          .prefixOptional()

          .field(account.signAt)

          .clear()
          .build()
          .getFieldList();
    }
  }

  public static class Docs {
  }

}
