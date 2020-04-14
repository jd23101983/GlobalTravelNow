package com.bigbang.globaltravelnow.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bigbang.globaltravelnow.R;
import com.bigbang.globaltravelnow.model.Result;
import com.bigbang.globaltravelnow.util.DebugLogger;
import com.bigbang.globaltravelnow.viewmodel.GlobalTravelViewModel;
import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.Response;

import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity {

    GlobalTravelViewModel globalTravelViewModel;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @BindView(R.id.earth_image)
    ImageView earthImageView;

    @BindView(R.id.country_spinner)
    Spinner countrySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        globalTravelViewModel = ViewModelProviders.of(this).get(GlobalTravelViewModel.class);

        compositeDisposable.add(globalTravelViewModel.getGlobalTravelData("New Zealand").subscribe(globalTravelResult -> {
            displayInformationRx(globalTravelResult);
        }, throwable -> {
            DebugLogger.logError(throwable);
        }));

        Glide.with(this).load(R.drawable.earth).into(earthImageView);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, globalTravelViewModel.getCountriesArray());
        countrySpinner.setAdapter(adapter);
    }



    public void displayInformationRx(Result result) {
        String countryCode = result.getData().getCode().getCountry();
        Log.d("TAG_X", "result: name: " + countryCode);
        Log.d("TAG_X", "request: item: " + result.getData().getSituation().getRating());
        Log.d("TAG_X", "request: item: " + result.getData().getLang().getEn().getAdvice());
    }
}
