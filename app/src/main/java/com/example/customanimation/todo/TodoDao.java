/*
 * Copyright (C) 12/12/22, 8:11 PM Nguyen Huy
 *
 * TodoDao.java [lastModified: 12/12/22, 8:11 PM]
 *
 * Contact:
 * facebook: https://www.facebook.com/nguyenhuy158/
 * github: https://www.github.com/nguyenhuy158/
 */

package com.example.customanimation.todo;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TodoDao {
	@Query("SELECT * FROM todos")
	List<Todo> getTodos();
	
	@Insert
	void insertTodos(Todo... todos);
	
	@Query("SELECT * FROM todos WHERE id == :todo_id")
	Todo getTodo(long todo_id);
	
	@Query("")
	void change(Todo uuidFirst, Todo uuidTodo);
	
	@Insert
	void insert(Todo todo);
	
	@Update
	void update(Todo todo);
	
	@Delete
	void delete(Todo todo);
}
