package com.program.cache.impl;

import java.util.Objects;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 
 * @author rrohit
 *
 * @param <K>
 * @param <V>
 */
public class MyCache<K,V> {
	
	private final MyMap<K,V> cacheMap;
	private final Queue<K> myQueue;
	private int capacity;
	private final static int DEFAULT_CAPACITY = 16;
	private final ReentrantReadWriteLock.WriteLock writeLock;
	private final ReentrantReadWriteLock.ReadLock readLock;
	
	public MyCache(){
		this(DEFAULT_CAPACITY);
	}

	public MyCache(int capacity) {
		if (capacity<=0){
			throw new IllegalArgumentException("Invalid Capacity");
		}
		this.capacity = capacity;
		this.cacheMap = new MyMap(capacity);
		this.myQueue = new Queue<K>();
		this.writeLock = new ReentrantReadWriteLock().writeLock();
		this.readLock = new ReentrantReadWriteLock().readLock();
	}
	
	/**
	 * 
	 * @param key
	 * @param value
	 * @return V value
	 */
	public V put(K key, V value){
		Objects.requireNonNull(key, "Key == null");
		writeLock.lock();
		try{
			V oldValue = cacheMap.put(key, value);
			if (oldValue != null) {
				removeThenAddKey(key);
				return oldValue;
			}else{
				addKey(key);
			}
			
			if (this.cacheMap.size() >= this.capacity){
				System.out.println("Thread :: "+Thread.currentThread()+"Cache Limit Full");
				System.out.println("Cahe Map = "+this.cacheMap);
				System.out.println("Queue  = "+this.myQueue);
				this.cacheMap.remove(this.myQueue.deQueue().getItem());
			}
		}finally{
			writeLock.unlock();
		}
		return null;
	}
	
	/**
	 * 
	 * @param key
	 * @return value
	 */
	public V get(K key){
		Objects.requireNonNull(key, "Key == null");
		readLock.lock();
		try{
			V oldValue = this.cacheMap.get(key);
			if (oldValue != null){
				removeThenAddKey(key);
				return oldValue;
			}
		}finally{
			readLock.unlock();
		}
		return null;
	}

	private void addKey(K key) {
		this.myQueue.enQueue(key);
	}

	private void removeThenAddKey(K key) {
		this.myQueue.remove(key);
		this.myQueue.enQueue(key);
	}
	
}
