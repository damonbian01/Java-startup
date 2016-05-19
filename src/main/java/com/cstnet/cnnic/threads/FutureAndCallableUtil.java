package com.cstnet.cnnic.threads;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 
 * 	@author Tao Bian 16/05/19
 *	Callable Future Executor
 *
 *	开启两个线程，计算数组的最大值
 */
public class FutureAndCallableUtil {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		int size = 20;
		int[] arr = new int[size];
		for(int i = 0; i < size; i++) {
			arr[i] = new Random().nextInt(500);
			System.out.print(arr[i] + " ");
		}
		
		FindMaxTask task1 = new FindMaxTask(arr, 0, size/2);
		FindMaxTask task2 = new FindMaxTask(arr, size/2, size -1);
		
		ExecutorService service = Executors.newFixedThreadPool(2);
		Future<Integer> future1 = service.submit(task1);
		Future<Integer> future2 = service.submit(task2);
		
		System.out.println();
		System.out.println("max num is " + Math.max(future1.get(), future2.get()));
		System.exit(0);
	}
	
	public static class FindMaxTask implements Callable<Integer> {
		private int[] arr;
		private int start;
		private int end;
		
		public FindMaxTask(int[] arr, int start, int end) {
			this.arr = arr;
			this.start = start;
			this.end = end;
		}
		
		public Integer call() throws Exception {
			int max = arr[start];
			for (int i = start; i <= end; i++) {
				if (max < arr[i])
					max = arr[i];
			}
			return max;
		}
		
	}
	
}
