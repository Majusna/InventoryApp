package com.example.android.inventory;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.inventory.data.OrdersContract.OrdersEntry;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_layout);

        CalendarView calendarView = findViewById(R.id.calendar_view);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month , int day) {
                String date = day + " - " + (month+1) + " - " + year;

                Log.e("CalendarActivity", "day,month,year" +date );

                Intent intent = new Intent(CalendarActivity.this,EditActivity.class);
                intent.putExtra("date", date);
                startActivity(intent);
                finish();



            }
        });
    }
}
