package com.example.u_jain.mobhackapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by U_Jain on 6/19/2017.
 */

public class CheckingParamsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        DataContainer dataContainer= (DataContainer) getArguments().getSerializable("Data Container");
        View view= inflater.inflate(R.layout.fragment_checking_params, container, false);
        TextView fragmenttv = (TextView) view.findViewById(R.id.fragmentTextView);
        fragmenttv.setText(dataContainer.entities[0].citicalityMeasure);

        return view;
    }
}

