package com.example.fat_boy;
import android.annotation.SuppressLint;
import android.os.StrictMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Objects;

import android.util.Log;

import kotlin.Suppress;

public class ConnectionDatabase  {
    protected static String db = "warehouse";
    protected static String ip = "10.0.2.2";
    protected static String port = "3306";
    protected static String username = "root";
    protected static String password = "root";
    public Connection conclass() {
        Connection con = null;



        StrictMode.ThreadPolicy a = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(a);
        String ConnectURL = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            ConnectURL =  "jdbc:mysql://"+ ip + ":"+ port +"/" + db;
             con = DriverManager.getConnection(ConnectURL,username,password);

        } catch (Exception e) {
            Log.e("eror", Objects.requireNonNull(e.getMessage()));
        }
        return con;
    }
}

