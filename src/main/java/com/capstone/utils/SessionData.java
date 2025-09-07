package com.capstone.utils;

/**
 * Stores runtime session data so multiple steps can share info.
 */
public class SessionData {
    private static String registeredUser;
    private static String registeredPass;
    private static String primaryAccount;

    public static void setRegisteredUser(String user) {
        registeredUser = user;
    }

    public static String getRegisteredUser() {
        return registeredUser;
    }

    public static void setRegisteredPass(String pass) {
        registeredPass = pass;
    }

    public static String getRegisteredPass() {
        return registeredPass;
    }

    public static void setPrimaryAccount(String acc) {
        primaryAccount = acc;
    }

    public static String getPrimaryAccount() {
        return primaryAccount;
    }
}
