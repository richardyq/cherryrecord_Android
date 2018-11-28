package yinq.Initialize;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import yinq.datamodel.User.UserAccountModel;
import yinq.datamodel.UserDefaults;
import yinq.ui.activity.ActivityUtil;
import yinq.ui.activity.UserLoginActivity;

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
        }
        else {
            //用户已经登录
            startLoadUserInfo();
        }
    }

    protected void startLoadUserInfo(){
        System.out.println("InitializeUtil::startLoadUserInfo");
    }


}
