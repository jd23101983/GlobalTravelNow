package com.bigbang.globaltravelnow.network;

import com.bigbang.globaltravelnow.model.Result;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import static com.bigbang.globaltravelnow.util.Constants.URL_POSTFIX;

public interface GlobalTravelService {

    @GET(URL_POSTFIX)
    Observable<Result> getCountryData(@Query("country") String countryCode);
}
