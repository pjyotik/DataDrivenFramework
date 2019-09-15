package com.learning.rough;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TestProperties {
	
	
	public static void main(String args[])  {
		
		Properties config = new Properties();
		Properties OR = new Properties();
		FileInputStream fis;
		try {
			fis = new FileInputStream(System.getProperty("user.dir") +"\\src\\test\\resources\\properties\\Config.properties");
			config.load(fis);
			
			fis = new FileInputStream(System.getProperty("user.dir") +"\\src\\test\\resources\\properties\\OR.properties");
			OR.load(fis);
		} 
		catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} 
		catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
		System.out.println(config.getProperty("browser"));
		System.out.println(OR.getProperty("bankMgrLoginBtn"));
		System.out.println(OR.getProperty("addCustBtn"));
		System.out.println(OR.getProperty("BnkMgrLogin"));
		
	}

}
