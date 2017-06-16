package cn.chronoswap.chronoswap;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

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
    private String test;
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
                    tvError.setText((String) msg.obj);
                    finish();
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
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String path = "http://www.chronoswap.cn/user_login.php";
        //创建okHttpClient对象
        OkHttpClient ohc = new OkHttpClient();
        //表单数据
        RequestBody fb = new FormBody.Builder()
                .add("name", username)
                .add("password", password)
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
                } else if (test.charAt(0) == 'F') {
                    msg.what = 1;
                    msg.obj = "用户名或密码错误";
                } else if (test.charAt(0) == 'T') {
                    msg.what = 2;
                    msg.obj = "登录成功";
                }
                mHandler.sendMessage(msg);
            }
        });
    }
}
/*
    public void Login(View v) {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String path = "http://www.chronoswap.cn/userlogin.php";
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("POST");

            //数据准备
            String data = "name=" + username + "&password=" + password;
            //至少要设置的两个请求头
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", data.length()+"");

            //post的方式提交实际上是留的方式提交给服务器
            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(data.getBytes());

            //获得结果码
            int responseCode = connection.getResponseCode();
            if (responseCode == 0) {
                //未找到用户
                tvError.setText("未找到用户");
            } else if (responseCode == 1) {
                //登陆成功
                tvError.setTextColor(getResources().getColor(R.color.right));
                tvError.setText("登录成功");
                return;
            } else if (responseCode == 2) {
                //密码错误
                tvError.setText("密码错误");
            } else {
                //登陆失败
                tvError.setText("登录失败");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }
}*/
