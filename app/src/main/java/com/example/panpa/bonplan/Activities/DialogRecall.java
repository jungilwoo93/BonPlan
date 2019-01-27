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

public class DialogRecall extends DialogFragment {
    private Spinner recall;
    private Button actionOK;
    private Button actionCancel;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.dialog_choice_recall,container,false);
        recall=v.findViewById(R.id.recall);
        actionOK= v.findViewById(R.id.validateRecall);
        actionCancel= v.findViewById(R.id.cancelRecall);
        actionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        actionOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = recall.getSelectedItem().toString();
                ((NoteEditActivity)getActivity()).recallTextView.setHint(input);
                getDialog().dismiss();
            }
        });
        return v;
    }
}
