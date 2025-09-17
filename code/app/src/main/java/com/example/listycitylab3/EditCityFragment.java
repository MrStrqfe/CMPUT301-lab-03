package com.example.listycitylab3;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

public class EditCityFragment extends DialogFragment {
    private static final String ARG_CITY = "city";
    private City city;

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT, // make it fill the width
                    ViewGroup.LayoutParams.WRAP_CONTENT  // keep height based on content
            );
        }
    }

    // Listener interface
    public interface EditCityListener {
        void onCityEdited();
    }

    private EditCityListener listener;

    // create a edit city fragment using the hints given in the lab description
    public static EditCityFragment newInstance(City city) {
        EditCityFragment fragment = new EditCityFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CITY, city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EditCityListener) {
            listener = (EditCityListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement EditCityListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            city = (City) getArguments().getSerializable(ARG_CITY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_city, container, false);

        EditText nameEditText = view.findViewById(R.id.editTextCityName);
        EditText provinceEditText = view.findViewById(R.id.editTextProvince);
        Button saveButton = view.findViewById(R.id.buttonSave);

        if (city != null) {
            nameEditText.setText(city.getName());
            provinceEditText.setText(city.getProvince());
        }

        saveButton.setOnClickListener(v -> {
            String newName = nameEditText.getText().toString();
            String newProvince = provinceEditText.getText().toString();

            if (city != null) {
                city.setName(newName);
                city.setProvince(newProvince);
            }

            if (listener != null) {
                listener.onCityEdited(); // tell MainActivity to refresh
            }

            dismiss(); // closes the dialog fragment
        });


        return view;
    }
}

