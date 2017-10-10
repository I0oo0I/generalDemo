package com.kxy.demo.util;

import java.util.List;

public class TreeNode<T> {

	private T node;
	
	private List<TreeNode<T>> chidNode;

	public T getNode() {
		return node;
	}

	public void setNode(T node) {
		this.node = node;
	}

	public List<TreeNode<T>> getChidNode() {
		return chidNode;
	}

	public void setChidNode(List<TreeNode<T>> chidNode) {
		this.chidNode = chidNode;
	}
	
}
