/*
 * Copyright (C) 12/12/22, 7:36 AM Nguyen Huy
 *
 * MainActivity.java [lastModified: 12/12/22, 7:24 AM]
 *
 * Contact:
 * facebook: https://www.facebook.com/nguyenhuy158/
 * github: https://www.github.com/nguyenhuy158/
 */

package com.example.customanimation;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.airbnb.lottie.LottieAnimationView;
import com.example.customanimation.todo.Todo;
import com.example.customanimation.todo.TodoAdapter;
import com.google.android.material.textfield.TextInputEditText;
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager;

import java.util.UUID;

public class MainActivity extends AppCompatActivity
		implements View.OnClickListener {
	
	LottieAnimationView animationViewSwitchGreen;
	LottieAnimationView animationViewHeartFav;
	LottieAnimationView animationViewYoutubeLikeButton;
	boolean             isSwitch  = false;
	boolean             isYoutube = false;
	
	RecyclerView recyclerViewTodoList;
	LinearLayout buttonAddTodo;
	
	TodoAdapter todoAdapter;
	
	TextInputEditText textInputTaskName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bindComponent();
		initComponent();
		setEvent();
		
		// todoAdapter.add(new Todo("Code 1",
		//                          "00:01"));
		
		// // handler
		// new Handler().postDelayed(new Runnable() {
		// 	                          @Override
		// 	                          public void run() {
		//
		// 		                          todoAdapter.add(new Todo("Code 2",
		// 		                                                   "00:01"));
		// 		                          todoAdapter.add(new Todo("Code 3",
		// 		                                                   "00:01"));
		// 	                          }
		//                           },
		//                           1000);
		
	}
	
	private void setEvent() {
		// set onClick
		// animationViewSwitchGreen.setOnClickListener(this);
		// animationViewHeartFav.setOnClickListener(this);
		// animationViewYoutubeLikeButton.setOnClickListener(this);
		
		buttonAddTodo.setOnClickListener(this);
	}
	
	private void initComponent() {
		RecyclerViewDragDropManager dragDropManager = new RecyclerViewDragDropManager();
		todoAdapter = new TodoAdapter(this,
		                              R.layout.todo_item);
		RecyclerView.Adapter wrappedAdapter = dragDropManager.createWrappedAdapter(todoAdapter);
		recyclerViewTodoList.setAdapter(wrappedAdapter);
		recyclerViewTodoList.setLayoutManager(new LinearLayoutManager(this,
		                                                              RecyclerView.VERTICAL,
		                                                              false));
		
		// disable change animations
		((SimpleItemAnimator) recyclerViewTodoList.getItemAnimator()).setSupportsChangeAnimations(false);
		
		// [OPTIONAL]
		// dragDropManager.setInitiateOnTouch(true);
		// dragDropManager.setInitiateOnLongPress(true);
		// dragDropManager.setInitiateOnMove(true);
		
		dragDropManager.attachRecyclerView(recyclerViewTodoList);
	}
	
	private void bindComponent() {
		recyclerViewTodoList = findViewById(R.id.todoList);
		buttonAddTodo        = findViewById(R.id.buttonAddTodo);
		textInputTaskName = findViewById(R.id.textInputTaskName);
		// animationViewSwitchGreen       = findViewById(R.id.animationViewSwitchGreen);
		// animationViewHeartFav
		//                                = findViewById(R.id.animationViewHeartFav);
		// animationViewYoutubeLikeButton
		//                                = findViewById(R.id.animationViewYoutubeLikeButton);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.buttonAddTodo:
				long uuid = UUID
						.randomUUID()
						.getMostSignificantBits() & Long.MAX_VALUE;
				String taskName = String.valueOf(textInputTaskName.getText());
				String time = "00:00";
				Todo todo = new Todo(uuid,
				                     taskName,
				                     time);
				todoAdapter.addTodo(todo);
				break;
			case R.id.animationViewSwitchGreen:
				if (isSwitch) {
					animationViewSwitchGreen.setMinAndMaxProgress(0.5f,
					                                              1f);
				} else {
					animationViewSwitchGreen.setMinAndMaxProgress(0f,
					                                              0.5f);
				}
				animationViewSwitchGreen.playAnimation();
				isSwitch = !isSwitch;
				break;
			case R.id.animationViewHeartFav:
				
				animationViewHeartFav.playAnimation();
				break;
			case R.id.animationViewYoutubeLikeButton:
				if (isYoutube) {
					animationViewYoutubeLikeButton.setMinAndMaxFrame("Dislike Animation",
					                                                 "End Dislike Animation",
					                                                 true);
				} else {
					animationViewYoutubeLikeButton.setMinAndMaxFrame("Like Animation",
					                                                 "End Like Animation",
					                                                 true);
				}
				isYoutube = !isYoutube;
				animationViewYoutubeLikeButton.playAnimation();
				break;
			default:
				break;
		}
	}
}