package yinq.datamodel;

import android.content.Context;
import android.content.SharedPreferences;

import yinq.datamodel.User.UserAccountModel;
import yinq.datamodel.User.UserInfoModel;
import yinq.userModule.requests.UserLoginRequest;

/**
 * Created by YinQ on 2018/11/28.
 */

public class UserDefaults {
    Context context;
    SharedPreferences sharedPreferences;

    private static String loginedAccountModelKey = "loginedAccountModel";
    private static String loginedAccountParamKey = "loginedAccountParam";
    private static String loginedUserModelKey = "loginedUserModel";

    public  UserDefaults(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences("userShare", Context.MODE_PRIVATE);
    }



    public void  saveUserAccount(UserAccountModel accountModel){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (accountModel == null){
            editor.remove(loginedAccountModelKey);
            return;
        }


        String json = accountModel.toJson();
        editor.putString(loginedAccountModelKey, json);
        editor.commit();
    }

    public UserAccountModel getLoginedUserAccount(){
        UserAccountModel accountModel = null;

        String json = sharedPreferences.getString(loginedAccountModelKey, "");
        if (json != null && !json.isEmpty()){
            return (UserAccountModel) new UserAccountModel().fromJson(json);
        }
        return accountModel;
    }

    public void saveLoginParam(UserLoginRequest.LoginParam loginParam){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (loginParam == null){
            editor.remove(loginedAccountParamKey);
            return;
        }


        String json = loginParam.toJson();
        editor.putString(loginedAccountParamKey, json);
        editor.commit();
    }

    public UserLoginRequest.LoginParam getLoginParam(){
        UserLoginRequest.LoginParam loginParam = null;

        String json = sharedPreferences.getString(loginedAccountParamKey, "");
        if (json != null && !json.isEmpty()){
            return (UserLoginRequest.LoginParam) new UserLoginRequest.LoginParam().fromJson(json);
        }
        return loginParam;
    }

    public void saveLoginedUserModel(UserInfoModel model){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (model == null){
            editor.remove(loginedUserModelKey);
            return;
        }


        String json = model.toJson();
        editor.putString(loginedUserModelKey, json);
        editor.commit();
    }

    public UserInfoModel getLoginedUserModel(){
        UserInfoModel userInfoModel = null;

        String json = sharedPreferences.getString(loginedUserModelKey, "");
        if (json != null && !json.isEmpty()){
            return (UserInfoModel) new UserInfoModel().fromJson(json);
        }
        return userInfoModel;
    }
}
