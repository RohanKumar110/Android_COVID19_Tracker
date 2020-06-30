package com.rk.covid_19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private int positionCountry;
    TextView txtCountry, txtCases,txtRecovered,txtCritical,txtActive,txtTodayCases,txtDeaths,txtTodayDeaths,txtTests,txtPopulation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        positionCountry = intent.getIntExtra("Position",0);

        getSupportActionBar().setTitle("Details of "+AffectiveCountries.modelList.get(positionCountry).getCountry());
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtCountry = findViewById(R.id.txtDetailCountry);
        txtCases = findViewById(R.id.txtDetailCases);
        txtTodayCases = findViewById(R.id.txtDetailTodayCases);
        txtDeaths = findViewById(R.id.txtDetailsDeath);
        txtTodayDeaths = findViewById(R.id.txtDetailTodayDeath);
        txtRecovered = findViewById(R.id.txtDetailRecovered);
        txtActive = findViewById(R.id.txtDetailActive);
        txtCritical = findViewById(R.id.txtDetailCritical);
        txtTests = findViewById(R.id.txtDetailTests);
        txtPopulation = findViewById(R.id.txtDetailPopulation);

        setValues();
    }

    public void setValues()
    {
        txtCountry.setText(AffectiveCountries.modelList.get(positionCountry).getCountry());
        txtCases.setText(AffectiveCountries.modelList.get(positionCountry).getCases());
        txtTodayCases.setText(AffectiveCountries.modelList.get(positionCountry).getTodayCases());
        txtDeaths.setText(AffectiveCountries.modelList.get(positionCountry).getDeaths());
        txtTodayDeaths.setText(AffectiveCountries.modelList.get(positionCountry).getTodayDeaths());
        txtRecovered.setText(AffectiveCountries.modelList.get(positionCountry).getRecovered());
        txtActive.setText(AffectiveCountries.modelList.get(positionCountry).getActive());
        txtCritical.setText(AffectiveCountries.modelList.get(positionCountry).getCritical());
        txtTests.setText(AffectiveCountries.modelList.get(positionCountry).getTests());
        txtPopulation.setText(AffectiveCountries.modelList.get(positionCountry).getPopulation());
    }
}