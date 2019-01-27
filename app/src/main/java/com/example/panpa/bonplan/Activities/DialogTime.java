package com.example.panpa.bonplan.Activities;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import com.example.panpa.bonplan.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DialogTime extends DialogFragment {

    private Spinner years;
    private Spinner months;
    private Spinner days;
    private Spinner hours;
    private Spinner minutes;
    private Button actionOK;
    private Button actionCancel;
    private int type; //0 start 1 end

    public void setType(int type){
        this.type=type;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.dialog_choice_time,container,false);
        years= v.findViewById(R.id.years);
        months= v.findViewById(R.id.months);
        days= v.findViewById(R.id.days);
        hours= v.findViewById(R.id.hours);
        minutes= v.findViewById(R.id.minutes);
        actionOK= v.findViewById(R.id.validateTime);
        actionCancel= v.findViewById(R.id.cancelTime);
        Calendar cal=Calendar.getInstance();
        //String monthname=(String)android.text.format.DateFormat.format("MMMM", new Date());
        //years.setSelection(((ArrayAdapter)years.getAdapter()).getPosition("February"));
        years.setSelection(((ArrayAdapter)years.getAdapter()).getPosition(Calendar.YEAR));
        months.setSelection(cal.get(Calendar.MONTH)+1);
        ArrayList<String> listDay = addDayOnSpinner();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, listDay);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        days.setAdapter(adapter);
        days.setSelection(cal.get(Calendar.DAY_OF_MONTH)-1);
        ArrayList<String> listHour = addDayOnSpinner();
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, listHour);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hours.setAdapter(adapter);
        hours.setSelection(cal.get(Calendar.HOUR_OF_DAY));
        ArrayList<String> listMinute = addMinuteOnSpinner();
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, listMinute);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minutes.setAdapter(adapter3);
        minutes.setSelection(0);
        //minutes.setSelection(cal.get(Calendar.MINUTE)%5+cal.get(Calendar.MINUTE));
        /*ArrayList<String> listYears = addYearOnSpinner();
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, listYears);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        years.setAdapter(adapter);*/
        actionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        actionOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                String year = years.getSelectedItem().toString();
                int month = months.getSelectedItemPosition()+1;
                String day = days.getSelectedItem().toString();
                String hour = hours.getSelectedItem().toString();
                String minu = minutes.getSelectedItem().toString();
                if(year==Integer.toString(Calendar.YEAR)){
                    if (type == 0) {
                        ((NoteEditActivity)getActivity()).startTextView.setHint(day + "/" + month + " " + hour + ":" + minu);
                    }else{
                        ((NoteEditActivity)getActivity()).endTextView.setHint(day + "/" + month + " " + hour + ":" + minu);
                    }

                }else{
                    if (type == 0) {
                        ((NoteEditActivity)getActivity()).startTextView.setHint(day + "/" + month + "/" + year + " " + hour + ":" + minu);
                    }else{
                        ((NoteEditActivity)getActivity()).endTextView.setHint(day + "/" + month + "/" + year + " " + hour + ":" + minu);
                    }

                }
                //String input = "Bello";
                //((NoteEditActivity)getActivity()).startTextView.setHint(input);
                getDialog().dismiss();
            }
        });
        return v;
    }

    public ArrayList<String> addDayOnSpinner(){
        ArrayList<String> list = new ArrayList<String>();
        for(int i = 1;i<31;i++){
            list.add(Integer.toString(i));
        }
        return list;
    }

    public ArrayList<String> addYearOnSpinner(){
        Calendar cal=Calendar.getInstance();
        ArrayList<String> list = new ArrayList<String>();
        for(int i = cal.get(Calendar.YEAR);i<cal.get(Calendar.YEAR)+10;i++){
            list.add(Integer.toString(i));
        }
        return list;
    }

    public ArrayList<String> addHourOnSpinner(){
        ArrayList<String> list = new ArrayList<String>();
        for(int i = 0;i<23;i++){
            list.add(Integer.toString(i));
        }
        return list;
    }

    public ArrayList<String> addMinuteOnSpinner(){
        ArrayList<String> list = new ArrayList<String>();
        for(int i = 0;i<59;i=i+5){
            list.add(Integer.toString(i));
        }
        //System.out.println(list.toString());
        return list;
    }



}
