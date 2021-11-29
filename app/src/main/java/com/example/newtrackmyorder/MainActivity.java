package com.example.newtrackmyorder;

import static com.example.newtrackmyorder.R.id.user_login_button;

import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.newtrackmyorder.util.Constants;

public class MainActivity extends AppCompatActivity {

    Button userButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userButton = (Button) findViewById(R.id.user_login_button);
        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.putExtra(Constants.LOGINTYPE, "U");
                startActivity(intent);
            }
        });
    }

    private void setContentView(int activity_main) {
    }

    private void startActivity(Intent intent) {
    }

    private void findViewById(int user_login_button) {
    }

    public void deliveryBoy(View v) {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.putExtra(Constants.LOGINTYPE, "D");
        startActivity(intent);
    }
}
