package com.skm.stl;

public class UsersManger {

	public static User Curr_User= null;
	
	public static boolean existUser(User u){
			if(Curr_User != null )
				return true;	
		return false;
	}
	public static void addUser(User u){
		Curr_User = u;
	}
}
