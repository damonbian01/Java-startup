package com.cstnet.cnnic.net;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import org.junit.Test;

public class InetAddressUtil {

	
	@Test
	public void testDNS() {
		try {
			InetAddress address = InetAddress.getByName("www.baidu.com");
			System.out.println(address);
			System.out.println(address.getHostAddress());
			System.out.println(address.getCanonicalHostName());
		} catch (UnknownHostException e) {
			System.out.println("Counld not find this");
		}
	}
	
	@Test
	public void testIp() {
		try {
			InetAddress address = InetAddress.getByName("220.181.112.244");
			System.out.println(address.getHostName());
			System.out.println(address.getHostAddress());
			System.out.println(address.getCanonicalHostName());
		} catch (UnknownHostException e) {
			System.out.println("Counld not find this");
		}
	}
	
	@Test
	public void testAllNetInterface() throws SocketException {
		Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
		while(interfaces.hasMoreElements()) {
			NetworkInterface in = interfaces.nextElement();
			System.out.println(in);
		}
	}
}
