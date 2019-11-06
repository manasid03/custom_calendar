package com.demo;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.model.Events;
import com.model.MonthDates;
import com.customcalendar.CustomCalendarView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import static com.demo.CustomizeCalendar.CUSTOM;
import static com.demo.CustomizeCalendar.EXTRA_BLOCK_SIZE;
import static com.demo.CustomizeCalendar.EXTRA_CONFIG;
import static com.demo.CustomizeCalendar.EXTRA_DATE_CLICK;
import static com.demo.CustomizeCalendar.EXTRA_DATE_FONT;
import static com.demo.CustomizeCalendar.EXTRA_DATE_TEXT;
import static com.demo.CustomizeCalendar.EXTRA_DAY_FONT;
import static com.demo.CustomizeCalendar.EXTRA_DAY_TEXT;
import static com.demo.CustomizeCalendar.EXTRA_RECYCLERVIEW;

public class MainActivity extends AppCompatActivity implements CustomCalendarView.DateClickListener, CustomCalendarView.EventClickListener {

    CustomCalendarView calendarView;
    ArrayList<Events> events = new ArrayList<Events>();
    Intent data = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void getIntentData() {
        data = getIntent();

        if(data.hasExtra(EXTRA_CONFIG)){

                if(data.getStringExtra(EXTRA_CONFIG).equals(CUSTOM)){

                    String dayFont = data.getStringExtra(EXTRA_DAY_FONT);
                    String dateFont = data.getStringExtra(EXTRA_DATE_FONT);
                    int daySize = data.getIntExtra(EXTRA_DAY_TEXT,12);
                    int dateSize = data.getIntExtra(EXTRA_DATE_TEXT,12);
                    int blockSize = data.getIntExtra(EXTRA_BLOCK_SIZE,25);
                    //int calHeight = data.getIntExtra(EXTRA_CAL_HEIGHT,200);
                    Drawable dateSelector = getResources().getDrawable(R.drawable.selected_date_round);
                    Drawable disabledDateSelector = getResources().getDrawable(R.drawable.disabled_date_round);
                    Drawable eventDrawable = getResources().getDrawable(R.drawable.ic_event1);

                    /*DrawableCompat.setTint(
                            DrawableCompat.wrap(eventDrawable),
                            ContextCompat.getColor(MainActivity.this, R.color.grey)
                    );
                    */

                    int selectedDateColor = getResources().getColor(R.color.colorPrimary);

                    boolean showRecyclerView = false, dateClick = false;
                    if(data.getStringExtra(EXTRA_RECYCLERVIEW).equals("Yes")){
                        showRecyclerView = true;
                    }else {
                        showRecyclerView = false;
                    }

                    if(data.getStringExtra(EXTRA_DATE_CLICK).equals("Yes")){
                        dateClick = true;
                    }else {
                        dateClick = false;
                    }

                    calendarView.setDayFont(dayFont)
                            .setDateFont(dateFont)
                            .setOldMonthDateClick(dateClick)
                            .setDateSelectorBackground(dateSelector)
                            .setDisabledDateSelectorBackground(disabledDateSelector)
                            .setEventDrawable(eventDrawable)
                            .setDateTextSize(dateSize)
                            .setDaysTextSize(daySize)
                            .setLayoutParameters(blockSize,blockSize)
                            .showDemoRecyclerView(true)
                            .setSelectedDateColor(selectedDateColor)
                            .showEvents(showRecyclerView)
                            .build();
                            //.setCalendarHeight(calHeight)

                    setEvents();
                    //calendarView.removeAllEvents();
                }else{
                    setDefaultConfig();
                }
            }
    }

    private void setDefaultConfig() {
        calendarView.setDayFont("fonts/Questrial-Regular.ttf")
                    .setDateFont("fonts/Questrial-Regular.ttf")
                    .setOldMonthDateClick(true)
                    .setDateSelectorBackground(getResources().getDrawable(R.drawable.selected_date_square))
                    .setDisabledDateSelectorBackground(getResources().getDrawable(R.drawable.disabled_dates_square))
                    .setDateTextSize(12)
                    .setDaysTextSize(12)
                    .setLayoutParameters(25,25)
                    .setCalendarHeight(200)
                    .showDemoRecyclerView(true)
                    .setSelectedDateColor(getResources().getColor(R.color.colorPrimary))
                    .showEvents(false)
                    .build();

        setEvents();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,1);
        Date date = calendar.getTime();
        //calendarView.removeSpecificEvent(new Events(date,true,"A Event "+2));
    }

    private void initViews() {

        getSupportActionBar().setTitle("Custom Calendar");
        calendarView = findViewById(R.id.mCalendar);
        calendarView.setOnDateClickListener(this);
        calendarView.setOnEventClickListener(this);
        getIntentData();
    }


    public void setEvents(){

        for(int i = 1; i<3; i++){
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE,i);
            Date date = calendar.getTime();
            events.add(new Events(date,true,"A Event "+i));
            events.add(new Events(date,true,"B Event "+i));

        }
        calendarView.setEvents(events);
    }
    @Override
    public void onDateClickListener(int position, View view, MonthDates dates) {

        //Toast.makeText(MainActivity.this,dates.toString(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onEventClickListener(int position, View view, Events event) {
        Toast.makeText(MainActivity.this,"You clicked "+event.getTitle(),Toast.LENGTH_LONG).show();
    }
}
