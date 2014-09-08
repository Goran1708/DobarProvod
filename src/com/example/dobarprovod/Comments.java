package com.example.dobarprovod;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
public class Comments extends Activity implements OnClickListener {

	LinearLayout commentSection;
	TextView tVComment;
	Button addComment;
	EditText inputComment;
	String comment;
	int commentId = 0;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comments);
		initializeVariables();
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

	}

	private void initializeVariables() {
		commentSection = (LinearLayout) findViewById(R.id.topLayout);
		inputComment = (EditText) findViewById(R.id.eTInputComment);
		addComment = (Button) findViewById(R.id.bAddComments);
		addComment.setOnClickListener(this);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bAddComments:
			comment = inputComment.getText().toString();
			tVComment = new TextView(this);
			tVComment.setText(comment);
			tVComment.setId(commentId);
			tVComment.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT));
			commentSection.addView(tVComment);
			commentId++;
		}
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent localIntent = new Intent(Comments.this,
				ClubInformation.class);
		startActivity(localIntent);
		return true;
	}
}
