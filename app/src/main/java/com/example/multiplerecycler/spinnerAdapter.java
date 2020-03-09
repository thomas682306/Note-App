package com.example.multiplerecycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class spinnerAdapter extends ArrayAdapter<spinnerClass> {

    public spinnerAdapter(@NonNull Context context,ArrayList<spinnerClass> spinnerClassObjArray) {
        super(context, 0, spinnerClassObjArray);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position,convertView,parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position,convertView,parent);
    }

    private View initView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        if(convertView ==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.spinner_item,parent,false);

        }


        ImageView priorityColour=convertView.findViewById(R.id.priorityColour);
        TextView priorityText=convertView.findViewById(R.id.prioritytv);

        spinnerClass spinnerObj =getItem(position);

        if(spinnerObj!=null){
        priorityColour.setImageResource(spinnerObj.getDrawableFile());
        priorityText.setText(spinnerObj.getPriorityName());
        }
        return  convertView;
    }
}
