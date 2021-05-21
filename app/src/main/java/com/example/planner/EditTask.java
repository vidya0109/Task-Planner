package com.example.planner;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class EditTask extends AppCompatActivity {
Button btn;
EditText editText,editText2,editText3;
FirebaseFirestore firebaseFirestore;
Task2 h;
FirebaseAuth f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        btn = findViewById(R.id.btnSaveTask);
        editText = findViewById(R.id.titledoes);
        editText2  = findViewById(R.id.datedoes);
        editText3 = findViewById(R.id.subtask);
        int pos = getIntent().getIntExtra("Pos",0);
        List<Task2> list = (List<Task2>) getIntent().getSerializableExtra("LIST");
        f = FirebaseAuth.getInstance();
        h = list.get(pos);
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
                    CollectionReference reference = firebaseFirestore.collection("Tasks").document(f.getUid()).collection("Notes");
                    final Task2 t = new Task2(editText.getText().toString(), editText2.getText().toString(),editText3.getText().toString());
                    reference.document(h.getId()).set(t).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Toast.makeText(EditTask.this, "Task has been updated..", Toast.LENGTH_SHORT).show();
                            editText.setText("");
                            editText2.setText("");
                            editText3.setText("");
                            Intent i = new Intent(EditTask.this, MainActivity.class);
                            startActivity(i);
                            finish();


                        }
                    });

                }


            }
        });






    }
    public void onBackPressed(){

        Intent i = new Intent(EditTask.this, MainActivity.class);
        startActivity(i);
        finish();

    }
}