package com.qa.test;



import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;

public class GetApiTest extends TestBase{

	TestBase testBase;
	String genUrl;
	String apiUrl;
	String url;
	RestClient restClient;
	CloseableHttpResponse closeableHttpResponce;
	
	@BeforeMethod
	public void setUp() {
		testBase = new TestBase();
		genUrl = prop.getProperty("URL");
		apiUrl = prop.getProperty("serviceURL");
		url = genUrl+apiUrl;
	}
	
	@Test (priority=1)
	public void getTest() throws ClientProtocolException, IOException, JSONException {
		restClient = new RestClient(); 
		closeableHttpResponce = restClient.get(url);
		
		//status code
		int statusCode =  closeableHttpResponce.getStatusLine().getStatusCode();
		System.out.println("Status Code: "+statusCode);
		
		Assert.assertEquals(statusCode, RESPONCE_STATUS_CODE_200, "Whong Status Code");
		
		//Json String
		String responceString = EntityUtils.toString(closeableHttpResponce.getEntity(), "UTF-8");	
		
		JSONObject responceJson = new JSONObject(responceString);
		System.out.println("JSON Code: "+responceJson);
		
					//single value assertion
		String perPageValue = TestUtil.getValueByJPath(responceJson, "/per_page");
		System.out.println("Value of per page:"+ perPageValue);
		Assert.assertEquals(Integer.parseInt(perPageValue), 3);
		
		String totalValue = TestUtil.getValueByJPath(responceJson, "/total");
		System.out.println("Value of total:"+ totalValue);
		Assert.assertEquals(totalValue, "12");
					//get value from JSON Array
		String lastName = TestUtil.getValueByJPath(responceJson, "/data[0]/last_name");
		String id = TestUtil.getValueByJPath(responceJson, "/data[0]/id");
		String firstName = TestUtil.getValueByJPath(responceJson, "/data[0]/first_name");
		System.out.println(lastName);
		System.out.println(id);
		System.out.println(firstName);
		
		Assert.assertEquals(Integer.parseInt(id), 1);
		
		// all headers
		Header[] headerArray = closeableHttpResponce.getAllHeaders();
		
		HashMap<String, String> allHeaders = new HashMap<String, String>();
		
		for(Header header : headerArray) {
			allHeaders.put(header.getName(), header.getValue());
		}
		System.out.println("Headers Array "+allHeaders);
	}
	
	@Test (priority=2)
	public void getTestWithHeaders() throws ClientProtocolException, IOException, JSONException {
		restClient = new RestClient(); 
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
		
		closeableHttpResponce = restClient.get(url, headerMap);
		
		//status code
		int statusCode =  closeableHttpResponce.getStatusLine().getStatusCode();
		System.out.println("Status Code: "+statusCode);
		
		Assert.assertEquals(statusCode, RESPONCE_STATUS_CODE_200, "Whong Status Code");
		
		//Json String
		String responceString = EntityUtils.toString(closeableHttpResponce.getEntity(), "UTF-8");	
		
		JSONObject responceJson = new JSONObject(responceString);
		System.out.println("JSON Code: "+responceJson);
		
					//single value assertion
		String perPageValue = TestUtil.getValueByJPath(responceJson, "/per_page");
		System.out.println("Value of per page:"+ perPageValue);
		Assert.assertEquals(Integer.parseInt(perPageValue), 3);
		
		String totalValue = TestUtil.getValueByJPath(responceJson, "/total");
		System.out.println("Value of total:"+ totalValue);
		Assert.assertEquals(totalValue, "12");
					//get value from JSON Array
		String lastName = TestUtil.getValueByJPath(responceJson, "/data[0]/last_name");
		String id = TestUtil.getValueByJPath(responceJson, "/data[0]/id");
		String firstName = TestUtil.getValueByJPath(responceJson, "/data[0]/first_name");
		System.out.println(lastName);
		System.out.println(id);
		System.out.println(firstName);
		
		Assert.assertEquals(Integer.parseInt(id), 1);
		
		// all headers
		Header[] headerArray = closeableHttpResponce.getAllHeaders();
		
		HashMap<String, String> allHeaders = new HashMap<String, String>();
		
		for(Header header : headerArray) {
			allHeaders.put(header.getName(), header.getValue());
		}
		System.out.println("Headers Array "+allHeaders);
	}
}
