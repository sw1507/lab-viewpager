package edu.uw.fragmentdemo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MoviewListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MoviewListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

/**
 * 1. 新建一个fragment，放在和MainActivity同样路径下面
 * 2. fragment xml 中加一个listview
 * 3. activity main xml中加一个<fragment
 * 把main java中的code 剪切到fragment java中
 * 加了一个frameLayout
 *
 * main 里面加了code fragmentManager，
 * 把xml中的fragment去掉，要在java中create一个dynamic fragment
 *
 */
public class MoviewListFragment extends Fragment {
    public static final String TAG = "ListFragment";
    private ArrayAdapter<Movie> adapter;
    public static final String SEARCH_TERM_KEY = "search_term";
    private static final String TAG = "MainActivity";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
   public MoviewListFragment newInstance(String searchTerm){
        Bundle args = new Bundle();
       args.putString(SEARCH_TERM_KEY, searchTerm);

       MoviewListFragment fragment = new MoviewListFragment();
       fragment.setArguments(args);

       return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_moview_list,container,false);


        adapter = new ArrayAdapter<Movie>(getActivity(),
                R.layout.list_item, R.id.txt_item, new ArrayList<Movie>());

        ListView listView = (ListView)rootView.findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = (Movie) parent.getItemAtPosition(position);
                Log.v(TAG, "You clicked on: " + movie);
            }
        });

        Bundle args = getArguments();
        if(args!=null){
            String searchTerm = args.getString(SEARCH_TERM_KEY);
            downloadMovieData(searchTerm);
        }
        //downloadMovieData("Mean Girls");
        return rootView;
    }

    //download media information from iTunes
    public void downloadMovieData(String searchTerm) {

        String urlString = "";
        try {
            urlString = "https://itunes.apple.com/search?term="+ URLEncoder.encode(searchTerm, "UTF-8")+"&media=movie&entity=movie&limit=25";
            //Log.v(TAG, urlString);
        }catch(UnsupportedEncodingException uee){
            Log.e(TAG, uee.toString());
            return;
        }

        Request request = new JsonObjectRequest(Request.Method.GET, urlString, null,
                new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject response) {
                        ArrayList<Movie> movies = new ArrayList<Movie>();

                        try {
                            //parse the JSON results
                            JSONArray results = response.getJSONArray("results"); //get array from "search" key
                            for(int i=0; i<results.length(); i++){
                                JSONObject track = results.getJSONObject(i);
                                if(!track.getString("wrapperType").equals("track")) //skip non-track results
                                    continue;
                                String title = track.getString("trackName");
                                String year = track.getString("releaseDate");
                                String description = track.getString("longDescription");
                                String url = track.getString("trackViewUrl");
                                Movie movie = new Movie(title, year, description, url);
                                movies.add(movie);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        adapter.clear();
                        for(Movie movie : movies) {
                            adapter.add(movie);
                        }                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.toString());
            }
        });

        RequestSingleton.getInstance(getActivity()).add(request);
    }
}
