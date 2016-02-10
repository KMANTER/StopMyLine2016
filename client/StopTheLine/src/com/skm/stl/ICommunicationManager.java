package com.skm.stl;

import javax.xml.ws.Response;

import org.apache.http.HttpResponse;

public interface ICommunicationManager {

	
	public User register(User u );
	public User Connect(User u);
	public HttpResponse CreateGameRoom(String name);
	public HttpResponse CheckForGame();
}
