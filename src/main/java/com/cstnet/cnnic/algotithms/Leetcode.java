package com.cstnet.cnnic.algotithms;

import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.html.parser.Entity;

import org.junit.Test;

public class Leetcode {

	/**
	 * 1. Two Sum Given an array of integers, return indices of the two numbers
	 * such that they add up to a specific target.
	 */
	@Test
	public void twoSum() {
		int[] nums = { 2, 7, 1, 15 };
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
	 * 2. Add Two Numbers You are given two linked lists representing two
	 * non-negative numbers. The digits are stored in reverse order and each of
	 * their nodes contain a single digit. Add the two numbers and return it as
	 * a linked list.
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
				return "" + val;
			else
				return "" + val + "," + next.toString();
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
			p3.next = new ListNode(sum % 10);
			p3 = p3.next;
			sum = sum / 10;
		}
		if (sum > 0)
			p3.next = new ListNode(sum);
		System.out.println(reNode.next.toString());
	}

	/**
	 * 3.1 Longest Substring Without Repeating Characters Given a string, find
	 * the length of the longest substring without repeating characters.
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
			maxLen = Math.max(j - i + 1, maxLen);
			realStart = (j - i + 1) >= maxLen ? i : realStart;
		}
		System.out.println("max length is:" + maxLen);
		System.out.println("max string start index is:" + realStart);
	}

	/**
	 * 3.2 Longest Substring With At Most Two Distinct Characters
	 */
	@Test
	public void lengthOfLongestSubstringTwoDistinct() {
		String s = "abc";
		int i = 0, maxLen = 1;
		Map map = new HashMap<String, Integer>();
		map.put(s.charAt(0), 0);
		for (int j = 1; j < s.length(); j++) {
			if (s.charAt(j) == s.charAt(j - 1)) {
				maxLen = Math.max(j - i + 1, maxLen);
				continue;
			}
			if (map.size() == 1 || (map.size() == 2 && map.containsKey(s.charAt(j)))) {
				map.put(s.charAt(j), j);
				maxLen = Math.max(j - i + 1, maxLen);
				continue;
			} else {
				i = (Integer) map.get(s.charAt(j - 1));
				map.clear();
				map.put(s.charAt(j - 1), i);
				map.put(s.charAt(j), j);
				maxLen = Math.max(j - i + 1, maxLen);
				continue;
			}
		}
		System.out.println("max length is:" + maxLen);
	}

	/**
	 * 4. Median of Two Sorted Arrays There are two sorted arrays nums1 and
	 * nums2 of size m and n respectively. Find the median of the two sorted
	 * arrays. The overall run time complexity should be O(log (m+n)).
	 */
	private double findKth(int[] nums1, int[] nums2, int start1, int len1, int start2, int len2, int poiK) {
		if (len1 > len2)
			return findKth(nums2, nums1, start2, len2, start1, len1, poiK);
		if (len1 == 0)
			return nums2[start2 + poiK - 1];
		if (poiK == 1)
			return Math.min(nums1[start1], nums2[start2]);
		int p1 = Math.min(poiK / 2, len1);
		int p2 = poiK - p1;
		if (nums1[start1 + p1 - 1] < nums2[start2 + p2 - 1]) {
			return findKth(nums1, nums2, start1 + p1, len1 - p1, start2, len2, poiK - p1);
		} else if (nums1[start1 + p1 - 1] > nums2[start2 + p2 - 1]) {
			return findKth(nums1, nums2, start1, len1, start2 + p2, len2 - p2, poiK - p2);
		} else {
			return nums1[start1 + p1 - 1];
		}
	}

	@Test
	public void findMedianSortedArrays() {
		int[] nums1 = { 1, 5, 18, 40 };
		int[] nums2 = { 2, 6, 13, 24 };
		double median = 0.0;
		int m = nums1.length;
		int n = nums2.length;
		if (m + n < 1)
			throw new IllegalArgumentException("at least one integer in two arrays");
		int k = (m + n) / 2;
		if ((m + n) % 2 == 0)
			median = (findKth(nums1, nums2, 0, m, 0, n, k) + findKth(nums1, nums2, 0, m, 0, n, k + 1)) / 2;
		else
			median = findKth(nums1, nums2, 0, m, 0, n, k + 1);
		System.out.println("median is:" + median);
	}

	/**
	 * 5. Longest Palindromic Substring Given a string S, find the longest
	 * palindromic substring in S. You may assume that the maximum length of S
	 * is 1000, and there exists one unique longest palindromic substring. use
	 * dynamic programming O(log(n*n))
	 */
	@Test
	public void longestPalindrome() {
		String s = "ccc";
		int m = s.length();
		boolean[][] table = new boolean[m][m];
		int maxStart = 0, maxEnd = 0;
		for (int i = 0; i < m; i++) {
			table[i][i] = true;
			if (i > 0 && s.charAt(i) == s.charAt(i - 1)) {
				table[i - 1][i] = true;
				maxStart = i - 1;
				maxEnd = i;
			}
		}
		for (int i = 3; i <= m; i++) {
			for (int j = 0; j < m - i + 1; j++) {
				if (table[j + 1][j + i - 2] && s.charAt(j) == s.charAt(j + i - 1)) {
					table[j][j + i - 1] = true;
					maxStart = j;
					maxEnd = j + i - 1;
				}
			}
		}
		System.out.println("max palindromic substring is " + s.substring(maxStart, maxEnd + 1));
	}

	/**
	 * 6. ZigZag Conversion The string "PAYPALISHIRING" is written in a zigzag
	 * pattern on a given number of rows like this: (you may want to display
	 * this pattern in a fixed font for better legibility) P A H N A P L S I I G
	 * Y I R And then read line by line: "PAHNAPLSIIGYIR"
	 */

	@Test
	public void convert() {
		String s = "PAYPALISHIRING";
		int numRows = 3;
		if (s.length() == 0) {
			System.out.println("");
			return;
		}
		if (numRows == 1) {
			System.out.println(s);
			return;
		}
		int maxColumn = (numRows - 1) * (s.length() / (2 * numRows - 2))
				+ (s.length() % (2 * numRows - 2) - numRows + 1 > 0 ? s.length() % (2 * numRows - 2) - numRows + 1
						: s.length() % (2 * numRows - 2) > 0 ? 1 : 0);
		char[][] matrix = new char[numRows][maxColumn];

		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			int x, y;
			int block_x = (i + 1) / (2 * numRows - 2);
			int block_y = (i + 1) % (2 * numRows - 2);
			if (block_y == 0) {
				x = 1;
				y = block_x * (numRows - 1) - 1;
			} else if (block_y <= numRows) {
				x = block_y - 1;
				y = block_x * (numRows - 1);
			} else {
				x = numRows - (block_y - numRows) - 1;
				y = block_x * (numRows - 1) + (block_y - numRows);
			}
			matrix[x][y] = ch;
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < maxColumn; j++) {
				if (matrix[i][j] != 0)
					sb.append(matrix[i][j]);
			}
		}
		System.out.println(sb.toString());
		;
	}

	/*
	 * 7. Reverse Integer Example1: x = 123, return 321 Example2: x = -123,
	 * return -321
	 */
	@Test
	public void reverseInteger() {
		int x = 1534236469;
		boolean start = true;
		StringBuffer sb = new StringBuffer();
		sb.append(x).reverse();
		for (int i = 0; i < sb.length(); i++) {
			if (sb.charAt(i) == '-') {
				sb.replace(i, i + 1, "");
				sb.insert(0, "-");
				continue;
			}
			if (sb.charAt(i) == '0' && start) {
				sb.replace(i, i + 1, "");
				i--;
				continue;
			}
			start = false;
		}
		if (sb.length() == 0) {
			System.out.println(0);
			return;
		}
		try {
			System.out.println(String.valueOf(Integer.valueOf(sb.toString())));
			;
		} catch (Exception e) {
			System.out.println(0);
		}
	}

	/**
	 * 8. String to Integer(atoi) Hint: Carefully consider all possible input
	 * cases. If you want a challenge, please do not see below and ask yourself
	 * what are the possible input cases.
	 */
	@Test
	public void myAtoi() {
		String str = "    10522545459";
		String str2 = str.trim().split(" ")[0];
		str2 = str2.substring(0, str2.length() > 12 ? 12 : str2.length());
		long result = 0L;
		long power = 1;
		Matcher matcher = Pattern.compile("^([0-9|\\-|+][0-9]+)|^([0-9])").matcher(str2);
		if (matcher.find()) {
			str2 = matcher.group(0);
			StringBuffer sb = new StringBuffer(str2);
			for (int i = sb.length() - 1; i >= 0; i--) {
				char ch = sb.charAt(i);
				if (i == 0 && ((ch == '-') || (ch == '+'))) {
					result = ch == '-' ? -1 * result : result;
				} else {
					result += (long) (ch - 48) * power;
					power *= 10;
				}
			}
			result = result > Integer.MAX_VALUE ? Integer.MAX_VALUE : result;
			result = result < Integer.MIN_VALUE ? Integer.MIN_VALUE : result;
			System.out.println(result);
		} else {
			System.out.println(0);
		}
	}

	/**
	 * 9. Palindrome Number Determine whether an integer is a palindrome. Do
	 * this without extra space.
	 */
	@Test
	public void isPalindrome() {
		int x = 0;
		StringBuffer sb = new StringBuffer();
		sb.append(x);
		if (x >= 0) {
			if (sb.reverse().toString().equals("" + x)) {
				System.out.println("true");
				return;
			}
		}
		System.out.println("false");
	}

	/**
	 * 10. Regular Expression Matching Implement regular expression matching
	 * with support for '.' and '*'
	 */
	public boolean myMatch(String s, String p) {
		if (p.isEmpty()) {
			return s.isEmpty();
		}
		if (p.equals(s)) {
			return true;
		}
		if (p.length() == 1 || p.charAt(1) != '*') {
			if (!s.isEmpty() && (p.charAt(0) == s.charAt(0) || p.charAt(0) == '.')) {
				return myMatch(s.substring(1), p.substring(1));
			} else {
				return false;
			}
		} else {
			while (!s.isEmpty() && (p.charAt(0) == s.charAt(0) || p.charAt(0) == '.')) {
				if (myMatch(s, p.substring(2))) {
					return true;
				}
				s = s.substring(1);
			}
			return myMatch(s, p.substring(2));
		}
	}

	@Test
	public void isMatch() {
		String s = "abbb";
		String p = "a*bbb";
		System.out.println(myMatch(s, p));
	}

	/*
	 * 11. Container With Most Water voliece O(n*n) the flowing is O(n)
	 */
	@Test
	public void maxArea() {
		int[] height = { 1, 2, 3, 3, 4, 7, 1, 3 };
		int maxArea = 0;
		int low = 0;
		int high = height.length - 1;
		while (low < high) {
			int area = (high - low) * Math.min(height[low], height[high]);
			maxArea = maxArea >= area ? maxArea : area;
			if (height[low] < height[high])
				low++;
			else
				high--;
		}
		System.out.println(maxArea);
	}

	/**
	 * 12. Integer to Roman Given an integer, convert it to a roman numeral.
	 * Input is guaranteed to be within the range from 1 to 3999
	 */

	/**
	 * 13. Roman to Integer
	 */

	/**
	 * 14. Longest Common Prefix Write a function to find the longest common
	 * prefix string amongst an array of strings.
	 */
	@Test
	public void longestCommonPrefix() {
		String[] strs = new String[] { "1234", "", "12456" };

		String com = "";
		if (strs.length == 0) {
			System.out.println(com);
			return;
		}
		int minLen = Integer.MAX_VALUE;
		for (int i = 0; i < strs.length; i++) {
			minLen = strs[i].length() < minLen ? strs[i].length() : minLen;
		}
		com = strs[0].substring(0, minLen);
		for (int i = 1; i < strs.length; i++) {
			String tmp = strs[i].substring(0, minLen);
			if (com.equals("")) {
				System.out.println(com);
				return;
			}
			if (tmp.equals(com))
				continue;
			else {
				for (int j = 0; j < minLen; j++) {
					if (com.charAt(j) == tmp.charAt(j))
						continue;
					else {
						com = com.substring(0, j);
						minLen = j;
						break;
					}
				}
			}
		}
		System.out.println(com);
	}

	/**
	 * 15. 3Sum Given an array S of n integers, are there elements a, b, c in S
	 * such that a + b + c = 0? Find all unique triplets in the array which
	 * gives the sum of zero.For example, given array S = {-1 0 1 2 -1 -4}, A
	 * solution set is: (-1, 0, 1) (-1, -1, 2)
	 */
	@Test
	public void threeSum() {
		int[] nums = new int[] { 7,-1,14,-12,-8,7,2,-15,8,8,-8,-14,-4,-5,7,9,11,-4,-15,-6,1,-14,4,3,10,-5,2,1,6,11,2,-2,-5,-7,-6,2,-15,11,-6,8,-4,2,1,-1,4,-6,-15,1,5,-15,10,14,9,-8,-6,4,-6,11,12,-15,7,-1,-9,9,-1,0,-4,-1,-12,-2,14,-9,7,0,-3,-4,1,-2,12,14,-10,0,5,14,-1,14,3,8,10,-8,8,-5,-2,6,-11,12,13,-7,-12,8,6,-13,14,-2,-5,-11,1,3,-6 };
		long begin = System.currentTimeMillis();
		List<List<Integer>> lst = new ArrayList<List<Integer>>();

		if (nums.length < 3) {
			System.out.println("null");
			return;
		}

		for (int i = 0; i < nums.length - 1; i++) {
			for (int j = 0; j < nums.length - 1 - i; j++) {
				if (nums[j] > nums[j + 1]) {
					int tmp = nums[j];
					nums[j] = nums[j + 1];
					nums[j + 1] = tmp;
				}
			}
		}

		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < nums.length; i++) {
			map.put(nums[i], i);
		}

		for (int i = 0; i < nums.length - 2 && nums[i] <= 0; i++) {
			if (i >= 1 && nums[i] == nums[i-1]) continue;
			for (int j = i + 1; j < nums.length - 1; j++) {
				if (j - 1 != i && nums[j] == nums[j-1]) continue;
				if (nums[i] + nums[j] >0 ) break;
				int third = 0 - nums[i] - nums[j];
				if (map.containsKey(third) && map.get(third) > j) {
					List<Integer> item = Arrays.asList(nums[i], nums[j], 0 - nums[i] - nums[j]);
					if(!lst.contains(item))
						lst.add(item);
					continue;
				}
			}
		}
		long end = System.currentTimeMillis();
		for (int i = 0; i < lst.size(); i++) {
			for (int j = 0; j < 3; j++) {
				System.out.print(lst.get(i).get(j) + " ");
				;
			}
			System.out.println();
		}
		System.out.println("time is:"+(end-begin));
	}
	
	/**
	 * 16. 3Sum Closest
	 * Given an array S of n integers, find three integers in S such that the sum is closest to a given number, target. Return the sum of the three integers. 
	 * You may assume that each input would have exactly one solution.
	 */
	@Test
	public void threeSumClosest() {
		int[] nums = {-1 ,2, 1, -4};
		int target = -4;
		
		Arrays.sort(nums);
		int ret = 0;
		int sum = 0;
		int closeDist = Integer.MAX_VALUE;
		for (int i = 0; i < nums.length - 2; i++) {
			if (i > 1 && nums[i] == nums[i-1]) continue;
			int low = i + 1;
			int high = nums.length - 1;
			while (low < high) {
				sum = nums[i] + nums[low] + nums[high];
				if (sum > target) {
					if (sum - target < closeDist) {
						closeDist = sum - target;
						ret = sum;
					}
					high--;
				} else if (sum < target) {
					if (target - sum < closeDist) {
						closeDist = target - sum;
						ret = sum;
					}
					low++;
				} else {
					ret = sum;
					System.out.println("ret:" + ret);
					return; 
				}
			}
		}
		System.out.println("ret:" + ret);
		return;
		
	}
	
	/**
	 * 18. 4Sum
	 * Given an array S of n integers, are there elements a, b, c, and d in S such that a + b + c + d = target? 
	 * Find all unique quadruplets in the array which gives the sum of target.
	 */
	private List<List<Integer>> subTwoSum(int i, int j, int[] nums, int newTarget) {
		List<List<Integer>> list = new ArrayList<List<Integer>>();
		int low = j + 1;
		int high = nums.length - 1;
		
		while (low < high) {
			int sum = nums[low] + nums[high];
			if (sum < newTarget) {
				low++;
			} else if (sum > newTarget) {
				high--;
			} else {
				List<Integer> lst = Arrays.asList(nums[i], nums[j], nums[low],nums[high]);
				if(!list.contains(lst))	list.add(lst);
				high--;
			}
		}
		
		return list;
	}
	
	@Test
	public void fourSum() {
		int[] nums = {1,-2,-5,-4,-3, 3, 3, 5};
		int target = -11;
		List<List<Integer>> list = new ArrayList<List<Integer>>();
		
		Arrays.sort(nums);
		for (int i = 0; i < nums.length - 3; i++) {
			if (i > 0 && nums[i] == nums[i-1]) continue;
			if (nums[i] > target && nums[i] > 0) break;
			for (int j = i+1; j < nums.length - 2; j++) {
				if (j - i > 1 && nums[j] == nums[j-1]) continue;
				if (nums[i] + nums[j] > target && nums[j] > 0) break;
				list.addAll(subTwoSum(i, j, nums, target - (nums[i] + nums[j])));
			}
		}
		
		for (int i = 0; i < list.size(); i++) {
			List<Integer> lst = list.get(i);
			for (int j = 0; j < lst.size(); j++)
				System.out.print(lst.get(j) + " ");
			System.out.println();
		}
	}
	
	/**
	 * 19. Remove Nth Node From End of List
	 * Given a linked list, remove the nth node from the end of list and return its head.
	 * For example,
	 * Given linked list: 1->2->3->4->5, and n = 2.
	 * After removing the second node from the end, the linked list becomes 1->2->3->5.
	 */
	@Test
	public void removeNthFromEnd() {
		ListNode list = new ListNode(0);
		ListNode.createNode(list, Arrays.asList(1,2));

		ListNode head = list.next;
		int n = 2;
		
		ListNode p = head;
		
		int count = 0;
		while (p != null) {
			count++;
			p = p.next;
		}
		p = head;
		count = count+1-n;
		if (count == 1) {
			head = head.next;
			if (head == null) System.out.println("null");
			else System.out.println(head.toString());
			return;
		}
		
		while ((--count)-1 > 0) {
			p = p.next;
		}
		p.next = p.next.next;
		System.out.println(head.toString());
	}
	
	/**
	 * 21. Merge Two Sorted Lists
	 * Merge two sorted linked lists and return it as a new list. 
	 * The new list should be made by splicing together the nodes of the first two lists.
	 */
	@Test
	public void mergeTwoLists() {
		ListNode l11 = new ListNode(0);
		ListNode l22 = new ListNode(0);
		ListNode.createNode(l11, Arrays.asList(2, 7, 45));
		ListNode.createNode(l22, Arrays.asList(4, 6, 34));
		ListNode l1 = l11.next;
		ListNode l2 = l22.next;
		
		ListNode head = new ListNode(0);
		ListNode p1 = l1;
		ListNode p2 = l2;
		ListNode p3 = head;
		while (p1 != null && p2 != null) {
			int val;
			if (p1.val <= p2.val) {
				p3.next = new ListNode(p1.val);
				p1 = p1.next;
			} else {
				p3.next = new ListNode(p2.val);
				p2 = p2.next;
			}
			p3 = p3.next;
		}
		if (p1 != null) p3.next = p1;
		if (p2 != null) p3.next = p2;
		System.out.println(head.next.toString());
	}
	
	
	/**
	 * 23. Merge k Sorted Lists
	 * Merge k sorted linked lists and return it as one sorted list. Analyze and describe its complexity.
	 * use PriorityQueue, the time complexity is O(n(logk)), k is number of lists and n is the num of all elements
	 */
