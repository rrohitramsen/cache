package com.program.cache.impl;

public class MyCacheTest {
	public static void main(String[] args) {
		MyCache<String, String> cache = new MyCache<String, String>(5);
		Task1 task = new Task1(cache);
		Thread t1 = new Thread(task);
		t1.start();
	}
}	

class Task1 implements Runnable{
	private MyCache<String, String> cache;
	
	public Task1(MyCache<String, String> cache){
		this.cache = cache;
	}
	@Override
	public void run() {
		for (int i=0; i<10; i++){
			cache.put("Rohit"+i, "Roose"+i);
		}
	}
	
}
