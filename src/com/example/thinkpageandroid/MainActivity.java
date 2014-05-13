package com.example.thinkpageandroid;

import com.thinkpage.sdk.*;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements TPWeatherManagerDelegate{
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
    		_weatherManager = new TPWeatherManager("TPCLIENTIOS", this);
    	}
       	EditText aText = (EditText) findViewById(R.id.editText1);
        String string = aText.getText().toString();
        switch (_fetchType) {
            case 0:
            _weatherManager.fetchAllWeather(new TPCity(string), TPWeatherManager.TPWeatherReportLanguage.kEnglish, TPWeatherManager.TPTemperatureUnit.kCelsius, TPWeatherManager.TPAirQualitySource.kAQIAll);
            break;
            case 1:
            _weatherManager.fetchCurrentWeather(new TPCity(string), TPWeatherManager.TPWeatherReportLanguage.kEnglish, TPWeatherManager.TPTemperatureUnit.kCelsius/*, TPWeatherManager.TPAirQualitySource.kAQIAll*/);
            break;
            case 2:
            _weatherManager.fetchFutureWeather(new TPCity(string), TPWeatherManager.TPWeatherReportLanguage.kEnglish, TPWeatherManager.TPTemperatureUnit.kCelsius/*, TPWeatherManager.TPAirQualitySource.kAQIAll*/);
            break;
            case 3:
            _weatherManager.fetchAirQuality(new TPCity(string), TPWeatherManager.TPWeatherReportLanguage.kEnglish, TPWeatherManager.TPTemperatureUnit.kCelsius, TPWeatherManager.TPAirQualitySource.kAQIAll);
            break;
            case 4:
            _weatherManager.fetchWeatherSuggestions(new TPCity(string), TPWeatherManager.TPWeatherReportLanguage.kEnglish, TPWeatherManager.TPTemperatureUnit.kCelsius/*, TPWeatherManager.TPAirQualitySource.kAQIAll*/);
            break;
        }
    }
    
	public void OnRequestSuccess(TPCity city, TPWeather report)
	{
		Toast.makeText(this, "Response received for city" + city.description(), Toast.LENGTH_LONG).show();
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
