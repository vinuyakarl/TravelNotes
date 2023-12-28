package com.example.travelnotes.main.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.travelnotes.R;
import com.example.travelnotes.main.entity.SortOption;

import java.util.ArrayList;

public class SortAdapter extends ArrayAdapter<SortOption> {
    private ArrayList<SortOption> sortOptions;
    private LayoutInflater inflater;

    public SortAdapter(Context context, ArrayList<SortOption> sortOptions) {
        super(context, 0, sortOptions);
        this.sortOptions = sortOptions;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = inflater.inflate(R.layout.sort_trip_list_content, null);
        }
        SortOption sort = sortOptions.get(position);
        TextView sortOption = view.findViewById(R.id.sortOption);
        CheckBox checkBox = view.findViewById(R.id.sortOptionCheckBox);
        sortOption.setText(sort.getSortType());

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sort.setChecked(isChecked);
        });

        return view;
    }
}
