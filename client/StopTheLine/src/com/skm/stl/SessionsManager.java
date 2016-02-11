package com.skm.stl;

public class SessionsManager {

	public static User currSession;

	public static void setSession(User u) {
		currSession = u;
	}

	public static boolean isConnected() {
		if (currSession != null)
			return true;
		else
			return false;
	}

	public static User getCurrSession() {
		if (isConnected())
			return currSession;
		else
			return null;
	}

	public static void cleanSession() {
		currSession = null;
	}

}
