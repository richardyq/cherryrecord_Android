package yinq.situation.dataModel;

import yinq.datamodel.JsonObject;
import yinq.datamodel.User.UserSummaryModel;

/**
 * Created by YinQ on 2018/11/29.
 */

public class SituationRecordModel extends JsonObject {

    private String id;
    private String date;
    private UserSummaryModel user;
    private int type;
    private String updateTime;

    public SituationRecordModel(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public UserSummaryModel getUser() {
        return user;
    }

    public void setUser(UserSummaryModel user) {
        this.user = user;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
