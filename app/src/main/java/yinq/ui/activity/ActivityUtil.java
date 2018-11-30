package yinq.ui.activity;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by YinQ on 2018/11/28.
 */

public class ActivityUtil {

    protected static ActivityUtil shareUtil = null;
    private Activity topActivity;

    public ActivityUtil(){

    }

    public static ActivityUtil shareActivityUtil(){
        if (shareUtil == null){
            shareUtil = new ActivityUtil();
        }
        return shareUtil;
    }

    public void setStartActivity(Activity activity){
        topActivity = activity;
    }


    public Activity getTopActivity() {
        return topActivity;
    }

    public void setTopActivity(Activity topActivity) {
        this.topActivity = topActivity;
    }

    public void intentAcitvity(Class c){
        Intent intent = new Intent(topActivity, c);
        topActivity.startActivity(intent);

    }
}
