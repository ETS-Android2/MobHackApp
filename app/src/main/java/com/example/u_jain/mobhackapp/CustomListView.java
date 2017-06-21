package com.example.u_jain.mobhackapp;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Subodh on 21-Jun-17.
 */

public class CustomListView extends ArrayAdapter {

    Context context;
    String[][] populateValues;
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.listitem,parent,false);
        TextView parameter = (TextView) row.findViewById(R.id.tvparamerter);
        TextView criticality = (TextView) row.findViewById(R.id.tvcriticality);
        TextView value = (TextView) row.findViewById(R.id.tvyesno);

        parameter.setText(populateValues[position][0]);
        criticality.setText(populateValues[position][1]);
        value.setText(populateValues[position][2]);
        return row;
    }

    public CustomListView(@NonNull Context context, String[][] populateValues) {
        super(context, R.layout.listitem,populateValues);
        this.context=context;
        this.populateValues=populateValues;

    }
}
