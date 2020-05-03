package com.bigbang.globaltravelnow.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bigbang.globaltravelnow.R;
import com.bigbang.globaltravelnow.model.CountryContinent;
import com.bigbang.globaltravelnow.model.Result;
import com.bigbang.globaltravelnow.util.DebugLogger;
import com.bigbang.globaltravelnow.viewmodel.GlobalTravelViewModel;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity {

    GlobalTravelViewModel globalTravelViewModel;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    TravelAdvisoryOneFragment travelAdvisoryOneFragment;
    ArrayAdapter<String> countryAdapter;

    @BindView(R.id.earth_image)
    ImageView earthImageView;

    @BindView(R.id.continent_spinner)
    Spinner continentSpinner;

    @BindView(R.id.country_spinner)
    Spinner countrySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        globalTravelViewModel = ViewModelProviders.of(this).get(GlobalTravelViewModel.class);

        Glide.with(this).load(R.drawable.earth).into(earthImageView);

        ArrayAdapter<String> continentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, globalTravelViewModel.getContinentArray());
        continentSpinner.setAdapter(continentAdapter);
        continentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                countryAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                                           android.R.layout.simple_spinner_dropdown_item,
                                                           globalTravelViewModel.getCountriesByContinentArray(continentSpinner.getSelectedItem().toString()));
                countrySpinner.setAdapter(countryAdapter);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        travelAdvisoryOneFragment = new TravelAdvisoryOneFragment();
    }

    @OnClick(R.id.get_country_data_button)
    public void getCountryData() {
        compositeDisposable.add(globalTravelViewModel.getGlobalTravelData(countrySpinner.getSelectedItem().toString())
                .subscribe(globalTravelResult -> {
            displayInformationRx(globalTravelResult);
        }, throwable -> {
            DebugLogger.logError(throwable);
        }));
    }

    public void displayInformationRx(Result globalTravelResult) {

        travelAdvisoryOneFragment.setTravelAdvisoryResult(globalTravelResult);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.travel_advisory_frame, travelAdvisoryOneFragment)
                .commit();

        String countryCode = globalTravelResult.getData().getCode().getCountry();
        Log.d("TAG_X", "result: name: " + countryCode);
        Log.d("TAG_X", "request: item: " + globalTravelResult.getData().getSituation().getRating());
        Log.d("TAG_X", "request: item: " + globalTravelResult.getData().getLang().getEn().getAdvice());
    }

    public void returnFromAdvisory() {
        getSupportFragmentManager()
                .beginTransaction()
                .remove(travelAdvisoryOneFragment)
                .commit();
    }
}
