package com.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.customcalendar.R;
import com.model.Events;
import com.model.MonthDates;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

import static com.customcalendar.CustomCalendarView.CURRENT_DATE_COLOR;
import static com.customcalendar.CustomCalendarView.CURRENT_MONTH;
import static com.customcalendar.CustomCalendarView.LAST_MONTH;
import static com.customcalendar.CustomCalendarView.PREV_DATE_COLOR;

public class CustomCalendarAdapter extends ArrayAdapter<MonthDates> {

    private LayoutInflater inflater;
    HashSet<Date> eventDays = new HashSet<>();
    OnDateClick dateClickListener = null;
    Typeface dateFont = null;
    Drawable background = null, disabledDatesUserInput = null,eventDrawable = null;
    int gridWidth=0, gridHeight=0,textSize = 0;
    int selectedDateColor = 0;
    ArrayList<Events> events = new ArrayList<>();
    boolean show = false;

    public CustomCalendarAdapter(Context context, ArrayList<MonthDates> days, HashSet<Date> eventDays)
    {
        super(context, R.layout.custom_calendar_day, days);
        this.eventDays = eventDays;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public View getView(final int position, View view, final ViewGroup parent)
    {
        // inflate item if it does not exist yet
        if (view == null)
            view = inflater.inflate(R.layout.custom_calendar_day, parent, false);

        final RelativeLayout relativeLayout = (RelativeLayout) view;

        final TextView tv_date = (TextView) relativeLayout.getChildAt(0);
        //final LinearLayout linearLayout = (LinearLayout) relativeLayout.getChildAt(1);

        final ImageView event = (ImageView) relativeLayout.getChildAt(1);
        //final ImageView event_plus = (ImageView) relativeLayout.getChildAt(2);

        if(gridWidth != 0 || gridHeight != 0){
            float resultDip = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,gridWidth,getContext().getResources().getDisplayMetrics());

            RelativeLayout.LayoutParams relParams = new RelativeLayout.LayoutParams((int)resultDip,(int)resultDip);
            relParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            relativeLayout.setLayoutParams(relParams);

            /*int half = (gridHeight-5)/7;
            Log.d("HEIGHT",""+half+" / "+gridHeight);
            float eventSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,half,getContext().getResources().getDisplayMetrics());

            RelativeLayout.LayoutParams eventLayoutPrams = new RelativeLayout.LayoutParams((int)eventSize,(int) eventSize);
            eventLayoutPrams.addRule(RelativeLayout.ALIGN_BOTTOM,tv_date.getId());
            eventLayoutPrams.setMargins(0,1,0,0);
            event.setLayoutParams(eventLayoutPrams);

            RelativeLayout.LayoutParams eventPlusLayoutPrams = new RelativeLayout.LayoutParams((int)eventSize,(int) eventSize);
            eventLayoutPrams.addRule(RelativeLayout.ALIGN_BOTTOM,tv_date.getId());
            eventLayoutPrams.setMargins(0,1,2,0);
            event_plus.setLayoutParams(eventPlusLayoutPrams);
            */
        }

        if(textSize != 0){
            tv_date.setTextSize((float) textSize);
        }


        Calendar today = Calendar.getInstance();
        // day in question
        Calendar calendar = Calendar.getInstance();
        final MonthDates date = getItem(position);
        calendar.setTime(date.getCalendar().getTime());

        int eventCount = getEventCount(getItem(position));

        //if(date.getEvent()){
        if(eventCount>=1 && show){
            event.setVisibility(View.VISIBLE);
            /*if(eventCount>1){
                event_plus.setVisibility(View.VISIBLE);
                Glide.with(view.getContext()).load(getContext().getResources().getDrawable(R.drawable.ic_add_black)).into(event_plus);
            }else{
                event_plus.setVisibility(View.GONE);
            }*/
            if(eventDrawable!=null){
                Glide.with(view.getContext()).load(eventDrawable).into(event);
            }else{
                Glide.with(view.getContext()).load(getContext().getResources().getDrawable(R.drawable.ic_event1)).into(event);
            }
        }else{
            event.setVisibility(View.GONE);
            //event_plus.setVisibility(View.GONE);
        }

        int tDate = today.get(Calendar.DATE);
        int tMonth = today.get(Calendar.MONTH);
        int tYear = today.get(Calendar.YEAR);

        if(tDate == calendar.get(Calendar.DATE) && tMonth == calendar.get(Calendar.MONTH) && tYear == calendar.get(Calendar.YEAR) && date.getDateOf()==CURRENT_MONTH){

            if(background == null){
                background = getContext().getResources().getDrawable(R.drawable.selected_date_round);
            }

            Glide.with(getContext()).load(background).into(new CustomTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    relativeLayout.setBackground(resource);
                }

                @Override
                public void onLoadCleared(@Nullable Drawable placeholder) {
                }

            });

