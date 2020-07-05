package com.teamhikingkenya.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.teamhikingkenya.HikingPlacesDetailsActivity;
import com.teamhikingkenya.R;
import com.teamhikingkenya.model.HikingPlaces;

import java.util.ArrayList;
import java.util.List;

public class HikingPlacesAdapter extends RecyclerView.Adapter<HikingPlacesAdapter.HikingPlacesViewHolder> implements Filterable {

    private Context context;
    private List<HikingPlaces> hikingPlacesList;
    private List<HikingPlaces> filtered_hikingPlacesList;

    public static final String EXTRA_HIKING_PLACES = "hiking_places";

    public HikingPlacesAdapter(Context context, List<HikingPlaces> hikingPlacesList) {
        this.context = context;
        this.hikingPlacesList = hikingPlacesList;
        filtered_hikingPlacesList = new ArrayList<>(hikingPlacesList);
    }

    @NonNull
    @Override
    public HikingPlacesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.hiking_layout, null);
        return new HikingPlacesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HikingPlacesViewHolder holder, int position) {
        HikingPlaces places = hikingPlacesList.get(position);

        holder.title.setTextColor(Color.BLACK);
        holder.location.setTextColor(Color.BLACK);
        holder.price.setTextColor(Color.BLACK);
        holder.title.setText(places.getTitle());
        holder.location.setText(places.getLocation());
        holder.price.setText(places.getPrice());
        if (places.getImage().equals("")){
            holder.image.setVisibility(View.GONE);
        } else {
            Glide.with(context)
                    .load(places.getImage())
                    .fitCenter()
                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .into(holder.image);
        }

    }

    @Override
    public int getItemCount() {
        return hikingPlacesList.size();
    }

    @Override
    public Filter getFilter(){
        return hikingFilter;
    }

    private Filter hikingFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<HikingPlaces> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(filtered_hikingPlacesList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (HikingPlaces places : filtered_hikingPlacesList){
                    if (places.getTitle().toLowerCase().contains(filterPattern) || places.getLocation().toLowerCase().contains(filterPattern)
                            || places.getDescription().toLowerCase().contains(filterPattern) || places.getPrice().contains(filterPattern)){
                        filteredList.add(places);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            hikingPlacesList. clear();
            hikingPlacesList.addAll((List) results.values );
            notifyDataSetChanged();
        }
    };

    class HikingPlacesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title, location, price;
        ImageView image;

        HikingPlacesViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            location = itemView.findViewById(R.id.location);
            price = itemView.findViewById(R.id.price);
            image = itemView.findViewById(R.id.image_view);

            itemView.setOnClickListener(this);

        }
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(context, HikingPlacesDetailsActivity.class);
            intent.putExtra(EXTRA_HIKING_PLACES, hikingPlacesList.get(getAdapterPosition()));
            //context.startActivity(intent);
        }
    }

}
