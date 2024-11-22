package com.example.fat_boy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    ConnectionDatabase connection;
    Connection con;
    EditText login,password;
    Button enter;
    TextView LoginText,PasswordText;
    String Login,Password;
    boolean checkSingIn;
    ResultSet rs;

    String name, str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            connection =new ConnectionDatabase();
            connect();
            login = (EditText) findViewById(R.id.LoginInput);
            password = (EditText) findViewById(R.id.PasswordInput);
            enter = (Button) findViewById(R.id.button);
            LoginText = (TextView) findViewById(R.id.text);
            PasswordText = (TextView) findViewById(R.id.text1);








            enter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ExecutorService executorService = Executors.newSingleThreadExecutor();
                    executorService.execute(() ->{
                        try {
                            con =connection.conclass();
                            String h = login.getText().toString();
                            String query = "SELECT * FROM `users` WHERE login = '" + login.getText().toString() +"'"+"AND pass = '"+ password.getText().toString()+"'";

                            PreparedStatement stmt = con.prepareStatement(query);
                            rs = stmt.executeQuery();

                            StringBuilder login_info = new StringBuilder();
                            StringBuilder password_info = new StringBuilder();


                            checkSingIn = (rs.isBeforeFirst());
                            if (checkSingIn){
                                Intent intent = new Intent(MainActivity.this, QR_CodeScaner.class);
                                startActivity(intent);
                            }
                            while (rs.next()){

                                login_info.append(rs.getString("login"));
                                password_info.append(rs.getString("pass"));
                            }

                            Login = login_info.toString();//просто тесты
                            Password = password_info.toString();//просто тесты
                        }
                        catch (SQLException e){
                            throw new RuntimeException(e);
                        }
                        runOnUiThread(()->{
                            try {
                                Thread.sleep(1000);
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }

                            LoginText.setText(Login);//просто тесты
                            PasswordText.setText(Password);//просто тесты
                        });


                });

            }

        });

            return insets;
                });
    }
    /*public void info(){
        try {


        }
        catch ()*/

    public void connect(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() ->{
            try {
                con = connection.conclass();
                if (con==null){
                    str = "Error";
                }else {
                    str = "con";
                }
            }
            catch (Exception e){
                throw new RuntimeException(e);
            }
            runOnUiThread(()->{
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                Toast.makeText(this,str,Toast.LENGTH_SHORT).show();

            });
        });
    }

}