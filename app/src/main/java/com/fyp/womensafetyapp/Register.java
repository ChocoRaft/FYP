package com.fyp.womensafetyapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends Activity {
	
	EditText name,number;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		//Toast.makeText(getApplicationContext(), "Activity created",Toast.LENGTH_LONG).show();

	}
	

	
	public void storeInDB(View v) {
		//Toast.makeText(getApplicationContext(), "save started",Toast.LENGTH_LONG).show();
		name = (EditText) this.findViewById(R.id.editText1);
		number = (EditText) this.findViewById(R.id.editText2);
		String str_name=name.getText().toString();
		String str_number=number.getText().toString();
		SQLiteDatabase db;
		db=openOrCreateDatabase("NumDB", Context.MODE_PRIVATE, null);
		//Toast.makeText(getApplicationContext(), "db created",Toast.LENGTH_LONG).show();
		
		db.execSQL("CREATE TABLE IF NOT EXISTS details(name VARCHAR,number VARCHAR);");
		//Toast.makeText(getApplicationContext(), "table created",Toast.LENGTH_LONG).show();
		
		Cursor c=db.rawQuery("SELECT * FROM details", null);

		db.execSQL("INSERT INTO details VALUES('"+str_name+"','"+str_number+"');");
		Toast.makeText(getApplicationContext(), "Successfully Saved",Toast.LENGTH_SHORT).show();

		
		
		db.close();
		back();
		}




	public void back() {
		finish();
		Intent i_back=new Intent(Register.this,Display.class);
		startActivity(i_back);

	}
	
	
	
	
	
	


}
