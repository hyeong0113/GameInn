package com.cmpt276.gameinn.constant;

import com.cmpt276.gameinn.wrapper.UserWrapper;

public final class UserInfo {
    public static String USER_SUB = "sub";

    private static String sub;

    private static UserWrapper wrapper;

    public static String getSub() {
        return sub;
    }

    public static void setSub(String subInput) {
        sub = subInput; 
    }

    public static UserWrapper getWrapper() {
        return wrapper;
    }

    public static void setWrapper(UserWrapper wrapperInput) {
        wrapper = wrapperInput; 
    }

    public static void deleteUserInfo() {
        sub = null;
        wrapper = null;
    }
}
