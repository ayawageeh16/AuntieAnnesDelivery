package com.simpletouch.auntieannesdelivery.view.view.user.login;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.simpletouch.auntieannesdelivery.view.view.MainActivity;
import com.simpletouch.business.helper.UserInputValidation;
import com.simpletouch.business.usecases.UserUseCase;
import com.simpletouch.entities.user.LoginParams;

public class LoginViewModel extends AndroidViewModel {

    public LoginParams loginParams;
    public UserInputValidation userInputValidation;

    private UserUseCase userUseCase;
    private Application application;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        this.userInputValidation = new UserInputValidation();
        loginParams = new LoginParams();
    }

    public boolean isLoginDataValid() {
        return userInputValidation.isLoginDataValid(loginParams);
    }

    public void login() {

    }
}
