package yinq.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yinq.cherrydialyrecord.R;

public class UserLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_user_login);
    }
}
