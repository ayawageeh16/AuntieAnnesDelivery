package com.simpletouch.business.helper;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.simpletouch.business.BR;
import com.simpletouch.business.R;
import com.simpletouch.entities.user.LoginParams;

public class UserInputValidation extends BaseObservable {

    private ErrorFields errorFields;

    //Entities

    public UserInputValidation() {
        this.errorFields = new ErrorFields();
    }

    public boolean isLoginDataValid(LoginParams loginParams) {
        boolean isValid = isPhoneNumberValid(loginParams.getPhoneNumber());
        isValid = isPasswordValid(loginParams.getPassword()) && isValid;
        return isValid;
    }

    /**
     * Validation Method That Validate That Entered Phone Number Not Empty And In The Valid Format
     *
     * @param phoneNumber
     * @return
     */
    private boolean isPhoneNumberValid(String phoneNumber) {
        // Check if phone number empty
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            errorFields.setPhoneNumberError(R.string.this_field_is_required);
            errorFields.setError(true);
            notifyPropertyChanged(com.simpletouch.business.BR.phoneNumberError);
            notifyPropertyChanged(com.simpletouch.business.BR.isError);
            return false;
            // Check if phone number format is invalid
        } else if (!InputValidationUtils.isPhoneValid(phoneNumber)) {
            errorFields.setPhoneNumberError(R.string.invalid_phone_number);
            errorFields.setError(true);
            notifyPropertyChanged(com.simpletouch.business.BR.phoneNumberError);
            notifyPropertyChanged(com.simpletouch.business.BR.isError);
            return false;
        } else {
            errorFields.setError(false);
            notifyPropertyChanged(com.simpletouch.business.BR.isError);
            return true;
        }
    }


    /**
     * Validation Method That Validate That Entered Password Not Empty And Between 3 And 10 Characters
     *
     * @param password
     * @return
     */
    private boolean isPasswordValid(String password) {
        // Check if password is empty
        if (password == null || password.isEmpty()) {
            errorFields.setPasswordError(R.string.this_field_is_required);
            errorFields.setError(true);
            notifyPropertyChanged(com.simpletouch.business.BR.passwordError);
            notifyPropertyChanged(com.simpletouch.business.BR.isError);
            return false;
            // Check if password length is between 3 and 10 characters
        } else if (!InputValidationUtils.isPasswordValid(password)) {
            errorFields.setPasswordError(R.string.password_error);
            errorFields.setError(true);
            notifyPropertyChanged(com.simpletouch.business.BR.passwordError);
            notifyPropertyChanged(com.simpletouch.business.BR.isError);
            return false;
        } else {
            errorFields.setError(false);
            notifyPropertyChanged(com.simpletouch.business.BR.isError);
            return true;
        }
    }


    // **** Binding ErrorFields Object Attributes **** //
    @Bindable
    public Integer getPhoneNumberError() {
        return errorFields.getPhoneNumberError();
    }

    @Bindable
    public Integer getPasswordError() {
        return errorFields.getPasswordError();
    }

    @Bindable
    public boolean getIsError() {
        return errorFields.isError();
    }
}
