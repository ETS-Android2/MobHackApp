package com.example.u_jain.mobhackapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startHomePageFragment();
    }

    public void startHomePageFragment()
    {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        Fragment fragmentHomePage = new HomePageFragment();
        ft.add(R.id.parentLinearLayout, fragmentHomePage);
        ft.commit();
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
            Toast.makeText(getApplicationContext(),"doctype exists", Toast.LENGTH_LONG).show();
            bool = true;
        }
        else
        {
            Toast.makeText(getApplicationContext(),"doctype not exists", Toast.LENGTH_LONG).show();
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
            Toast.makeText(getApplicationContext(),"title exists", Toast.LENGTH_SHORT).show();
            if(matcher.groupCount()>0)
            {
                Toast.makeText(getApplicationContext(),"title size : " + matcher.group(1).length(), Toast.LENGTH_LONG).show();
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
            Toast.makeText(getApplicationContext(),"title not exists", Toast.LENGTH_LONG).show();
        }
        return bool;
    }


}
