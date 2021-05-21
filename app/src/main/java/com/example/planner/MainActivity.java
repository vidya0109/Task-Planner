package com.example.planner;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity {


    TextView titlepage, subtitlepage;
    RecyclerView ourdoes;
    MyAdapter adapter;
    FirebaseFirestore firebaseFirestore;
    ArrayList<Task2> list ;
    Button btn2;
    Task2 h;
    ItemTouchHelper.SimpleCallback simpleCallback;
    FirebaseAuth firebaseAuth;
    String phone;
    ProgressBar progressBar;
    FloatingActionButton fab;
    public void logout(View view){

        firebaseAuth.signOut();
        sendtoLogin();


    }
    public void NewTask(View view){

        Intent i = new Intent(MainActivity.this,NewTask.class);
        i.putExtra("phone",firebaseAuth.getUid());
        startActivity(i);
        finish();

    }
    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user==null || firebaseAuth.getUid()==null){

            sendtoLogin();
        }

    }
    public void sendtoLogin(){

        Intent intent = new Intent(MainActivity.this,Phone.class);
        startActivity(intent);
        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        firebaseAuth = FirebaseAuth.getInstance();
        phone = getIntent().getStringExtra("Phone");
        titlepage = findViewById(R.id.titlepage);
        subtitlepage = findViewById(R.id.subtitlepage);
        btn2 = findViewById(R.id.btnAddNew);
        Typeface MLight = Typeface.createFromAsset(getAssets(), "ML.ttf");
        Typeface MMedium = Typeface.createFromAsset(getAssets(), "MM.ttf");
        titlepage.setTypeface(MMedium);
        subtitlepage.setTypeface(MLight);
        ourdoes = findViewById(R.id.ourdoes);
        ourdoes.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<Task2>();
        adapter = new MyAdapter(MainActivity.this, list);
        ourdoes.setAdapter(adapter);


        if (firebaseAuth.getUid()!=null) {
               Retrieve();
        }


        simpleCallback  = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                int position = viewHolder.getAdapterPosition();

                h = list.get(position);
                switch (direction){

                    case ItemTouchHelper.LEFT :

                        list.remove(position);
                        adapter.notifyItemRemoved(position);
                        Snackbar.make(ourdoes , "Deeleted" , BaseTransientBottomBar.LENGTH_LONG);
                        firebaseFirestore = FirebaseFirestore.getInstance();
                        CollectionReference ref = firebaseFirestore.collection("Tasks");
                      ref.document(firebaseAuth.getUid()).collection("Notes").document(h.getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {

                                    Toast.makeText(MainActivity.this, "Task has been succesfully deleted.", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });



                        break;


                    case ItemTouchHelper.RIGHT :
                        Intent i = new Intent(MainActivity.this,EditTask.class);
                        i.putExtra("LIST", (Serializable) list);
                        i.putExtra("Pos" , position);
                        startActivity(i);
                        finish();
                        break;


                }


            }

         @Override
         public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

             new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                     .addSwipeLeftBackgroundColor(ContextCompat.getColor(MainActivity.this , R.color.colorAccent))
                     .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                     .addSwipeLeftLabel("Delete")
                     .addSwipeRightBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.colorPrimary))
                     .addSwipeRightActionIcon(R.drawable.ic_baseline_edit_24)
                     .addSwipeRightLabel("Edit")
                     .create()
                     .decorate();

             super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
         }
     };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(ourdoes);


}

public void Retrieve(){


        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("Tasks").document(firebaseAuth.getUid()).collection("Notes").get()
            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                    List<DocumentSnapshot> list2 = queryDocumentSnapshots.getDocuments();

                    for (DocumentSnapshot d : list2) {

                        Task2 p =d.toObject(Task2.class);
                        p.setId(d.getId());
                        list.add(p);
                    }


                    adapter.notifyDataSetChanged();


                }
            });



}


}


