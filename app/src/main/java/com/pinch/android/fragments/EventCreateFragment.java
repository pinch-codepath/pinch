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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.api.client.util.DateTime;
import com.pinch.android.R;
import com.pinch.android.Utils;
import com.pinch.android.remote.InsertEventTask;
import com.pinch.backend.eventEndpoint.model.Event;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EventCreateFragment extends Fragment implements OnClickListener, AddressDialogFragment.AddressDialogListener, SkillsDialogFragment.SkillsDialogListener {

    protected View fragmentView;

    EditText etTitle;
    EditText etSkills;
    EditText etAddress;
    EditText etDescription;

    TextView tvDate;
    TextView tvPhoto;
    TextView tvTimeTo;
    TextView tvTimeFrom;

    Calendar dateCalendar;
    Calendar timeFromCalendar;
    Calendar timeToCalendar;
    DatePickerDialog dateDialog;
    TimePickerDialog timeToDialog;
    TimePickerDialog timeFromDialog;

    Button btnCreate;

    String skill1;
    String skill2;
    String skill3;
    String addressZip;
    String addressCity;
    String addressState;
    String addressStreet;
    String addressNeighborhood;

    Bitmap eventImage = null;

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
        btnCreate = (Button) fragmentView.findViewById(R.id.btnCreate);
    }

    protected void setupListener() {
        etAddress.setOnClickListener(this);
        etSkills.setOnClickListener(this);
        tvDate.setOnClickListener(this);
        tvTimeFrom.setOnClickListener(this);
        tvTimeTo.setOnClickListener(this);
        tvPhoto.setOnClickListener(this);
        btnCreate.setOnClickListener(this);
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

            case R.id.btnCreate:
                createEvent();
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
        this.addressStreet = street;
        this.addressCity = city;
        this.addressZip = zip;
        this.addressState = state;
        this.addressNeighborhood = neighborhood;
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
        this.skill1 = skill1;
        this.skill2 = skill2;
        this.skill3 = skill3;
    }

    public void createEvent() {
        Event event = new Event();
        String title = etTitle.getText().toString();
        if (title == null || title.isEmpty()) {
            Toast.makeText(getContext(), "Please enter event title", Toast.LENGTH_SHORT).show();
            return;
        }
        String description = etDescription.getText().toString();
        if (description == null || description.isEmpty()) {
            Toast.makeText(getContext(), "Please enter event description", Toast.LENGTH_SHORT).show();
            return;
        }
        if (this.addressStreet == null || this.addressStreet.isEmpty()) {
            Toast.makeText(getContext(), "Please enter event address", Toast.LENGTH_SHORT).show();
            return;
        }
        if (this.skill1 == null || this.skill1.isEmpty()) {
            Toast.makeText(getContext(), "Please enter required skills", Toast.LENGTH_SHORT).show();
            return;
        }

        event.setTitle(title);
        event.setDescription(description);
        event.setStartTime(new DateTime(timeFromCalendar.getTime()));
        event.setEndTime(new DateTime(timeToCalendar.getTime()));
        event.setAddressStreet(this.addressStreet);
        event.setAddressCity(this.addressCity);
        event.setAddressState(this.addressState);
        event.setAddressZip(Long.valueOf(this.addressZip).longValue());
        event.setAddressNeighborhood(this.addressNeighborhood);
        event.setSkill1(this.skill1);
        event.setSkill2(this.skill2);
        event.setSkill3(this.skill3);
        long organizationId = Utils.getMyOrganization().getId();

        new InsertEventTask(new InsertEventTask.InsertEventResultsListener() {
            @Override
            public void onEventInsert() {
                Toast.makeText(getContext(), "Event created!", Toast.LENGTH_SHORT).show();
            }
        }, organizationId, event, eventImage).execute();
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
        Uri photoUri = data.getData();
        try {
            eventImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), photoUri);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Image Upload Failed", Toast.LENGTH_SHORT).show();
        }
        if (eventImage == null) {
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
        ivPic.setImageBitmap(eventImage);
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
