package com.example.planner;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    TextView titlepage, subtitlepage;
    RecyclerView ourdoes;
    MyAdapter adapter;
    FirebaseFirestore firebaseFirestore;
    ArrayList<Task> list ;
    Integer doesNum = new Random().nextInt();
    String keydoes = Integer.toString(doesNum);
    EditText editText,editText2;
    Button btn,btn2;
    public void NewTask(View view){

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final View customLayout = getLayoutInflater().inflate(R.layout.custom_layout, null);
        builder.setView(customLayout);

        btn = customLayout.findViewById(R.id.btnSaveTask);
        editText = customLayout.findViewById(R.id.titledoes);
        editText2 = customLayout.findViewById(R.id.datedoes);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseFirestore = FirebaseFirestore.getInstance();
                CollectionReference reference = firebaseFirestore.collection("Tasks");
                final Task t = new Task(editText.getText().toString(),editText2.getText().toString());
                reference.add(t).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        list.add(t);
                        Toast.makeText(getApplicationContext(),"Succesfully added your task",Toast.LENGTH_LONG).show();
                        editText.setText("");
                        editText2.setText("");

                        adapter = new MyAdapter(MainActivity.this, list);
                        ourdoes.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }
                });

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();








    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titlepage = findViewById(R.id.titlepage);
        subtitlepage = findViewById(R.id.subtitlepage);


        btn2 = findViewById(R.id.btnAddNew);
        Typeface MLight = Typeface.createFromAsset(getAssets(), "ML.ttf");
        Typeface MMedium = Typeface.createFromAsset(getAssets(), "MM.ttf");
        titlepage.setTypeface(MMedium);
        subtitlepage.setTypeface(MLight);


        ourdoes = findViewById(R.id.ourdoes);
        ourdoes.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<Task>();
        adapter = new MyAdapter(MainActivity.this, list);
        ourdoes.setAdapter(adapter);
     firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("Tasks").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        List<DocumentSnapshot> list2 = queryDocumentSnapshots.getDocuments();

                        for (DocumentSnapshot d : list2) {

                            Task p =d.toObject(Task.class);
                            list.add(p);
                        }


                        adapter.notifyDataSetChanged();

                    }
                });
}
}


