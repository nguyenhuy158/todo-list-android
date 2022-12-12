/*
 * Copyright (C) 12/12/22, 8:51 AM Nguyen Huy
 *
 * todo.java [lastModified: 12/12/22, 8:51 AM]
 *
 * Contact:
 * facebook: https://www.facebook.com/nguyenhuy158/
 * github: https://www.github.com/nguyenhuy158/
 */

package com.example.customanimation.todo;

public class Todo {
	String  taskName;
	String  time;
	boolean isDone = false;
	
	public Todo(String taskName,
	            String time) {
		this.taskName = taskName;
		this.time     = time;
	}
	
	public Todo(String taskName,
	            String time,
	            boolean isDone) {
		this.taskName = taskName;
		this.time     = time;
		this.isDone   = isDone;
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
