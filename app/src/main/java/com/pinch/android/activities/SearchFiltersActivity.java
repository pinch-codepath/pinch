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
import android.widget.TextView;

import com.pinch.android.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.DateFormat;
import java.util.Calendar;

public class SearchFiltersActivity extends AppCompatActivity {

    DatePickerDialog fromDateDialog;
    DatePickerDialog toDateDialog;
    TimePickerDialog fromTimeDialog;
    TimePickerDialog toTimeDialog;
    TextView tvFromDate;
    TextView tvToDate;
    TextView tvFromTime;
    TextView tvToTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_filters);

        setupDateTimePickers();

        Button btSearch = (Button) findViewById(R.id.btSearch);
        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent();
                setResult(200, searchIntent);
                finish();
            }
        });
    }

    private void setupDateTimePickers() {
        tvFromDate = (TextView) findViewById(R.id.tvFromDate);
        tvToDate = (TextView) findViewById(R.id.tvToDate);
        tvFromTime = (TextView) findViewById(R.id.tvFromTime);
        tvToTime = (TextView) findViewById(R.id.tvToTime);

        tvFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                fromDateDialog = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
                                tvFromDate.setText(month + "/" + day + "/" + year);
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                fromDateDialog.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        tvToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                toDateDialog = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
                                tvToDate.setText(month + "/" + day + "/" + year);
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                toDateDialog.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        tvFromTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                fromTimeDialog = TimePickerDialog.newInstance(
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(RadialPickerLayout radialPickerLayout, int hour, int minute) {
                                tvFromTime.setText(hour + ":" + minute);
                            }
                        },
                        now.get(Calendar.HOUR),
                        now.get(Calendar.MINUTE),
                        android.text.format.DateFormat.is24HourFormat(getApplicationContext())
                );
                fromTimeDialog.show(getFragmentManager(), "Timepickerdialog");
            }
        });

        tvToTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                toTimeDialog = TimePickerDialog.newInstance(
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(RadialPickerLayout radialPickerLayout, int hour, int minute) {
                                tvToTime.setText(hour + ":" + minute);
                            }
                        },
                        now.get(Calendar.HOUR),
                        now.get(Calendar.MINUTE),
                        android.text.format.DateFormat.is24HourFormat(getApplicationContext())
                );
                toTimeDialog.show(getFragmentManager(), "Timepickerdialog");
            }
        });
    }
}
