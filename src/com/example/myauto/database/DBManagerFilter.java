package com.example.myauto.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManagerFilter {
	private static DBHelper dbHelper;
	private static SQLiteDatabase db;

	public static void init(Context context) {
		if (dbHelper == null) {
			dbHelper = new DBHelper(context);
			db = dbHelper.getWritableDatabase();
		}
	}
	
	public static String getManIdByName(String name) {
		String[] tableColumns = new String[] { dbHelper.MAN_ID+" as _id" };
		String whereClause = dbHelper.MAN_NAME+" = ?";
		String[] whereArgs = new String[] { name };
		Cursor c = db.query(dbHelper.MANUFACTURERS_TABLE, tableColumns, whereClause,
				whereArgs, null, null, null);
		
		String res = "";
		c.moveToFirst();
		while (c.isAfterLast() == false) 
		{
			res = ""+c.getInt(0);
		    c.moveToNext();
		}
		return res;
	}
	
	public static String getModIdByName(String name) {
		if(name == null)
			return "";
		String[] tableColumns = new String[] { dbHelper.MOD_ID+" as _id" };
		String whereClause = dbHelper.MOD_NAME+" = ?";
		String[] whereArgs = new String[] { name };
		Cursor c = db.query(dbHelper.MODELS_TABLE, tableColumns, whereClause,
				whereArgs, null, null, null);
		
		String res = "";
		c.moveToFirst();
		while (c.isAfterLast() == false) 
		{
			res = ""+c.getInt(0);
		    c.moveToNext();
		}
		return res;
	}
	
	public static String getFuelGearIdByName(String name, int flag) {
		String[] tableColumns = new String[] { dbHelper.FUEL_ID+" as _id" };
		String whereClause = dbHelper.FUEL_NAME+" = ?";
		String[] whereArgs = new String[] { name };
		String table = dbHelper.FUEL_TABLE;
		if(flag == 1)
			table = dbHelper.GEAR_TABLE;
		Cursor c = db.query(table, tableColumns, whereClause,
				whereArgs, null, null, null);
		
		String res = "";
		if (c.moveToFirst()) {
			res = ""+c.getInt(0);
		}
		return res;
	}
}
