package com.customcalendar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.adapters.CustomCalendarAdapter;
import com.adapters.DataAdapter;
import com.swipegesture.OnSwipeTouchListener;
import com.model.Events;
import com.model.MonthDates;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

public class CustomCalendarView extends LinearLayout {

    Context context=null;
    LinearLayout header;
    Button btnToday;
    ImageView btnPrev;
    ImageView btnNext;
    RelativeLayout relativeLayout;
    GridView gridView;
    TextView D1,D2,D3,D4,D5,D6,D7,tv_date_selected,event_count;
    public static final int LAST_MONTH = -1;
    public static final int CURRENT_MONTH = 1;
    public static int DATE_CLICK_ENABLE = 1;
    public static int DATE_CLICK_DISABLE = 0;
    HashSet<Date> eventDays = new HashSet<>();
    CustomCalendarAdapter adapter = null;
    DateClickListener listener;
    int selectedDateColor = 0;
    //Date Arrays
    ArrayList<MonthDates> FinalDates = new ArrayList<>();
    public static int CALENDAR_OF_MONTH = 0;
    Calendar dateCalendar = null, currentMonthCal = null;
    Drawable background = null, disabledBackground = null, defaultDrawable = null, disabledDatesUserInput = null;
    private ArrayList<Events> events = null;
    boolean showRecyclerView = false;
    public static final String PREV_DATE_COLOR = "#8A8A8A";
    public static final String CURRENT_DATE_COLOR = "#000000";
    public String fontPath = null;
    EventClickListener eventListener = null;
    private boolean showEvents = false;

    public CustomCalendarView(Context context) {
        super(context);
        this.context = context;
        initLayout();
    }

    public CustomCalendarView(Context context, AttributeSet set) {
        super(context,set);
        this.context = context;
        initLayout();

    }

