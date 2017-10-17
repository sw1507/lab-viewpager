package edu.uw.fragmentdemo;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }



    //respond to search button clicking
    public void handleSearchClick(View v){
        EditText text = (EditText)findViewById(R.id.txt_search);
        String searchTerm = text.getText().toString();

        MoviewListFragment listFragment = MoviewListFragment.newInstance(searchTerm);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, listFragment, "MovieListFragment");

//        FragmentManager fm = getSupportFragmentManager();
//        MoviewListFragment listFragment = (MoviewListFragment)fm.findFragmentById(R.id.fragment);
//        listFragment.downloadMovieData(searchTerm);

        //downloadMovieData(searchTerm);
    }


}
