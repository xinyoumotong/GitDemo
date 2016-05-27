package com.example.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * 
 * @author VACK
 *
 */
public class MyDatabaseOpenHelper extends SQLiteOpenHelper {

	public MyDatabaseOpenHelper(Context context, String name, int version) {
		super(context, name, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d("db", "db");
		db.execSQL("create table if not exists collectionsingle(_id integer  primary key autoincrement,picurl varchar(128),author varchar(64),singlename varchar(64),collname varchar(64),singleurl varchar(128))");
		db.execSQL("create table if not exists collectioncoll(_id integer primary key autoincrement,picurl varchar(128), author varchar(64),collname varchar(64),collurl varchar(128),description varchar(128))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}
