package com.example.travelnotes.main.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.travelnotes.R;
import com.example.travelnotes.main.entity.Trip;
import com.example.travelnotes.main.entity.TripManager;
import com.example.travelnotes.main.entity.User;
import com.example.travelnotes.main.entity.UserManager;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

public class TripAdapter extends ArrayAdapter<Trip> {
    private ArrayList<Trip> allTrips;
    private LayoutInflater inflater;
    private String chosenSortOption = "Date";
    private String chosenSortDirection = "Ascending";

    public TripAdapter(Context context, ArrayList<Trip> trips) {
        super(context, 0, trips);
        this.allTrips = new ArrayList<>(trips);
        this.inflater = LayoutInflater.from(context);
    }

    public void sortTripsList(@Nullable String sortOption, @Nullable String sortDirection) {
        Comparator<Trip> comparator = null;
        if (sortOption == null || sortDirection == null) {
            sortOption = chosenSortOption;
            sortDirection = chosenSortDirection;
        }

        else {
            chosenSortDirection = sortDirection;
            chosenSortOption = sortOption;
        }


        switch(sortOption) {
            case "Cost": {
                comparator = Comparator.comparing(Trip::getCost);
                break;
            }
            case "Date": {
                comparator = Comparator.comparing(Trip::getTripStarted);
                break;
            }
            case "Name": {
                comparator = Comparator.comparing(Trip::getDestination);
                break;
            }
        }

        if (sortDirection.equals("Descending")) {
            comparator = comparator.reversed();
        }

        Collections.sort(this.allTrips, comparator);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.home_page_list_content, null);
        }
        Trip trip = allTrips.get(position);

        TextView tripDestination = view.findViewById(R.id.destinationTextView);
        TextView tripDate = view.findViewById(R.id.dateTextView);

        tripDestination.setText(trip.getDestination());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        String stringStartDate = dateFormat.format(trip.getTripStarted());
        String stringEndDate = dateFormat.format(trip.getTripEnded());
        tripDate.setText(stringStartDate + " - "  + stringEndDate);

        return view;
    }

    @Override
    public int getCount() {
        return allTrips.size();
    }

    @Override
    public Trip getItem(int position) {
        return allTrips.get(position);
    }

    public void setTrips(ArrayList<Trip> trips) {
        this.allTrips.clear();
        this.allTrips.addAll(trips);
        notifyDataSetChanged();

    }

    public void deleteTrip(Trip trip) {
        this.allTrips.remove(trip);
        notifyDataSetChanged();
    }

}
