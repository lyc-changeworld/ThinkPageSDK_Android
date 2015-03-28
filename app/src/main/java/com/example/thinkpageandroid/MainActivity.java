package com.example.thinkpageandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.thinkpage.sdk.TPAirQuality;
import com.thinkpage.sdk.TPCity;
import com.thinkpage.sdk.TPWeather;
import com.thinkpage.sdk.TPWeatherFuture;
import com.thinkpage.sdk.TPWeatherManager;
import com.thinkpage.sdk.TPWeatherManagerDelegate;

import java.util.ArrayList;

public class MainActivity extends Activity implements TPWeatherManagerDelegate{
    static private String key = ""; // put a valid key here

	TPWeatherManager _weatherManager = null;
    int _fetchType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _fetchType = 0;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void onGetWeatherClick(View view)
    {
    	if (_weatherManager == null)
    	{
    		_weatherManager = new TPWeatherManager(key, this);
    	}
       	EditText aText = (EditText) findViewById(R.id.editText1);
        String string = aText.getText().toString();
        switch (_fetchType) {
            case 0:
            _weatherManager.fetchAllWeather(new TPCity(string), TPWeatherManager.TPWeatherReportLanguage.kSimplifiedChinese, TPWeatherManager.TPTemperatureUnit.kCelsius, TPWeatherManager.TPAirQualitySource.kAQIAll);
            break;
            case 1:
            _weatherManager.fetchCurrentWeather(new TPCity(string), TPWeatherManager.TPWeatherReportLanguage.kSimplifiedChinese, TPWeatherManager.TPTemperatureUnit.kCelsius/*, TPWeatherManager.TPAirQualitySource.kAQIAll*/);
            break;
            case 2:
            _weatherManager.fetchFutureWeather(new TPCity(string), TPWeatherManager.TPWeatherReportLanguage.kSimplifiedChinese, TPWeatherManager.TPTemperatureUnit.kCelsius/*, TPWeatherManager.TPAirQualitySource.kAQIAll*/);
            break;
            case 3:
            _weatherManager.fetchAirQuality(new TPCity(string), TPWeatherManager.TPWeatherReportLanguage.kSimplifiedChinese, TPWeatherManager.TPTemperatureUnit.kCelsius, TPWeatherManager.TPAirQualitySource.kAQIAll);
            break;
            case 4:
            _weatherManager.fetchWeatherSuggestions(new TPCity(string), TPWeatherManager.TPWeatherReportLanguage.kSimplifiedChinese, TPWeatherManager.TPTemperatureUnit.kCelsius/*, TPWeatherManager.TPAirQualitySource.kAQIAll*/);
            break;
        }
    }
    
	public void OnRequestSuccess(TPCity city, TPWeather report)
	{
		Toast.makeText(this, "Response received for city" + city.description(), Toast.LENGTH_LONG).show();
        final android.widget.ListView listView = (android.widget.ListView) findViewById(R.id.listView);
        final ArrayList<String> list = new ArrayList<String>();
        if (report.city != null)
            list.add("城市名: "+report.city.getName() + "ID:" + report.city.getCityID());
        if (report.sunsetTime != null)
            list.add("Sunset time : "+report.sunsetTime);
        if (report.sunriseTime != null)
            list.add("Sunset time : "+report.sunriseTime);
        if (report.currentWeather != null)
        {
            list.add("天气 : "+report.currentWeather.text);
            list.add("代码 : "+report.currentWeather.code);
            list.add("气温 : "+report.currentWeather.temperature);
            list.add("能见度 : "+report.currentWeather.visibility);
            list.add("湿度 : "+report.currentWeather.humidity);
            list.add("风速 : "+report.currentWeather.windSpeed);
            list.add("风力 : "+report.currentWeather.windScale);
            list.add("风向 : "+report.currentWeather.windDirection);
        }
        if (report.futureWeathers != null)
        {
            TPWeatherFuture futureWeather = report.futureWeathers[0];
            list.add("预报日期 : "+ futureWeather.date);
            list.add("白天 : "+ futureWeather.code1);
            list.add("夜间 : "+ futureWeather.code2);
            list.add("星期 : "+ futureWeather.day);
            list.add("天气描述 : "+ futureWeather.text);
            list.add("最高气温: "+ futureWeather.temperatureHigh);
            list.add("最低气温: "+ futureWeather.temperatureLow);
        }
        if (report.airQualities != null)
        {
            TPAirQuality airQuality = report.airQualities[0];
            list.add("pm10 : "+ airQuality.pm10);
            list.add("pm25 : "+ airQuality.pm25);
            list.add("aqi : "+ airQuality.aqi);
            list.add("co : "+ airQuality.co);
            list.add("no2 : "+ airQuality.no2);
            list.add("o3: "+ airQuality.o3);
            list.add("so2: "+ airQuality.so2);
        }
        if (report.weatherSuggestions != null)
        {
            list.add(report.weatherSuggestions.carwashBrief + ":" + report.weatherSuggestions.carwashDetails);
            list.add(report.weatherSuggestions.dressingBrief + ":" + report.weatherSuggestions.dressingDetails);
            list.add(report.weatherSuggestions.fluBrief + ":" + report.weatherSuggestions.fluDetails);
            list.add(report.weatherSuggestions.sportBrief + ":" + report.weatherSuggestions.sportDetails);
            list.add(report.weatherSuggestions.travelBrief + ":" + report.weatherSuggestions.travelBrief);
        }
        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(itemsAdapter);
	}
	
	public void OnRequestFailure(TPCity city, String errorString)
	{
		Toast.makeText(this, "Request failed for city" + city.description(), Toast.LENGTH_LONG).show();
	}

    public void onCheckboxClick(View view)
    {
        // Is the view now checked?
        //boolean checked = ((android.widget.CheckBox) view).isChecked();
        android.widget.CheckBox aCheckbox1 = (android.widget.CheckBox) findViewById(R.id.checkBox1);
        android.widget.CheckBox aCheckbox2 = (android.widget.CheckBox) findViewById(R.id.checkBox2);
        android.widget.CheckBox aCheckbox3 = (android.widget.CheckBox) findViewById(R.id.checkBox3);
        android.widget.CheckBox aCheckbox4 = (android.widget.CheckBox) findViewById(R.id.checkBox4);
        android.widget.CheckBox aCheckbox5 = (android.widget.CheckBox) findViewById(R.id.checkBox5);
        // Check which checkbox was clicked
        aCheckbox1.setChecked(false);
        aCheckbox2.setChecked(false);
        aCheckbox3.setChecked(false);
        aCheckbox4.setChecked(false);
        aCheckbox5.setChecked(false);

        switch(view.getId())
        {
            case R.id.checkBox1:
                aCheckbox1.setChecked(true);
                _fetchType = 0;
                break;
            case R.id.checkBox2:
                aCheckbox2.setChecked(true);
                _fetchType = 1;
                break;
            case R.id.checkBox3:
                aCheckbox3.setChecked(true);
                _fetchType = 2;
                break;
            case R.id.checkBox4:
                aCheckbox4.setChecked(true);
                _fetchType = 3;
                break;
            case R.id.checkBox5:
                aCheckbox5.setChecked(true);
                _fetchType = 4;
                break;
        }

    }
}

