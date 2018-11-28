package yinq.datamodel;

import android.content.Context;
import android.content.SharedPreferences;

import yinq.datamodel.User.UserAccountModel;

/**
 * Created by YinQ on 2018/11/28.
 */

public class UserDefaults {
    Context context;
    SharedPreferences sharedPreferences;

    private static String loginedAccountModelKey = "loginedAccountModel";

    public  UserDefaults(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences("userShare", Context.MODE_PRIVATE);
    }



    public void  saveUserAccount(UserAccountModel accountModel){
        if (accountModel == null){
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
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
}
