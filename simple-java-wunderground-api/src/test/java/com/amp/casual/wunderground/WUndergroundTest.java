package com.amp.casual.wunderground;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.junit.BeforeClass;
import org.junit.Test;

import com.amp.casual.wunderground.util.SimpleWeather;

public class WUndergroundTest 
{
	private static final String PROPERTIES_FILE = "src/test/resources/wunderground.properties";
	private static final String USER_FILE = "src/test/resources/wunderground.user.properties";
	
	private static final Properties prop = new Properties();
	private static SimpleWeather weather;
	

	@BeforeClass
	public static void setUpClass() throws IOException{
		prop.load(new FileInputStream(PROPERTIES_FILE));
		String key = prop.getProperty("apiKey");
		String zip = prop.getProperty("zipCode");
		File userFile = new File(USER_FILE);
		if(userFile.exists()){
			prop.load(new FileInputStream(userFile));
			key = prop.getProperty("apiKey");
			zip = prop.getProperty("zipCode");
		}
		weather = new SimpleWeather(key, zip);
	}
	
	@Test
    public void testCurrentWeather()
    {
    	String currentWeather = weather.getWeatherData().getCurrentObservation().getWeather();
        assertEquals(!currentWeather.isEmpty(), true);
    }
    
	@Test
    public void testCurrentTemperature(){
    	double currentTempF = weather.getWeatherData().getCurrentObservation().getTempF();
    	assertTrue(currentTempF != 0.0);
    }
}
