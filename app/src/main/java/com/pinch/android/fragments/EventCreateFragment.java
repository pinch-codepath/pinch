package com.pinch.android.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.view.View.OnClickListener;

import com.pinch.android.R;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;

//import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
//import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

//import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
//import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EventCreateFragment extends Fragment implements OnClickListener, AddressDialogFragment.AddressDialogListener, SkillsDialogFragment.SkillsDialogListener {

    protected View fragmentView;

    EditText etTitle;
    EditText etAddress;
    EditText etDescription;
    EditText etSkills;

    TextView tvDate;
    TextView tvTimeFrom;
    TextView tvTimeTo;

    TextView tvPhoto;

    Calendar calendar;
    Calendar fromCalendar;
    Calendar toCalendar;
    DatePickerDialog dateDialog;
    TimePickerDialog toTimeDialog;
    TimePickerDialog fromTimeDialog;


    public EventCreateFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_event_create, container, false);
        setupViews();
        setupListener();
        //setupDateTimePickers();
        return fragmentView;
    }

    protected void setupViews() {
        etTitle = (EditText) fragmentView.findViewById(R.id.etTitle);
        etSkills = (EditText) fragmentView.findViewById(R.id.etSkills);
        etAddress = (EditText) fragmentView.findViewById(R.id.etAddress);
        etDescription = (EditText) fragmentView.findViewById(R.id.etDescription);
        tvDate = (TextView) fragmentView.findViewById(R.id.tvDate);
        tvTimeFrom = (TextView) fragmentView.findViewById(R.id.tvTimeFrom);
        tvTimeTo = (TextView) fragmentView.findViewById(R.id.tvTimeTo);
        tvPhoto = (TextView) fragmentView.findViewById(R.id.tvPhoto);
    }

    protected void setupListener() {
//        etAddress.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                FragmentManager fm = getFragmentManager();
//                AddressDialogFragment addressDialog = AddressDialogFragment.newInstance();
//                addressDialog.setTargetFragment(get, 1);
//                addressDialog.show(fm, "activity_compose");
//            }
//        });
        etAddress.setOnClickListener(this);
        etSkills.setOnClickListener(this);
        tvDate.setOnClickListener(this);
        tvTimeFrom.setOnClickListener(this);
        tvTimeTo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        FragmentManager fm = getFragmentManager();

        switch (v.getId()) {

            case R.id.etAddress:
                AddressDialogFragment addressDialog = AddressDialogFragment.newInstance();
                addressDialog.setTargetFragment(this, 1);
                addressDialog.show(fm, "activity_compose");
                break;

            case R.id.etSkills:
                SkillsDialogFragment skillsDialog = SkillsDialogFragment.newInstance();
                skillsDialog.setTargetFragment(this, 1);
                skillsDialog.show(fm, "activity_compose");
                break;

            case R.id.tvDate:

                if (calendar == null) {
                    calendar = Calendar.getInstance();
                }

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                dateDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        tvDate.setText(new SimpleDateFormat("MM/dd").format(newDate.getTime()));
                    }
                }, year, month, day);

                dateDialog.show();

                break;

            case R.id.tvTimeFrom:

                if (calendar == null) {
                    calendar = Calendar.getInstance();
                }

                if (fromCalendar == null) {
                    fromCalendar = Calendar.getInstance();

                }

                int hour = calendar.get(Calendar.HOUR);
                int min = calendar.get(Calendar.MINUTE);

                fromTimeDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        fromCalendar.set(
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DATE),
                                hour,
                                minute
                        );
                        tvTimeFrom.setText(hour + ":" + minute);

                    }
                }, hour, min, true);

                fromTimeDialog.show();
                break;

            default:
                break;
        }

    }

    public void onFinishAddressDialog(String street, String city, String state, String zip, String neighborhood) {
        String address = street + " (" + neighborhood + "), " + city + ", " + state + ", " + zip;
        etAddress.setText(address);
    }

    public void onFinishSkillsDialog(String skill1, String skill2, String skill3) {
        String skills = skill1 + ", " + skill2 + ", " + skill3;
        etSkills.setText(skills);
    }


}
