package com.program.cache.impl;

import java.util.concurrent.locks.ReentrantLock;
/**
 * 
 * @author rrohit
 *
 * @param <T>
 */
public class Queue<T> {

	private final ReentrantLock lock;
	private Node<T> head;
	private Node<T> tail;
	private int count;
//	private int capacity;
	
	public Queue(){//int capacity){
		this.head = this.tail = null;
		lock = new ReentrantLock();
	}
	
	/**
	 * Thread safe method to insert element at the head of the queue.
	 * @param item
	 */
	public void enQueue(T item){
		lock.lock();
		System.out.println("******Queue Adding ****  "+item);
		try{
			Node<T> node = new Node<T>(item);
			if (this.head == null || this.tail == null) { //Queue is empty
				this.head = node;
				this.tail = node;
			}else{ // add element at rear of queue, in FIFO order
				this.tail.setNext(node);
				this.tail = node;
			}
			this.count++;
		}finally{
			lock.unlock();
		}
		
	}
	
	/**
	 * Thread safe method to remove element from tail of queue.
	 * @return @Node<T>
	 */
	public Node<T> deQueue(){
		lock.lock();
		try{
			if (this.head == null){
				System.out.println("****Queue Underflow*****");
				return null;
			}else{//remove from head of queue, FIFO order
				Node<T> temp = this.head;
				this.head = head.getNext();
				System.out.println("******Queue Delelting from head ****  "+temp.getItem());
				return temp;
			}
		}finally{
			lock.unlock();
		}
	}
	
	/**
	 * Removes the first occurrence of item from queue, starting from head of Queue.
	 * @param item
	 * @return @Node<T> if found else null
	 */
	public Node<T> remove(T item){
		lock.lock();
		System.out.println("******Queue Removing ****  "+item);
		try{	
			if (this.head == null){
				System.out.println("******Queue underflow****");
			}else{
				Node<T> current = this.head;
				Node<T> prev = current;
				Node<T> temp;
				while (current != null){
					if (current.getItem().equals(item)){
						temp = current;
						prev.setNext(current.getNext());
						return temp;
					}
					prev = current;
					current = current.getNext();
				}
			}
		}finally{
			lock.unlock();
		}
		return null;
	}

}
