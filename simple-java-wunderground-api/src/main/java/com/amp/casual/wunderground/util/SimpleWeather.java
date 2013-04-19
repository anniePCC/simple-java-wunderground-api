package com.amp.casual.wunderground.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import com.amp.casual.wunderground.datatypes.WUndergroundData;
import com.google.gson.Gson;

public class SimpleWeather {
	private static final Gson gson = new Gson();
	
	private final String API_END_POINT = "http://api.wunderground.com/api/";
	private WUndergroundData wUndergroundData; 
	
	public SimpleWeather(String apiKey, String location){
		String fullQuery = 
				API_END_POINT + 
				apiKey +
				"/conditions/q/" +
				location +	".json";
		System.out.println(fullQuery);
		InputStream source = retrieveStream(fullQuery);
		Reader reader = new InputStreamReader(source);
		WUndergroundData weatherData = gson.fromJson(reader, WUndergroundData.class);
		System.out.println("Got weather for " + location + " : " + weatherData.getCurrentObservation().getWeather());
		this.wUndergroundData = weatherData;
	}
	
	public WUndergroundData getWeatherData(){
		return wUndergroundData;
	}
	
	private InputStream retrieveStream(String url) {
		HttpClient client = HttpClientBuilder.create().build(); 
		HttpGet getRequest = new HttpGet(url);
		try {
			HttpResponse getResponse = client.execute(getRequest);
			final int statusCode = getResponse.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				System.out.println("Error " + statusCode + " for URL " + url);
//				Log.w(getClass().getSimpleName(), "Error " + statusCode
//						+ " for URL " + url);
				return null;
			}
			HttpEntity getResponseEntity = getResponse.getEntity();
			return getResponseEntity.getContent();
		}
		catch (IOException e) {
			getRequest.abort();
			System.out.println("Error for URL " + url);
			e.printStackTrace();
//			Log.w(getClass().getSimpleName(), "Error for URL " + url, e);
		}
		return null;
	}
}
