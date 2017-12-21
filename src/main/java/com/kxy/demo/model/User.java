package com.kxy.demo.model;

import java.io.Serializable;

public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5586852736991073397L;
	

	private String id;
	
	private String name;
	
	private String parentId;
	
	private Integer level;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public User(String id, String name, String parentId, Integer level) {
		super();
		this.id = id;
		this.name = name;
		this.parentId = parentId;
		this.level = level;
	}

	public User() {
		super();
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", parentId=" + parentId + ", level=" + level + "]";
	}
	
}
