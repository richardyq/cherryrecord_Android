package yinq.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.yinq.cherrydialyrecord.MainActivity;
import com.yinq.cherrydialyrecord.R;

import yinq.Initialize.InitializeUtil;

public class WelcomeInitializeActivity extends AppCompatActivity {

    private Handler welcomeHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:{

                    startInitialize();
                    break;
                }
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_welcome_initialize);

        welcomeHandler.sendEmptyMessageDelayed(1,1000*3);

        ActivityUtil.shareActivityUtil().setStartActivity(this);
    }

    private void startInitialize(){
        InitializeUtil util = new InitializeUtil(this);
        util.startInitialize();
    }
}
