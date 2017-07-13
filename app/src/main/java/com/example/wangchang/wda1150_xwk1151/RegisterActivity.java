package com.example.wangchang.wda1150_xwk1151;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wangchang.wda1150_xwk1151.Been.UserBeen;

import java.util.List;

/**
 * Created by dell-pc on 2017/7/13.
 */

public class RegisterActivity extends AppCompatActivity {

    private EditText mEmailView;
    private EditText mPasswordView;
    private EditText mAgainPasswordView;
    private UserBeenDao userBeenDao;
    private Button mRegistBtn;
    private Button mCencel;
    private List<UserBeen> userBeenList;
    private int usercount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //初始化数据库
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getApplicationContext(),"user-db",null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        userBeenDao = daoSession.getUserBeenDao();

        userBeenList = userBeenDao.loadAll();
        usercount = userBeenList.size();

        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mAgainPasswordView = (EditText) findViewById(R.id.password_again);
        mRegistBtn = (Button) findViewById(R.id.email_register_button);
        mRegistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();

            }
        });
        mCencel = (Button) findViewById(R.id.cencel);
        mCencel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void attemptRegister() {
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mAgainPasswordView.setError(null);

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String againpassword = mAgainPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }else if (isEmailinDb(email)){
            mEmailView.setError(getString(R.string.error_indb_email));
            focusView = mEmailView;
            cancel = true;
        }
        else if (TextUtils.isEmpty(password)){
            mPasswordView.setError(getString(R.string.error_empty_passowrd));
            focusView = mPasswordView;
            cancel = true;
        }else if (!isPasswordValid(password)){
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        else if (TextUtils.isEmpty(againpassword)){
            mAgainPasswordView.setError(getString(R.string.error_empty_againpassword));
            focusView = mAgainPasswordView;
            cancel = true;
        }
        else if (!isPasswordMatch(password,againpassword)){
            mAgainPasswordView.setError(getString(R.string.error_notmach_againpassword));
            focusView = mAgainPasswordView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            Long id;
            UserBeen search = userBeenDao.queryBuilder().orderDesc(UserBeenDao.Properties.Id).limit(1).build().unique();
            if (search != null){
                id = search.getId()+1;
            }else {
                id = 1L;
            }
            UserBeen this_user = new UserBeen(id,email,password);
            userBeenDao.insert(this_user);

            Toast.makeText(getApplicationContext(),"您的账号："+email+"注册成功",Toast.LENGTH_LONG).show();
            setResult(1);
            finish();

        }
    }

    private boolean isPasswordMatch(String password, String againpassword) {
        if (password.equals(againpassword)){
            return true;
        }
        return false;
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }

    private boolean isEmailinDb(String email) {
        for (int i=0;i<usercount;i++){
            if (userBeenList.get(i).getEmail().equals(email)){
                return true;
            }
        }
        return false;

    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }
}
