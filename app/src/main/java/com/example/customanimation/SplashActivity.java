/*
 * Copyright (C) 12/12/22, 8:06 AM Nguyen Huy
 *
 * splashActivity.java [lastModified: 12/12/22, 8:06 AM]
 *
 * Contact:
 * facebook: https://www.facebook.com/nguyenhuy158/
 * github: https://www.github.com/nguyenhuy158/
 */

package com.example.customanimation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class SplashActivity extends AppCompatActivity {
	
	private static final long SPLASH_ACTIVITY_TIMEOUT = 2000;
	LottieAnimationView animationViewYoutubeIcon;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		animationViewYoutubeIcon = findViewById(R.id.animationViewYoutubeIcon);
		// animationViewYoutubeIcon.playAnimation();
		
		new Handler().postDelayed(new Runnable() {
			                          @Override
			                          public void run() {
				                          animationViewYoutubeIcon.setVisibility(View.GONE);
				                          Intent intent = new Intent(SplashActivity.this,
				                                                     MainActivity.class);
				                          startActivity(intent);
				                          overridePendingTransition(android.R.anim.fade_in,
				                                                    android.R.anim.fade_out);
				                          finish();
			                          }
		                          },
		                          SPLASH_ACTIVITY_TIMEOUT);
		
	}
}