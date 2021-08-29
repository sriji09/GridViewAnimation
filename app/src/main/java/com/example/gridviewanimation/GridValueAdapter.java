package com.example.gridviewanimation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GridValueAdapter extends ArrayAdapter<ValueModel> {
    private final ArrayList<ValueModel> valueModel;

    public GridValueAdapter(@NonNull Context context, ArrayList<ValueModel> valueModel) {
        super(context, 0, valueModel);
        this.valueModel = valueModel;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view== null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_card, parent, false);
        }
        ValueModel valueModel = this.valueModel.get(position);
        TextView value1 = view.findViewById(R.id.text1);
        TextView value2= view.findViewById(R.id.text2);
        value1.setText(String.valueOf(valueModel.getLowerValue()));
        value2.setText(String.valueOf(valueModel.getUpperValue()));

        TextView text11 = view.findViewById(R.id.text11);
        TextView text22 = view.findViewById(R.id.text22);
        text11.setText(String.valueOf(valueModel.getLowerValue()));
        text22.setText(String.valueOf(valueModel.getUpperValue()));
        return view;
    }
}
