package com.cstnet.cnnic.java_startup;

import org.junit.Test;

/**
 * Hello world!
 *
 */
public class App 
{
	
	public int sum(int ...args) {
		int result = 0;
		for (int i = 0; i < args.length; i++)
			result += args[i];
		return result;
	}
	
	@Test
	public void testMutableArgs() {
		System.out.println(sum(1,2,3,4));;
	}
}
