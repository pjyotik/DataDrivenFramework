package com.learning.rough;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.learning.utilities.MonitoringMail;
import com.learning.utilities.TestConfig;

public class TestHostAddr {

	public static void main(String[] args) throws UnknownHostException, AddressException, MessagingException {
		
		MonitoringMail mail = new MonitoringMail();
		String messageBody ="http://"+InetAddress.getLocalHost().getHostAddress()
				+":8080/job/LiveProject_DataDrivenTestFramework/Extent_5fReport/";
		System.out.println(messageBody);
		
		mail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to, TestConfig.subject, messageBody);
		
		
	}

}
