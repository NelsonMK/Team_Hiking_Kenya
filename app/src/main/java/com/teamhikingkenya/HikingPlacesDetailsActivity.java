package com.teamhikingkenya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.teamhikingkenya.model.HikingPlaces;

import java.util.ArrayList;
import java.util.List;

import static com.teamhikingkenya.adapters.HikingPlacesAdapter.EXTRA_HIKING_PLACES;

public class HikingPlacesDetailsActivity extends AppCompatActivity {

    //HikingPlaces hikingPlaces = getHikingPlacesDetails();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hiking_places_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Mt Kenya");

       /*ImageView imageView = findViewById(R.id.hiking_place_image);
        Glide.with(this)
                .load(hikingPlaces.getImage())
                .into(imageView);*/

    }

    /**
     * getting hiking places details obtained from putExtras passed by HikingPlacesAdapter using intents
     * @return HikingPlaces object
     */
    private HikingPlaces getHikingPlacesDetails() {
        Intent intent = getIntent();
        return intent.getParcelableExtra(EXTRA_HIKING_PLACES);
    }
}