package yinq.userModule;

import java.lang.reflect.InvocationTargetException;

import yinq.Request.HttpRequestManager;
import yinq.Request.HttpRequestObservice;
import yinq.userModule.requests.UserInfoRequest;
import yinq.userModule.requests.UserLoginRequest;

/**
 * Created by YinQ on 2018/11/29.
 */

public class UserUtil {

    public UserUtil(){

    }

    public void startLogin(String account, String password, HttpRequestObservice observice) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        UserLoginRequest.LoginParam loginParam = new  UserLoginRequest.LoginParam(account, password);
        HttpRequestManager.createRequest(UserLoginRequest.class, loginParam.toJson(), observice);
    }

    public void startGetUserInfo(String userId, HttpRequestObservice observice) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        UserInfoRequest.UserInfoParam param = new UserInfoRequest.UserInfoParam(userId);
        HttpRequestManager.createRequest(UserInfoRequest.class, param.toJson(), observice);

    }
}
