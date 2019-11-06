package com.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class CustomizeCalendar extends AppCompatActivity {

    Spinner sp_date_text,sp_day_text,sp_day_font,sp_date_font,sp_recyclerview,sp_oldDateClick,sp_block_size,sp_cal_height;
    Button bt_customize,bt_skip;
    public static final String EXTRA_DAY_FONT="DAY_FONT", EXTRA_DATE_FONT = "DATE_FONT", EXTRA_DATE_TEXT = "DATE_TEXT",EXTRA_DAY_TEXT = "DAY_TEXT",EXTRA_CAL_HEIGHT = "CAL_HEIGHT", EXTRA_BLOCK_SIZE = "BLOCK_SIZE", EXTRA_RECYCLERVIEW="RECYCLERVIEW", EXTRA_DATE_CLICK = "DATE_CLICK", EXTRA_CONFIG = "CONFIG", DEFAULT = "DEFAULT", CUSTOM = "CUSTOM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_calendar);

        initViews();
        initOnClickListeners();

    }

    private void initOnClickListeners() {
        bt_customize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CustomizeCalendar.this,MainActivity.class);
                intent.putExtra(EXTRA_CONFIG,CUSTOM);
                intent.putExtra(EXTRA_DATE_FONT,sp_date_font.getSelectedItem().toString());
                intent.putExtra(EXTRA_DAY_FONT,sp_day_font.getSelectedItem().toString());
                intent.putExtra(EXTRA_DATE_TEXT,Integer.parseInt(sp_date_text.getSelectedItem().toString()));
                intent.putExtra(EXTRA_DAY_TEXT,Integer.parseInt(sp_day_text.getSelectedItem().toString()));
                //intent.putExtra(EXTRA_CAL_HEIGHT,Integer.parseInt(sp_cal_height.getSelectedItem().toString()));
                intent.putExtra(EXTRA_BLOCK_SIZE,Integer.parseInt(sp_block_size.getSelectedItem().toString()));
                intent.putExtra(EXTRA_RECYCLERVIEW,sp_recyclerview.getSelectedItem().toString());
                intent.putExtra(EXTRA_DATE_CLICK,sp_oldDateClick.getSelectedItem().toString());
                startActivity(intent);
            }
        });

        bt_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomizeCalendar.this,MainActivity.class);
                intent.putExtra(EXTRA_CONFIG,DEFAULT);
                startActivity(intent);
            }
        });
    }

    private void initViews() {

        getSupportActionBar().setTitle("Custom Calendar");
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setIcon(R.drawable.ic_calendar);
        sp_date_font = findViewById(R.id.sp_date_font);
        sp_day_font = findViewById(R.id.sp_day_font);
        sp_date_text = findViewById(R.id.sp_date_textSize);
        sp_day_text= findViewById(R.id.sp_day_textSize);
        //sp_cal_height= findViewById(R.id.sp_calHeight);
        sp_block_size= findViewById(R.id.sp_blockSize);
        sp_recyclerview= findViewById(R.id.sp_sample_recyclerview);
        sp_oldDateClick= findViewById(R.id.sp_enable);
        sp_oldDateClick= findViewById(R.id.sp_enable);
        bt_customize= findViewById(R.id.customize);
        bt_skip= findViewById(R.id.skip);


        // Initializing an ArrayAdapter
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                this,R.layout.spinner_item,getResources().getStringArray(R.array.fonts)
        );

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                this,R.layout.spinner_item,getResources().getStringArray(R.array.day_font_size)
        );

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(
                this,R.layout.spinner_item,getResources().getStringArray(R.array.date_font_size)
        );

        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(
                this,R.layout.spinner_item,getResources().getStringArray(R.array.block_size)
        );

        ArrayAdapter<String> adapter6 = new ArrayAdapter<String>(
                this,R.layout.spinner_item,getResources().getStringArray(R.array.yes_no)
        );


        adapter1.setDropDownViewResource(R.layout.spinner_item);
        sp_date_font.setAdapter(adapter1);
        sp_day_font.setAdapter(adapter1);

        adapter2.setDropDownViewResource(R.layout.spinner_item);
        sp_day_text.setAdapter(adapter2);

        adapter3.setDropDownViewResource(R.layout.spinner_item);
        sp_date_text.setAdapter(adapter3);

        adapter4.setDropDownViewResource(R.layout.spinner_item);
        sp_block_size.setAdapter(adapter4);

        adapter6.setDropDownViewResource(R.layout.spinner_item);
        sp_oldDateClick.setAdapter(adapter6);
        sp_recyclerview.setAdapter(adapter6);

    }
}
