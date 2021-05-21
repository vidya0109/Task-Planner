package com.example.planner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class NewTask extends AppCompatActivity {
EditText editText ,editText2,editText3;
Button btn;
FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        final String phone = getIntent().getStringExtra("phone");
        btn = findViewById(R.id.btnSaveTask);
        editText = findViewById(R.id.titledoes);
        editText2 = findViewById(R.id.datedoes);
        editText3 = findViewById(R.id.subtask);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().equals("")){

                    editText.setError("Please enter your task");
                    editText.requestFocus();
                }
                if (editText2.getText().toString().equals("")){

                    editText2.setError("Please enter your deadline");
                    editText2.requestFocus();

                }
                if (editText3.getText().toString().equals("")){

                    editText3.setError("Please enter your deadline");
                    editText3.requestFocus();

                }
                else {
                    firebaseFirestore = FirebaseFirestore.getInstance();
                    CollectionReference reference = firebaseFirestore.collection("Tasks").document(phone).collection("Notes");
                    final Task2 t = new Task2(editText.getText().toString(), editText2.getText().toString(),editText3.getText().toString());
                    reference.add(t).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getApplicationContext(), "Succesfully added your task", Toast.LENGTH_LONG).show();
                            editText.setText("");
                            editText2.setText("");
                            editText3.setText("");

                            Intent i = new Intent(NewTask.this, MainActivity.class);
                            startActivity(i);
                            finish();

                        }
                    });
                }
            }
        });

    }
    public void onBackPressed(){

        Intent i = new Intent(NewTask.this, MainActivity.class);
        startActivity(i);
        finish();

    }
}