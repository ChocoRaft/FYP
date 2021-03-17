package com.fyp.womensafetyapp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void sendAlert(View v){
		Intent i_send=new Intent(MainActivity.this, SendAlert.class);
		startActivity(i_send);

	}
	
	public void display_no(View v) {
		Intent i_view=new Intent(MainActivity.this,Display.class);
		startActivity(i_view);
		
	}

	public void instruct(View v) {
		Intent i_help=new Intent(MainActivity.this,Instructions.class);
    	startActivity(i_help);
	}

	public void emergencyContacts(View v) {
		Intent i_verify=new Intent(MainActivity.this,EmergencyContacts.class);
		startActivity(i_verify);
	}

	public void back(View v) {
		finish();
	}
}
