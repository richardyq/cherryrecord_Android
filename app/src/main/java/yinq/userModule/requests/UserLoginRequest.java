package yinq.userModule.requests;

import com.google.gson.Gson;

import yinq.Request.HttpRequest;
import yinq.Request.HttpRequestObservice;
import yinq.Request.HttpRequestUrl;
import yinq.datamodel.JsonObject;
import yinq.datamodel.User.UserAccountModel;

/**
 * Created by YinQ on 2018/11/29.
 */

public class UserLoginRequest extends HttpRequest {

    public static class LoginParam extends JsonObject{
        private String account;
        private String password;

        public LoginParam(){

        }

        public LoginParam(String account, String password){
            this.account = account;
            this.password = password;
        }


        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }


    }

    public UserLoginRequest(String account, String password, HttpRequestObservice observice){
        LoginParam loginParam = new LoginParam(account, password);

        initWith(loginParam.toJson(), observice);
    }

    public UserLoginRequest(String json, HttpRequestObservice observice){
        super(json, observice);
    }

    @Override
    protected String getHttpUrl() {
        return HttpRequestUrl.getHttpUrl("userService", "login");

    }

    @Override
    protected Object getRespResult(Object respResult) {
        //return super.getRespResult(respResult);

        Gson gson = new Gson();
        String json = gson.toJson(respResult);

        UserAccountModel accountModel = (UserAccountModel) new UserAccountModel().fromJson(json);
        return accountModel;
    }
}
