package com.example.u_jain.mobhackapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by U_Jain on 6/19/2017.
 */

public class CheckingParamsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        DataContainer dc= (DataContainer) getArguments().getSerializable("Data Container");
        return inflater.inflate(R.layout.fragment_checking_params, container, false);
    }
}



/*

From Activity you send data with intent as:

Bundle bundle = new Bundle();
bundle.putString("edttext", "From Activity");
// set Fragmentclass Arguments
Fragmentclass fragobj = new Fragmentclass();
fragobj.setArguments(bundle);



and in Fragment onCreateView method:

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
    String strtext = getArguments().getString("edttext");
    return inflater.inflate(R.layout.fragment, container, false);
}*/
