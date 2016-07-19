package com.cstnet.cnnic.algotithms;

import org.junit.Test;

import java.util.*;

/**
 * Created by biantao on 16/7/18.
 * 剑指offer上面的
 */
public class Offer {

    /**
     * 面试题3
     * 矩阵,从上往下递减,从左往右递减,求目标target
     */
    public static class Find {
        public static boolean find(int[][] matrix, int target) {
            boolean result = false;
            if (null == matrix) return result;

            int rows = matrix.length;
            int columns = matrix[0].length;
            int i = 0;
            int j = columns - 1;
            while (j >= 0 && i <= rows - 1) {
                if (matrix[i][j] == target) {
                    return true;
                }
                if (matrix[i][j] < target) {
                    ++i;
                    continue;
                }
                if (matrix[i][j] > target) {
                    --j;
                    continue;
                }
            }
            return result;
        }
    }

    @Test
    public void testFind() {
        int[][] matrix = {
                {1, 2, 8, 9},
                {2, 4, 9, 12},
                {4, 7, 10, 13},
                {6, 8, 11, 15}
        };
        System.out.println(Find.find(matrix, 100));
    }


    /**
     * 面试题4
     * 将每个空格替换成%20
     */
    public static class ReplaceBlank {
        public static String replaceBlank(String input) {
            if (null == input) return null;
            return input.replaceAll(" ", "%20");
        }

        public static String replaceBlank2(String input) {
            if (null == input) return null;
            StringBuffer sb = new StringBuffer();
            for (char c : input.toCharArray()) {
                if (c == ' ') sb.append("%20");
                else sb.append(c);
            }
            return sb.toString();
        }
    }

    @Test
    public void testReplaceBlank() {
        String s = "a b c";
        System.out.println(ReplaceBlank.replaceBlank(s));
        System.out.println(ReplaceBlank.replaceBlank2(s));
    }

    /**
     * 面试题5
     * 给出一个链表,反向打印出链表的值
     */
    public static class PrintListReverse {

        public static void printListReverse(ListNode head) {
            if (null == head) return;
            Stack<ListNode> stack = new Stack<ListNode>();
            while (null != head) {
                stack.add(head);
                head = head.next;
            }
            while (!stack.empty()) {
                ListNode node = stack.pop();
                System.out.println(node.val);
            }
        }

    }

    @Test
    public void testPrintListReverse() {
        PrintListReverse.printListReverse(ListNode.createList(1, 2, 3, 4, 5, 6, 7, 8, 9));
    }

    /**
     * 面试题6
     * 输入前序和中序,重建该二叉树
     */
    public static class BinaryTreeRebuild {
        public static BinaryTree rebuild(int[] pre, int[] middle) {
            if (null == pre || null == middle || pre.length != middle.length) return null;
            BinaryTree root = new BinaryTree(pre[0]);
            int pos = pos(middle, root.val);
            BinaryTree left = BinaryTreeRebuild.rebuild(subArray(pre, 1, pos), subArray(middle, 0, pos - 1));
            BinaryTree right = BinaryTreeRebuild.rebuild(subArray(pre, pos + 1, pre.length - 1), subArray(middle, pos + 1, middle.length - 1));
            root.left = left;
            root.right = right;
            return root;
        }

        private static int pos(int[] nums, int target) {
            for (int i = 0; i < nums.length; i++) {
                if (target == nums[i]) return i;
            }
            return -1;
        }

        private static int[] subArray(int[] nums, int start, int end) {
            if (start > end || start < 0 || end < 0) return null;
            int[] array = new int[end - start + 1];
            for (int i = 0; i < array.length; i++) {
                array[i] = nums[i + start];
            }
            return array;
        }
    }

    @Test
    public void testBinaryTreeRebuild() {
        int[] pre = {1, 2, 4, 7, 3, 5, 6, 8};
        int[] middle = {4, 7, 2, 1, 5, 3, 8, 6};
        BinaryTree.PrintBinaryTree(BinaryTreeRebuild.rebuild(pre, middle));
    }

