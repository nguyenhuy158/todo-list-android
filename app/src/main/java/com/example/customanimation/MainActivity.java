/*
 * Copyright (C) 12/12/22, 2:26 PM Nguyen Huy
 *
 * MainActivity.java [lastModified: 12/12/22, 2:25 PM]
 *
 * Contact:
 * facebook: https://www.facebook.com/nguyenhuy158/
 * github: https://www.github.com/nguyenhuy158/
 */

package com.example.customanimation;

import static com.example.customanimation.constants.Constants.BUNDLE_KEY_PUT_TODO;
import static com.example.customanimation.constants.Constants.CUSTOM_DIALOG;
import static com.example.customanimation.constants.Constants.REQUEST_CODE_TODO_TO_DETAIL;
import static com.example.customanimation.constants.Constants.getDate;
import static com.example.customanimation.constants.Constants.getTime;
import static com.example.customanimation.constants.Constants.updateDate;
import static com.example.customanimation.constants.Constants.updateTime;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.customanimation.todo.Todo;
import com.example.customanimation.todo.TodoAdapter;
import com.example.customanimation.todo.TodoDao;
import com.example.customanimation.todo.TodoDatabase;
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.RecyclerViewSwipeManager;
import com.h6ah4i.android.widget.advrecyclerview.touchguard.RecyclerViewTouchActionGuardManager;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class MainActivity<pulic> extends AppCompatActivity
		implements View.OnClickListener {
	final Calendar myCalendar = Calendar.getInstance();
	DatePickerDialog.OnDateSetListener date;
	TimePickerDialog.OnTimeSetListener time;
	LottieAnimationView                animationViewSwitchGreen;
	LottieAnimationView                animationViewHeartFav;
	LottieAnimationView                animationViewYoutubeLikeButton;
	boolean                            isSwitch  = false;
	boolean                            isYoutube = false;
	
	RecyclerView        recyclerViewTodoList;
	LottieAnimationView buttonAddTodo;
	Button              buttonDemo;
	
	TodoAdapter todoAdapter;
	EditText    ediTextDate;
	EditText    ediTextTime;
	
	EditText                            textInputTaskName;
	RecyclerViewTouchActionGuardManager recyclerViewTouchActionGuardManager;
	TodoDao todoDao;
	private RecyclerViewDragDropManager recyclerViewDragDropManager;
	private RecyclerViewSwipeManager    recyclerViewSwipeManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bindComponent();
		initComponent();
		setEvent();
	}
	
	@Override
	protected void onActivityResult(int requestCode,
	                                int resultCode,
	                                @Nullable Intent data) {
		super.onActivityResult(requestCode,
		                       resultCode,
		                       data);
		if (requestCode == REQUEST_CODE_TODO_TO_DETAIL && resultCode == RESULT_OK && data != null) {
			Todo todo = (Todo) data
					.getExtras()
					.getSerializable(BUNDLE_KEY_PUT_TODO);
			todoAdapter.updateTodo(todo);
			
		}
	}
	
	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(MainActivity.this)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle("Closing")
				.setMessage("Are you sure you want to close app 😭?")
				.setPositiveButton("Yes",
				                   new DialogInterface.OnClickListener() {
					                   @Override
					                   public void onClick(DialogInterface dialog,
					                                       int which) {
						                   finish();
					                   }
					
				                   })
				.setNegativeButton("No",
				                   null)
				.show();
	}
	
	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.activity_fade_in,
		                          R.anim.activity_fade_out);
	}
	
	private void setEvent() {
		// set onClick
		animationViewSwitchGreen.setOnClickListener(this);
		animationViewHeartFav.setOnClickListener(this);
		animationViewYoutubeLikeButton.setOnClickListener(this);
		
		buttonAddTodo.setOnClickListener(this);
		buttonDemo.setOnClickListener(this);
		ediTextDate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				
				new DatePickerDialog(MainActivity.this,
				                     date,
				                     myCalendar.get(Calendar.YEAR),
				                     myCalendar.get(Calendar.MONTH),
				                     myCalendar.get(Calendar.DAY_OF_MONTH)).show();
			}
		});
		ediTextTime.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				
				new TimePickerDialog(MainActivity.this,
				                     time,
				                     myCalendar.get(Calendar.HOUR_OF_DAY),
				                     myCalendar.get(Calendar.MINUTE),
				                     true).show();
			}
		});
	}
	
	private void initComponent() {
		// todoDao
		todoDao = TodoDatabase
				.getInstance(MainActivity.this)
				.todoDao();
		
		// calendar
		updateDate(ediTextDate,
		           myCalendar);
		updateTime(ediTextTime,
		           myCalendar);
		
		// recycler view
		RecyclerViewDragDropManager recyclerViewDragDropManager
				= new RecyclerViewDragDropManager();
		RecyclerViewSwipeManager recyclerViewSwipeManager
				= new RecyclerViewSwipeManager();
		recyclerViewTodoList.setLayoutManager(new LinearLayoutManager(this,
		                                                              RecyclerView.VERTICAL,
		                                                              false));
		
		// v1.0 drag
		todoAdapter = new TodoAdapter(this,
		                              R.layout.todo_item);
		RecyclerView.Adapter wrappedAdapter
				= recyclerViewDragDropManager.createWrappedAdapter(todoAdapter);
		wrappedAdapter
				= recyclerViewSwipeManager.createWrappedAdapter(wrappedAdapter);
		recyclerViewTodoList.setAdapter(wrappedAdapter);
		
		
		// disable change animations
		// ((SimpleItemAnimator) recyclerViewTodoList.getItemAnimator()).setSupportsChangeAnimations(false);
		
		// [OPTIONAL]
		recyclerViewDragDropManager.setInitiateOnTouch(true);
		recyclerViewDragDropManager.setInitiateOnLongPress(true);
		recyclerViewDragDropManager.setInitiateOnMove(true);
		
		recyclerViewDragDropManager.attachRecyclerView(recyclerViewTodoList);
		recyclerViewSwipeManager.attachRecyclerView(recyclerViewTodoList);
		
		
		// date time picker
		date = new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view,
			                      int year,
			                      int month,
			                      int day) {
				myCalendar.set(Calendar.YEAR,
				               year);
				myCalendar.set(Calendar.MONTH,
				               month);
				myCalendar.set(Calendar.DAY_OF_MONTH,
				               day);
				updateDate(ediTextDate,
				           myCalendar);
			}
		};
		
		time = new TimePickerDialog.OnTimeSetListener() {
			@Override
			public void onTimeSet(TimePicker view,
			                      int hourOfDay,
			                      int minute) {
				myCalendar.set(Calendar.HOUR_OF_DAY,
				               hourOfDay);
				myCalendar.set(Calendar.MINUTE,
				               minute);
				updateTime(ediTextTime,
				           myCalendar);
				
			}
		};
		
		//	update todo in revert activity
		getTodoFromDataBaseAndIntoAdapter();
	}
	
	private void getTodoFromDataBaseAndIntoAdapter() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				List<Todo> todos = todoDao.getTodos();
				for (Todo todo : todos) {
					new Handler(Looper.getMainLooper()).post(new Runnable() {
						@Override
						public void run() {
							todoAdapter.addTodo(todo);
						}
					});
				}
			}
		}).start();
	}
	
	private void bindComponent() {
		recyclerViewTodoList = findViewById(R.id.todoList);
		buttonAddTodo        = findViewById(R.id.buttonAddTodo);
		textInputTaskName    = findViewById(R.id.textInputTaskName);
		buttonDemo           = findViewById(R.id.buttonDemo);
		ediTextDate          = findViewById(R.id.editTextDate);
		ediTextTime          = findViewById(R.id.editTextTime);
		animationViewSwitchGreen       = findViewById(R.id.animationViewSwitchGreen);
		animationViewHeartFav
		                               = findViewById(R.id.animationViewHeartFav);
		animationViewYoutubeLikeButton
		                               = findViewById(R.id.animationViewYoutubeLikeButton);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.buttonDemo:
				CustomDialogFragment dialog = new CustomDialogFragment();
				dialog.show(getSupportFragmentManager(),
				            CUSTOM_DIALOG);
				break;
			case R.id.buttonAddTodo:
				handleAddTodo();
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
	
	private void handleAddTodo() {
		buttonAddTodo.setMinAndMaxProgress(0.0f,
		                                   1.0f);
		buttonAddTodo.playAnimation();
		
		
		long uuid = UUID
				.randomUUID()
				.getMostSignificantBits() & Long.MAX_VALUE;
		String taskName = String.valueOf(textInputTaskName.getText());
		String time     = getTime(myCalendar);
		String date     = getDate(myCalendar);
		
		if (taskName == null || taskName.length() == 0 || taskName.isEmpty()) {
			Animation animation
					= AnimationUtils.loadAnimation(MainActivity.this,
					                               R.anim.shake_error);
			textInputTaskName.startAnimation(animation);
			textInputTaskName.requestFocus();
		} else {
			textInputTaskName.setText(null);
			Todo todo = new Todo(uuid,
			                     taskName,
			                     time,
			                     date);
			todoAdapter.addTodo(todo);
			new Thread(new Runnable() {
				@Override
				public void run() {
					todoDao.insert(todo);
				}
			}).start();
		}
	}
}