package com.pinch.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.pinch.android.R;
import com.pinch.android.SearchFilters;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class SearchFiltersActivity extends AppCompatActivity {

    SearchFilters filters;
    Calendar fromCalendar;
    Calendar toCalendar;

    //views
    TextView tvFromDate;
    TextView tvToDate;
    Spinner spinnerDistance;
    Spinner spinnerEvent;
    Spinner spinnerSkills;

    //pickers
    DatePickerDialog fromDateDialog;
    DatePickerDialog toDateDialog;

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
                        null,
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

        spinnerDistance = (Spinner) findViewById(R.id.spinnerDistance);
        spinnerEvent = (Spinner) findViewById(R.id.spinnerEvent);
        spinnerSkills = (Spinner) findViewById(R.id.spinnerSkills);
        tvFromDate = (TextView) findViewById(R.id.tvFromDate);
        tvToDate = (TextView) findViewById(R.id.tvToDate);

        spinnerDistance.setSelection(filters.getDistanceSpinnerIndex());
        spinnerEvent.setSelection(filters.getEventTypeSpinnerIndex());
        spinnerSkills.setSelection(filters.getSkillsRequiredSpinnerIndex());
        if(filters.getFromCalendar() != null) {
            fromCalendar = filters.getFromCalendar();
            tvFromDate.setText((fromCalendar.get(Calendar.MONTH)+1) + "/" + fromCalendar.get(Calendar.DATE) + "/" + fromCalendar.get(Calendar.YEAR));
        }
        if(filters.getToCalendar() != null) {
            toCalendar = filters.getToCalendar();
            tvToDate.setText((toCalendar.get(Calendar.MONTH)+1) + "/" + toCalendar.get(Calendar.DATE) + "/" + toCalendar.get(Calendar.YEAR));
        }
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
                                fromCalendar.set(year, month, day, 0, 0, 1);
                                tvFromDate.setText((month+1) + "/" + day + "/" + year);
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
                                toCalendar.set(year, month, day, 23, 59);
                                tvToDate.setText((month+1) + "/" + day + "/" + year);
                            }
                        },
                        toCalendar.get(Calendar.YEAR),
                        toCalendar.get(Calendar.MONTH),
                        toCalendar.get(Calendar.DAY_OF_MONTH)
                );
                toDateDialog.show(getFragmentManager(), "Datepickerdialog");
            }
        });
    }
}
