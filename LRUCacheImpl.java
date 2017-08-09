package com.program.cache.impl;


import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Concurrent LRU Cache
 * @author Rohit Kumar
 *
 * @param <K>
 * @param <V>
 */
public class LRUCacheImpl<K, V> implements Cache<K,V> {


    private final ReentrantLock lock = new ReentrantLock();


    private final Map<K, V> map = new ConcurrentHashMap<K, V>(); //Cache
    private final Queue<K> queue = new LinkedList<K>(); //Queue to maintain Keys in LRU order i.e FIFO
    private final int limit;


    public LRUCacheImpl(int limit) {
        this.limit = limit;
    }


    public V put(K key, V value) {
    	Objects.requireNonNull(key, "Key==Null");
    	Objects.requireNonNull(value, "Value==Null");
    	
        V oldValue = map.put(key, value);
        if (oldValue != null) {
            removeThenAddKey(key); // If key accesses again it must be updated in queue as well
        } else {
            addKey(key);
        }
        if (map.size() > limit) {
            map.remove(removeLast());
        }
        return oldValue;
    }


    public V get(K key) {
        V value = map.get(key);
        if(value != null){
            removeThenAddKey(key);
        }
        return value;
    }


    private void addKey(K key) {
        lock.lock();
        try {
            queue.offer(key);
        } finally {
            lock.unlock();
        }
    }


    private K removeLast() {
        try {
            final K removedKey = queue.poll();
            return removedKey;
        } finally {
            lock.unlock();
        }
    }


    private void removeThenAddKey(K key) {
        lock.lock();
        try {
            queue.remove(key);;
            this.addKey(key);
        } finally {
            lock.unlock();
        }


    }


    public String toString() {
        return map.toString();
    }
}
