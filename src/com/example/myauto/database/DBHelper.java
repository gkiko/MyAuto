package com.example.myauto.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "MyAutoDatabase";
	private static final int DATABASE_VERSION = 13;

	// Table MANUFACTURERS (spinner)
	public static final String MANUFACTURERS_TABLE = "MANUFACTURERS";
	public static final String MAN_ID_SPINNER = "id_sp";
	public static final String MAN_ID = "id";
	public static final String MAN_NAME = "name";

	// Table MODELS (spinner)
	public static final String MODELS_TABLE = "MAN_MODELS";
	public static final String MOD_ID = "id";
	public static final String MOD_ID_MAN = "id_man";
	public static final String MOD_NAME = "name";
	public static final String MOD_GROUP = "group_mod";

	// Table VVIP and NEW
	public static final String VVIP_TABLE = "VVIP";
	public static final String NEW_TABLE = "NEW";
	public static final String VVIP_ID = "id";
	public static final String VVIP_NAME = "name";
	public static final String VVIP_PRICE = "price";
	public static final String VVIP_YEAR = "year";
	public static final String VVIP_IMG_URL = "img_url";

	// Table Fuel and Gear (spinners)
	public static final String FUEL_TABLE = "FUEL";
	public static final String GEAR_TABLE = "GEAR";
	public static final String FUEL_ID = "id";
	public static final String FUEL_NAME = "name";

	public static final String MANUFACTURERS_CREATE = "create table "
			+ MANUFACTURERS_TABLE + " ("+MAN_ID_SPINNER+" integer primary key," + MAN_ID + " integer , "
			+ MAN_NAME + " text not null);";
	public static final String MODELS_CREATE = "create table " + MODELS_TABLE
			+ " (" + MOD_ID + " integer primary key, " + MOD_ID_MAN + " integer, "
			+ MOD_NAME + " text not null, " + MOD_GROUP + " text);";
	public static final String TABLE_DESC = " (" + VVIP_ID
			+ " integer primary key," + VVIP_NAME + " text not null,"
			+ VVIP_PRICE + " text not null," + VVIP_YEAR + " text not null,"
			+ VVIP_IMG_URL + " text);";
	public static final String VVIP_CREATE = "create table if not exists "
			+ VVIP_TABLE + TABLE_DESC;
	public static final String NEW_CREATE = "create table if not exists "
			+ NEW_TABLE + TABLE_DESC;
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
		System.out.println(MANUFACTURERS_CREATE);
		System.out.println(MODELS_CREATE);
		System.out.println(VVIP_CREATE);
		db.execSQL(MANUFACTURERS_CREATE);
		db.execSQL(MODELS_CREATE);
		db.execSQL(VVIP_CREATE);
		db.execSQL(NEW_CREATE);
		db.execSQL(FUEL_CREATE);
		db.execSQL(GEAR_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + MANUFACTURERS_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + MODELS_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + VVIP_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + NEW_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + FUEL_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + GEAR_TABLE);
		onCreate(db);
	}

}
