package com.ace.cms;

import org.springframework.context.ApplicationContext;

public class ApplicationContextHolder {
	
	
	public static ApplicationContext context;
	
	public static ApplicationContext getApplicationContext(){
		return context;
	}
	
	public static void setApplicationContext(ApplicationContext context){
		ApplicationContextHolder.context =  context ; 
	}
	
	
	
}
