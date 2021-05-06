// Created by Vijaya Yeruva on 11/20/2020
// Reference: https://developer.android.com/guide/topics/ui/notifiers/notifications

package com.example.notification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    // notificationId is a unique int for each notification that you must define
    public int notificationId = 1;
    public String CHANNEL_ID = "one";
    Button add;
    CalendarView calendarView;
    int selectedYear;
    int selectedMonth;
    int selectedDay;
    TextView display;
    TextView selectedDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendarView = (CalendarView) findViewById(R.id.simpleCalendarView);
        display = (TextView) findViewById(R.id.headText);
        selectedDate = (TextView) findViewById(R.id.dateText);
        add = (Button) findViewById(R.id.button);

        // Set the date to today in the format Month/Day/Year
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String today = dateFormat.format(calendarView.getDate());
        selectedDate.setText(today);

        // Listen for click on a different date
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView,
                                            int year, int month, int day) {
                // Save the date as variables
                selectedDay = day;
                selectedMonth = month;
                selectedYear = year;

                // Change the header and the date to selected date
                String head = "Selected date: ";
                String date = ((month+1) + "/" + day + "/" + year);
                display.setText(head);
                selectedDate.setText(date);
            }
        });

        // Listen for click on the add button
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Execute insert function
                insert();
            }
        });
    }

    // Create a new instance of an event
    public void insert() {
        Intent intent = new Intent(Intent.ACTION_INSERT,
                CalendarContract.Events.CONTENT_URI);
        // Add the calendar event details
        intent.putExtra(CalendarContract.Events.TITLE, "Launch!");
        intent.putExtra(CalendarContract.Events.DESCRIPTION,
                "Learn Java Android Coding");
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION,
                "UMKC.com");
        Calendar startTime = Calendar.getInstance();
        startTime.set(selectedYear, selectedMonth, selectedDay);
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                startTime.getTimeInMillis());
        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
        // Use the Calendar app to add the new event.
        startActivity(intent);
    }

    public void sendNotification(View view) {
        // Set the notification's tap action
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, AlertDetails.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // Create a basic notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "one");
        builder.setSmallIcon(R.drawable.notification_icon);
        builder.setContentTitle("Message");
        builder.setContentText("This is an example for notification app");
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText("This is an example for notification app"));
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);

        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, builder.build());
    }
}