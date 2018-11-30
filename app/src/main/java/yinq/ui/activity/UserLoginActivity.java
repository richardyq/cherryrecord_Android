package yinq.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yinq.cherrydialyrecord.R;

import java.lang.reflect.InvocationTargetException;

import yinq.Initialize.InitializeUtil;
import yinq.Request.HttpRequestObservice;
import yinq.Request.HttpRespModel;
import yinq.datamodel.User.UserAccountModel;
import yinq.datamodel.UserDefaults;
import yinq.userModule.UserUtil;
import yinq.userModule.requests.UserLoginRequest;

import static android.widget.Toast.*;
import static android.widget.Toast.LENGTH_SHORT;

public class UserLoginActivity extends AppCompatActivity {

    EditText accountEditText;
    EditText passwordEditText;
    Button loginButton;
    UserAccountModel accountModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_user_login);
        bindSubViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ActivityUtil.shareUtil.setTopActivity(this);
    }

    private void bindSubViews(){
        accountEditText = findViewById(R.id.login_edit_text_account);
        passwordEditText = findViewById(R.id.login_edit_text_password);
        loginButton = findViewById(R.id.login_button);

        //自动填写登录账号和密码
        UserDefaults defaults = new UserDefaults(this);
        UserLoginRequest.LoginParam loginParam = defaults.getLoginParam();
        if (loginParam != null){
            accountEditText.setText(loginParam.getAccount());
            passwordEditText.setText(loginParam.getPassword());
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    loginButtonClicked();
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
        });
    }

    protected void loginButtonClicked() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        final String acctText = accountEditText.getText().toString();
        final String pwdText = passwordEditText.getText().toString();
        if (checkLoginInputIsValid(acctText, pwdText) == false){
            return;
        }

        //登录操作
        HttpRequestObservice observice = new HttpRequestObservice() {


            @Override
            public void onRequestSuccess(Object result) {
                //保存登录信息
                accountModel = (UserAccountModel) result;
                System.out.println("login success userId:" + accountModel.getUserId());
                UserLoginRequest.LoginParam loginParam = new UserLoginRequest.LoginParam(acctText, pwdText);
                UserDefaults defaults = new UserDefaults(UserLoginActivity.this);
                defaults.saveLoginParam(loginParam);
                defaults.saveUserAccount(accountModel);
            }

            @Override
            public void onRequestFail(int errCode, String errMsg) {
                Toast.makeText(UserLoginActivity.this, errMsg, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onRequestComplete(int errCode) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
                if (errCode == 0){
                    //获取用户信息
                    InitializeUtil initializeUtil = new InitializeUtil(UserLoginActivity.this);
                    initializeUtil.startLoadUserInfo(accountModel.getUserId());
                }
            }
        };

        UserUtil userUtil = new UserUtil();
        userUtil.startLogin(acctText, pwdText, observice);
    }

    /*
    checkLoginInput
    检查登录参数是否合法
     */
    private boolean checkLoginInputIsValid(String acct, String pwd){
        boolean isValid = false;

        if (acct == null || acct.length() < 6 || acct.length() > 15){
            makeText(this, "您输入的登录账号不正确，请输入6-15位正确的登录账号。", LENGTH_SHORT).show();
            return isValid;
        }
        if (pwd == null || pwd.length() < 6 || pwd.length() > 15){
            makeText(this, "您输入的登录密码不正确，请输入6-15位正确的登录密码。", LENGTH_SHORT).show();
            return isValid;
        }

        isValid = true;
        return isValid;
    }
}
