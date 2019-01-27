package com.example.panpa.bonplan.Activities;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.example.panpa.bonplan.R;

import java.util.ArrayList;

public class DialogFreq extends DialogFragment {
    private Spinner freq;
    private Button actionOK;
    private Button actionCancel;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.dialog_choice_freq,container,false);
        freq=v.findViewById(R.id.freq);
        actionOK= v.findViewById(R.id.validateFreq);
        actionCancel= v.findViewById(R.id.cancelFreq);
        actionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        actionOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = freq.getSelectedItem().toString();
                ((NoteEditActivity)getActivity()).freqTextView.setHint(input);
                getDialog().dismiss();
            }
        });
        return v;
    }

}
