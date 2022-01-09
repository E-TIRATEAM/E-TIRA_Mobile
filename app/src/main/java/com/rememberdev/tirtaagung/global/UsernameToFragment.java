package com.rememberdev.tirtaagung.global;

public class UsernameToFragment {
    private static String username;
    private static String nomorHP;

    public static String getNomorHP() {
        return nomorHP;
    }

    public static void setNomorHP(String nomorHP) {
        UsernameToFragment.nomorHP = nomorHP;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        UsernameToFragment.username = username;
    }
}
