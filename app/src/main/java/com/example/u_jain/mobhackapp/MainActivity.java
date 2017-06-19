package com.example.u_jain.mobhackapp;

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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    EditText etURL;
    Button btnSubmit;

    String sourceCode = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.tv);
        etURL = (EditText) findViewById(R.id.etURL);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);


    }

    private void checkScoringParameters() {
        Toast.makeText(getApplicationContext(),"in check scroring params", Toast.LENGTH_SHORT).show();
        if(!sourceCode.equals(""))
        {
            // CODE TO CALL CHECKER FUNCTIONS CAN BE WRITTEN HERE
            checkDoctype(sourceCode);
            checkTitleLength(sourceCode);
            DataContainer dataContainer = new DataContainer(getApplicationContext());
        }
        else
        {
            // SOURCE CODE COULD NOT BE FETCHED
        }
    }

    public void evaluateURL(View v)
    {
        String website = etURL.getText().toString();
        String sourceCode = "";
        try {
            sourceCode = getWebsiteSourceCode(website);
            tv.setText(sourceCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sourceCode = sourceCode.toLowerCase();
        this.sourceCode = sourceCode;

        checkScoringParameters();
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

    public String getWebsiteSourceCode(String website) throws IOException {
        String resString = "";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        StringBuilder sb = new StringBuilder();
        URL url = new URL(website);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "windows-1251"), 8);

            String line = "";
            while((line  = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            in.close();
        } finally {
            urlConnection.disconnect();
        }
        resString = sb.toString();
        return  resString;
    }
}
