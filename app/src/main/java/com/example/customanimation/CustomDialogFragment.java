/*
 * Copyright (C) 12/12/22, 2:26 PM Nguyen Huy
 *
 * CustomDialogFragment.java [lastModified: 12/12/22, 1:26 PM]
 *
 * Contact:
 * facebook: https://www.facebook.com/nguyenhuy158/
 * github: https://www.github.com/nguyenhuy158/
 */

package com.example.customanimation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class CustomDialogFragment extends DialogFragment {
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater,
	                         @Nullable ViewGroup container,
	                         @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_custom_dialog,
		                             container);
		return view;
		// return super.onCreateView(inflater,
		//                           container,
		//                           savedInstanceState);
	}
	
	@NonNull
	@Override
	public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
		View layout = getActivity()
				.getLayoutInflater()
				.inflate(R.layout.activity_custom_dialog,
				         null,
				         false);
		assert layout != null;
		//build the alert dialog child of this fragment
		AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
		//restore the background_color and layout_gravity that Android strips
		b
				.getContext()
				.getTheme()
				.applyStyle(R.style.Theme_CustomAnimation,
				            true);
		b.setView(layout);
		return b.create();
		// return super.onCreateDialog(savedInstanceState);
	}
}
