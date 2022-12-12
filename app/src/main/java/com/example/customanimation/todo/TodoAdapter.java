/*
 * Copyright (C) 12/12/22, 8:52 AM Nguyen Huy
 *
 * TodoAdapter.java [lastModified: 12/12/22, 8:52 AM]
 *
 * Contact:
 * facebook: https://www.facebook.com/nguyenhuy158/
 * github: https://www.github.com/nguyenhuy158/
 */

package com.example.customanimation.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.customanimation.R;

import java.util.ArrayList;
import java.util.List;

public class TodoAdapter
		extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
	Context    context;
	int        layout;
	List<Todo> todoList = new ArrayList<>();
	
	public TodoAdapter(Context context,
	                   int layout) {
		this.context = context;
		this.layout  = layout;
	}
	
	public TodoAdapter(Context context,
	                   int layout,
	                   List<Todo> todoList) {
		this.context  = context;
		this.layout   = layout;
		this.todoList = todoList;
	}
	
	@NonNull
	@Override
	public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
	                                         int viewType) {
		View view = LayoutInflater
				.from(context)
				.inflate(layout,
				         parent,
				         false);
		return new TodoViewHolder(view);
	}
	
	public void addTodo(Todo todo) {
		todoList.add(0, todo);
		notifyItemInserted(0);
	}
	
	@Override
	public void onBindViewHolder(@NonNull TodoViewHolder holder,
	                             int position) {
		if (holder == null) {return;}
		
		Todo todo = todoList.get(position);
		
		holder.textViewTaskName.setText(todo.getTaskName());
		holder.textViewTime.setText(todo.getTime());
		if (todo.isDone()) {
			holder.buttonDone.setMinAndMaxProgress(1f,
			                                       1f);
			holder.buttonDone.playAnimation();
		}
		
		// holder.buttonDone.setOnClickListener(new View.OnClickListener() {
		// 	@Override
		// 	public void onClick(View v) {
		// 		if (todo.isDone()) {
		// 			holder.buttonDone.setSpeed(-2f);
		// 			holder.buttonDone.setMinAndMaxProgress(0f,
		// 			                                       1f);
		// 		} else {
		// 			holder.buttonDone.setSpeed(2f);
		// 			holder.buttonDone.setMinAndMaxProgress(0f,
		// 			                                       1f);
		// 		}
		// 		todo.setDone(!todo.isDone());
		// 		holder.buttonDone.playAnimation();
		// 	}
		// });
		holder.todoItem.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (todo.isDone()) {
					holder.buttonDone.setSpeed(-2f);
					holder.buttonDone.setMinAndMaxProgress(0f,
					                                       1f);
				} else {
					holder.buttonDone.setSpeed(2f);
					holder.buttonDone.setMinAndMaxProgress(0f,
					                                       1f);
				}
				todo.setDone(!todo.isDone());
				holder.buttonDone.playAnimation();
			}
		});
	}
	
	@Override
	public int getItemCount() {
		return todoList.size();
	}
	
	class TodoViewHolder extends RecyclerView.ViewHolder {
		LottieAnimationView buttonDone;
		TextView            textViewTaskName;
		TextView            textViewTime;
		
		LinearLayout todoItem;
		
		public TodoViewHolder(@NonNull View itemView) {
			super(itemView);
			buttonDone       = itemView.findViewById(R.id.buttonDone);
			textViewTaskName = itemView.findViewById(R.id.textViewTaskName);
			textViewTime     = itemView.findViewById(R.id.textViewTime);
			todoItem     = itemView.findViewById(R.id.todoItem);
		}
		
		
	}
	
	
}
