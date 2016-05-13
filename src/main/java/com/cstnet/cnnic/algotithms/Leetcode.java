package com.cstnet.cnnic.algotithms;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class Leetcode {

	
	/**
	 * 1.  Two Sum
	 * Given an array of integers, 
	 * return indices of the two numbers such that they add up to a specific target.
	 */
	@Test
	public void twoSum() {
		int[] nums = {2,7,1,15};
		int target = 9;
		
		Map map = new HashMap<Integer, Integer>();
		for (int i = 0; i < nums.length; i++) {
			Integer index = (Integer) map.get(target - nums[i]);
			if (index == null)
				map.put(nums[i], i);
			else {
				String reStr = "" + index + "," + i;
				System.out.println("indexs are: " + reStr);
				return;
			}
		}
		throw new IllegalArgumentException("No two sum solution");
	}
	/**
	 * 2.  Add Two Numbers
	 * You are given two linked lists representing two non-negative numbers.
	 * The digits are stored in reverse order and each of their nodes contain a single digit. 
	 * Add the two numbers and return it as a linked list.
	 */
	public static class ListNode {
		public int val;
		public ListNode next = null;
		public ListNode(int x) {
			val = x;
		}
		
		public static void createNode(ListNode initNode, List<Integer> list) {
			initNode.next = new ListNode(list.get(0));
			if (list.size() > 1)
				createNode(initNode.next, list.subList(1, list.size()));
		}

		@Override
		public String toString() {
			if (next == null)
				return ""+val;
			else
				return ""+val+","+next.toString();
		}
		
	}
	
	@Test
	public void addTwoNumbers() {
		ListNode l11 = new ListNode(0);
		ListNode l22 = new ListNode(0);
		ListNode.createNode(l11, Arrays.asList(2, 4, 3));
		ListNode.createNode(l22, Arrays.asList(5, 6, 4));
		ListNode l1 = l11.next;
		ListNode l2 = l22.next;
		System.out.println(l1.toString());
		System.out.println(l2.toString());
		ListNode reNode = new ListNode(0);
		ListNode p1 = l1;
		ListNode p2 = l2;
		ListNode p3 = reNode;
		int sum = 0;
		while (p1 != null || p2 != null) {
			if (p1 != null) {
				sum += p1.val;
				p1 = p1.next;
			}
			
			if (p2 != null) {
				sum += p2.val;
				p2 = p2.next;
			}
				p3.next = new ListNode(sum%10);
				p3 = p3.next;
				sum = sum/10;
		}
		if (sum > 0)
			p3.next = new ListNode(sum);
		System.out.println(reNode.next.toString());
	}
	
	/**
	 * 3.1  Longest Substring Without Repeating Characters
	 * Given a string, find the length of the longest substring without repeating characters.
	 */
	@Test
	public void lengthOfLongestSubstring() {
		String s = "abcdefg";
		boolean[] exist = new boolean[256];
		int i = 0, maxLen = 0;
		int realStart = 0;
		for (int j = 0; j < s.length(); j++) {
			while (exist[s.charAt(j)]) {
				exist[s.charAt(i)] = false;
				i++;
			}
			exist[s.charAt(j)] = true;
			maxLen = Math.max(j-i+1, maxLen);
			realStart = (j-i+1) >= maxLen ? i : realStart; 
		}
		System.out.println("max length is:" + maxLen);
		System.out.println("max string start index is:" + realStart);
	}
	
	/**
	 * 3.2  Longest Substring With At Most Two Distinct Characters
	 */
	@Test
	public void lengthOfLongestSubstringTwoDistinct() {
		String s = "abc";
		int i = 0, maxLen = 1;
		Map map = new HashMap<String, Integer>();
		map.put(s.charAt(0), 0);
		for (int j = 1; j < s.length(); j++) {
			if (s.charAt(j) == s.charAt(j-1)) {
				maxLen = Math.max(j-i+1, maxLen);
				continue;
			}
			if (map.size() == 1 || (map.size() == 2 && map.containsKey(s.charAt(j)))) {
				map.put(s.charAt(j), j);
				maxLen = Math.max(j-i+1, maxLen);
				continue;
			} else {
				i = (Integer) map.get(s.charAt(j-1));
				map.clear();
				map.put(s.charAt(j-1), i);
				map.put(s.charAt(j), j);
				maxLen = Math.max(j-i+1, maxLen);
				continue;
			}
		}
		System.out.println("max length is:" + maxLen);
	}
	
	/**
	 * 4.  Median of Two Sorted Arrays
	 * There are two sorted arrays nums1 and nums2 of size m and n respectively. Find the median of the two sorted arrays. 
	 * The overall run time complexity should be O(log (m+n)).
	 */
	private double findKth(int[] nums1, int[] nums2, int start1, int len1, int start2, int len2, int poiK) {
		if (len1 > len2)
			return findKth(nums2, nums1, start2, len2, start1, len1, poiK);
		if (len1 == 0)
			return nums2[start2 + poiK -1];
		if (poiK == 1)
			return Math.min(nums1[start1], nums2[start2]);
		int p1 = Math.min(poiK/2, len1);
		int p2 = poiK - p1;
		if (nums1[start1+p1-1] < nums2[start2+p2-1]) {
			return findKth(nums1, nums2, start1 + p1, len1 - p1, start2, len2, poiK - p1);
		} else if (nums1[start1+p1-1] > nums2[start2+p2-1]) {
			return findKth(nums1, nums2, start1, len1, start2 + p2, len2 - p2, poiK - p2);
		} else {
			return nums1[start1+p1-1];
		}
	}
	
	@Test
	public void findMedianSortedArrays() {
		int[] nums1 = {1, 5, 18, 40};
		int[] nums2 = {2, 6, 13, 24};
		double median = 0.0;
		int m = nums1.length;
		int n = nums2.length;
		if (m + n < 1)
			throw new IllegalArgumentException("at least one integer in two arrays");
		int k = (m+n) / 2;
		if ((m+n)%2 == 0)
			median = (findKth(nums1, nums2, 0, m, 0, n, k) + findKth(nums1, nums2, 0, m, 0, n, k + 1))/2;
		else
			median = findKth(nums1, nums2, 0, m, 0, n, k+1);
		System.out.println("median is:" + median);
	}
	
	@Test
	public void test() {
	}
}
