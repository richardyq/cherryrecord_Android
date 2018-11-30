package yinq.userModule.requests;

import com.google.gson.Gson;

import yinq.Request.HttpRequest;
import yinq.Request.HttpRequestObservice;
import yinq.Request.HttpRequestUrl;
import yinq.datamodel.JsonObject;
import yinq.datamodel.User.UserInfoModel;


/**
 * Created by YinQ on 2018/11/29.
 */

public class UserInfoRequest extends HttpRequest {

    public static class UserInfoParam extends JsonObject{

        private String userId;

        public UserInfoParam(){

        }

        public UserInfoParam(String userId){
            this.userId = userId;
        }
    }

    public UserInfoRequest(String json, HttpRequestObservice observice){
        super(json, observice);
    }

    @Override
    protected String getHttpUrl() {
        return HttpRequestUrl.getHttpUrl("userService", "getUserInfo");
    }

    @Override
    protected Object getRespResult(Object respResult) {
        //return super.getRespResult(respResult);

        Gson gson = new Gson();
        String json = gson.toJson(respResult);

        UserInfoModel userModel;
        userModel = (UserInfoModel) new UserInfoModel().fromJson(json);
        return userModel;
    }
}
