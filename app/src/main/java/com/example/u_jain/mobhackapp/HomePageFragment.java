package com.example.u_jain.mobhackapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Created by U_Jain on 6/21/2017.
 */

public class HomePageFragment extends Fragment {
    TextView tv;
    EditText etURL;
    Button btnSubmit;
    int responseStatusCode=-1;

    static String sourceCode;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home_page, container, false);
        tv = (TextView) view.findViewById(R.id.tv);
        etURL = (EditText) view.findViewById(R.id.URL_Enter);
        btnSubmit = (Button) view.findViewById(R.id.btn1);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String website = etURL.getText().toString();
                String sourceCode = "";
                try {
                    sourceCode = getWebsiteSourceCode(website);
                    tv.setText(sourceCode);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                sourceCode = sourceCode.toLowerCase();
                HomePageFragment.sourceCode = sourceCode;

                checkScoringParameters();
            }
        });
        return view;
    }

    private void checkScoringParameters() {
//        Toast.makeText(getActivity().getApplicationContext(),"in check scroring params", Toast.LENGTH_SHORT).show();
        if(!sourceCode.equals(""))
        {
            DataContainer dataContainer = new DataContainer(getActivity().getApplicationContext());
            startFragment(dataContainer);
        }
        else
        {
            // SOURCE CODE COULD NOT BE FETCHED
        }
    }


    public void startFragment(DataContainer dataContainer)
    {
        Bundle bundle = new Bundle();
        bundle.putSerializable("Data Container",dataContainer);
        bundle.putString("Source Code", sourceCode);
        bundle.putInt("Response Status Code", responseStatusCode);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        Fragment fragment = new CheckingParamsFragment();
        fragment.setArguments(bundle);
        ft.replace(R.id.parentLinearLayout, fragment);
        ft.commit();
    }

    public String getWebsiteSourceCode(String website) throws IOException {
        String resString = "";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        StringBuilder sb = new StringBuilder();
        URL url = new URL(website);
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "windows-1251"), 8);
            responseStatusCode = urlConnection.getResponseCode();
            String line = "";
            while((line  = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            in.close();
        }
        catch (Exception e)
        {
            Toast.makeText(getActivity().getApplicationContext(),"Change your internet network!", Toast.LENGTH_LONG).show();
        }
        finally {
            urlConnection.disconnect();
        }
        resString = sb.toString();
        return  resString;
    }
}
