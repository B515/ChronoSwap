package cn.chronoswap.chronoswap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUsername = (EditText) findViewById(R.id.login_etUsername);
        etPassword = (EditText) findViewById(R.id.login_etPassword);
        tvError = (TextView) findViewById(R.id.login_tvError);
        btnLogin = (Button) findViewById(R.id.login_btnLogin);
        btnLogin.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            test = Login();
            if(test == null){
                tvError.setText("请输入用户名或密码");
            }
            else if (test.charAt(0) == 'F') {
                tvError.setText("用户名或密码错误");
            } else if (test.charAt(0) == 'T') {
                tvError.setTextColor(getResources().getColor(R.color.right));
                tvError.setText("登录成功");
                finish();
            }
        }
    };
    //登录操作
    protected String Login() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String path = "http://www.chronoswap.cn/userlogin.php";
        //创建okHttpClient对象
        OkHttpClient ohc = new OkHttpClient();
        //表单数据
        RequestBody fb = new FormBody.Builder()
                .addEncoded("name", username)
                .addEncoded("password", password)
                .build();
        //创建一个Request
        Request req = new Request.Builder()
                .url(path)
                .post(fb)
                .build();
        Response res;
        try {
            res = ohc.newCall(req).execute();
            String a = res.body().string();
            return a;
        } catch (Exception e) {
            return null;
        }

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
