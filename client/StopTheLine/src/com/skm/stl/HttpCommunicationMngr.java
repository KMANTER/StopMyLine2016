package com.skm.stl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.Response;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

@SuppressWarnings({ "resource", "unused","deprecation" })
public class HttpCommunicationMngr implements ICommunicationManager {
	
	private static String URL = "http://localhost:8080/";


	@Override
	public User register(User u) {
		// TODO Auto-generated method stub
		String[] args= null;
		StatusLine repStat;
		try {
			
			HttpResponse rep = sendPost(args, "user/create/" +u.getName());
			 repStat = rep.getStatusLine();
			 if(repStat.getStatusCode() == 200)
				 return u;
			 else
				 return null;
				 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public User Connect(User u) {
		// TODO Auto-generated method stub
		StatusLine repStat;
		try {
			
			HttpResponse rep = sendGet("user/" +u.getName());
			 repStat = rep.getStatusLine();
			 if(repStat.getStatusCode() == 200)
				 return u;
			 else
				 return null;
				 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public HttpResponse CreateGameRoom(String name) {
		// TODO Auto-generated method stub
		String[] args= null;
		StatusLine repStat;
		try {
			
			HttpResponse rep = sendPost(args, "game/create/" +name);
			 repStat = rep.getStatusLine();
			 if(repStat.getStatusCode() == 200)
				 return rep;
			 else
				 return null;
				 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}	
	}

	@Override
	public HttpResponse CheckForGame() {
		// TODO Auto-generated method stub
		StatusLine repStat;
		try {
			
			HttpResponse rep = sendGet("game/check");
			 repStat = rep.getStatusLine();
			 if(repStat.getStatusCode() == 200)
				 return rep;
			 else
				 return null;
				 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	// HTTP GET request
	private HttpResponse sendGet(String resource) throws Exception {


		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(URL + resource);
		HttpResponse response = client.execute(request);
		BufferedReader rd = new BufferedReader(
                       new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		System.out.println(result.toString());
		
		return response;

	}

	// HTTP POST request
	private HttpResponse sendPost(String[] args, String resource) throws Exception {


		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(URL + resource);


		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		if(args != null){
			for(int i=0 ; i< args.length ; i++)
				 urlParameters.add(new BasicNameValuePair("user", args[i]));			
		}

		post.setEntity(new UrlEncodedFormEntity(urlParameters));

		HttpResponse response = client.execute(post);
		BufferedReader rd = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		System.out.println(result.toString());

		return response;
	}

}
