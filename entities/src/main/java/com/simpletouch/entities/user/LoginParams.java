package com.simpletouch.entities.user;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LoginParams extends BaseObservable {

    private String phoneNumber;
    private String password;

    @Bindable
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        notifyPropertyChanged(com.simpletouch.entities.BR.phoneNumber);
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(com.simpletouch.entities.BR.password);
    }
}
