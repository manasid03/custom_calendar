package com.adapters;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.customcalendar.R;
import com.model.Events;

import java.text.SimpleDateFormat;

public class DataAdapter extends ListAdapter<Events, DataAdapter.MyViewHolder> {

    OnEventClick eventClickListener = null;
    Typeface font = null;
    public DataAdapter(){
        super(diffUtilCallBack);
    }

    public static final DiffUtil.ItemCallback<Events> diffUtilCallBack = new DiffUtil.ItemCallback<Events>() {
        @Override
        public boolean areItemsTheSame(@NonNull Events oldItem, @NonNull Events newItem) {
            return (oldItem.getEventDate().equals(newItem.getEventDate())) && (oldItem.getTitle().equals(newItem.getTitle()));
        }

        @Override
        public boolean areContentsTheSame(@NonNull Events oldItem, @NonNull Events newItem) {
            return (oldItem.getEventDate().equals(newItem.getEventDate())) && (oldItem.getTitle().equals(newItem.getTitle()));
        }
    };

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Events data = getItem(position);
        SimpleDateFormat sdf = new SimpleDateFormat("d");
        String inputDate = sdf.format(data.getEventDate());
        char initial = data.getTitle().toUpperCase().charAt(0);
        holder.tv_date.setText(""+initial);
        holder.tv_value.setText(data.getTitle());

        if(font!=null){
            holder.tv_value.setTypeface(font);
            holder.tv_date.setTypeface(font);
        }

    }

    public Events getValueAt(int position){
        return getItem(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_date,tv_value;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_date = itemView.findViewById(R.id.tv_date);
            tv_value = itemView.findViewById(R.id.tv_value);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(eventClickListener!=null) {
                        int position = getAdapterPosition();
                        eventClickListener.onEventClickListener(getAdapterPosition(),view,getValueAt(position));
                    }
                }
            });
        }
    }

    public interface OnEventClick{
        public void onEventClickListener(int position, View view, Events monthDates);
    }

    public void setOnEventClickListener(OnEventClick eventClickListener){
        this.eventClickListener = eventClickListener;
    }

    public void setFont(Typeface font){
        this.font = font;
    }
}
