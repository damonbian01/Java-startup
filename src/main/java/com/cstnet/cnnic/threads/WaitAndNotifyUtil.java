package com.cstnet.cnnic.threads;

/**
 * Object.wait() Object.notify Thread.sleep
 * @author Tao Bian 2016/05/12
 * 
 * wai()和 notify()必须和synchronized(obj)一起使用
 * wait()	获得对象锁之后，主动释放对象锁，同时本线程休眠，知道有其他线程notify()	
 * notify()	相应的synchronized语句块执行完毕后才会释放对象锁，然后jvm在wait()对象锁的线程中随机选取一个，赋予其对象锁
 * Thread.sleep()和Object.wait()区别-二者都可以暂停本线程：
 * wait()在释放cpu资源的同时，释放对象锁的控制
 * 
 * Example:
 * 建立三个线程A,B,C
 * 要求交替打印10次ABC
 * 
 * Runtime.getRuntime().addShutdownHook(Thread) 在jvm退出后将会执行的操作
 */

public class WaitAndNotifyUtil {

	public static void main(String[] args) throws InterruptedException {
		Runtime.getRuntime().addShutdownHook(new Thread(new CleanRunner(), "shutdownhook"));
		
		Object a = new Object();
		Object b = new Object();
		Object c = new Object();
		
		new Thread(new ThreadPrinter("A", c, a)).start();
		Thread.sleep(10);
		new Thread(new ThreadPrinter("B", a, b)).start();
		Thread.sleep(10);
		new Thread(new ThreadPrinter("C", b, c)).start();
		
	}
	
	public static class ThreadPrinter implements Runnable {
		private String printName;
		private Object waitPre;
		private Object self;
		
		public ThreadPrinter(String printName, Object waitPre, Object self) {
			super();
			this.printName = printName;
			this.waitPre = waitPre;
			this.self = self;
		}

		public void run() {
			int count = 10;
			while (count-- > 0) {
				synchronized (waitPre) {
					synchronized (self) {
						self.notify();
						System.out.print((10-count)+printName);
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					
					try {
						/**
						 * B or C will be blocked in the end, so count must > 0
						 */
						if (count > 0)
							waitPre.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
				
			}
		}
		
	}
	
	public static class CleanRunner implements Runnable {

		public void run() {
			System.out.println("\nI can do a lot of things when jvm exit");
		}
		
	}

}
