package yinq.situation.util;

import android.content.Context;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;

import yinq.Request.HttpRequestManager;
import yinq.Request.HttpRequestObservice;
import yinq.datamodel.JsonObject;
import yinq.datamodel.User.UserInfoModel;
import yinq.datamodel.UserDefaults;
import yinq.situation.requests.AddSleepSituationRequest;
import yinq.situation.requests.TodaySleepSituationsRequest;

/**
 * Created by YinQ on 2018/11/30.
 */

public class SleepSituationUtil {

    public class SleepSituationParam extends JsonObject{
        private String userId;
        private String date;
        private int code;
        private int status;

        public SleepSituationParam(Context context){
            UserDefaults userDefaults = new UserDefaults(context);
            UserInfoModel userInfoModel = userDefaults.getLoginedUserModel();
            this.userId = userInfoModel.getUserId();

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            this.date = format.format(new Date());
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    public SleepSituationUtil(){

    }

    public void startAddSleepSituation(Context context,
                                       int code,
                                       int status,
                                       HttpRequestObservice observice){
        SleepSituationParam param = new SleepSituationParam(context);
        param.setCode(code);
        param.setStatus(status);

        System.out.println("startAddSleepSituation param json:");
        System.out.println(param.toJson());

        try {
            HttpRequestManager.createRequest(AddSleepSituationRequest.class, param.toJson(), observice);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void startLoadTodaySleepSituations(HttpRequestObservice observice){
        try {
            HttpRequestManager.createRequest(TodaySleepSituationsRequest.class, "", observice);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
