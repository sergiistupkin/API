package com.qa.test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.data.UserData;

public class PostApiTest extends TestBase{
	TestBase testBase;
	String genUrl;
	String apiUrl;
	String url;
	RestClient restClient;
	CloseableHttpResponse closeableHttpResponse;
	
	@BeforeMethod
	public void setUp() {
		testBase = new TestBase();
		genUrl = prop.getProperty("URL");
		apiUrl = prop.getProperty("serviceURL");
		url=genUrl+apiUrl;
	}
	
	@Test
	public void postTest() throws JsonGenerationException, JsonMappingException, IOException, JSONException {
		restClient = new RestClient();
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
		
		//jakson API: 
		ObjectMapper mapper = new ObjectMapper();
		UserData users = new UserData("Sergii", "QA Engineer"); //expected users object
		
		// convert object JAVA to JSON (Marshaling)
		mapper.writeValue(new File("./src/main/java/com/qa/data/data.json"), users);
		
		//convert object to json in String
		String userJsonString = mapper.writeValueAsString(users);
		System.out.println("JSON rsponse: "+userJsonString);
		
		//post Method
		closeableHttpResponse = restClient.post(url, userJsonString, headerMap);
		
		//validation
		//check status
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println(statusCode);
		Assert.assertEquals(statusCode, testBase.RESPONCE_STATUS_CODE_201);
		
		//jsonString
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8"); 
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("Response from API is: "+ responseJson);
		
		//validate response value (json to java object)
		UserData userObj = mapper.readValue(responseString, UserData.class); //actual users object
		System.out.println(userObj);
		
		System.out.println(users.getName().equals(userObj.getName()));
		Assert.assertTrue(users.getName().equals(userObj.getName()));
		
		System.out.println(users.getJob().equals(userObj.getJob()));
		Assert.assertTrue(users.getJob().equals(userObj.getJob()));
		
		System.out.println(userObj.getId());
		System.out.println(userObj.getCreatedAt());
		
	}
	

}
