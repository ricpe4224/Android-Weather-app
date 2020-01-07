package markoperic.unizd.vreremska1;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

 interface WeatherService {
    @GET("data/2.5/weather?")
    Call<WeatherResponse> getCurrentWeatherData(@Query("q") String city, @Query("units") String units, @Query("APPID") String app_id);
}

