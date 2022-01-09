package com.rememberdev.tirtaagung.global;

public class GlobalVariabel {

    /**localhost / XAMPP*/
//    private static String globalUrlServer = "http://192.168.43.114/tirtaagung/";

    /**Hosting WS-TIF*/
    private static String globalUrlServer = "https://ws-tif.com/tirta-agung/rest-api/";

//    private static String globalUrlServer = "http://wisatatirtaagung.unaux.com/";
//    private static String globalUrlServer = "https://reciprocal-opposite.000webhostapp.com/tirtaagung/";

    public static String getGlobalUrlServer() {
        return globalUrlServer;
    }

    public static void setGlobalUrlServer(String globalUrlServer) {
        GlobalVariabel.globalUrlServer = globalUrlServer;
    }
}
