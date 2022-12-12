/*
 * Copyright (C) 12/12/22, 2:26 PM Nguyen Huy
 *
 * Constants.java [lastModified: 12/12/22, 1:09 PM]
 *
 * Contact:
 * facebook: https://www.facebook.com/nguyenhuy158/
 * github: https://www.github.com/nguyenhuy158/
 */

package com.example.customanimation.constants;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Constants {
	public static final long   SPLASH_ACTIVITY_TIMEOUT = 1000;
	public static final long   FINISH_ACTIVITY_TIMEOUT     = 3000;
	public static final int    REQUEST_CODE_TODO_TO_DETAIL = 52000668;
	public static final String CUSTOM_DIALOG               = "CUSTOM_DIALOG";
	public static final String BUNDLE_KEY_PUT_TODO     = "BUNDLE_KEY_PUT_TODO";
	
	
	public static void hideKeyboard(Activity activity) {
		InputMethodManager imm
				= (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
		//Find the currently focused view, so we can grab the correct window token from it.
		View view = activity.getCurrentFocus();
		//If no view currently has focus, create a new one, just so we can grab a window token from it
		if (view == null) {
			view = new View(activity);
		}
		imm.hideSoftInputFromWindow(view.getWindowToken(),
		                            0);
	}
	
	public static String getDate(Calendar myCalendar) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dateFormat.format(myCalendar.getTime());
	}
	
	public static String getTime(Calendar myCalendar) {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		return dateFormat.format(myCalendar.getTime());
	}
	
	
	public static void updateDate(EditText editTextDate,
	                              Calendar myCalendar) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		editTextDate.setText(dateFormat.format(myCalendar.getTime()));
	}
	
	public static void updateTime(EditText editTextTime,
	                              Calendar myCalendar) {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		editTextTime.setText(dateFormat.format(myCalendar.getTime()));
	}
}
