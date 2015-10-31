package com.pinch.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.pinch.android.R;
import com.pinch.android.SearchFilters;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SearchFiltersActivity extends AppCompatActivity {

    SearchFilters filters;
    Calendar fromCalendar;
    Calendar toCalendar;

    //views
    LinearLayout llFromCalendar;
    LinearLayout llToCalendar;
    TextView tvFromMonth;
    TextView tvFromDate;
    TextView tvFromYear;
    TextView tvToMonth;
    TextView tvToDate;
    TextView tvToYear;
    EditText etKeywords;
    Spinner spinnerDistance;


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
                        etKeywords.getText().toString(),
                        fromCalendar,
                        toCalendar,
                        spinnerDistance.getSelectedItemPosition());
                searchIntent.putExtra("filters", filters);
                setResult(200, searchIntent);
                finish();
            }
        });
    }

    private void setupViewObjects() {
        filters = getIntent().getParcelableExtra("filters");

        llFromCalendar = (LinearLayout) findViewById(R.id.llFromCalendar);
        llToCalendar = (LinearLayout) findViewById(R.id.llToCalendar);
        tvFromMonth = (TextView) findViewById(R.id.tvFromMonth);
        tvFromDate = (TextView) findViewById(R.id.tvFromDate);
        tvFromYear = (TextView) findViewById(R.id.tvFromYear);
        tvToMonth = (TextView) findViewById(R.id.tvToMonth);
        tvToDate = (TextView) findViewById(R.id.tvToDate);
        tvToYear = (TextView) findViewById(R.id.tvToYear);
        etKeywords = (EditText) findViewById(R.id.etKeywords);
        spinnerDistance = (Spinner) findViewById(R.id.spinnerDistance);

        if (filters.getFromCalendar() != null) {
            fromCalendar = filters.getFromCalendar();
            tvFromMonth.setText(new SimpleDateFormat("MMM").format(fromCalendar.getTime()));
            tvFromDate.setText(new SimpleDateFormat("dd").format(fromCalendar.getTime()));
            tvFromYear.setText(new SimpleDateFormat("yyyy").format(fromCalendar.getTime()));
        }
        if (filters.getToCalendar() != null) {
            toCalendar = filters.getToCalendar();
            tvToMonth.setText(new SimpleDateFormat("MMM").format(toCalendar.getTime()));
            tvToDate.setText(new SimpleDateFormat("dd").format(toCalendar.getTime()));
            tvToYear.setText(new SimpleDateFormat("yyyy").format(toCalendar.getTime()));
        }
        etKeywords.setText(filters.getKeyword());
        spinnerDistance.setSelection(filters.getDistanceSpinnerIndex());
    }

    private void setupDateTimePickers() {
        llFromCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fromCalendar == null) {
                    fromCalendar = Calendar.getInstance();
                }
                fromDateDialog = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
                                fromCalendar.set(year, month, day, 0, 0, 1);
                                tvFromMonth.setText(new SimpleDateFormat("MMM").format(fromCalendar.getTime()));
                                tvFromDate.setText(new SimpleDateFormat("dd").format(fromCalendar.getTime()));
                                tvFromYear.setText(new SimpleDateFormat("yyyy").format(fromCalendar.getTime()));
                            }
                        },
                        fromCalendar.get(Calendar.YEAR),
                        fromCalendar.get(Calendar.MONTH),
                        fromCalendar.get(Calendar.DAY_OF_MONTH)
                );
                fromDateDialog.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        llToCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toCalendar == null) {
                    toCalendar = Calendar.getInstance();
                }
                toDateDialog = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
                                toCalendar.set(year, month, day, 23, 59);
                                tvToMonth.setText(new SimpleDateFormat("MMM").format(toCalendar.getTime()));
                                tvToDate.setText(new SimpleDateFormat("dd").format(toCalendar.getTime()));
                                tvToYear.setText(new SimpleDateFormat("yyyy").format(toCalendar.getTime()));
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
