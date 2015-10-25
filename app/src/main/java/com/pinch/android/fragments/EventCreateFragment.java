package com.pinch.android.fragments;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.pinch.android.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

//import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
//import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
//import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
//import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

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

    Calendar dateCalendar;
    Calendar timeFromCalendar;
    Calendar timeToCalendar;
    DatePickerDialog dateDialog;
    TimePickerDialog timeToDialog;
    TimePickerDialog timeFromDialog;


    public EventCreateFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_event_create, container, false);
        setupViews();
        setupListener();
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
        etAddress.setOnClickListener(this);
        etSkills.setOnClickListener(this);
        tvDate.setOnClickListener(this);
        tvTimeFrom.setOnClickListener(this);
        tvTimeTo.setOnClickListener(this);
        tvPhoto.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.etAddress:
                showAddressDialog();
                break;

            case R.id.etSkills:
                showSkillsDialog();
                break;

            case R.id.tvDate:
                showDateDialog();
                break;

            case R.id.tvTimeFrom:
                showTimeFromDialog();
                break;

            case R.id.tvTimeTo:
                showTimeToDialog();
                break;

            case R.id.tvPhoto:
                uploadPhoto();
                break;

            default:
                break;
        }

    }

    public void showAddressDialog() {
        FragmentManager fm = getFragmentManager();
        AddressDialogFragment addressDialog = AddressDialogFragment.newInstance();
        addressDialog.setTargetFragment(this, 1);
        addressDialog.show(fm, "activity_compose");
    }

    public void onFinishAddressDialog(String street, String city, String state, String zip, String neighborhood) {
        String address = street + " (" + neighborhood + "), " + city + ", " + state + ", " + zip;
        etAddress.setText(address);
    }

    public void showSkillsDialog() {
        FragmentManager fm = getFragmentManager();
        SkillsDialogFragment skillsDialog = SkillsDialogFragment.newInstance();
        skillsDialog.setTargetFragment(this, 1);
        skillsDialog.show(fm, "activity_compose");
    }

    public void onFinishSkillsDialog(String skill1, String skill2, String skill3) {
        String skills = skill1 + ", " + skill2 + ", " + skill3;
        etSkills.setText(skills);
    }

    public void showDateDialog() {
        if (dateCalendar == null) {
            dateCalendar = Calendar.getInstance();
        }

        int year = dateCalendar.get(Calendar.YEAR);
        int month = dateCalendar.get(Calendar.MONTH);
        int day = dateCalendar.get(Calendar.DAY_OF_MONTH);

        dateDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateCalendar.set(year, monthOfYear, dayOfMonth);
                tvDate.setText(new SimpleDateFormat("MM/dd").format(dateCalendar.getTime()));
            }
        }, year, month, day);

        dateDialog.show();
    }

    public void showTimeFromDialog() {
        if (dateCalendar == null) {
            dateCalendar = Calendar.getInstance();
        }
        if (timeFromCalendar == null) {
            timeFromCalendar = Calendar.getInstance();
        }
        int hour = timeFromCalendar.get(Calendar.HOUR);
        int min = timeFromCalendar.get(Calendar.MINUTE);

        timeFromDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                timeFromCalendar.set(
                        dateCalendar.get(Calendar.YEAR),
                        dateCalendar.get(Calendar.MONTH),
                        dateCalendar.get(Calendar.DATE),
                        hour,
                        minute
                );
                tvTimeFrom.setText(hour + ":" + minute);

            }
        }, hour, min, true);

        timeFromDialog.show();
    }

    public void showTimeToDialog() {
        if (dateCalendar == null) {
            dateCalendar = Calendar.getInstance();
        }
        if (timeToCalendar == null) {
            timeToCalendar = Calendar.getInstance();
        }
        int hour = timeToCalendar.get(Calendar.HOUR);
        int min = timeToCalendar.get(Calendar.MINUTE);

        timeToDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                timeToCalendar.set(
                        dateCalendar.get(Calendar.YEAR),
                        dateCalendar.get(Calendar.MONTH),
                        dateCalendar.get(Calendar.DATE),
                        hour,
                        minute
                );
                tvTimeTo.setText(hour + ":" + minute);

            }
        }, hour, min, true);

        timeToDialog.show();
    }

    public void uploadPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        Bitmap selectedImage = null;
        Uri photoUri = data.getData();
        try {
            selectedImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), photoUri);
        } catch (Exception e) {

        }
        if (selectedImage == null) {
            return;
        }

        /************************
         * Create Image View
         ************************/
        ImageView ivPic = new ImageView(getContext());
        ivPic.setId(R.id.ivPic);
        /************************
         * Set Layout Params
         * ************************/
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_LEFT, R.id.etTitle);
        params.addRule(RelativeLayout.BELOW, R.id.tvOrgName);
       // params.width = 100;
        params.height = 100;
        params.bottomMargin = 15;
        /************************
         * Add View
         * ************************/
        RelativeLayout topLayout = (RelativeLayout) fragmentView.findViewById(R.id.topLayout);
        topLayout.addView(ivPic, params);
        /************************
         * Set Pic
         * ************************/
        ivPic.setImageBitmap(selectedImage);
        /************************
         * Adjust rest of the content
         * ************************/
        RelativeLayout.LayoutParams llParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        llParams.addRule(RelativeLayout.BELOW, 0);
        llParams.addRule(RelativeLayout.BELOW, R.id.ivPic);
        llParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        TextView tvTitle = (TextView) fragmentView.findViewById(R.id.tvTitle);
        tvTitle.setLayoutParams(llParams);
    }

}