            if(selectedDateColor !=0){
                tv_date.setTextColor(selectedDateColor);
            }

        }else{
            if(date.getCalendar().get(Calendar.DATE) == 1 && today.get(Calendar.MONTH)!= calendar.get(Calendar.MONTH) && date.getDateOf() != LAST_MONTH ){
                if(background == null){
                    background = getContext().getResources().getDrawable(R.drawable.selected_date_round);
                }

                Glide.with(getContext()).load(background).into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        relativeLayout.setBackground(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }

                });

                if(selectedDateColor !=0){
                    tv_date.setTextColor(selectedDateColor);
                }

            }else{
                if(date.getDateOf() == LAST_MONTH){
                    tv_date.setTextColor(Color.parseColor(PREV_DATE_COLOR));
                }else {
                    tv_date.setTextColor(Color.parseColor(CURRENT_DATE_COLOR));
                }
            }


        }

        if(dateFont !=null){
            tv_date.setTypeface(dateFont);
        }

        tv_date.setText(String.valueOf(date.getCalendar().get(Calendar.DATE)));

        view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        if(dateClickListener!=null) {
                            if(date.getDisable() == 1){
                                dateClickListener.onDateClickListener(position,view,date);
                            }
                        }
                }
            });


        return view;
    }

    private int getEventCount(MonthDates date) {
        int count = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String adapterDate = sdf.format(date.getCalendar().getTime());

        if(events!=null){
            for (Events item : events) {

                String inputDate = sdf.format(item.getEventDate().getTime());
                ArrayList<Events> dateEvents = new ArrayList<>();
                dateEvents.add(new Events(item.getEventDate(),item.isStatus(),item.getTitle()));
                date.setEventList(dateEvents);
                if (inputDate.toLowerCase().equals(adapterDate.toLowerCase())) {
                    count++;
                }
            }
        }
        return count;
    }

    public void setSelectedDateColor(int selectedDateColor) {
        this.selectedDateColor = selectedDateColor;
    }

    public interface OnDateClick{
        public void onDateClickListener(int position, View view, MonthDates monthDates);
    }

    public void setOnDateClickListener(OnDateClick dateClickListener){
        this.dateClickListener = dateClickListener;
    }

    public void setDateFont(Typeface dateFont){
        this.dateFont = dateFont;
    }

    public void setEventDrawable(Drawable eventDrawable){
        this.eventDrawable = eventDrawable;
    }

    public void setDateSelectorBackground(Drawable background){
        this.background = background;
    }

    public void setDisabledDateSelectorBackground(Drawable disabledDatesUserInput){
        this.disabledDatesUserInput = disabledDatesUserInput;
    }

    public void setLayoutParameters(int gridWidth, int gridHeight){
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
    }
    public void setDateTextSize(int textSize){
        this.textSize = textSize;
    }

    public void setEvents(ArrayList<Events> events){
        this.events = events;
    }

    public void showEvents(boolean show){
        this.show = show;
    }
}
