/*
 * Copyright (C) 12/12/22, 8:13 PM Nguyen Huy
 *
 * TodoDatabase.java [lastModified: 12/12/22, 8:13 PM]
 *
 * Contact:
 * facebook: https://www.facebook.com/nguyenhuy158/
 * github: https://www.github.com/nguyenhuy158/
 */

package com.example.customanimation.todo;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Todo.class},
          version = 1)
public abstract class TodoDatabase extends RoomDatabase {
	public static final String       DATABASE_NAME = "Room-database";
	private static      TodoDatabase todoDatabase;
	
	public static TodoDatabase getInstance(Context context) {
		if (todoDatabase == null) {
			todoDatabase = Room
					.databaseBuilder(context,
					                 TodoDatabase.class,
					                 DATABASE_NAME)
					.build();
		}
		return todoDatabase;
	}
	
	public abstract TodoDao todoDao();
}
