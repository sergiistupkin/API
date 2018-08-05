package com.qa.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;

public class RestClient {
	
	//GET method 
	public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException, JSONException {
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url);  // http get request
		CloseableHttpResponse closeableHttpResponce = httpClient.execute(httpget); //hit the GET url
		return closeableHttpResponce;
	}
	
	//GET method with Header
		public CloseableHttpResponse get(String url, HashMap<String, String> headerMap) throws ClientProtocolException, IOException, JSONException {
			
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet httpget = new HttpGet(url);  // http get request
			for(Map.Entry<String, String> entry : headerMap.entrySet()) {
				httpget.addHeader(entry.getKey(), entry.getValue());
			}
			CloseableHttpResponse closeableHttpResponce = httpClient.execute(httpget); //hit(send) the GET url
			return closeableHttpResponce;
		}

		
		//POST Method
		public CloseableHttpResponse post(String url, String entityString, HashMap<String, String> headerMap) throws ClientProtocolException, IOException {
			
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);    //for request
			httpPost.setEntity(new StringEntity(entityString));   //for payload
			
			//for headers
			for(Map.Entry<String, String> entry : headerMap.entrySet()){
				httpPost.addHeader(entry.getKey(), entry.getValue()); 
			}
			CloseableHttpResponse closeableHttpResponce = httpClient.execute(httpPost);   //submit request
			return closeableHttpResponce;
		}
		
}
 