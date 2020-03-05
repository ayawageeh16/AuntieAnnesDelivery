package com.simpletouch.auntieannesdelivery.view.view.user.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.simpletouch.auntieannesdelivery.R;
import com.simpletouch.auntieannesdelivery.databinding.ActivityLoginBinding;
import com.simpletouch.auntieannesdelivery.view.listener.LoginEventListener;
import com.simpletouch.auntieannesdelivery.view.utils.ViewUtils;
import com.simpletouch.auntieannesdelivery.view.view.MainActivity;
import com.simpletouch.auntieannesdelivery.view.view.user.forgetpassword.ForgetPasswordActivity;
import com.simpletouch.business.network.NetworkUtils;

public class LoginActivity extends AppCompatActivity implements LoginEventListener {


    private LoginViewModel loginViewModel;
    private ActivityLoginBinding dataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        init();
    }

    private void init() {
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        dataBinding.setLoginViewModel(loginViewModel);
        dataBinding.setLoginEventListener(this);
    }

    @Override
    public void onSignInButtonClicked() {
        if (loginViewModel.isLoginDataValid()) {
            loginRequest();
        }
    }

    @Override
    public void onForgetPasswordClicked() {
        Intent intent = new Intent(this, ForgetPasswordActivity.class);
        startActivity(intent);
    }

    /**
     * Login API Call Request
     */
    private void loginRequest() {
        if(NetworkUtils.isOnline(this)){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            //TODO --> Make Login API Call
            //loginViewModel.login();
        }else {
            ViewUtils.showToast(this, getString(R.string.no_connection), Toast.LENGTH_SHORT);
        }
    }

}