    /**
     * 面试题7
     * 用两个栈实现队列,实现appendTail和deleteHead功能
     * 本题目太傻瓜
     */
    public static class StackQueue<T> {
        Stack<T> stack1 = new Stack<T>();
        Stack<T> stack2 = new Stack<T>();

        public void appendTail(T tail) {

        }

        public T deleteHead() {
            return null;
        }

    }

    /**
     * 面试题8
     * 输出旋转数组的最小数字
     * 原数组是递增数组,关键点,使用二分查找,复杂度log(n)
     */
    public static class Problem8 {
        public static int solution(int[] nums) {
            if (null == nums) new Exception("invalid arguments");
            int i = 0;
            int j = nums.length - 1;
            int middle = 0;
            while (nums[i] >= nums[j]) {
                if (j - i <= 1) {
                    middle = j;
                    break;
                }
                middle = (i + j) / 2;

                if (nums[middle] <= nums[i]) j = middle;
                else if (nums[middle] > nums[j]) i = middle;
            }
            return nums[middle];
        }
    }

    @Test
    public void testProblem8() {
        System.out.println(Problem8.solution(new int[]{13, 14, 15, 1, 2, 3, 4}));
    }

    /**
     * 面试题9
     * 斐波那契数列,输入n,输出第n项
     * 1,1,2,3,5,8
     */
    public static class Fibonacci {
        public static int fibonacci(int n) {
            int a = 0;
            int b = 0;
            int c = 1;
            if (n <= 0) return a;
            if (n == 1) return 1;
            for (int i = 2; i <= n; i++) {
                a = b + c;
                b = c;
                c = a;
            }
            return a;
        }
    }

    @Test
    public void testFibonacci() {
        System.out.println(Fibonacci.fibonacci(6));
    }

    /**
     * 面试题10
     * 输入一个整数,输出2进制中1的个数
     * 9 = 1001
     */
    public static class Problem10 {
        public static int solution(int n) {
            int result = 0;
            while (n > 0) {
                if (n % 2 == 1) {
                    result++;
                }
                n >>= 1;
            }
            return result;
        }
    }

    @Test
    public void testProblem10() {
        System.out.println(Problem10.solution(9));
        System.out.println(Problem10.solution(1));
        System.out.println(Problem10.solution(2));
    }

    /**
     * 面试题12
     * 打印出1到最大的n位数
     * 4则输出1,2,...9999
     */
    public static class Problem12 {
        public static void solution(int n) {
            for (int i = 1; i <= n; i++) {
                List<String> s = printN(i);
                for (String str : s)
                    System.out.print(str + " ");
                System.out.println();
            }

        }

        public static List<String> printN(int n) {
            List<String> result = new ArrayList<String>();
            if (n == 1) {
                return Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9");
            }
            List<String> ret = printN(n - 1);
            for (String s : ret) {
                for (int i = 0; i < 10; i++) {
                    result.add(s + i);
                }
            }
            return result;
        }
    }

    @Test
    public void testProblem12() {
        Problem12.solution(10);
    }

    /**
     * 面试题14
     * 调整数组顺序,使基数位于偶数前面
     */
    public static class Problem14 {
        public static void solution(int... nums) {
            int i = 0;
            int j = nums.length - 1;
            while (i < j) {
                while (i < j && nums[i] % 2 != 0) i++;
                while (i < j && nums[j] % 2 == 0) j--;
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
                i++;
                j--;
            }
        }
    }

    @Test
    public void testProblem14() {
        int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Problem14.solution(nums);
        for (int n : nums) {
            System.out.println(n + " ");
        }
    }

    /**
     * 面试题17
     * 合并两个有序的链表
     */
    public static class Problem17 {
        public static ListNode solution(ListNode node1, ListNode node2) {
            if (null == node1) return node2;
            if (null == node2) return node1;
            ListNode head = null;
            ListNode rest = null;
            if (node1.val <= node2.val) {
                head = node1;
                rest = Problem17.solution(node1.next, node2);
            } else {
                head = node2;
                rest = Problem17.solution(node1, node2.next);
            }
            head.next = rest;
            return head;
        }
    }

