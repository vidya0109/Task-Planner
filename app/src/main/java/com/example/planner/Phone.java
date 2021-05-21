package com.example.planner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Phone extends AppCompatActivity {
    Button button;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

     editText = findViewById(R.id.subtask);
     button = findViewById(R.id.btnSaveTask);

     button.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             String phone = editText.getText().toString();
             if (phone.equals("")){

                 editText.setError("Enter your number");
                 editText.requestFocus();

             }
             else {
                 Intent i = new Intent(Phone.this, Login.class);
                 i.putExtra("phone", phone);
                 startActivity(i);
                 finish();
             }
         }
     });


    }
}