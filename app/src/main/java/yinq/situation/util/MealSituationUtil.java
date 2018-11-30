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
import yinq.situation.requests.AddMealSituationRequest;
import yinq.situation.requests.TodayMealSituationsRequest;

/**
 * Created by YinQ on 2018/11/29.
 */

public class MealSituationUtil {

    public class MealSituationParam extends JsonObject{

        private String userId;
        private String date;
        private int code;
        private int speed;
        private int amount;
        private int feed;

        public MealSituationParam(Context context){
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

        public void setCode(int mealCode) {
            this.code = mealCode;
        }

        public int getSpeed() {
            return speed;
        }

        public void setSpeed(int speed) {
            this.speed = speed;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getFeed() {
            return feed;
        }

        public void setFeed(int feed) {
            this.feed = feed;
        }
    }

    public MealSituationUtil(){


    }

    public void startAddMealSituation(Context context,
                                      int code,
                                      int speed,
                                      int amount,
                                      int feed,
                                      HttpRequestObservice observice){
        MealSituationParam param = new MealSituationParam(context);
        param.setCode(code);
        param.setSpeed(speed);
        param.setAmount(amount);
        param.setFeed(feed);

        System.out.println("startAddMealSituation param json:");
        System.out.println(param.toJson());


        try {
            HttpRequestManager.createRequest(AddMealSituationRequest.class, param.toJson(), observice);
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

    public void startLoadTodaySituations(HttpRequestObservice observice){

        try {
            HttpRequestManager.createRequest(TodayMealSituationsRequest.class, "", observice);
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
