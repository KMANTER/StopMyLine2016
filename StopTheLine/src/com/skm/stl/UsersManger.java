package com.skm.stl;

import java.util.ArrayList;
import java.util.List;

public class UsersManger {

	public static List<User> registredUsers= new ArrayList<User>();
	
	public static boolean existUser(User u){
		for(int i=0; i<registredUsers.size() ; i++){
			if(registredUsers.get(i).getName().equals(u.getName()))
				return true;
		}
		return false;
	}
	public static void addUser(User u){
		registredUsers.add(u);
	}
}
