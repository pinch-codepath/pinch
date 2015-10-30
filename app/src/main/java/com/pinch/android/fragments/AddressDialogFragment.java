package com.pinch.android.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pinch.android.R;

public class AddressDialogFragment extends DialogFragment {
    Button btnOk;
    Button btnCancel;
    EditText etStreet;
    EditText etNeighborhood;
    EditText etCity;
    EditText etState;
    EditText etZip;

    public AddressDialogFragment() {
    }

    public static AddressDialogFragment newInstance() {
        AddressDialogFragment frag = new AddressDialogFragment();
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_enter_address, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews(view);
        setupListeners();
    }

    private void setupViews(View view) {
        btnOk = (Button) view.findViewById(R.id.btnOk);
        btnCancel = (Button) view.findViewById(R.id.btnCancel);
        etStreet = (EditText) view.findViewById(R.id.etStreet);
        etNeighborhood = (EditText) view.findViewById(R.id.etNeighborhood);
        etCity = (EditText) view.findViewById(R.id.etCity);
        etState = (EditText) view.findViewById(R.id.etState);
        etZip = (EditText) view.findViewById(R.id.etZip);
    }

    private void setupListeners() {
        ////////////////////////////////////////////////////////
        // On Click listener for Button "Ok"
        ////////////////////////////////////////////////////////
        btnOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String zip = etZip.getText().toString();
                String city = etCity.getText().toString();
                String state = etState.getText().toString();
                String street = etStreet.getText().toString();
                String neighborhood = etNeighborhood.getText().toString();

                if (street == null || street.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter street name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (city == null || city.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter city", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (state == null || state.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter state", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (zip == null || zip.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter zip", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (neighborhood == null || neighborhood.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter neighborhood", Toast.LENGTH_SHORT).show();
                    return;
                }

                AddressDialogListener listener = (AddressDialogListener) getTargetFragment();
                listener.onFinishAddressDialog(street, city, state, zip, neighborhood);
                dismiss();
            }
        });
        ////////////////////////////////////////////////////////
        // On Click listener for Button "CANCEL"
        ////////////////////////////////////////////////////////
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public interface AddressDialogListener {
        void onFinishAddressDialog(String street,
                                   String city,
                                   String state,
                                   String zip,
                                   String neighborhood);
    }


}
