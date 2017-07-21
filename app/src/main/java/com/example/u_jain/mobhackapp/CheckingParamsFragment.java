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
import android.widget.ListView;
import android.widget.TextView;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    ListView listView;
    TextView fragmenttv;

    int responseStatusCode;
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
        responseStatusCode = getArguments().getInt("Response Status Code");
        View view= inflater.inflate(R.layout.fragment_checking_params, container, false);
        context = getActivity().getApplicationContext();
        fragmenttv = (TextView) view.findViewById(R.id.tvScore);
        listView= (ListView) view.findViewById(R.id.listView);
        (new CheckParamTask()).execute();

        return view;
    }
    public class CheckParamTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params) {
            checkDoctype(sourceCode);
            checkTitleLength(sourceCode);
            check200StatusCode(sourceCode);
            check4xxError(sourceCode);
            check3xxError(sourceCode);
            check5xxError(sourceCode);
            checkDescription(sourceCode);
            checkCanonical(sourceCode);
            checkMetaKeyword(sourceCode);
            checkOGDescription(sourceCode);
            checkOGTitle(sourceCode);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            CustomListView adapter = new CustomListView(context,dataContainer.allValues());
            listView.setAdapter(adapter);
        }
    }
    public boolean check3xxError(String sourceCode)
    {
        boolean bool = false;
        int tempResponseStatusCode = responseStatusCode;
        while(tempResponseStatusCode/10 != 0)
        {
            tempResponseStatusCode /= 10;
        }
        if(tempResponseStatusCode == 3)
        {
            bool = true;
            dataContainer.entities[0].paramHashMap.put("3xx error","yes");
            // Write code to set Hashmap for 3xx
        }
        else {
            dataContainer.entities[0].paramHashMap.put("3xx error","no");
            bool = false;
        }
        return bool;
    }

    public boolean check4xxError(String sourceCode)
    {
        boolean bool = false;
        int tempResponseStatusCode = responseStatusCode;
        while(tempResponseStatusCode/10 != 0)
        {
            tempResponseStatusCode /= 10;
        }
        if(tempResponseStatusCode == 4)
        {
            bool = true;
            dataContainer.entities[3].paramHashMap.put("4xx error","yes");
            // Write code to set Hashmap for 3xx
        }
        else {
            dataContainer.entities[3].paramHashMap.put("4xx error","no");
            bool = false;
        }
        return bool;
    }

    public boolean check5xxError(String sourceCode)
    {
        boolean bool = false;
        int tempResponseStatusCode = responseStatusCode;
        while(tempResponseStatusCode/10 != 0)
        {
            tempResponseStatusCode /= 10;
        }
        if(tempResponseStatusCode == 5)
        {
            bool = true;
            dataContainer.entities[4].paramHashMap.put("status code 5xx","yes");
            // Write code to set Hashmap for 3xx
        }
        else {
            dataContainer.entities[4].paramHashMap.put("status code 5xx","no");
            bool = false;
        }
        return bool;
    }

    public boolean check200StatusCode(String sourceCode)
    {
        boolean bool = false;
        int tempResponseStatusCode = responseStatusCode;
        if(tempResponseStatusCode == 200)
        {
            bool = true;
            dataContainer.entities[4].paramHashMap.put("status code 200","yes");
            // Write code to set Hashmap for 3xx
        }
        else {
            bool = false;
            dataContainer.entities[4].paramHashMap.put("status code 200","no");
        }
        return bool;
    }

    public boolean checkDescription(String sourceCode)
    {
        boolean bool = false;
        final String strToChk = "[<][\\s]*[m][e][t][a][\\s]{1,}[n][a][m][e][\\s]*[=][\\s]*[\"][d][e][s][c][r][i]\t[p][t][i][o][n][\"][\\s]{1,}[c][o][n][t][e][n][t][\\s]*[=].*[/][>]";
        Pattern pattern = Pattern.compile(strToChk);
        Matcher matcher = pattern.matcher(sourceCode);
        if(matcher.find())
        {
            dataContainer.entities[1].paramHashMap.put("missing or empty meta description tag","yes");
            bool = true;
        }
        else
        {
            dataContainer.entities[1].paramHashMap.put("missing or empty meta description tag","no");
        }
        return bool;
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
            bool = true;
        }
        else
        {
            dataContainer.entities[0].paramHashMap.put("doctype","no");
        }
        return bool;
    }

    public boolean checkCanonical(String sourceCode)
    {
        boolean bool =  false;
        String str2Check = "[<][\\s]*[l][i][n][k][\\s]{1,}[r][e][l][\\s]*[=][\\s]*[\"][c][a][n][o][n][i][c][a][l][\"][\\s]{1,}[h][r][e][f][\\s]*[=].*[/][>]";
        Pattern pattern = Pattern.compile(str2Check);
        Matcher matcher = pattern.matcher(sourceCode);
        if (matcher.find()) {

            String s1=matcher.group();

            String str= func("href",s1);

            if(str!=null)
            {
                //Write code to set hash with yes
                dataContainer.entities[0].paramHashMap.put("broken canonical","no");
                bool = true;
            }
            else
            {
                //No in hash
                dataContainer.entities[0].paramHashMap.put("broken canonical","yes");
                bool = false;
            }
        }
        return bool;
    }
    public boolean checkMetaKeyword(String sourceCode)
    {
        boolean bool = false;
        final String strToChk = "[<][\\s]*[m][e][t][a][\\s]{1,}[n][a][m][e][\\s]*[=][\\s]*[\"][k][e][y][w][o][r][d][\"][\\s]{1,}[c][o][n][t][e][n][t][\\s]*[=].*[/][>]";
        Pattern pattern = Pattern.compile(strToChk);
        Matcher matcher = pattern.matcher(sourceCode);
        if(matcher.find())
        {
            //Toast.makeText(context,"doctype exists", Toast.LENGTH_LONG).show();
            dataContainer.entities[0].paramHashMap.put("missing meta keyword tag","no");
            bool = true;
        }
        else
        {
            dataContainer.entities[0].paramHashMap.put("missing meta keyword tag","yes");
        }
        return bool;
    }

    public boolean checkOGDescription(String sourceCode)
    {
        boolean bool = false;
        final String strToChk = "[<][\\s]*[m][e][t][a][\\s]{1,}[n][a][m][e][\\s]*[=][\\s]*[\"][o][g][:][d][e][s][c][r][i][p][t][i][o][n][\"][\\s]{1,}[c][o][n][t][e][n][t][\\s]*[=].*[/][>]";
        Pattern pattern = Pattern.compile(strToChk);
        Matcher matcher = pattern.matcher(sourceCode);
        if(matcher.find())
        {
            dataContainer.entities[0].paramHashMap.put("og:description","no");
            bool = true;
        }
        else
        {
            dataContainer.entities[0].paramHashMap.put("og:description","yes");
        }
        return bool;
    }

    public boolean checkOGTitle(String sourceCode)
    {
        boolean bool = false;
        final String strToChk = "[<][\\s]*[m][e][t][a][\\s]{1,}[n][a][m][e][\\s]*[=][\\s]*[\"][o][g][:][t][i][t][l][e][\\\"][\\s]{1,}[c][o][n][t][e][n][t][\\s]*[=].*[/][>]";
        Pattern pattern = Pattern.compile(strToChk);
        Matcher matcher = pattern.matcher(sourceCode);
        if(matcher.find())
        {
            dataContainer.entities[0].paramHashMap.put("og:title","no");
            bool = true;
        }
        else
        {
            dataContainer.entities[0].paramHashMap.put("og:title","yes");
        }
        return bool;
    }

    public boolean checkRobots(String sourceCode)
    {
        boolean bool = false;
        final String strToChk = "[<][\\s]*[m][e][t][a][\\s]{1,}[n][a][m][e][\\s]*[=][\\s]*[\"][r][o][b][o][t][s][\"][\\s]{1,}[c][o][n][t][e][n][t][\\s]*[=].*[/][>]";
        Pattern pattern = Pattern.compile(strToChk);
        Matcher matcher = pattern.matcher(sourceCode);
        if(matcher.find())
        {
            //Toast.makeText(context,"doctype exists", Toast.LENGTH_LONG).show();
            dataContainer.entities[1].paramHashMap.put("og:title","no");
            bool = true;
        }
        else
        {
            dataContainer.entities[1].paramHashMap.put("og:title","yes");
        }
        return bool;
    }

    public String func(String str,String tag)
    {
        int i= tag.indexOf(str);
        int j= tag.indexOf("=",i);
        int k= tag.indexOf("\"",j);
        int h= tag.indexOf("\"",k+1);
        String yobaby= tag.substring(k+1,h);
        return(yobaby);
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

