package com.simpletouch.business.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class InputValidationUtils {


    public InputValidationUtils() {
    }

    public static final String PHONE_PATTERN = "(01)?[0-9]{11}";

    public static boolean isEmpty(String input) {
        return input.trim().isEmpty();
    }

    public static boolean isPasswordValid(String password) {
        return password.trim().length() >= 3 && password.trim().length() <= 10;
    }

    public static boolean isPhoneValid(String phone) {
        final Pattern pattern = Pattern.compile(PHONE_PATTERN);
        final Matcher matcher = pattern.matcher(phone.trim());
        return matcher.matches();
    }
}
