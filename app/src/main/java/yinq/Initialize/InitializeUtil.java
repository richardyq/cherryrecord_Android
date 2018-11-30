package yinq.Initialize;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.yinq.cherrydialyrecord.MainActivity;

import java.lang.reflect.InvocationTargetException;

import yinq.Request.HttpRequestManager;
import yinq.Request.HttpRequestObservice;
import yinq.datamodel.User.UserAccountModel;
import yinq.datamodel.User.UserInfoModel;
import yinq.datamodel.UserDefaults;
import yinq.ui.activity.ActivityUtil;
import yinq.ui.activity.UserLoginActivity;
import yinq.userModule.UserUtil;
import yinq.userModule.requests.UserInfoRequest;
import yinq.userModule.requests.UserLoginRequest;

/**
 * Created by YinQ on 2018/11/28.
 */

public class InitializeUtil {
    private Context context;

    public InitializeUtil(Context context){
        this.context = context;
    }

    public void startInitialize(){
        UserDefaults userDefaults = new UserDefaults(context);
        UserAccountModel userAccountModel = userDefaults.getLoginedUserAccount();
        if (userAccountModel == null){
            //用户还没有登录，需要跳转到登录界面
            System.out.println("startInitialize user has not been logined.");

            Intent intent = new Intent(context, UserLoginActivity.class);
            context.startActivity(intent);

            ActivityUtil activityUtil = ActivityUtil.shareActivityUtil();
            Activity topActivity = activityUtil.getTopActivity();
            activityUtil.intentAcitvity(UserLoginActivity.class);
            topActivity.finish();
        }
        else {
            //用户已经登录
            //startLoadUserInfo(userAccountModel.getUserId());
            startAutoLogin();
        }
    }

    protected void startAutoLogin(){
        UserDefaults userDefaults = new UserDefaults(context);
        UserLoginRequest.LoginParam loginParam = userDefaults.getLoginParam();

        HttpRequestObservice observice = new HttpRequestObservice() {
            UserDefaults defaults = new UserDefaults(context);
            @Override
            public void onRequestSuccess(Object result) {

                //保存登录账号信息
                UserAccountModel accountModel = (UserAccountModel) result;
                System.out.println("login success userId:" + accountModel.getUserId());

                defaults.saveUserAccount(accountModel);
            }

            @Override
            public void onRequestFail(int errCode, String errMsg) {
                Toast.makeText(context, errMsg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRequestComplete(int errCode) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
                if (errCode == 0){

                    UserAccountModel accountModel = defaults.getLoginedUserAccount();
                    startLoadUserInfo(accountModel.getUserId());
                }
            }
        };

        UserUtil userUtil = new UserUtil();
        try {
            userUtil.startLogin(loginParam.getAccount(), loginParam.getPassword(), observice);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }

    public void startLoadUserInfo(String userId){
        System.out.println("InitializeUtil::startLoadUserInfo");
        if (userId == null || userId.isEmpty()){
            return;
        }

        HttpRequestObservice observice = new HttpRequestObservice() {
            @Override
            public void onRequestSuccess(Object result) {
                //保存登录的用户信息
                UserDefaults userDefaults = new UserDefaults(context);
                userDefaults.saveLoginedUserModel((UserInfoModel) result);
            }

            @Override
            public void onRequestFail(int errCode, String errMsg) {
                Toast.makeText(context, errMsg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRequestComplete(int errCode) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
                if (errCode == 0){
                    //跳转到主界面
                    Activity topActivity = ActivityUtil.shareActivityUtil().getTopActivity();
                    ActivityUtil.shareActivityUtil().intentAcitvity(MainActivity.class);
                    topActivity.finish();
                }
            }
        };

        UserUtil userUtil = new UserUtil();
        try {
            userUtil.startGetUserInfo(userId, observice);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


}
