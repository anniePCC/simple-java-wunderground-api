package com.amp.casual.wunderground;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.junit.BeforeClass;
import org.junit.Test;

import com.amp.casual.wunderground.util.SimpleWeather;

public class WUndergroundTest 
{
	private static final Properties prop = new Properties();
	private static SimpleWeather weather;

	@BeforeClass
	public static void setUpClass() throws IOException{
		prop.load(new FileInputStream("src/test/resources/wunderground.properties"));
		String key = prop.getProperty("apiKey");
		String zip = prop.getProperty("zipCode");
		weather = new SimpleWeather(key, zip);
		System.out.println("Setup called");
	}

	@Test
    public void testCurrentWeather()
    {
    	String currentWeather = weather.getWeatherData().getCurrentObservation().getWeather();
    	System.out.println(currentWeather);
        assertEquals(!currentWeather.isEmpty(), true);
    }
    
	@Test
    public void testCurrentTemperature(){
    	double currentTempF = weather.getWeatherData().getCurrentObservation().getTempF();
    	System.out.println(currentTempF);
    	assertTrue(currentTempF != 0.0);
    }
}
