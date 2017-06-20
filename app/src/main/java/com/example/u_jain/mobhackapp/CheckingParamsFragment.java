package com.example.u_jain.mobhackapp;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by U_Jain on 6/19/2017.
 */

public class CheckingParamsFragment extends Fragment {
    DataContainer dataContainer;
    Context context;
    String sourceCode;

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        dataContainer= (DataContainer) getArguments().getSerializable("Data Container");
        sourceCode = getArguments().getString("Source Code");
        context = getActivity().getApplicationContext();
        View view= inflater.inflate(R.layout.fragment_checking_params, container, false);
        TextView fragmenttv = (TextView) view.findViewById(R.id.fragmentTextView);
        fragmenttv.setText(dataContainer.entities[0].criticalityMeasure);
        (new CheckParamTask()).execute();
        return view;
    }
    public class CheckParamTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params) {
            checkDoctype(sourceCode);
            checkTitleLength(sourceCode);
            return null;
        }
    }
    public boolean checkDoctype(String sourceCode)
    {
        Log.v("in doctype checking : ", "yes");
        boolean bool = false;
        final String strToChk = "[<][!][d][o][c][t][y][p][e][ ][h][t][m][l][>]";
        Pattern pattern = Pattern.compile(strToChk);
        Matcher matcher = pattern.matcher(sourceCode);
        if(matcher.find())
        {
           //Toast.makeText(context,"doctype exists", Toast.LENGTH_LONG).show();
            dataContainer.entities[0].paramHashMap.put("doctype","yes");
            Log.v("Check:",dataContainer.entities[0].paramHashMap.get("doctype"));
            bool = true;
        }
        else
        {
            dataContainer.entities[0].paramHashMap.put("doctype","no");
        }
        return bool;
    }

    public boolean checkTitleLength(String sourceCode)
    {
        Log.v("in title checking : ", "yes");
        boolean bool = false;
        final String strToChk = "[<][t][i][t][l][e][>](.*)[<][/][t][i][t][l][e][>]";
        Pattern pattern = Pattern.compile(strToChk);
        Matcher matcher = pattern.matcher(sourceCode);
        if(matcher.find())
        {
           //Toast.makeText(context,"title exists", Toast.LENGTH_SHORT).show();
            dataContainer.entities[0].paramHashMap.put("title tag","yes");
            if(matcher.groupCount()>0)
            {
              // Toast.makeText(context,"title size : " + matcher.group(1).length(), Toast.LENGTH_LONG).show();
                //dataContainer.entities[0].paramHashMap.put("title tag","yes");
//                if()
//                {
//                    //WRITE LOGIC FOR TITLE LENGTH AND SET BOOL ACCORDINGLY
//                }
//                else
//                {
//
//                }

                bool = true;
            }
        }
        else
        {
           //Toast.makeText(context,"title not exists", Toast.LENGTH_LONG).show();
            dataContainer.entities[0].paramHashMap.put("title tag","no");
        }
        return bool;
    }
}

