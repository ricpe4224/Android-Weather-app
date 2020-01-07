package markoperic.unizd.vreremska1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public final String BaseUrl = "http://api.openweathermap.org/";
    public final String AppId = "2e65127e909e178d0af311a81f39948c";
    public static String units ="metric";
    public static String city ;
    private EditText etUnos;
    private TextView tvImeGrada;
    private TextView tvTemp;
    private TextView tvOsjecaj;
    private TextView tvVlaga;
    private TextView tvTlak;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etUnos=findViewById(R.id.etUnos);
        tvTemp=findViewById(R.id.tvTrenutnaTemp);
        tvTlak=findViewById(R.id.tvTlak);
        tvOsjecaj=findViewById(R.id.tvOsjecaj);
        tvVlaga=findViewById(R.id.tvVlaga);
        tvImeGrada=findViewById(R.id.tvImeGrada);

        ImageButton btn = (ImageButton) this.findViewById(R.id.btnTrazi);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                city=etUnos.getText().toString();
                getCurrentData();

                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });


    }
    public void getCurrentData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherService service = retrofit.create(WeatherService.class);
        Call<WeatherResponse> call = service.getCurrentWeatherData(city,units, AppId);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                if (response.code() == 200) {
                    WeatherResponse weatherResponse = response.body();
                    assert weatherResponse != null;
                    tvImeGrada.setText(city.toUpperCase()+", "+weatherResponse.sys.getCountry().toUpperCase());
                    tvTemp.setText("TRENUTNA TEMP. JE "+weatherResponse.main.temp.toString()+"C");
                    tvTlak.setText("TLAK JE "+weatherResponse.main.pressure.toString()+"hpa");
                    tvOsjecaj.setText("OSJECAJ TEMP JE "+weatherResponse.main.feelsLike.toString()+"C");
                    tvVlaga.setText("VLAGA JE "+weatherResponse.main.humidity.toString()+"%");


                    Log.i("-----------------------------------------------------------------------", String.valueOf(weatherResponse.main.tempMin));
                    Log.i("-----------------------------------------------------------------------", String.valueOf(weatherResponse.sys.country));



                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherResponse> call, @NonNull Throwable t) {
                //weatherData.setText(t.getMessage());
                Log.i("-----------------------------------------------------------------------", "nesto ne radi ");
                Toast.makeText(MainActivity.this, "Probaj ponovo kasnije", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


//   http://api.openweathermap.org/data/2.5/weather?q=Zadar&APPID=c5f3aff2417a5090d65fe44c9bfdce1e
