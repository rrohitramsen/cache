package com.program.cache.impl;

/**
 * 
 * @author rrohit
 *
 * @param <T>
 */
public class Node<T> {
	
	private Node<T> next;
	private Node<T> prev;
	private T item;
	
	public Node(){
		this.next = null;
		this.prev = null;
	}
	
	public Node(T item){
		this.item = item;
		this.next = null;
		this.prev = null;
	}

	public Node<T> getNext() {
		return next;
	}

	public void setNext(Node<T> next) {
		this.next = next;
	}

	public Node<T> getPrev() {
		return prev;
	}

	public void setPrev(Node<T> prev) {
		this.prev = prev;
	}

	public T getItem() {
		return item;
	}

	public void setItem(T item) {
		this.item = item;
	}
}
