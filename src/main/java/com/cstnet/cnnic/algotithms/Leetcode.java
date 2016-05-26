package com.cstnet.cnnic.algotithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	

	@Test
	public void test() {
	}
}
