package com.example.connectapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private CheckBox checkBoxGame, checkBoxWeb;
    private EditText editTextMessage;
    private TextView textViewCount;
    private Button btnViewResult;
    private int registrationCount = 0;
    private ArrayList<String> registrations = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spinner = findViewById(R.id.spinner);
        checkBoxGame = findViewById(R.id.checkBoxGame);
        checkBoxWeb = findViewById(R.id.checkboxWeb);
        editTextMessage = findViewById(R.id.editTextMessage);
        textViewCount = findViewById(R.id.textViewCount);
        btnViewResult = findViewById(R.id.btnViewResult);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.forum_array,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        btnViewResult.setOnClickListener(v -> showResults());

    }

    private void showResults() {
        if (registrationCount == 0) {
            Toast.makeText(this, "Chưa có đăng ký nào!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        intent.putStringArrayListExtra("registrations", registrations);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_add) {
            handleAdd();
            return true;
        } else if (id == R.id.menu_save) {
            handleSave();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }



    private void handleAdd() {
        String message = editTextMessage.getText().toString();
        boolean hasSelected = checkBoxGame.isChecked() || checkBoxWeb.isChecked();

        if (!message.isEmpty() && hasSelected && spinner.getSelectedItem() != null) {
            registrationCount++;
            textViewCount.setText("Số lần đăng ký: " + registrationCount);

            String selectedForum = spinner.getSelectedItem().toString();
            String hobbies = (checkBoxGame.isChecked() ? "Game" : "") + (checkBoxWeb.isChecked() ? " Lướt web" : "");
            String registrationInfo = "Đăng ký " + registrationCount + ": Diễn đàn - " + selectedForum + "; Sở thích: " + hobbies + "; Lời nhắn: " + message;
            registrations.add(registrationInfo);

            Toast.makeText(this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleSave() {
        String filename = "filedangki.txt";
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            for (String registration : registrations) {
                outputStream.write((registration + "\n").getBytes());
            }

            outputStream.close();
            Toast.makeText(this, "Dữ liệu đã được lưu!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}