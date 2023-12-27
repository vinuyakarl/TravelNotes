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
import com.example.travelnotes.main.entity.Itinerary;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

public class ItineraryAdapter extends ArrayAdapter<Itinerary> {
    private ArrayList<Itinerary> itineraryList;
    private LayoutInflater inflater;

    public ItineraryAdapter(Context context, ArrayList<Itinerary> itineraryList) {
        super(context, 0, itineraryList);
        this.itineraryList = itineraryList;
        this.inflater = LayoutInflater.from(context);
    }

    public void sortItineraryList() {
        Collections.sort(this.itineraryList, Comparator.comparing(Itinerary::getDateAndTime));
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        sortItineraryList();
        View view = convertView;

        if (view == null) {
            view = inflater.inflate(R.layout.view_trip_list_content, null);
        }
        Itinerary itinerary = itineraryList.get(position);

        TextView itineraryActivity = view.findViewById(R.id.itineraryActivityTextView);
        TextView itineraryLocation = view.findViewById(R.id.itineraryLocationTextView);
        TextView itineraryCost = view.findViewById(R.id.itineraryCostTextView);
        TextView itineraryDate = view.findViewById(R.id.itineraryDateTextView);
        TextView itineraryTime = view.findViewById(R.id.itineraryTimeTextView);

        itineraryActivity.setText(itinerary.getActivity());
        itineraryLocation.setText(itinerary.getLocation());

        itineraryCost.setText(String.format("$%.2f", itinerary.getCost()));

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd", Locale.getDefault());
        String date = dateFormat.format(itinerary.getDate());
        itineraryDate.setText(date);
        itineraryTime.setText(itinerary.getTimeStart() + " - " + itinerary.getTimeEnd());

        return view;
    }

}
