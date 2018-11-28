package yinq.datamodel.User;


import android.content.Context;

import yinq.datamodel.JsonObject;
import yinq.datamodel.UserDefaults;

/**
 * Created by YinQ on 2018/11/28.
 */

public class UserAccountModel extends JsonObject {

    private String account;
    private String password;
    private String userId;
    private String lastLoginDateTime;

    public UserAccountModel(){

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastLoginDateTime() {
        return lastLoginDateTime;
    }

    public void setLastLoginDateTime(String lastLoginDateTime) {
        this.lastLoginDateTime = lastLoginDateTime;
    }
}
