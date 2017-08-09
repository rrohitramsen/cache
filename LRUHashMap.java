package com.program.cache.impl;

import java.util.LinkedHashMap;

public class LRUHashMap<K, V> extends LinkedHashMap<K, V>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int capacity;
	
	public LRUHashMap(int capacity) {
		super(capacity, 0.75f, true);
		this.capacity = capacity;
	}
	
	@Override
	protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
		return size() > this.capacity;
	}
}
