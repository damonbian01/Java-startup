package com.cstnet.cnnic.collections;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class ListUtil {

	@Test
	public void testSubList() {
		List list = Arrays.asList(1,2,3,4,5,6);
		/*[1, 2, 3, 4, 5, 6]*/
		System.out.println(list.subList(0, list.size()).toString());
		/*false*/
		System.out.println(list.subList(0, 0) == null);
		/*java.lang.IllegalArgumentException: fromIndex(1) > toIndex(0)*/
		System.out.println(list.subList(1, 0) == null);
	}
	
	@Test
	public void testArrCopy() {
		String[] raw = {"123","234","345"};
		String[] sub = Arrays.copyOfRange(raw, 0, 1);
		for (int i = 0; i < sub.length; i++)
			System.out.println(sub[i]);
	}
	
}
