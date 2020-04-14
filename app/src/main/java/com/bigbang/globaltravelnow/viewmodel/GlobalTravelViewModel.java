package com.bigbang.globaltravelnow.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.bigbang.globaltravelnow.model.Result;
import com.bigbang.globaltravelnow.network.GlobalTravelRetrofitInstance;
import com.bigbang.globaltravelnow.network.NetworkTesting;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GlobalTravelViewModel extends AndroidViewModel {

    private Map<String, String> countriesMap;
    private List<String> countriesArray;
    private GlobalTravelRetrofitInstance globalTravelRetrofitInstance;
    private NetworkTesting networkTesting = new NetworkTesting();

    public GlobalTravelViewModel(@NonNull Application application) {
        super(application);

        setupCountryMapAndArray();
        globalTravelRetrofitInstance = new GlobalTravelRetrofitInstance();
        networkTesting.doTest();
    }

    public void setupCountryMapAndArray() {
        countriesMap = new HashMap<>();
        countriesArray = new ArrayList<String>();
        for (String iso : Locale.getISOCountries()) {
            Locale l = new Locale("", iso);
            countriesMap.put(l.getDisplayCountry(), iso);
            countriesArray.add(l.getDisplayCountry());
        }
    }

    public List<String> getCountriesArray() {
        return countriesArray;
    }

    public String getCountryCode(String countryName) { return countriesMap.get(countryName); }

    public Observable<Result> getGlobalTravelData(String countryNameCode) {
        return  globalTravelRetrofitInstance
                .getGlobalTravelData(countriesMap.get(countryNameCode))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
}
