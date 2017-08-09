package com.program.cache.impl;

/**
 * 
 * @author rrohit
 *
 * @param <K>
 * @param <V>
 */
public class MyMap<K,V> {
	
	private static final int DEFAULT_CAPAPCITY = 16;
	private int capacity;
	private int size;
	private Entry<K,V> [] table;
	
	public MyMap(){
		this(DEFAULT_CAPAPCITY);
	}
	
	public MyMap(int capacity){
		if (capacity<=0){
			throw new IllegalArgumentException("Invalid Capacity");
		}
		this.capacity = capacity;
		//this.table = new Entry[capacity]; 
	}
	
	public V put(K key, V value){
		if (table == null){
			inflateTable();
		}
		if (key == null)
			putForNull(value); // put at index 0
		
		int hashCode = key.hashCode();
		int index = indexFor(hashCode, table.length);
		
		for (Entry<K,V> e = table[index]; e!=null; e = e.next){
			Object k = e.key;
			if ((e.hash == hashCode) && ( (k == key)||(key.equals(k) ) )){
				V oldValue = e.value;
				e.value = value;
				return oldValue;
			}
		}
		addEntry(key, value, hashCode, index );
		return null;
	}
	
	private void addEntry(K key, V value, int hashCode, int bucketIndex) {
		if (size > capacity){
			resize(2*table.length);
		}
		createEntry(key, value, hashCode, bucketIndex);
	}

	private void createEntry(K key, V value, int hashCode, int bucketIndex) {
		Entry<K,V> next = table[bucketIndex];
		table[bucketIndex] = new Entry(key, value, hashCode, next);
		this.size++;
	}
	
	public int size(){
		return this.size;
	}

	private void resize(int i) {
		// TODO Auto-generated method stub
		
	}

	private int indexFor(int hashCode, int length) {
		return hashCode & length-1;
	}

	private void putForNull(V value) {
		// TODO Auto-generated method stub
		
	}

	private void inflateTable() {
		this.table = new Entry[this.capacity];
	}

	public V get(K key){
		if (key == null){
			getForNullKey();
		}
		int hashCode = key.hashCode();
		int index = indexFor(hashCode, table.length);
		Object k;
		for (Entry<K,V> e= table[index]; e!= null; e=e.next){
			k = e.key;
			if ( (e.hash==hashCode) && ( (k == key) || (key.equals(k)))){
				return e.value;
			}
		}
		return null;
	}
		
	private void getForNullKey() {
		// TODO Auto-generated method stub
		
	}
	
	public Entry<K,V> remove(K key){
		if (key == null){
			removeForNullKey();
		}
		
		int hashCode = key.hashCode();
		int index = indexFor(hashCode, table.length);
		Entry<K,V> prev = table[index];
		Entry<K,V> e = prev;
		while(e!=null){
			Entry<K,V> next = e.next;
            Object k;
            if (e.hash == hashCode &&
                ((k = e.key) == key || (key != null && key.equals(k)))) {
                size--;
                if (prev == e)
                    table[index] = next;
                else
                    prev.next = next;
                return e;
            }
            prev = e;
            e = next;
		}
		return e;
	}

	private void removeForNullKey() {
		// TODO Auto-generated method stub
		
	}

	static class Entry<K,V> {
		final K key;
		V value;
		Entry<K,V> next;
		int hash;
		public Entry(K key, V value, int hash, Entry<K,V> next){
			this.key = key;
			this.value = value;
			this.next = next;
			this.hash = hash;
		}
	} 
}
