package com.example.connectapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    private ListView listView;
    private Button btnBack, btnSendRegistration;
    private ArrayList<String> registrations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        listView = findViewById(R.id.listView);
        btnBack = findViewById(R.id.btnBack);

        btnSendRegistration = findViewById(R.id.btnSendRegistration);

        registrations = getIntent().getStringArrayListExtra("registrations");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, registrations);

        listView.setAdapter(adapter);

//        btnBack.setOnClickListener(v -> {
//            Intent intent = new Intent(MainActivity2.this, MainActivity.class);
//            startActivity(intent);
//        });

        btnBack.setOnClickListener(v -> finish());

        btnSendRegistration.setOnClickListener(v -> sendEmail());

    }

    private void sendEmail() {
        String email = "ktpm2@haui.edu.vn";
        String subject = "Đăng kí kết bạn";
        String message = "Các diễn đàn - sở thích - lời nhắn:\n";

        for (String reg : registrations) {
            message += reg + "\n";
        }

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);

        emailIntent.setPackage("com.google.android.gm");

        try {
            startActivity(Intent.createChooser(emailIntent, "Gửi email..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity2.this, "Không có ứng dụng Email nào được cài đặt.", Toast.LENGTH_SHORT).show();
        }
    }
}