/*
 * Copyright (C) 12/12/22, 2:26 PM Nguyen Huy
 *
 * Todo.java [lastModified: 12/12/22, 10:47 AM]
 *
 * Contact:
 * facebook: https://www.facebook.com/nguyenhuy158/
 * github: https://www.github.com/nguyenhuy158/
 */

package com.example.customanimation.todo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "todos")
public class Todo implements Serializable {
	@PrimaryKey
	long id;
	String  taskName;
	String  time;
	String  date;
	boolean isDone = false;
	String  description;
	String  tag;
	
	// public Todo(long id,
	//             String taskName,
	//             String time) {
	// 	this.id       = id;
	// 	this.taskName = taskName;
	// 	this.time     = time;
	// }
	//
	// public Todo(String taskName,
	//             String time) {
	// 	this.taskName = taskName;
	// 	this.time     = time;
	// }
	//
	// public Todo(String taskName,
	//             String time,
	//             boolean isDone) {
	// 	this.taskName = taskName;
	// 	this.time     = time;
	// 	this.isDone   = isDone;
	// }
	
	public Todo(long id,
	            String taskName,
	            String time,
	            String date) {
		this.id       = id;
		this.taskName = taskName;
		this.time     = time;
		this.date     = date;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getTag() {
		return tag;
	}
	
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getTaskName() {
		return taskName;
	}
	
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public boolean isDone() {
		return isDone;
	}
	
	public void setDone(boolean done) {
		isDone = done;
	}
}
