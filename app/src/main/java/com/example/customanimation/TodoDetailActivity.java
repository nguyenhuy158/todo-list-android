/*
 * Copyright (C) 12/12/22, 4:06 PM Nguyen Huy
 *
 * TodoDetailActivity.java [lastModified: 12/12/22, 3:57 PM]
 *
 * Contact:
 * facebook: https://www.facebook.com/nguyenhuy158/
 * github: https://www.github.com/nguyenhuy158/
 */

package com.example.customanimation;

import static com.example.customanimation.constants.Constants.BUNDLE_KEY_PUT_TODO;
import static com.example.customanimation.constants.Constants.FINISH_ACTIVITY_TIMEOUT;
import static com.example.customanimation.constants.Constants.getDate;
import static com.example.customanimation.constants.Constants.getTime;
import static com.example.customanimation.constants.Constants.updateDate;
import static com.example.customanimation.constants.Constants.updateTime;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.customanimation.todo.Todo;
import com.example.customanimation.todo.TodoDao;
import com.example.customanimation.todo.TodoDatabase;

import java.util.Calendar;

public class TodoDetailActivity extends AppCompatActivity
		implements View.OnClickListener {
	final Calendar myCalendar = Calendar.getInstance();
	
	TextView            textViewTaskName;
	EditText            editTextTime;
	EditText            editTextDate;
	EditText            editTextDescription;
	LottieAnimationView buttonDone;
	LottieAnimationView buttonSave;
	Todo                todo;
	TodoDao             todoDao;
	
	private DatePickerDialog.OnDateSetListener date;
	private TimePickerDialog.OnTimeSetListener time;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_detail);
		
		// get todo item
		try {
			todo = (Todo) getIntent()
					.getExtras()
					.getSerializable(BUNDLE_KEY_PUT_TODO);
		} catch (Exception exception) {
			todo = null;
		}
		// split
		bindView();
		updateUI();
		initEventAndData();
		handleEvent();
	}
	
	private void handleEvent() {
	
	}
	
	private void handleEventSave() {
		buttonSave.setMinAndMaxProgress(0.0f,
		                                1.0f);
		buttonSave.playAnimation();
		
		String date        = getDate(myCalendar);
		String time        = getTime(myCalendar);
		String description = String.valueOf(editTextDescription.getText());
		
		todo.setDate(date);
		todo.setTime(time);
		todo.setDescription(description);
		
		Intent returnIntent = new Intent();
		Bundle bundle       = new Bundle();
		bundle.putSerializable(BUNDLE_KEY_PUT_TODO,
		                       todo);
		returnIntent.putExtras(bundle);
		
		setResult(Activity.RESULT_OK,
		          returnIntent);
		
		new Handler().postDelayed(new Runnable() {
			                          @Override
			                          public void run() {
				                          finish();
			                          }
		                          },
		                          FINISH_ACTIVITY_TIMEOUT);
	}
	
	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(android.R.anim.fade_in,
		                          android.R.anim.fade_out);
	}
	
	private void updateUI() {
		if (todo != null) {
			textViewTaskName.setText(todo.getTaskName());
			editTextTime.setText(todo.getTime());
			editTextDate.setText(todo.getDate());
			editTextDescription.setText(todo.getDescription());
			
			// button done
			buttonDone.setMinAndMaxProgress(0.0f,
			                                1.0f);
			if (todo.isDone()) {
				buttonDone.setSpeed(1);
			} else {
				buttonDone.setSpeed(-1);
			}
			buttonDone.playAnimation();
		}
	}
	
	private void bindView() {
		textViewTaskName    = findViewById(R.id.textViewTaskName);
		editTextTime        = findViewById(R.id.editTextTime);
		editTextDate        = findViewById(R.id.editTextDate);
		editTextDescription = findViewById(R.id.editTextDescription);
		buttonDone          = findViewById(R.id.buttonDone);
		buttonSave          = findViewById(R.id.buttonSave);
	}
	
	private void initEventAndData() {
		// todoDao
		todoDao = TodoDatabase
				.getInstance(this)
				.todoDao();
		// event
		editTextTime.setOnClickListener(this);
		editTextDate.setOnClickListener(this);
		buttonDone.setOnClickListener(this);
		buttonSave.setOnClickListener(this);
		
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
				updateDate(editTextDate,
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
				updateTime(editTextTime,
				           myCalendar);
				
			}
		};
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.buttonDone:
				buttonDone.setMinAndMaxProgress(0.0f,
				                                1.0f);
				if (todo.isDone()) {
					buttonDone.setSpeed(-1);
				} else {
					buttonDone.setSpeed(1);
				}
				buttonDone.playAnimation();
				todo.setDone(!todo.isDone());
				
				break;
			case R.id.buttonSave:
				handleEventSave();
				break;
			case R.id.editTextDate:
				new DatePickerDialog(TodoDetailActivity.this,
				                     date,
				                     myCalendar.get(Calendar.YEAR),
				                     myCalendar.get(Calendar.MONTH),
				                     myCalendar.get(Calendar.DAY_OF_MONTH)).show();
				break;
			case R.id.editTextTime:
				new TimePickerDialog(TodoDetailActivity.this,
				                     time,
				                     myCalendar.get(Calendar.HOUR_OF_DAY),
				                     myCalendar.get(Calendar.MINUTE),
				                     true).show();
				break;
			default:
				break;
		}
	}
	
	
}