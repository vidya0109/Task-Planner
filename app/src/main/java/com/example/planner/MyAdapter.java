package com.example.planner;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    Context context;
    ArrayList<Task2> task;

    public MyAdapter(Context c, ArrayList<Task2> p) {
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
            long difference = date1.getTime() - date2.getTime();
            long differenceDates = difference / (24 * 60 * 60 * 1000);
            if (differenceDates<0){

                long dif = Math.abs(date1.getTime() - date2.getTime());
                long diff2 = dif / (24 * 60 * 60 * 1000);

                if (diff2<=3){

                    Intent intent = new Intent(context, AlarmReceiver.class);
                    intent.putExtra("notificationId", task.get(i).getTitle());
                    intent.putExtra("message", diff2);

                    // PendingIntent
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(
                            context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT
                    );


                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    Calendar startTime = Calendar.getInstance();
                    startTime.set(Calendar.HOUR_OF_DAY, 20);
                    startTime.set(Calendar.MINUTE, 0);
                    startTime.set(Calendar.SECOND, 0);
                    long alarmStartTime = startTime.getTimeInMillis();

                    // Set Alarm
                    alarmManager.set(AlarmManager.RTC_WAKEUP, alarmStartTime, pendingIntent);



                }

                String dayDifference = Long.toString(diff2);
                myViewHolder.titledoes.setText(task.get(i).getTitle());
                myViewHolder.datedoes.setText(dayDifference +" days left");
                myViewHolder.plot.setText(task.get(i).getSubtask());


            }
            else if (difference==0){

                Toast.makeText(context,"Due today "+task.get(i).getTitle(),Toast.LENGTH_LONG).show();
                myViewHolder.titledoes.setText(task.get(i).getTitle());
                myViewHolder.datedoes.setText("Due today");

                myViewHolder.plot.setText(task.get(i).getSubtask());


            }
            else {

                String dayDifference = Long.toString(differenceDates);
                myViewHolder.titledoes.setText(task.get(i).getTitle());
                myViewHolder.datedoes.setText(dayDifference + " days late");
                myViewHolder.plot.setText(task.get(i).getSubtask());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        boolean isExpanded = task.get(i).isExpanded();
        myViewHolder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);






    }
    @Override

    public int getItemCount() {

        return task.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titledoes,datedoes,plot;

        ConstraintLayout expandableLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titledoes = (TextView) itemView.findViewById(R.id.titledoes);
            datedoes = (TextView)itemView.findViewById(R.id.datedoes);
            plot = itemView.findViewById(R.id.plotTextView);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);

            titledoes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Task2 h = task.get(getAdapterPosition());
                    h.setExpanded(!h.isExpanded());
                    notifyItemChanged(getAdapterPosition());

                }
            });

        }
    }

}
