package com.cstnet.cnnic.threads;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch同步辅助类
 * 1.  public CountDownLatch(int count)
 * 2.  public void countDown();	//count--
 * 3.  public void await();		//count > 0 ? 阻塞当前线程
 * @author Tao Bian 2016/05/12
 *
 */
public class LatchUtil {
	public static final int threads = 10;

	public static void main(String[] args) throws InterruptedException {
		
		final CountDownLatch latch = new CountDownLatch(threads);
		for (int i = 0; i < threads; i++) {
			new Thread(new Worker(latch), "worker-"+i).start();
		}
		
		latch.await();
		System.out.println("main end");
		
	}
	
	public static class Worker implements Runnable {
		private CountDownLatch runnerLatch = null;
		
		public Worker() {
			
		}
		
		public Worker(CountDownLatch latch) {
			this.runnerLatch = latch;
		}

		public void run() {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName());
			this.runnerLatch.countDown();
		}
		
	}
	

}
