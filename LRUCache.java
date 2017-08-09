package com.program.cache.impl;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;


public class LRUCache<K,V> implements Cache<K, V> {
	

	private static final int DEFAULT_CAPACITY = 16;
	private final LRUHashMap<K,V> map;
	
	private int maxMemorySize;
	
	public LRUCache(){
		this(DEFAULT_CAPACITY);
	}
	
	public LRUCache(int capacity){
		if (capacity<=0){
			throw new IllegalArgumentException("capacity <=0");
		}
		this.map = new LRUHashMap<K,V>(capacity);
		this.maxMemorySize = capacity * 1024;
	}
	
	/**
	 * 
	 */
	@Override
	public V get(K key) {
		Objects.requireNonNull(key, "key == null");
		synchronized(map){
			V value = map.get(key);
			if (value != null) {
				return null;
			}
		}
		return null;
	}

	@Override
	public V put(K key, V value) {
		Objects.requireNonNull(key, "key == null");
		Objects.requireNonNull(value, "value == null");
		V previous;
		synchronized(map){
			previous = map.put(key, value);
			
		}
		return null;
	}

	public V remove(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	public void clear() {
		// TODO Auto-generated method stub
		
	}

	public int getMaxMemorySize() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMemorySize() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public synchronized final Map<K, V> snapshot() {
		return new LinkedHashMap<>(map);
	} 

}