    private void initLayout() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.calendar_view,this);

        initViews();
    }

    private void initViews() {
        header = findViewById(R.id.calendar_header);
        btnPrev = findViewById(R.id.calendar_prev_button);
        btnNext = findViewById(R.id.calendar_next_button);
        btnToday = findViewById(R.id.bt_today);
        gridView = findViewById(R.id.calendar_grid);
        relativeLayout = findViewById(R.id.relative_layout);
        tv_date_selected = findViewById(R.id.tv_date_selected);
        event_count = findViewById(R.id.event_count);

        D1 = findViewById(R.id.d1);
        D2 = findViewById(R.id.d2);
        D3 = findViewById(R.id.d3);
        D4 = findViewById(R.id.d4);
        D5 = findViewById(R.id.d5);
        D6 = findViewById(R.id.d6);
        D7 = findViewById(R.id.d7);

        disabledBackground = getResources().getDrawable(R.drawable.disabled_dates_square);
        defaultDrawable = getResources().getDrawable(R.drawable.selected_date_round);

        adapter = new CustomCalendarAdapter(context, FinalDates, eventDays);

        setClickListeners();
        setTouchListeners();

    }



    private void setTouchListeners() {

        gridView.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            public void onSwipeTop() {
                //Toast.makeText(getContext(), "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                //Toast.makeText(getContext(), "right", Toast.LENGTH_SHORT).show();
                CALENDAR_OF_MONTH--;
                setDateText(CALENDAR_OF_MONTH,1);
                setCalendar(CALENDAR_OF_MONTH);
            }
            public void onSwipeLeft() {
                //Toast.makeText(getContext(), "left", Toast.LENGTH_SHORT).show();
                CALENDAR_OF_MONTH++;
                setDateText(CALENDAR_OF_MONTH,1);
                setCalendar(CALENDAR_OF_MONTH);
            }
            public void onSwipeBottom() {
                //Toast.makeText(getContext(), "bottom", Toast.LENGTH_SHORT).show();
            }

        });
    }

    public void setDateText(int month, int date){

        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.MONTH,month);
        mCalendar.set(Calendar.DATE,date);

        //mCalendar.set(Calendar.YEAR,dateCalendar.get(Calendar.YEAR));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat newSDF = new SimpleDateFormat("dd-MMM-yyyy");

        String inputDate = sdf.format(mCalendar.getTime());

        try {
            Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(inputDate);
            // String to date
            String newDate = newSDF.format(date1);
            tv_date_selected.setText(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }



    public void setSelectedDateText(int month, int date, int year){

        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.MONTH,month);
        mCalendar.set(Calendar.DATE,date);
        mCalendar.set(Calendar.YEAR,year);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat newSDF = new SimpleDateFormat("dd-MMM-yyyy");

        String inputDate = sdf.format(mCalendar.getTime());

        try {
            Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(inputDate);
            // String to date
            String newDate = newSDF.format(date1);
            tv_date_selected.setText(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setCurrentMonthCalendar(){
        Calendar mCalendar = Calendar.getInstance();
        CALENDAR_OF_MONTH = mCalendar.get(Calendar.MONTH);
        setDateText(CALENDAR_OF_MONTH,mCalendar.get(Calendar.DATE));
        setCalendar(CALENDAR_OF_MONTH);
    }

    private void setClickListeners() {
        btnPrev.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                CALENDAR_OF_MONTH--;
                setDateText(CALENDAR_OF_MONTH,1);
                setCalendar(CALENDAR_OF_MONTH);
            }
        });


        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                CALENDAR_OF_MONTH++;
                setDateText(CALENDAR_OF_MONTH,1);
                setCalendar(CALENDAR_OF_MONTH);
            }
        });

        btnToday.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setCurrentMonthCalendar();
            }
        });

    }

    public void setCalendar(int monthValue){

        adapter.clear();

        //current month
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.DATE,1);
        mCalendar.set(Calendar.MONTH,monthValue);

        // sun = 1, mon= 2, tues= 3
        int dayColumnNo = mCalendar.get(Calendar.DAY_OF_WEEK);
        int year = mCalendar.get(Calendar.YEAR);

        dateCalendar = mCalendar;

        //PREVIOUS MONTH
        int month = mCalendar.get(Calendar.MONTH);

        Calendar preCalendar = Calendar.getInstance();
        preCalendar.set(Calendar.MONTH,month);
        preCalendar.set(Calendar.DATE,1);
        // last month date
        preCalendar.add(Calendar.DATE,-1);
        int lastMonthDate = preCalendar.get(Calendar.DATE);

        // last month's total date count to be filled in current month
        int lastMonthCellCount = dayColumnNo - 2; // Calendar starts with monday

        // last month date's start index
        int startIndex = lastMonthDate - lastMonthCellCount;

        for (int i = startIndex; i<=lastMonthDate; i++){

            Calendar mDate = Calendar.getInstance();
            mDate.set(Calendar.DATE,i);
            mDate.set(Calendar.MONTH,month-1);
            if (monthValue == Calendar.JANUARY-1){
                mDate.add(Calendar.YEAR,-1);
            }
            MonthDates monthDates = new MonthDates(mDate,LAST_MONTH,DATE_CLICK_DISABLE,false);
            FinalDates.add(monthDates);
        }


        //CURRENT MONTH
        Calendar currentDate = dateCalendar;
        currentDate.add(Calendar.MONTH,1);
        currentDate.set(Calendar.DATE,1);
        currentDate.set(Calendar.DATE,-1);

        int lastDateOfCurrentMonth = currentDate.get(Calendar.DATE);


        for (int j = 0; j<=lastDateOfCurrentMonth; j++){
            Calendar mDate = Calendar.getInstance();
            mDate.set(Calendar.MONTH,monthValue);
            mDate.set(Calendar.DATE,j+1);

            if(j == 0){
                currentMonthCal = Calendar.getInstance();
                currentMonthCal.setTime(mDate.getTime());
            }
            MonthDates monthDates = null;
            if(events!=null){

                boolean status = false;
                for(int m=0;m<events.size();m++){
                    Date eDate = events.get(m).getEventDate();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String date1 = sdf.format(eDate);
                    String date2 = sdf.format(mDate.getTime());

                    if(date1.equals(date2)){
                        status = true;
                        break;
                    }
                }

                monthDates = new MonthDates(mDate,CURRENT_MONTH,DATE_CLICK_ENABLE,status);
                FinalDates.add(monthDates);

            }else{
                monthDates = new MonthDates(mDate,CURRENT_MONTH,DATE_CLICK_ENABLE,false);
                FinalDates.add(monthDates);
            }



        }


        //NEXT MONTH
        Calendar mDate = mCalendar;

        mDate.add(Calendar.MONTH,monthValue+1);
        mDate.set(Calendar.DATE,1);

        if(FinalDates.size()/7!=0){

            int remaining = 7 - FinalDates.size()%7;

            for(int i=0;i<remaining; i++){
                Calendar date = Calendar.getInstance();
                date.set(Calendar.MONTH,monthValue+1);
                date.set(Calendar.DATE,i+1);
                if (monthValue == Calendar.DECEMBER-1){
                    mDate.add(Calendar.YEAR,+1);
                }
                MonthDates monthDates = new MonthDates(date,LAST_MONTH,DATE_CLICK_DISABLE,false); //next month
                FinalDates.add(monthDates);
            }
        }

        if(showRecyclerView){
            setCalendarRecyclerView();
        }else {
            event_count.setText("Day is pretty free!");
        }

        setCalendarAdapter();


    }

    private void setCalendarAdapter() {

        gridView.setAdapter(adapter);

        adapter.setOnDateClickListener(new CustomCalendarAdapter.OnDateClick() {
            @Override
            public void onDateClickListener(int position, View view, MonthDates monthDates) {

                if(listener!=null){
                    if(monthDates.getDisable() == DATE_CLICK_ENABLE){
                        listener.onDateClickListener(position,view,monthDates);

                        int childCount = gridView.getChildCount();
                        for(int i = 0; i<childCount; i++){

                            final RelativeLayout relativeLayout = (RelativeLayout) gridView.getChildAt(i);
                            TextView dateTextView = (TextView) relativeLayout.getChildAt(0);

                            if(i == position){

                                if(selectedDateColor != 0 && monthDates.getDateOf() == CURRENT_MONTH){
                                    dateTextView.setTextColor(selectedDateColor);
                                }

                                Drawable bg = null;
                                if(background == null){

                                    if(monthDates.getDateOf() == LAST_MONTH){
                                        if(disabledDatesUserInput!=null){
                                            bg = disabledDatesUserInput;
                                        }else{
                                            bg = disabledBackground;
                                        }
                                    }else{
                                        bg = defaultDrawable;
                                    }

                                }else{

                                    if(monthDates.getDateOf() == LAST_MONTH){

                                        if(disabledDatesUserInput!=null){
                                            bg = disabledDatesUserInput;
                                        }else{
                                            bg = disabledBackground;
                                        }
                                    }else {
                                        bg = background;
                                    }
                                }


                                Glide.with(getContext()).load(bg).into(new CustomTarget<Drawable>() {
                                    @Override
                                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                        relativeLayout.setBackground(resource);
                                    }

                                    @Override
                                    public void onLoadCleared(@Nullable Drawable placeholder) {
                                    }

                                });

                                setSelectedDateText(monthDates.getCalendar().get(Calendar.MONTH),monthDates.getCalendar().get(Calendar.DATE),monthDates.getCalendar().get(Calendar.YEAR));

                                if(showRecyclerView && showEvents){
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                    String inputDate = sdf.format(monthDates.getCalendar().getTime());
                                    filter(inputDate);
                                }else{
                                    event_count.setText("Day is pretty free!");
                                }


                            }else {
                                relativeLayout.setBackground(null);
                                MonthDates mDates = FinalDates.get(i);
                                if(mDates.getDateOf() == CURRENT_MONTH){
                                    dateTextView.setTextColor(Color.parseColor(CURRENT_DATE_COLOR));
                                }else {
                                    dateTextView.setTextColor(Color.parseColor(PREV_DATE_COLOR));
                                }
                            }
                        }



                    }
                }
            }
        });


        if(dataAdapter!=null){
            dataAdapter.setOnEventClickListener(new DataAdapter.OnEventClick() {
                @Override
                public void onEventClickListener(int position, View view, Events event) {
                    if(eventListener!=null){
                        eventListener.onEventClickListener(position,view, event);
                    }
                }
            });
        }

    }

    public void setOnEventClickListener(final EventClickListener eventListener){
        this.eventListener = eventListener;
    }

    public interface EventClickListener{
        public void onEventClickListener(int position, View view, Events events);
    }


    public void setOnDateClickListener(final DateClickListener listener){

        this.listener = listener;
    }

    public interface DateClickListener{
        public void onDateClickListener(int position, View view, MonthDates dates);
    }

    public void setEvents(ArrayList<Events> events){
        if(events!=null){
            this.events = events;
            adapter.setEvents(events);
            setCalendarAdapter();
            setCurrentMonthCalendar();

        }
    }

    public void build(){
        setCurrentMonthCalendar();
    }

    public CustomCalendarView setDateSelectorBackground(Drawable background){
        if(background!=null){
            this.background = background;
            adapter.setDateSelectorBackground(background);
        }

        return this;
    }

    public CustomCalendarView setDisabledDateSelectorBackground(Drawable disabledDatesUserInput){

        if(disabledDatesUserInput!=null){
            this.disabledDatesUserInput = disabledDatesUserInput;
            adapter.setDisabledDateSelectorBackground(disabledDatesUserInput);
        }

        return this;
    }

    public CustomCalendarView setOldMonthDateClick(boolean click){
        if(click){
            DATE_CLICK_DISABLE = 1;
            setCalendar(CALENDAR_OF_MONTH);
        }else{
            DATE_CLICK_DISABLE = 0;
            setCalendar(CALENDAR_OF_MONTH);
        }
        return this;
    }

    public CustomCalendarView setSelectedDateColor(int selectedDateColor){
        if(selectedDateColor !=0 ){
            this.selectedDateColor = selectedDateColor;
            adapter.setSelectedDateColor(selectedDateColor);
        }
        return this;
    }


    public CustomCalendarView setDayFont(String fontPath){

        if(fontPath!=null){

            this.fontPath = fontPath;

            Typeface dayFont = Typeface.createFromAsset(getContext().getAssets(), fontPath);
            D1.setTypeface(dayFont);
            D2.setTypeface(dayFont);
            D3.setTypeface(dayFont);
            D4.setTypeface(dayFont);
            D5.setTypeface(dayFont);
            D6.setTypeface(dayFont);
            D7.setTypeface(dayFont);

            btnToday.setTypeface(dayFont);
            event_count.setTypeface(dayFont);
            tv_date_selected.setTypeface(dayFont);
        }

        return this;
    }

    public CustomCalendarView setDateFont(String fontPath){
        if(fontPath!=null){
            Typeface dateFont = Typeface.createFromAsset(getContext().getAssets(), fontPath);
            adapter.setDateFont(dateFont);
        }

        return this;
    }

    public CustomCalendarView setLayoutParameters(int gridWidth, int gridHeight){
        if(gridHeight!=0 && gridWidth!=0){
            adapter.setLayoutParameters(gridWidth,gridHeight);
        }

        return this;
    }

    public CustomCalendarView setDateTextSize(int textSize){
        if(textSize != 0){
            adapter.setDateTextSize(textSize);
        }

        return this;
    }

    public CustomCalendarView setDaysTextSize(int textSize){
        if(textSize != 0){
            D1.setTextSize(textSize);
            D2.setTextSize(textSize);
            D3.setTextSize(textSize);
            D4.setTextSize(textSize);
            D5.setTextSize(textSize);
            D6.setTextSize(textSize);
            D7.setTextSize(textSize);

            btnToday.setTextSize(textSize);
        }

        return this;
    }

    public CustomCalendarView setCalendarHeight(int height){
        if(height!=0){
            float resultDip = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,height,getContext().getResources().getDisplayMetrics());
            gridView.setMinimumHeight((int)resultDip);
        }

        return this;
    }

    public CustomCalendarView showDemoRecyclerView(boolean show){
        if(show){
            this.showRecyclerView = true;
            setCalendarRecyclerView();
        }else{
            this.showRecyclerView = false;
        }
        return this;
    }

    public CustomCalendarView setEventDrawable(Drawable eventDrawable){
        if(adapter!=null){
            adapter.setEventDrawable(eventDrawable);
        }

        return this;
    }

    public CustomCalendarView showEvents(boolean show){
        if(adapter!=null){
            showEvents = show;
            adapter.showEvents(show);
            setCalendarAdapter();
        }

        return this;
    }

    RecyclerView recyclerView = null;
    DataAdapter dataAdapter = null;

    public void setCalendarRecyclerView(){
        recyclerView = findViewById(R.id.mRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        resetRecyclerView();
    }

    private void resetRecyclerView() {

        if(dataAdapter == null){
            dataAdapter = new DataAdapter();
            if(fontPath!=null){
                Typeface font = Typeface.createFromAsset(getContext().getAssets(), fontPath);
                dataAdapter.setFont(font);
            };

        }

        if(events!= null){
            dataAdapter.submitList(events);
            recyclerView.setAdapter(dataAdapter);

            Calendar mCalendar = Calendar.getInstance();

            SimpleDateFormat date_sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

            String inputDate = sdf.format(mCalendar.getTime());
            String newDate = sdf.format(currentMonthCal.getTime());
            String finalDate = "";

            if(inputDate.equals(newDate)){
                finalDate = date_sdf.format(mCalendar.getTime());
            }else {
                finalDate = date_sdf.format(currentMonthCal.getTime());
            }

            filter(finalDate);
        }

    }

    private void filter(String text) {

        //new array list that will hold the filtered data
        ArrayList<Events> filteredList = new ArrayList<>();

        for (Events item : events) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String inputDate = sdf.format(item.getEventDate().getTime());

            if (inputDate.toLowerCase().equals(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        dataAdapter.submitList(filteredList);
        dataAdapter.notifyDataSetChanged();
        recyclerView.smoothScrollToPosition(0);
        int eventCount = filteredList.size();
        if(eventCount>0){
            event_count.setText(""+eventCount+" Events today!");
        }else {
            event_count.setText("Day is pretty free!");
        }


    }


    public void removeAllEvents(){
       if(events!=null){
           events.clear();
           //resetRecyclerView();
           build();
       }
    }

    public void removeSpecificEvent(Events event){
        if(events!=null){
            for(int i = 0;i<events.size();i++){
                if(events.get(i).getTitle().equals(event.getTitle())){
                    events.remove(i);
                    break;
                }

            }
            //resetRecyclerView();
            build();
        }

    }

}
