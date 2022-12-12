/*
 * Copyright (C) 12/12/22, 2:26 PM Nguyen Huy
 *
 * ExampleInstrumentedTest.java [lastModified: 12/12/22, 7:38 AM]
 *
 * Contact:
 * facebook: https://www.facebook.com/nguyenhuy158/
 * github: https://www.github.com/nguyenhuy158/
 */

package com.example.customanimation;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
	@Test
	public void useAppContext() {
		// Context of the app under test.
		Context appContext = InstrumentationRegistry
				.getInstrumentation()
				.getTargetContext();
		assertEquals("com.example.customanimation",
		             appContext.getPackageName());
	}
}