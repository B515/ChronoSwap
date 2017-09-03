package cn.chronoswap.chronoswap.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
                    getInfo(msg.obj.toString());
                } else if (msg.what == 3) {
                    Toast.makeText(LoginActivity.this, "数据更新失败，请重新登录", Toast.LENGTH_SHORT).show();
                } else if (msg.what == 4) {
                    UserInfoManager.setUserInfo(LoginActivity.this, msg.obj.toString());
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else tvError.setText((String) msg.obj);
            }
        };
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (TextUtils.isEmpty(etUsername.getText()) | TextUtils.isEmpty(etPassword.getText())) {
                tvError.setText("请输入用户名和密码");
            } else {
                Login();
            }
        }
    };

    //生成session
    protected String createSession(String key) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date curDate = new Date(System.currentTimeMillis());
        String time = formatter.format(curDate);
        return time + key.charAt(0);
    }

    //登录操作
    protected void Login() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        final String session = createSession(username);
        String path = "http://www.chronoswap.cn/user_login.php";
        //创建okHttpClient对象
        OkHttpClient ohc = new OkHttpClient();
        //表单数据
        RequestBody fb = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .add("session", session)
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
                String[] str = test.split("&");
                if (test.charAt(0) == 'F') {
                    msg.what = 1;
                    msg.obj = "用户名或密码错误";
                } else if (test.charAt(0) == 'T') {
                    msg.what = 2;
                    msg.obj = str[1] + "&" + session;
                }
                mHandler.sendMessage(msg);
            }
        });
    }

    //从网络获取用户信息
    private void getInfo(String info) {
        String[] str = info.split("&");
        final String id = str[0];
        final String session = str[1];
        String path = "http://www.chronoswap.cn/userinfo_get.php";
        //创建okHttpClient对象
        OkHttpClient ohc = new OkHttpClient();
        //表单数据
        RequestBody fb = new FormBody.Builder()
                .add("userid", id)
                .add("session", session)
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
                String text = response.body().string() + "&" + session;
                Message msg = new Message();
                if (text == null) {
                    msg.what = 3;
                } else {
                    msg.what = 4;
                    msg.obj = text;
                }
                mHandler.sendMessage(msg);
            }
        });
    }

}

