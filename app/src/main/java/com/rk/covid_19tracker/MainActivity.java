package com.rk.covid_19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    TextView txtCases,txtRecovered,txtCritical,txtActive,txtTodayCases,txtTotalDeaths,txtTodayDeaths,txtAffectedCountries;
    SimpleArcLoader loader;
    ScrollView scrollView;
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCases = findViewById(R.id.txtCases);
        txtRecovered = findViewById(R.id.txtRecovered);
        txtCritical = findViewById(R.id.txtCritical);
        txtActive = findViewById(R.id.txtActive);
        txtTodayCases = findViewById(R.id.txtTodayCases);
        txtTotalDeaths = findViewById(R.id.txtTotalDeaths);
        txtTodayDeaths = findViewById(R.id.txtTodayDeaths);
        txtAffectedCountries = findViewById(R.id.txtAffectedCountries);

        loader = findViewById(R.id.loader);
        scrollView = findViewById(R.id.scrollview_stats);
        pieChart = findViewById(R.id.piechart);

        fetchStats();

    }

    public void fetchStats()
    {
        final String API = "https://corona.lmao.ninja/v2/all";
        loader.start();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    txtCases.setText(jsonObject.getString("cases"));
                    txtRecovered.setText(jsonObject.getString("recovered"));
                    txtCritical.setText(jsonObject.getString("critical"));
                    txtActive.setText(jsonObject.getString("active"));
                    txtTodayCases.setText(jsonObject.getString("todayCases"));
                    txtTotalDeaths.setText(jsonObject.getString("deaths"));
                    txtTodayDeaths.setText(jsonObject.getString("todayDeaths"));
                    txtAffectedCountries.setText(jsonObject.getString("affectedCountries"));

                    pieChart.addPieSlice(new PieModel("Cases",Integer.parseInt(txtCases.getText().toString()), Color.parseColor("#FFA726")));
                    pieChart.addPieSlice(new PieModel("Recovered",Integer.parseInt(txtRecovered.getText().toString()), Color.parseColor("#66BB6A")));
                    pieChart.addPieSlice(new PieModel("Deaths",Integer.parseInt(txtTotalDeaths.getText().toString()), Color.parseColor("#EF5350")));
                    pieChart.addPieSlice(new PieModel("Active",Integer.parseInt(txtActive.getText().toString()), Color.parseColor("#29B6F6")));
                    pieChart.startAnimation();

                    loader.stop();
                    loader.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);


                } catch (JSONException e) {
                    e.printStackTrace();
                    loader.stop();
                    loader.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError error) {
                new Handler(Looper.myLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public void trackCounties(View view)
    {
        startActivity(new Intent(MainActivity.this,AffectiveCountries.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}