//	@Test
//	public void mergeKLists() {
//		ListNode l11 = new ListNode(0);
//		ListNode l22 = new ListNode(0);
//		ListNode.createNode(l11, Arrays.asList(2, 7, 45));
//		ListNode.createNode(l22, Arrays.asList(4, 6, 34));
//		ListNode l1 = l11.next;
//		ListNode l2 = l22.next;
//		ListNode[] lists = new ListNode[] {l1,l2};
//
//		if (lists == null || lists.length == 0) {
//			System.out.println("null");
//			return;
//		}
//
//		PriorityQueue<ListNode> queue = new PriorityQueue<ListNode>(new Comparator<ListNode>() {
//
//			public int compare(ListNode o1, ListNode o2) {
//				return o1.val - o2.val;
//			}
//		});
//
//		ListNode head = new ListNode(0);
//		ListNode p = head;
//
//		for (ListNode node : lists) {
//			if (node != null)
//				queue.offer(node);
//		}
//		while (!queue.isEmpty()) {
//			ListNode currentMin = queue.poll();
//			p.next = currentMin;
//			p = currentMin;
//			if (currentMin.next != null)
//				queue.offer(currentMin.next);
//
//		}
//		System.out.println(head.next.toString());
//	}
	
	/**
	 * 24. Swap Nodes in Pairs
	 */
	
	/**
	 * 30. Substring with Concatenation of All Words
	 * Given a linked list, swap every two adjacent nodes and return its head.
	 * For example
	 * Given 1->2->3->4, you should return the list as 2->1->4->3
	 */
	@Test
	public void swapPairs() {
		ListNode l11 = new ListNode(0);
		ListNode.createNode(l11, Arrays.asList(2, 7, 45,34));
		ListNode head = l11.next;
		
		if (head == null) {
			System.out.println("null");
			return;
		}
		ListNode p = head;
		int count = 0;
		while(p != null) {
			if (p.next != null && count%2 == 0) {
				int tmp = p.val;
				p.val = p.next.val;
				p.next.val = tmp;
			}
			p = p.next;
			count++;
		}
		System.out.println(head.toString());
	}
	
	/**
	 * 53. Maximum Subarray
	 * Find the contiguous subarray within an array (containing at least one number) 
	 * which has the largest sum.
	 * For example, given the array [−2,1,−3,4,−1,2,1,−5,4],
	 * the contiguous subarray [4,−1,2,1] has the largest sum = 6.
	 * use DP ? Greedy?
	 */
	
	@Test
	public void maxSubArrayTLE() {
		int[] nums = {1};
		
		if (nums == null || nums.length == 0) {
			System.out.println("null");
			return;
		}
		
		int size = nums.length;
		int dp[][] = new int[size][size];
		int sum = nums[0];
		
		for (int i = 0; i < size; i++) {
			dp[i][i] = nums[i];
			if (dp[i][i] > sum) sum = dp[i][i];
			for (int j = i+1; j < size; j++) {
				dp[i][j] = dp[i][j-1] + nums[j];
				if (dp[i][j] > sum) sum = dp[i][j];
			}
		}
		
		System.out.println(sum);
	}
	
	@Test
	public void maxSubArray() {
		int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
		
		if (nums == null || nums.length == 0) {
			System.out.println("null");
			return;
		}
		
		int sum = 0;
		int maxSum = Integer.MIN_VALUE;
		for (int i = 0; i < nums.length; i++) {
			sum += nums[i];
			if (sum > maxSum) maxSum = sum;
			if (sum < 0) sum = 0;
		}
		System.out.println("max:" + maxSum);
	}
	
	/**
	 * 62. Unique Paths
	 * A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).T
	 * he robot can only move either down or right at any point in time. The robot is trying to reach the bottom-right corner of the grid 
	 * (marked 'Finish' in the diagram below).How many possible unique paths are there?
	 * dp[1,j] = 1
	 * dp[i,1] = 1
	 * dp[i,j] = dp[i,j-1]+dp[i-1,j]
	 */
	@Test
	public void uniquePaths() {
		int m = 3;
		int n = 7;
		
		if (m < 1 || m < 1 || m > 100 || n > 100)
			throw new IllegalArgumentException("m or n is incorrect");
		
		//index start from 1
		int[][] dp = new int[m+1][n+1];
		for (int i = 1; i < m+1; i++) dp[i][1] = 1;
		for (int j = 1; j < n+1; j++) dp[1][j] = 1;
		for (int i = 2; i < m+1; i++) {
			for (int j = 2; j < n+1; j++)
				dp[i][j] = dp[i][j-1] + dp[i-1][j];
		}
		
		System.out.println(dp[m][n]);
	}
	
	/**
	 * 63. Unique Paths II
	 * Follow up for "Unique Paths":Now consider if some obstacles are added to the grids. 
	 * How many unique paths would there be?
	 * An obstacle and empty space is marked as 1 and 0 respectively in the grid.
	 * [
  		[0,0,0],
  		[0,1,0],
  		[0,0,0]
		]
	 */
	@Test
	public void uniquePathsWithObstacles() {
		int[][] obstacleGrid = {
				{0,0,0},
				{0,1,0},
				{0,0,0}};
		
		int m = obstacleGrid.length;
		int n = obstacleGrid[0].length;
		
		int[][] dp = new int[m][n];
	
		int blockM = 0;
		for (int i = 0; i < m; i++) {
			if (blockM == 1) {
				dp[i][0] = 0;
			} else if (obstacleGrid[i][0] == 1) {
				dp[i][0] = 0;
				blockM = 1;
			} else {
				dp[i][0] = 1;
			}
		}
		int blockN = 0;
		for (int j = 0; j < n; j++) {
			if (blockN == 1) {
				dp[0][j] = 0;
			} else if (obstacleGrid[0][j] == 1) {
				dp[0][j] = 0;
				blockN = 1;
			} else {
				dp[0][j] = 1;
			}
		}
		
		for (int i = 1; i < m; i++) {
			for (int j = 1; j < n; j++) {
				if (obstacleGrid[i][j] == 1) dp[i][j] = 0;
				else dp[i][j] = dp[i-1][j] + dp[i][j-1];
			}
		}
		
		System.out.println(dp[m-1][n-1]);
	}
	
	/**
	 * 64. Minimum Path Sum
	 * Given a m x n grid filled with non-negative numbers, find a path from top left to bottom right 
	 * which minimizes the sum of all numbers along its path.
	 * use DP
	 */
	@Test
	public void minPathSum() {
		int[][] grid = {
				{0,0,0},
				{0,1,0},
				{0,0,0}};
		
		int m = grid.length;
		int n = grid[0].length;
		int[][] dp = new int[m][n];
		dp[0][0] = grid[0][0];
		for (int i = 1; i < m; i++) dp[i][0] = dp[i-1][0] + grid[i][0];
		for (int j = 1; j < n; j++) dp[0][j] = dp[0][j-1] + grid[0][j];
		
		for (int i = 1; i < m; i++) {
			for (int j = 1; j < n; j++) {
				dp[i][j] = Math.min(dp[i-1][j], dp[i][j-1]) + grid[i][j];
			}
		}
		System.out.println(dp[m-1][m-1]);
	}
	
	/**
	 * 70. Climbing Stairs
	 * You are climbing a stair case. It takes n steps to reach to the top.
	 * Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
	 * dp[n] = dp[n-1] + dp[n-2]
	 */
	@Test
	public void climbStairs() {
		int n = 4;
		
		if (n < 1) {
			System.out.println("0");
			return;
		}
		if (n == 1) {
			System.out.println("1");
			return;
		}
		if (n == 2) {
			System.out.println("2");
			return;
		}
		
		int[] dp = new int[n+1];
		dp[0] = 1;
		dp[1] = 1;
		dp[2] = 2;
		for (int i = 3; i < n+1; i++) {
			dp[i] = dp[i-1] + dp[i-2];
		}
		System.out.println(dp[n]);
	}
	
	/**
	 * 72. Edit Distance
	 * Given two words word1 and word2, 
	 * find the minimum number of steps required to convert word1 to word2. (each operation is counted as 1 step.)
	 * You have the following 3 operations permitted on a word:
	 * a) Insert a character
	 * b) Delete a character
	 * c) Replace a character
	 */
	@Test
	public void minDistance() {
		String word1;
		String word2;
		int ret = 0;
		System.out.println(ret);
	}

	/**
	 * 98. Validate Binary Search Tree
	 * Assume a BST is defined as follows:
	 * The left subtree of a node contains only nodes with keys less than the node's key.
	 * The right subtree of a node contains only nodes with keys greater than the node's key.
	 * Both the left and right subtrees must also be binary search trees.
	 */
	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		TreeNode(int x) {
			val = x;
		}
	}

	private boolean isValid(TreeNode root, long low, long high) {
		boolean result = true;

		if(root != null) {
			if(!(root.val > low && root.val < high)) return false;
			TreeNode left = root.left;
			TreeNode right = root.right;

			if(!(isValid(left, low, root.val) && isValid(right, root.val, high)))
				result = false;
		}
		return result;
	}

	private boolean isValidBST(TreeNode root) {
		boolean result = true;

		if(root != null) {
			TreeNode left = root.left;
			TreeNode right = root.right;

			if(!(isValid(left, (long) Integer.MIN_VALUE-1, root.val) && isValid(right, root.val, (long) Integer.MAX_VALUE+1)))
				result = false;
		}
		return result;
	}

	@Test
	public void isValidBSTjMain() {
		TreeNode root = null;
		System.out.println(isValidBST(root));
	}
	
	@Test
	public void test() {
	}
	
	/**
	 * 面试题，输出对角线
	 */
	@Test
	public void printTriangle() {
		int[][] data = {
				{1, 2, 3, 4},
				{5, 6, 7, 8},
				{9, 10, 11, 12},
				{13, 14, 15, 16}
		};
		
		int m = data.length;
		int n = data[0].length;
		for (int i = 0; i < n; i++ ) {
			for (int j = 0; j <= i; j++) {
				System.out.print(data[j][n-1-i+j] + " ");
			}
			System.out.println();
		}
		for (int i = 1; i < n; i++) {
			for (int j = i; j < n; j++) {
				System.out.print(data[j][j-i] + " ");
			}
			System.out.println();
		}
	}
	
	/**
	 * 面试题，reverse字符串
	 */
	@Test
	public void reverseStr() {
		String str = "welcome to com";
		StringBuffer sb = new StringBuffer(str);
		sb.reverse();
		System.out.println(sb.toString());
	}
	
	/**
	 * 面试题， hashmap排序
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void sortMap() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(1, "1");
		map.put(5, "5");
		map.put(2, "2");
		map.put(12, "12");
		map.put(4, "4");
		
		List list = new ArrayList(map.entrySet());
		Collections.sort(list, new Comparator<java.util.Map.Entry<Integer, String>>() {

			public int compare(java.util.Map.Entry<Integer, String> o1, java.util.Map.Entry<Integer, String> o2) {
				return o1.getKey()-o2.getKey();
			}
		});
		
		Iterator<java.util.Map.Entry<Integer, String>> itera = list.iterator();
		while (itera.hasNext())
			System.out.println(itera.next().getValue());
		
		Map<Integer,String> tMap = new TreeMap<Integer, String>(map);
		Iterator<java.util.Map.Entry<Integer, String>> iter = tMap.entrySet().iterator();
		while (iter.hasNext())
			System.out.println(iter.next().getValue());
	}
	
	/**
	 * 面试题，n个不等长字符串，打印出来
	 */
	@Test
	public void printNStr() {
		List<String> lst = new ArrayList<String>(Arrays.asList(new String[]{
				"12345",
				"123456789",
				"3456789"}));
		int maxLen = 0;
		StringBuffer sb = new StringBuffer();
		int[] lens = new int[lst.size()];
		for (int i = 0; i < lst.size(); i++) {
			int tmpLen = lst.get(i).length(); 
			maxLen = tmpLen > maxLen ? tmpLen : maxLen;
			lens[i] = tmpLen;
		}
		for (int i = 0; i < maxLen; i++) {
			for (int j = 0; j < lst.size(); j++) {
				if (lens[j] > i)
					sb.append(lst.get(j).charAt(i));
			}
			sb.append("\n");
		}
		System.out.println(sb.toString());
	}
}
