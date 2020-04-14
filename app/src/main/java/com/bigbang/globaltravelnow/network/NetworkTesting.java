package com.bigbang.globaltravelnow.network;

import android.util.Log;
import android.widget.Toast;

import com.bigbang.globaltravelnow.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class NetworkTesting {

    public NetworkTesting() {}

    public void doTest() {

        Log.d("TAG_X", "inside doTest()");

        //make a new OkHttpClient object
        OkHttpClient client = new OkHttpClient();

        //new request object - method chaining
        Request request = new Request.Builder()
                .url("https://www.travel-advisory.info/api?countrycode=GB")
                .build();

        //make a request from a Call class
        Call call = client.newCall(request);

        //make and asynchronous call with the enqueue method
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    Log.d("TAG_X", "inside onResponse()");
                    if (response.isSuccessful()) {
                        Log.d("TAG_X", "inside onResponse() - was successful");
                        String jsonData = response.body().string();

                        JSONObject jStatus = new JSONObject(jsonData);
                        JSONObject request1 = jStatus.getJSONObject("api_status");
                        JSONObject item = request1.getJSONObject("request");
                        String countryCode = item.getString("item");

                        Log.d("TAG_X", "jsonData: countryCode = " + countryCode);

                        JSONObject jData = new JSONObject(jsonData);
                        JSONObject code = jData.getJSONObject("data");
                        JSONObject more = code.getJSONObject(countryCode.toUpperCase());
                        String name = more.getString("name");
                        Log.d("TAG_X", "jsonData: name = " + name);

                        String continent = more.getString("continent");
                        Log.d("TAG_X", "jsonData: continent = " + continent);

                        JSONObject advisory = more.getJSONObject("advisory");
                        String score = advisory.getString("score");
                        Log.d("TAG_X", "jsonData: score = " + score);
                        String message = advisory.getString("message");
                        Log.d("TAG_X", "jsonData: message = " + message);
                        String updated = advisory.getString("updated");
                        Log.d("TAG_X", "jsonData: updated = " + updated);

/*
           "data": {
			"DE": {
			  "iso_alpha2": "DE",
			  "name": "Germany",
			  "continent": "EU",
			  "advisory": {
				"score": 1.5,
				"sources_active": 4,
				"message": "Germany has a current risk level of 1.5 (out of 5). We Advise: Travelling Germany is (relatively) safe.",
				"updated": "2019-02-12 19:53:38",
				"source": "https:\/\/www.travel-advisory.info\/germany"
			  }
			}
		  }
 */
                        //mCurrentData =
                        //getCurrentDetails(jsonData);
                    } else {
                        Log.d("TAG_X", "ERROR");

                    }
                } catch (IOException e) {
                    Log.e(TAG, "Exception caught", e);
                } catch (JSONException e) {
                    Log.e(TAG, "Exception Caught", e);
                }
            }


        });
    }

    private  void getCurrentDetails(String jsonData) throws JSONException {

        Log.d("TAG_X", "inside getCurrentDetails() . . . ");

        JSONObject results = new JSONObject(jsonData);
        String code = results.getString("item");
        Log.d("TAG_X", "FROM JSON: code = " + code.toString());

        /*
        JSONObject currently = new JSONObject("currently");

        CurrentWeather currentWeather = new CurrentWeatvher();
        currentWeather.setHumidity(currently.getDouble("humidity"));
        currentWeather.setTime(currently.getLong("time"));
        currentWeather.setIcon(currently.getString("icon"));
        currentWeather.setPrecipChance(currently.getDouble("precipProbability"));
        currentWeather.setSummary(currently.getString("summary"));
        currentWeather.setTemperature(currently.getDouble("temperature"));
        currentWeather.setTimeZone(timezone);
        //return null
        //return new CurrentWeather();

        Log.d(TAG, currentWeather.getFormattedTime());

        return currentWeather;
*/
    }

}

