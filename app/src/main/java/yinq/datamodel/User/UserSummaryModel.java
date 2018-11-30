package yinq.datamodel.User;

import yinq.datamodel.JsonObject;

/**
 * Created by YinQ on 2018/11/29.
 */

public class UserSummaryModel extends JsonObject {

    private String userId;
    private String userName;

    public UserSummaryModel(){

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
