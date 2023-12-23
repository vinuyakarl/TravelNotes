package com.example.travelnotes.main.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.travelnotes.R;
import com.example.travelnotes.main.entity.Trip;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class TripAdapter extends ArrayAdapter<Trip> {
    private  ArrayList<Trip> trips;
    private LayoutInflater inflater;

    public TripAdapter(Context context, ArrayList<Trip> trips) {
        super(context, 0, trips);
        this.trips = trips;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = inflater.inflate(R.layout.list_content, null);
        }
        Trip trip = trips.get(position);

        TextView tripDestination = view.findViewById(R.id.destinationTextView);
        TextView tripDate = view.findViewById(R.id.dateTextView);
        tripDestination.setText(trip.getDestination());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        String stringStartDate = dateFormat.format(trip.getTripStarted());
        String stringEndDate = dateFormat.format(trip.getTripEnded());
        tripDate.setText(stringStartDate + " - "  + stringEndDate);

        return view;
    }
}
