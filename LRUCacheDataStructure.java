package com.program.cache.impl;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * A memory cache interface.
 *
 * @author Rohit Kumar
 */
public class LRUCacheDataStructure {
	
	public class Node<K,V> {
		Node<K, V> next;
		Node<K, V> prev;
		K key;
		V value;
		
		public Node() {
			this.next= null;
			this.prev= null;
		}
		public Node(K key, V value){
			this();
			this.key = key;
			this.value= value;
		}
	}
	
	class LRUCache<K,V>{
		
		private	Node<K,V> head;
		private	Node<K,V> tail;
		private Map <K, Node<K,V>> map;
		private int maxSize;
		
		public LRUCache(int size){
			
			this.maxSize = size;
			map = new HashMap<K, Node<K,V>>();
			head = new Node<K,V>();
			tail = new Node<K,V>();
			head.prev = null;
			head.next = tail;
			tail.prev = head;
			tail.next = null;
		}
		
		public LRUCache(){
			this.maxSize = 0;
			head = new Node<K,V>();
			tail = new Node<K,V>();
			head.prev = null;
			head.next = tail;
			tail.prev = head;
			tail.next = null;
		}
		
		public V get(K key){
			Node node = map.get(key);
			if (node == null) {
				return null;
			}
			// only one element
			if (maxSize == 1){
				return (V)node.value;
			}
			
			// Refresh
			// detach it from the linked list and add the starting coz its being accessed
			detach(node);
			attach(node);
			return (V)node.value;
		}
		
		public void put(K key, V value){
			if (maxSize <= 0){
				return;
			}
			Node node = map.get(key);
			
			if (null != node) {
				//refresh the data
				detach(node);
				attach(node); // add node at the head of linked list
				node.value = value;
			}else{
				// create new node and adjust the node
				node = new Node(key, value);
				map.put(key, node);
				attach(node);
				// if cache is full then delete the least used data from linked list
				// which is at the end of the linked list
				if (map.size() > maxSize) {
					detach(tail.prev);
					map.remove(tail.key);
				}
			}
		}
		
		/*
		 * insert at the head
		 */
		public void attach(Node node){
			node.next = head.next;
			node.prev = head;
			
			head.next.prev = node;
			head.next = node;
		}
		
		public void detach(Node node){
			node.prev.next = node.next;
			node.next.prev = node.prev;
		}
		
	}
}
