package com.teamhikingkenya.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.teamhikingkenya.R;
import com.teamhikingkenya.adapters.HikingPlacesAdapter;
import com.teamhikingkenya.model.HikingPlaces;
import com.teamhikingkenya.utils.URLS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private Context context;
    private RecyclerView recyclerView;

    private List<HikingPlaces> hikingPlacesList;
    private HikingPlacesAdapter hikingPlacesAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.context = getContext();

        recyclerView = view.findViewById(R.id.hikingPlacesRecycler);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(context, 16));

        hikingPlacesList = new ArrayList<>();

    }

    private void loadHikingPlaces(){
        @SuppressLint("StaticFieldLeak")
        class LoadHikingPlaces extends AsyncTask<Void, Void, List<HikingPlaces>> {
            @Override
            protected void onPreExecute(){
                super.onPreExecute();
            }

            @Override
            protected List<HikingPlaces> doInBackground(Void... voids) {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, URLS.URL_HIKING_PLACES,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i("Hiking places", "response: " + response);
                                try {

                                    JSONArray array = new JSONArray(response);

                                    for (int i = 0; i < array.length(); i++) {

                                        JSONObject jsonHiking = array.getJSONObject(i);

                                        HikingPlaces hikingPlaces = new HikingPlaces();
                                        hikingPlaces.setId(jsonHiking.getInt("id"));
                                        hikingPlaces.setTitle(jsonHiking.getString("title"));
                                        hikingPlaces.setImage(jsonHiking.getString("image"));
                                        hikingPlaces.setPrice(jsonHiking.getString("price"));
                                        hikingPlaces.setLocation(jsonHiking.getString("location"));
                                        hikingPlaces.setDescription(jsonHiking.getString("description"));

                                        hikingPlacesList.add(hikingPlaces);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });

                Volley.newRequestQueue(context).add(stringRequest);

                return hikingPlacesList;
            }

            @Override
            protected void onPostExecute(List<HikingPlaces> productsList) {
                hikingPlacesAdapter = new HikingPlacesAdapter(context, hikingPlacesList);
                recyclerView.setAdapter(hikingPlacesAdapter);
                super.onPostExecute(productsList);
            }
        }

        LoadHikingPlaces loadHikingPlaces = new LoadHikingPlaces();
        loadHikingPlaces.execute();
    }
}
