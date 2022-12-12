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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.customanimation.R;
import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.draggable.ItemDraggableRange;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractDraggableItemViewHolder;

import java.util.ArrayList;
import java.util.List;

public class TodoAdapter
		extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder>
		implements DraggableItemAdapter<TodoAdapter.TodoViewHolder> {
	Context    context;
	int        layout;
	List<Todo> todoList = new ArrayList<>();
	
	public TodoAdapter() {
		setHasStableIds(true);
	}
	
	public TodoAdapter(Context context,
	                   int layout) {
		setHasStableIds(true);
		this.context = context;
		this.layout  = layout;
	}
	
	public TodoAdapter(Context context,
	                   int layout,
	                   List<Todo> todoList) {
		setHasStableIds(true);
		this.context  = context;
		this.layout   = layout;
		this.todoList = todoList;
	}
	
	@Override
	public long getItemId(int position) {
		// return super.getItemId(position);
		return todoList
				.get(position)
				.getId();
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
		todoList.add(0,
		             todo);
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
	
	@Override
	public boolean onCheckCanStartDrag(@NonNull TodoViewHolder holder,
	                                   int position,
	                                   int x,
	                                   int y) {
		View itemView   = holder.itemView;
		View dragHandle = holder.dragHandle;
		
		int handleWidth  = dragHandle.getWidth();
		int handleHeight = dragHandle.getHeight();
		int handleLeft   = dragHandle.getLeft();
		int handleTop    = dragHandle.getTop();
		
		return (x >= handleLeft) && (x < handleLeft + handleWidth) && (y >= handleTop) && (y < handleTop + handleHeight);
	}
	
	@Nullable
	@Override
	public ItemDraggableRange onGetItemDraggableRange(@NonNull TodoViewHolder holder,
	                                                  int position) {
		return null;
	}
	
	@Override
	public void onMoveItem(int fromPosition,
	                       int toPosition) {
		// TODO: 12/12/2022 add animation here 
		Todo removed = todoList.remove(fromPosition);
		todoList.add(toPosition,
		             removed);
	}
	
	@Override
	public boolean onCheckCanDrop(int draggingPosition,
	                              int dropPosition) {
		return false;
	}
	
	@Override
	public void onItemDragStarted(int position) {
		notifyDataSetChanged();
	}
	
	@Override
	public void onItemDragFinished(int fromPosition,
	                               int toPosition,
	                               boolean result) {
		notifyDataSetChanged();
	}
	
	class TodoViewHolder extends AbstractDraggableItemViewHolder {
		LottieAnimationView buttonDone;
		TextView            textViewTaskName;
		TextView            textViewTime;
		
		LinearLayout todoItem;
		ImageView    dragHandle;
		
		public TodoViewHolder(@NonNull View itemView) {
			super(itemView);
			buttonDone       = itemView.findViewById(R.id.buttonDone);
			textViewTaskName = itemView.findViewById(R.id.textViewTaskName);
			textViewTime     = itemView.findViewById(R.id.textViewTime);
			todoItem       = itemView.findViewById(R.id.todoItem);
			
			dragHandle = itemView.findViewById(R.id.drag_handle);
		}
		
		
	}
	
	
}
