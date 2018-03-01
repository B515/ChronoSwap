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

import cn.chronoswap.chronoswap.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignUpActivity extends AppCompatActivity {
    private EditText etUsername, etPassword, etRePassword;
    private TextView tvError;
    private Button btnSignUp, btnCancel;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        etUsername = (EditText) findViewById(R.id.sign_up_et_username);
        etPassword = (EditText) findViewById(R.id.sign_up_et_password);
        etRePassword = (EditText) findViewById(R.id.sign_up_et_repassword);
//        etGender = (EditText) findViewById(R.id.sign_up_et_gender);
//        etBirthday = (EditText) findViewById(R.id.sign_up_et_birthday);
        tvError = (TextView) findViewById(R.id.sign_up_tv_error);
        btnSignUp = (Button) findViewById(R.id.sign_up_btn_ok);
        btnCancel = (Button) findViewById(R.id.sign_up_btn_cancel);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkInput())
                    SignUp();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    Toast.makeText(SignUpActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else tvError.setText((String) msg.obj);
            }
        };
    }

    //注册操作
    protected void SignUp() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
//        String gender = "男";
//        String birthday = "1900年1月1日";
//        String university = "-";
//        String Stu_id = "-";
//        String phone_num = "-";
//        String nickname = "-";
        String path = "http://www.chronoswap.cn/userinfo_register.php";
        //创建okHttpClient对象
        OkHttpClient ohc = new OkHttpClient();
        //表单数据
        RequestBody fb = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
//                .add("gender", gender)
//                .add("birthday", birthday)
//                .add("university", university)
//                .add("Stu_id", Stu_id)
//                .add("phone_num", phone_num)
//                .add("nickname", nickname)
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
                if (test.charAt(0) == '1') {
                    msg.what = 1;
                } else {
                    msg.obj = test;
                }
                mHandler.sendMessage(msg);
            }
        });
    }

    //检测输入是否合法
    private boolean checkInput() {
        if (TextUtils.isEmpty(etUsername.getText())) {
            Toast.makeText(SignUpActivity.this, "请填写用户名", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(etPassword.getText())) {
            Toast.makeText(SignUpActivity.this, "请填写密码", Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(etRePassword.getText())) {
            Toast.makeText(SignUpActivity.this, "请重复输入密码", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!etRePassword.getText().toString().equals(etPassword.getText().toString())) {
            Toast.makeText(SignUpActivity.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
            return false;
        } else return true;
    }
}
