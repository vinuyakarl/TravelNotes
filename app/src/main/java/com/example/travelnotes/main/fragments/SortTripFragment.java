package com.example.travelnotes.main.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.travelnotes.R;
import com.example.travelnotes.main.adapters.SortAdapter;
import com.example.travelnotes.main.entity.SortOption;

import java.util.ArrayList;

public class SortTripFragment extends DialogFragment {
    private Button ascendingButton;
    private Button descendingButton;

    private Button closeButton;
    private SortAdapter sortAdapter;
    ArrayList<SortOption> sortOptions;
    private ListView sortOptionsListView;
    private SortOption chosenSort;
    private String sortDirection;
    private OnSortOptionSelectedListener sortOptionSelectedListener;

    public interface OnSortOptionSelectedListener {
        void onSortOptionSelected(SortOption chosenSort, String sortDirection);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            sortOptionSelectedListener = (OnSortOptionSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnSortOptionSelectedListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomDialog);
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_sort_trip, null);

        getUIElements(view);
        populateSortOptionsList();

        sortAdapter = new SortAdapter(getContext(), sortOptions);
        sortOptionsListView.setAdapter(sortAdapter);

        closeButton.setOnClickListener(v -> closeButtonPressed());
        ascendingButton.setOnClickListener(v -> sortButtonPressed("Ascending"));
        descendingButton.setOnClickListener(v -> sortButtonPressed("Descending"));
        builder.setView(view);
        return builder.create();
    }

    public void getUIElements(View view) {
        ascendingButton = view.findViewById(R.id.ascendingButton);
        descendingButton = view.findViewById(R.id.descendingButton);
        closeButton = view.findViewById(R.id.closeButton);
        sortOptionsListView = view.findViewById(R.id.sortOptionsListView);
    }

    public void populateSortOptionsList() {
        sortOptions = new ArrayList<>();
        sortOptions.add(new SortOption("Cost", false));
        sortOptions.add(new SortOption("Date", false));
        sortOptions.add(new SortOption("Name", false));
    }

    public void closeButtonPressed() {
        dismiss();
    }

    public void sortButtonPressed(String directionOfSort) {
        getSortOption();
        if (chosenSort != null) {
            sortDirection = directionOfSort;
            if (sortOptionSelectedListener != null) {
                sortOptionSelectedListener.onSortOptionSelected(chosenSort, sortDirection);
            }
            dismiss();
        }
    }

    public void getSortOption() {
        boolean isValid = isValid();
        if (isValid) {
            for (SortOption sortOption: sortOptions) {
                if (sortOption.isChecked()) {
                    chosenSort = sortOption;
                }
            }
        }
        else {
            Toast.makeText(getContext(), "Invalid Selection", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isValid() {
        boolean oneChecked = false;
        for (SortOption sortOption: sortOptions) {

            // Check if sortOption is checked
            if (sortOption.isChecked()) {

                // If there is already a checked option, it is invalid
                if (oneChecked) {
                    return false;
                }
                oneChecked = true;
            }
        }
        return oneChecked;
    }
}



