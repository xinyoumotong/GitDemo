package com.example.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

/**
 * 
 * @author VACK
 *
 */
public class DbManager {
	private final static String DATABASE_NAME = "mydb.db";
	private final static int DATABASE_VERSION = 1;
	private SQLiteDatabase database;
	private MyDatabaseOpenHelper helper;

	public DbManager(Context context) {
		helper = new MyDatabaseOpenHelper(context, DATABASE_NAME,
				DATABASE_VERSION);
		database = helper.getReadableDatabase();

	}

	/**
	 * 插入专题数据 ContentValues中包含 专题的url:collurl，封面图片：picurl 作者名：author</br>
	 * 专题名：collname 简介 ：description
	 * 
	 * @author ysf
	 * @param values
	 * @return
	 * 
	 */
	public boolean insertColl(ContentValues values) {
		database = helper.getWritableDatabase();
		try {
			database.insert("collectioncoll", null, values);
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 插入单篇数据 ContentValues包含 单篇的url:singleurl </br> 封面图片：picurl,作者名：author,
	 * 单篇名：singlename 专题名：collname
	 * 
	 * @author ysf
	 * @param values
	 * @return picurl varchar(128),author varchar(64),singlename
	 *         varchar(64),collname varchar(64),singleurl varchar(128)
	 */
	public boolean insertSingle(ContentValues values) {
		database = helper.getWritableDatabase();
		try {
			database.insert("collectionsingle", null, values);
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 查询表中所有单篇
	 * 
	 * @author ysf
	 * @return 
	 *         Cursor-"_id","picurl","author","singlename","collname","singleurl"
	 */
	public Cursor querySingle() {
		database = helper.getWritableDatabase();
		Cursor cursor = database.query("collectionsingle", new String[] {
				"_id", "picurl", "author", "singlename", "collname",
				"singleurl" }, null, null, null, null, null);
		return cursor;
	}

	/**
	 * 查询表中所有专题
	 * 
	 * @author ysf
	 * @return Cursor-"_id","picurl","author","collname","collurl"，"description"
	 */
	public Cursor queryColl() {
		database = helper.getWritableDatabase();
		Cursor cursor = database.query("collectioncoll", new String[] { "_id",
				"picurl", "author", "collname", "collurl", "description" },
				null, null, null, null, null);
		return cursor;
	}

	/**
	 * 根据单篇名删除表中元素
	 * 
	 * @param singlename
	 * @author ysf
	 */
	public void removeSingle(String singlename) {
		database = helper.getWritableDatabase();
		database.delete("collectionsingle", "singlename=?",
				new String[] { singlename });
	}

	/**
	 * 根据专题名删除表中元素
	 * 
	 * @param collname
	 * @author ysf
	 */
	public void removeColl(String collname) {
		database = helper.getWritableDatabase();
		database.delete("collectioncoll", "collname=?",
				new String[] { collname });
	}
}
