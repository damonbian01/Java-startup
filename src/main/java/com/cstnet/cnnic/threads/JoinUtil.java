package com.cstnet.cnnic.threads;

/**
 * Join 类似于将多线程串行化,B中调用A.join()方法，A执行完后才会执行B
 * public void join();	A执行完后才会执行B
 * public void join(long ms);	B只会等待A ms的时间	
 * @author Tao Bian 2016/05/12
 *
 */
public class JoinUtil {

	public static void main(String[] args) throws InterruptedException {

		Thread subThread = new Thread(new Runnable() {
			
			public void run() {
				try {
					System.out.println(Thread.currentThread().getName() + " will spleep 5s");
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("I will done first");
			}
		}, "sub thread");
		
		subThread.start();
//		subThread.join();
		subThread.join(2000);
		System.out.println("Main will done end");
	}

}
