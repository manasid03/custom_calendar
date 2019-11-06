# custom_calendar
This is customized calendar. 
You can customize it in a way you want to. You have got various options to play around with your calendar. 
Open to suggestions, if you have any! 
Give it a try! 

[![](https://jitpack.io/v/manasid03/custom_calendar.svg)](https://jitpack.io/#manasid03/custom_calendar)

![Alt text](/relative/path/to/img.jpg?raw=true "1")

#How to include it in your project?

    Step 1. Add the JitPack repository to your build file
    
    #Gradle 
    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
    
    
    #Maven
    <repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
   
    
    Step 2. Add the dependency
    
    #Gradle
    dependencies {
	        implementation 'com.github.manasid03:custom_calendar:v1.0.0'
	}
    
    #Maven
    <dependency>
	    <groupId>com.github.manasid03</groupId>
	    <artifactId>custom_calendar</artifactId>
	    <version>Tag</version>
	</dependency>
    
#To initialize a calendar

    CustomCalendarView calendarView = findViewById(R.id.mCalendar);       
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
                            
                            
                            
#To handle date click

    calendarView.setOnDateClickListener(this);
    @Override
    public void onDateClickListener(int position, View view, MonthDates dates) {
        Toast.makeText(MainActivity.this,dates.toString(),Toast.LENGTH_LONG).show();
    }


#To handle event click

    calendarView.setOnEventClickListener(this);
    @Override
    public void onEventClickListener(int position, View view, Events event) {
        Toast.makeText(MainActivity.this,"You clicked "+event.getTitle(),Toast.LENGTH_LONG).show();
    }
    
    
#To set events

    ArrayList<Events> events = new ArrayList<Events>();
    Calendar calendar = Calendar.getInstance();
    events.add(new Events(calendar.getTime(),true,"A Event "+i));
    calendarView.setEvents(events);


#To remove a specific event

    calendarView.removeSpecificEvent(new Events(date,true,"A Event "+2));

#To remove all events

    calendarView.removeAllEvents();
