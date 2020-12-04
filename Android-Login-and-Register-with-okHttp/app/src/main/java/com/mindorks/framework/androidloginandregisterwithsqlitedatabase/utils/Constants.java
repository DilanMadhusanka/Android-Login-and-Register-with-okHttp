package com.mindorks.framework.androidloginandregisterwithsqlitedatabase.utils;

public class Constants {

    public static String BASE_URL = "http://localhost:8080";
    public static String PHARMACY_DEMO_HOST =BASE_URL+"/api/";
    public static String PHARMACY_SHARE_LINK ="/public-pharmacy-view";


    public static String PHARMACY_PREFS = "pharmacy_prefs";
    public  static String USER_DATA = "user_data";
    public static String API_TOKEN ="api_token";
    public static String DEVICE_UNIQUE_TOKEN = "unique_token";

    public static int AUTH_SUCCESS_CODE = 200;
    public static int AUTH_CREATED_CODE = 201;
    public static int INTERNAL_SERVER_ERROR = 500;
    public static int NO_VALID_SUBSCRIPTION_PLAN = 1000;
}
