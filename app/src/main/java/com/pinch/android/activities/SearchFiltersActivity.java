package com.pinch.android.activities;

import android.content.Intent;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.pinch.android.R;
import com.pinch.android.SearchFilters;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.DateFormat;
import java.util.Calendar;

public class SearchFiltersActivity extends AppCompatActivity {

    SearchFilters filters;
    Calendar fromCalendar;
    Calendar toCalendar;

    //views
    TextView tvFromDate;
    TextView tvToDate;
    TextView tvFromTime;
    TextView tvToTime;
    Spinner spinnerDistance;
    Spinner spinnerEvent;
    Spinner spinnerSkills;

    //pickers
    DatePickerDialog fromDateDialog;
    DatePickerDialog toDateDialog;
    TimePickerDialog fromTimeDialog;
    TimePickerDialog toTimeDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_filters);

        setupViewObjects();
        setupDateTimePickers();

        Button btSearch = (Button) findViewById(R.id.btSearch);
        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent();
                filters = new SearchFilters(
                        fromCalendar,
                        toCalendar,
                        spinnerDistance.getSelectedItemPosition(),
                        spinnerEvent.getSelectedItemPosition(),
                        spinnerSkills.getSelectedItemPosition());
                searchIntent.putExtra("filters", filters);
                setResult(200, searchIntent);
                finish();
            }
        });
    }

    private void setupViewObjects() {
        filters = getIntent().getParcelableExtra("filters");

        tvFromDate = (TextView) findViewById(R.id.tvFromDate);
        tvToDate = (TextView) findViewById(R.id.tvToDate);
        tvFromTime = (TextView) findViewById(R.id.tvFromTime);
        tvToTime = (TextView) findViewById(R.id.tvToTime);
        spinnerDistance = (Spinner) findViewById(R.id.spinnerDistance);
        spinnerEvent = (Spinner) findViewById(R.id.spinnerEvent);
        spinnerSkills = (Spinner) findViewById(R.id.spinnerSkills);

        if(filters.getFromCalendar() != null) {
            fromCalendar = filters.getFromCalendar();
            tvFromDate.setText(fromCalendar.get(Calendar.MONTH) + "/" + fromCalendar.get(Calendar.DATE) + "/" + fromCalendar.get(Calendar.YEAR));
            tvFromTime.setText(fromCalendar.get(Calendar.HOUR) + ":" + fromCalendar.get(Calendar.MINUTE));
        }
        if(filters.getToCalendar() != null) {
            toCalendar = filters.getToCalendar();
            tvToDate.setText(toCalendar.get(Calendar.MONTH) + "/" + toCalendar.get(Calendar.DATE) + "/" + toCalendar.get(Calendar.YEAR));
            tvToTime.setText(toCalendar.get(Calendar.HOUR) + ":" + toCalendar.get(Calendar.MINUTE));
        }
        spinnerDistance.setSelection(filters.getDistanceSpinnerIndex());
        spinnerEvent.setSelection(filters.getEventTypeSpinnerIndex());
        spinnerSkills.setSelection(filters.getSkillsRequiredSpinnerIndex());
    }

    private void setupDateTimePickers() {
        tvFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fromCalendar == null) {
                    fromCalendar = Calendar.getInstance();
                }
                fromDateDialog = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
                                fromCalendar.set(year, month, day);
                                tvFromDate.setText(month + "/" + day + "/" + year);
                            }
                        },
                        fromCalendar.get(Calendar.YEAR),
                        fromCalendar.get(Calendar.MONTH),
                        fromCalendar.get(Calendar.DAY_OF_MONTH)
                );
                fromDateDialog.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        tvToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(toCalendar == null) {
                    toCalendar = Calendar.getInstance();
                }
                toDateDialog = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
                                toCalendar.set(year, month, day);
                                tvToDate.setText(month + "/" + day + "/" + year);
                            }
                        },
                        toCalendar.get(Calendar.YEAR),
                        toCalendar.get(Calendar.MONTH),
                        toCalendar.get(Calendar.DAY_OF_MONTH)
                );
                toDateDialog.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        tvFromTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fromCalendar == null) {
                    fromCalendar = Calendar.getInstance();
                }
                fromTimeDialog = TimePickerDialog.newInstance(
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(RadialPickerLayout radialPickerLayout, int hour, int minute) {
                                fromCalendar.set(
                                        fromCalendar.get(Calendar.YEAR),
                                        fromCalendar.get(Calendar.MONTH),
                                        fromCalendar.get(Calendar.DATE),
                                        hour,
                                        minute
                                );
                                tvFromTime.setText(hour + ":" + minute);
                            }
                        },
                        fromCalendar.get(Calendar.HOUR),
                        fromCalendar.get(Calendar.MINUTE),
                        android.text.format.DateFormat.is24HourFormat(getApplicationContext())
                );
                fromTimeDialog.show(getFragmentManager(), "Timepickerdialog");
            }
        });

        tvToTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(toCalendar == null) {
                    toCalendar = Calendar.getInstance();
                }
                toTimeDialog = TimePickerDialog.newInstance(
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(RadialPickerLayout radialPickerLayout, int hour, int minute) {
                                toCalendar.set(
                                        toCalendar.get(Calendar.YEAR),
                                        toCalendar.get(Calendar.MONTH),
                                        toCalendar.get(Calendar.DATE),
                                        hour,
                                        minute
                                );
                                tvToTime.setText(hour + ":" + minute);
                            }
                        },
                        toCalendar.get(Calendar.HOUR),
                        toCalendar.get(Calendar.MINUTE),
                        android.text.format.DateFormat.is24HourFormat(getApplicationContext())
                );
                toTimeDialog.show(getFragmentManager(), "Timepickerdialog");
            }
        });
    }
}