    @Test
    public void testProbleam17() {
        ListNode node1 = ListNode.createList(1, 3, 5, 7, 9);
        ListNode node2 = ListNode.createList(2, 4, 6, 8);
        ListNode head = Problem17.solution(node1, node2);
        while (null != head) {
            System.out.println(head.val + " ");
            head = head.next;
        }
    }

    /**
     * 面试题18
     * 判断一个二叉树是不是另外一个二叉树的子结构
     */
    public static class Problem18 {
        public static boolean solution(BinaryTree tree1, BinaryTree tree2) {
            boolean result = false;
            if (tree1 != null && tree2 != null) {
                if (tree1.val == tree2.val) {
                    result = tree1ContainsTree2(tree1, tree2);
                }
                if (!result) {
                    result = Problem18.solution(tree1.left, tree2);
                }
                if (!result) {
                    result = Problem18.solution(tree1.right, tree2);
                }
            }
            return result;
        }

        public static boolean tree1ContainsTree2(BinaryTree tree1, BinaryTree tree2) {
            if (tree2 == null) return true;
            if (tree1 == null) return false;
            if (tree1.val != tree2.val) return false;
            return tree1ContainsTree2(tree1.left, tree2.left) && tree1ContainsTree2(tree1.right, tree2.right);
        }
    }

    @Test
    public void testProblem18() {

    }


    /**
     * 面试题19
     * 输入一个二叉树,输出它的镜像
     * 互换左右子树
     */
    public static class Problem19 {
        public static BinaryTree solution(BinaryTree tree) {
            if (null == tree) return null;
            BinaryTree left = tree.left;
            BinaryTree right = tree.right;
            if (null == left && null == right) return tree;
            BinaryTree preLeft = Problem19.solution(left);
            BinaryTree preRight = Problem19.solution(right);
            tree.left = preRight;
            tree.right = preLeft;
            return tree;
        }
    }

    @Test
    public void testProblem19() {

    }

    /**
     * 面试题20
     * 顺时针打印矩阵
     */
    public static class Problem20 {
        public static void solution(int[][] nums) {
            if (null == nums) return;
            int rows = nums.length;
            int columns = nums[0].length;
            int startR = 0;
            int endR = rows - 1;
            int startC = 0;
            int endC = columns - 1;
            while (startR <= endR && startC <= endC) {
                printCircle(nums, startR, endR, startC, endC);
                startR++;
                endR--;
                startC++;
                endC--;
            }


        }

        public static void printCircle(int[][] nums, int rowS, int rowE, int colS, int colE) {
            for (int i = colS; i <= colE; i++) {
                System.out.print(nums[rowS][i] + " ");
            }
            for (int i = rowS + 1; i <= rowE; i++) {
                System.out.print(nums[i][colE] + " ");
            }
            if (rowS < rowE) {
                for (int i = colE - 1; i >= colS; i--) {
                    System.out.print(nums[rowE][i] + " ");
                }
            }
            for (int i = rowE - 1; i > rowS; i--) {
                System.out.print(nums[i][colS] + " ");
            }
        }
    }

    @Test
    public void testProblem20() {
        Problem20.solution(new int[][]{
                {1, 2, 3, 4,5},
                {6, 7, 8,9,10},
                {11, 12,13,14,15},
                {16,17,18,19,20},
                {21,22,23,24,25}
        });
    }

    /**
     * 面试题22
     * 栈的压入,弹出序列
     * 1,2,3,4,5
     * 4,5,3,2,1 true
     * 4,5,3,1,2 false
     */
    public static class Problem22 {
        public static boolean solution(int[] pushN, int[] popN) {
            if (null == pushN || null == popN || pushN.length != popN.length) return false;
            Stack<Integer> stack = new Stack<Integer>();
            int i = 0;
            for (int pop : popN) {
                while (stack.empty() || stack.peek() != pop) {
                    if (i < pushN.length) stack.push(pushN[i++]);
                    else return false;
                }
                stack.pop();
            }
            return true;
        }
    }

