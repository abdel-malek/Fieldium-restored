package com.bluebrand.fieldiumadmin.View;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.bluebrand.fieldiumadmin.R;

public class CustomProgressDialog extends Dialog {

	boolean stopBack = false  ; 
	String message ;
	
	public CustomProgressDialog(Context context, String message , boolean stopBack) {
		super(context);
		this.message = message ; 
		this.stopBack = stopBack ; 
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.custom_progress_dialog);

		getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT)); 
	
		TextView textView_message = (TextView) findViewById(R.id.textView_message);
		textView_message.setText(message);

		ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
		progressBar.setVisibility(View.VISIBLE);

	}

}
