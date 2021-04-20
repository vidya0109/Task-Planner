package com.example.planner;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    Context context;
    ArrayList<Task> task;

    public MyAdapter(Context c, ArrayList<Task> p) {
        context = c;
        task = p;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_does,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {


        String currentdate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
        Date date1;
        Date date2;
        SimpleDateFormat dates = new SimpleDateFormat("MM/dd/yyyy");
        try {
            final String getDeadline = task.get(i).getDeadline();
            date1 = dates.parse(currentdate);
            date2 = dates.parse(getDeadline);
            long difference = Math.abs(date1.getTime() - date2.getTime());
            long differenceDates = difference / (24 * 60 * 60 * 1000);
            String dayDifference = Long.toString(differenceDates);
            myViewHolder.titledoes.setText(task.get(i).getTitle());
            myViewHolder.datedoes.setText(dayDifference +" days left");


        } catch (ParseException e) {
            e.printStackTrace();
        }





    }
    @Override

    public int getItemCount() {
        return task.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titledoes,datedoes;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titledoes = (TextView) itemView.findViewById(R.id.titledoes);
            datedoes = (TextView)itemView.findViewById(R.id.datedoes);


        }
    }

}
