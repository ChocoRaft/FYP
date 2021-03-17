package com.fyp.womensafetyapp;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Display extends Activity{

	//ArrayAdapter adapter;
	SQLiteDatabase db;
	ListView contactList;
	ArrayList<String> names = new ArrayList<String>();
	ArrayList<String> nums = new ArrayList<String>();
	Cursor c;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display);
		contactList = (ListView) this.findViewById(R.id.contactsListView);



		db=openOrCreateDatabase("NumDB", Context.MODE_PRIVATE, null);
		db.execSQL("CREATE TABLE IF NOT EXISTS details(name VARCHAR,number VARCHAR);");

		c=db.rawQuery("SELECT * FROM details", null);
		   if(c.getCount()==0) {
		       return;
		   }

		   while(c.moveToNext()) {
		   	   names.add(c.getString(0));
		   	   nums.add(c.getString(1));

		   }
		   showMessage(names, nums);




		contactList.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> arg0, View view, final int position, long arg3) {
				AlertDialog.Builder alertDialog = new  AlertDialog.Builder(Display.this);
				alertDialog.setTitle("Delete");
				alertDialog.setMessage("Do you want to delete contact "+contactList.getItemAtPosition(position));
				alertDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						deleteSelectedItem((String)contactList.getItemAtPosition(position));
					}
				});

				alertDialog.show();
				return true;
			}
		});





	}

	public void deleteSelectedItem(String number){
		db=openOrCreateDatabase("NumDB", Context.MODE_PRIVATE, null);
		String table = "details";
		String whereClause = "name=?";
		String[] whereArgs = new String[] { String.valueOf(number) };
		db.delete(table, whereClause, whereArgs);
		finish();
		startActivity(getIntent());

	}





	
	public void showMessage(final ArrayList names, final ArrayList nums) {
		final String[] namesArray = {"1"};
		final String[] numsArray = {"2"};
		names.toArray(namesArray);
        nums.toArray(numsArray);
        //adapter = new ArrayAdapter<String>(this, R.layout.listviewfile, names);


		ArrayAdapter adapter = new ArrayAdapter(this, R.layout.listviewfile, android.R.id.text1, names) {
			//@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view = super.getView(position, convertView, parent);
				TextView text1 = (TextView) view.findViewById(android.R.id.text1);
				TextView text2 = (TextView) view.findViewById(android.R.id.text2);

				text1.setText((String) names.get(position));
				text2.setText((String) nums.get(position));
				return view;
			}
		};

        contactList.setAdapter(adapter);
    }


	public void register(View v) {
		finish();
		Intent i_register=new Intent(Display.this,Register.class);
		startActivity(i_register);

	}
	
	public void back(View v) {
		finish();
		Intent i_back=new Intent(Display.this,MainActivity.class);
		startActivity(i_back);
		
		}


	private class OnItemLongClickListener implements AdapterView.OnItemLongClickListener {
		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
			return false;
		}
	}
}