    @Test
    public void testProblem22() {
        System.out.println(Problem22.solution(new int[]{1,2,3,4,5}, new int[]{1,2,3,4,5}));
    }

    /**
     *面试题24
     * 二叉搜索树的后续遍历序列
     * 输入一串序列,判断是否是某个二叉搜索树的后续遍历,如果是,则返回true,否则返回false
     * 5,7,6,9,11,10,8 true
     * 7,4,6,5 false
     */
    public static class Problem24 {
        public static boolean solution(int... nums) {
            if (null == nums) return true;
            if (nums.length == 1) return true;
            int root = nums[nums.length - 1];
            int i = 0;
            for (i = 0; i < nums.length - 1; i++) {
                if (nums[i] < root) continue;
                else break;
            }
            int[] left = null;
            int [] right = null;
            if (i == nums.length - 1) {
                // 必定全是左子树
                left = Arrays.copyOfRange(nums,0,i);
            } else if (i == 0) {
                //可能全是右子树
                right = Arrays.copyOfRange(nums, 0, nums.length - 1);
                for (int n : right) {
                    // 确认所有节点都大于root
                    if (n < root) return false;
                }
            } else {
                left = Arrays.copyOfRange(nums, 0, i);
                right = Arrays.copyOfRange(nums,i,nums.length - 1);
                for (int n : right) {
                    // 确认所有节点都大于root
                    if (n < root) return false;
                }
            }
            return Problem24.solution(left) && Problem24.solution(right);
        }
    }

    @Test
    public void testProblem24() {
        System.out.println(Problem24.solution(7,4,6,5));
    }

    /**
     * 面试题25
     * 二叉树中和为某一值的路径
     * DFS
     */
    public static class Problem25 {
        public static void solution(BinaryTree root, int target) {
        }
    }

    @Test
    public void testProblem25() {
        Problem25.solution(BinaryTree.createBinaryTree(1,2,3,4,5,6,7,0,0,0,0,0,0), 11);
    }

    /**
     * 面试题26
     * 链表+队列复制
     * BFS
     */
    public static class Problem26 {
        public static void solution() {

        }
    }


    /********************************************以下都是辅助工具类******************************************************/

    /**
     * ListNode
     */
    public static class ListNode {
        int val;
        ListNode next;

        public ListNode() {
        }

        public ListNode(int val) {
            this.val = val;
        }

        public static ListNode createList(int... nums) {
            if (null == nums) return null;
            ListNode head = new ListNode(0);
            ListNode root = head;
            for (int n : nums) {
                ListNode node = new ListNode(n);
                root.next = node;
                root = node;
            }
            return head.next;
        }
    }

    /**
     * BinaryTree
     */
    public static class BinaryTree {
        int val;
        BinaryTree left;
        BinaryTree right;

        public BinaryTree(int val) {
            this.val = val;
        }

        /**
         * 创建二叉树
         *      1
         *   2      3
         * 4  5    6 7
         */
        public static BinaryTree createBinaryTree(int... nums) {
            BinaryTree root = null;
            if (null == nums) return root;
            root = new BinaryTree(nums[0]);
            List<BinaryTree> list = new ArrayList<BinaryTree>();
            list.add(root);
            for (int i = 1; i < nums.length; i++) {
                if (0 == nums[i]) {
                    list.add(null);
                    continue;
                }
                else {
                    BinaryTree node = new BinaryTree(nums[i]);
                    BinaryTree parent = list.get((i - 1) / 2);
                    if (i % 2 != 0) parent.left = node;
                    else parent.right = node;
                    list.add(node);
                }
            }
            return root;
        }

        /**
         * DFS先序遍历输出
         */
        public static void PrintBinaryTree(BinaryTree root) {
            if (null == root) return;
            Stack<BinaryTree> stack = new Stack<BinaryTree>();
            stack.add(root);
            while (!stack.empty()) {
                BinaryTree node = stack.pop();
                System.out.print(node.val + " ");
                if (null != node.right) stack.add(node.right);
                if (null != node.left) stack.add(node.left);
            }
        }
    }
}
