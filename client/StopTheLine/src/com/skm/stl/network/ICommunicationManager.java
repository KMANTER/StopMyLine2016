package com.skm.stl.network;

import org.apache.http.HttpResponse;

import com.skm.stl.User;

public interface ICommunicationManager {

	
	public User register(User u );
	public User Connect(User u);
	public HttpResponse CreateGameRoom();
	public HttpResponse CheckForGame();
}
