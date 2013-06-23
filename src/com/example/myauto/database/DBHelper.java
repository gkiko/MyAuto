package com.example.myauto.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "MyAutoDatabase";
	private static final int DATABASE_VERSION = 3;

	// Table MANUFACTURERS (spinner)
	public static final String MAKE_TABLE = "MANUFACTURERS";
	public static final String MAKE_ID_SPINNER = "id_sp";
	public static final String MAKE_ID = "id";
	public static final String MAKE_NAME = "name";

	// Table MODELS (spinner)
	public static final String MODELS_TABLE = "MODELS";
	public static final String MOD_ID = "id";
	public static final String MOD_ID_MAN = "id_man";
	public static final String MOD_NAME = "name";
	public static final String MOD_GROUP = "group_mod";

	// Table Fuel and Gear (spinners)
	public static final String FUEL_TABLE = "FUEL";
	public static final String GEAR_TABLE = "GEAR";
	public static final String FUEL_ID = "id";
	public static final String FUEL_NAME = "name";

	public static final String MANUFACTURERS_CREATE = "create table "
			+ MAKE_TABLE + " ("+MAKE_ID_SPINNER+" integer primary key," + MAKE_ID + " integer , "
			+ MAKE_NAME + " text not null);";
	public static final String MODELS_CREATE = "create table " + MODELS_TABLE
			+ " (" + MOD_ID + " integer primary key, " + MOD_ID_MAN + " integer, "
			+ MOD_NAME + " text not null, " + MOD_GROUP + " text);";
	public static final String FUEL_CREATE = "create table if not exists "
			+ FUEL_TABLE + "(" + FUEL_ID + " integer primary key," + FUEL_NAME
			+ " text not null);";
	public static final String GEAR_CREATE = "create table if not exists "
			+ GEAR_TABLE + "(" + FUEL_ID + " integer primary key," + FUEL_NAME
			+ " text not null);";

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createTables(db);
	}
	
	public static void createTables(SQLiteDatabase db) {
		db.execSQL(MANUFACTURERS_CREATE);
		db.execSQL(MODELS_CREATE);
		db.execSQL(FUEL_CREATE);
		db.execSQL(GEAR_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		dropTables(db);
		onCreate(db);
	}
	
	public static void dropTables(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + MAKE_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + MODELS_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + FUEL_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + GEAR_TABLE);
	}

}
