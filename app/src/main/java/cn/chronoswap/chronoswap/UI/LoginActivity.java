package cn.chronoswap.chronoswap.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import cn.chronoswap.chronoswap.R;
import cn.chronoswap.chronoswap.db.UserInfoManager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private TextView tvError;
    private Button btnLogin;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUsername = (EditText) findViewById(R.id.login_etUsername);
        etPassword = (EditText) findViewById(R.id.login_etPassword);
        tvError = (TextView) findViewById(R.id.login_tvError);
        btnLogin = (Button) findViewById(R.id.login_btnLogin);
        btnLogin.setOnClickListener(listener);
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 2) {
                    tvError.setTextColor(getResources().getColor(R.color.right));
                    tvError.setText("登录成功");
                    UserInfoManager.saveID(LoginActivity.this, msg.obj.toString());
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else tvError.setText((String) msg.obj);
            }
        };
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Login();
        }
    };

    //登录操作
    protected void Login() {
        TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        String imei = TelephonyMgr.getDeviceId();
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String path = "http://www.chronoswap.cn/user_login.php";
        //创建okHttpClient对象
        OkHttpClient ohc = new OkHttpClient();
        //表单数据
        RequestBody fb = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .add("session", imei)
                .build();
        //创建一个Request
        Request req = new Request.Builder()
                .url(path)
                .post(fb)
                .build();
        Call call = ohc.newCall(req);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String test = response.body().string();
                Message msg = new Message();
                if (test == null) {
                    msg.what = 0;
                    msg.obj = "请输入用户名或密码";
                } else {
                    String[] str = test.split("&");
                    if (test.charAt(0) == 'F') {
                        msg.what = 1;
                        msg.obj = "用户名或密码错误";
                    } else if (test.charAt(0) == 'T') {
                        msg.what = 2;
                        msg.obj = str[1];
                    }
                }
                mHandler.sendMessage(msg);
            }
        });
    }


}

