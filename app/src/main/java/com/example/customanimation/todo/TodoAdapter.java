/*
 * Copyright (C) 12/12/22, 2:26 PM Nguyen Huy
 *
 * TodoAdapter.java [lastModified: 12/12/22, 12:41 PM]
 *
 * Contact:
 * facebook: https://www.facebook.com/nguyenhuy158/
 * github: https://www.github.com/nguyenhuy158/
 */

package com.example.customanimation.todo;

import static com.example.customanimation.constants.Constants.BUNDLE_KEY_PUT_TODO;
import static com.example.customanimation.constants.Constants.REQUEST_CODE_TODO_TO_DETAIL;
import static com.example.customanimation.constants.Constants.hideKeyboard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.customanimation.R;
import com.example.customanimation.TodoDetailActivity;
import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.draggable.ItemDraggableRange;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.SwipeableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.SwipeableItemConstants;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultAction;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultActionDoNothing;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultActionMoveToSwipedDirection;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractDraggableSwipeableItemViewHolder;

import java.util.ArrayList;
import java.util.List;

public class TodoAdapter
		extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder>
		implements DraggableItemAdapter<TodoAdapter.TodoViewHolder>,
		           SwipeableItemAdapter<TodoAdapter.TodoViewHolder> {
	// Variable
	Context    context;
	int        layout;
	List<Todo> todoList = new ArrayList<>();
	
	
	// Init
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
	
	// RecyclerView.Adapter
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
	
	public void updateTodo(Todo todo) {
		int indexTodo = -1;
		for (int i = 0; i < todoList.size(); i++) {
			if (todoList.get(i).getId() == todo.getId()) {
				indexTodo = i;
				break;
			}
		}
		if (indexTodo != -1) {
			todoList.set(indexTodo,
			             todo);
			notifyItemChanged(indexTodo);
		}
	}
	
	@Override
	public void onBindViewHolder(@NonNull TodoViewHolder holder,
	                             int position) {
		if (holder == null) {return;}
		
		Todo todo = todoList.get(position);
		
		holder.textViewTaskName.setText(todo.getTaskName());
		holder.textViewTime.setText(todo.getTime());
		holder.textViewDate.setText(todo.getDate());
		if (todo.isDone()) {
			holder.buttonDone.setMinAndMaxProgress(1f,
			                                       1f);
			holder.buttonDone.playAnimation();
		}
		
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
		
		holder.todoItem.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				hideKeyboard((Activity) context);
				Intent intent = new Intent(context,
				                           TodoDetailActivity.class);
				ActivityOptionsCompat activityOptionsCompat
						= ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,
						                                                     holder.textViewTaskName,
						                                                     holder.textViewTaskName.getTransitionName());
				Bundle bundle = new Bundle();
				bundle.putSerializable(BUNDLE_KEY_PUT_TODO,
				                       todo);
				intent.putExtras(bundle);
				((Activity) context).startActivityForResult(intent,
				                                            REQUEST_CODE_TODO_TO_DETAIL,
				                                            activityOptionsCompat.toBundle());
				return false;
			}
		});
	}
	
	@Override
	public int getItemCount() {
		return todoList.size();
	}
	
	// DraggableItemAdapter
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
	
	
	// SwipeableItemAdapter
	@Override
	public int onGetSwipeReactionType(@NonNull TodoViewHolder holder,
	                                  int position,
	                                  int x,
	                                  int y) {
		// return SwipeableItemConstants.REACTION_CAN_SWIPE_LEFT;
		return SwipeableItemConstants.REACTION_CAN_SWIPE_BOTH_H;
	}
	
	@Override
	public void onSwipeItemStarted(@NonNull TodoViewHolder holder,
	                               int position) {
		notifyDataSetChanged();
	}
	
	@Override
	public void onSetSwipeBackground(@NonNull TodoViewHolder holder,
	                                 int position,
	                                 int type) {
		if (type == SwipeableItemConstants.DRAWABLE_SWIPE_LEFT_BACKGROUND || type == SwipeableItemConstants.DRAWABLE_SWIPE_RIGHT_BACKGROUND) {
			holder.itemView.setBackground(context
					                              .getResources()
					                              .getDrawable(R.drawable.background_todo_item));
			
			// background_swipe_todo_item
		} else {
			holder.itemView.setBackground(context
					                              .getResources()
					                              .getDrawable(R.drawable.background_todo_item));
		}
	}
	
	@Nullable
	@Override
	public SwipeResultAction onSwipeItem(@NonNull TodoViewHolder holder,
	                                     int position,
	                                     int result) {
		if (result == SwipeableItemConstants.RESULT_SWIPED_LEFT || result == SwipeableItemConstants.RESULT_SWIPED_RIGHT) {
			notifyItemRemoved(position);
			todoList.remove(position);
			return new SwipeResultActionMoveToSwipedDirection() {
				// Optionally, you can override these three methods
				// - void onPerformAction()
				// - void onSlideAnimationEnd()
				// - void onCleanUp()
			};
		} else {
			return new SwipeResultActionDoNothing();
		}
	}
	
	// TodoViewHolder
	class TodoViewHolder extends AbstractDraggableSwipeableItemViewHolder {
		LottieAnimationView buttonDone;
		TextView            textViewTaskName;
		TextView            textViewTime;
		TextView            textViewDate;
		
		
		LinearLayout todoItem;
		ImageView    dragHandle;
		LinearLayout container;
		
		public TodoViewHolder(@NonNull View itemView) {
			super(itemView);
			buttonDone       = itemView.findViewById(R.id.buttonDone);
			textViewTaskName = itemView.findViewById(R.id.textViewTaskName);
			textViewTime     = itemView.findViewById(R.id.textViewTime);
			textViewDate     = itemView.findViewById(R.id.textViewDate);
			todoItem         = itemView.findViewById(R.id.todoItem);
			container        = itemView.findViewById(R.id.container);
			
			dragHandle = itemView.findViewById(R.id.drag_handle);
		}
		
		@NonNull
		@Override
		public View getSwipeableContainerView() {
			return container;
		}
		
		
	}
	
	
}
