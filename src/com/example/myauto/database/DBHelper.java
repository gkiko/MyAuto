package com.example.myauto.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "MyAutoDatabase";
	private static final int DATABASE_VERSION = 6;

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
	public static final String FUEL_NAME_ENG = "name_en";
	public static final String FUEL_NAME_GEO = "name_ge";
	public static final String FUEL_NAME_RUS = "name_ru";
	
	// Table Categories
	public static final String CATEGORIES_TABLE = "CATEGORIES";
	public static final String CATEGORY_ID = "id";
	public static final String CATEGORY_NAME_ENG = "name_en";
	public static final String CATEGORY_NAME_GEO = "name_ge";
	public static final String CATEGORY_NAME_RUS = "name_ru";
	
	// Table Door Types
	public static final String DOOR_TYPES_TABLE = "DOOR_TYPES";
	public static final String DOOR_TYPES_ID = "id";
	public static final String DOOR_TYPES_NAME_ENG = "name_en";
	public static final String DOOR_TYPES_NAME_GEO = "name_ge";
	public static final String DOOR_TYPES_NAME_RUS = "name_ru";
	
	// Table Drive Types
	public static final String DRIVE_TYPES_TABLE = "DRIVE_TYPES";
	public static final String DRIVE_TYPES_ID = "id";
	public static final String DRIVE_TYPES_NAME_ENG = "name_en";
	public static final String DRIVE_TYPES_NAME_GEO = "name_ge";
	public static final String DRIVE_TYPES_NAME_RUS = "name_ru";
	
	// Table Locations
	public static final String LOCATIONS_TABLE = "LOCATIONS";
	public static final String LOCATIONS_ID = "id";
	public static final String LOCATIONS_PARENT_ID = "parent_id";
	public static final String LOCATIONS_NAME_ENG = "name_en";
	public static final String LOCATIONS_NAME_GEO = "name_ge";
	public static final String LOCATIONS_NAME_RUS = "name_ru";
	
	//Table Days
	public static final String DAYS_TABLE = "DAYS";
	public static final String DAYS_ID = "id";
	public static final String DAYS_NAME_ENG = "name_en";
	public static final String DAYS_NAME_GEO = "name_ge";
	public static final String DAYS_NAME_RUS = "name_ru";

	// Create Tables Queries
	public static final String MANUFACTURERS_CREATE = "create table "
			+ MAKE_TABLE + " ("+MAKE_ID_SPINNER+" integer primary key," + MAKE_ID + " integer , "
			+ MAKE_NAME + " text not null);";
	public static final String MODELS_CREATE = "create table " + MODELS_TABLE
			+ " (" + MOD_ID + " integer primary key, " + MOD_ID_MAN + " integer, "
			+ MOD_NAME + " text not null, " + MOD_GROUP + " text);";
	public static final String FUEL_CREATE = "create table if not exists "
			+ FUEL_TABLE + "(" + FUEL_ID + " integer primary key," + FUEL_NAME_ENG
			+ " text not null," + FUEL_NAME_GEO + " tetx not null," + FUEL_NAME_RUS + " text not null);";
	public static final String GEAR_CREATE = "create table if not exists "
			+ GEAR_TABLE + "(" + FUEL_ID + " integer primary key," + FUEL_NAME_ENG
			+ " text not null," + FUEL_NAME_GEO + " tetx not null," + FUEL_NAME_RUS + " text not null);";
	public static final String CATEGORIES_CREATE = "create table if not exists " 
			+ CATEGORIES_TABLE + " ("+CATEGORY_ID+" integer primary key," + CATEGORY_NAME_ENG
			+ " text not null," + CATEGORY_NAME_GEO + " text not null," + CATEGORY_NAME_RUS + " text not null);";
	public static final String DOOR_TYPES_CREATE = "create table if not exists "
			+ DOOR_TYPES_TABLE + " (" + DOOR_TYPES_ID + " integer primary key," + DOOR_TYPES_NAME_ENG
			+ " text not null," + DOOR_TYPES_NAME_GEO + " text not null," + DOOR_TYPES_NAME_RUS + " text not null);";
	public static final String DRIVE_TYPES_CREATE = "create table if not exists "
			+ DRIVE_TYPES_TABLE + " (" + DRIVE_TYPES_ID + " integer primary key," + DRIVE_TYPES_NAME_ENG
			+ " text not null," + DRIVE_TYPES_NAME_GEO + " text not null," + DRIVE_TYPES_NAME_RUS + " text not null);";
	public static final String LOCATIONS_CREATE = "create table if not exists "
			+ LOCATIONS_TABLE + " (" + LOCATIONS_ID + " integer primary key," + LOCATIONS_PARENT_ID
			+ " integer, " + LOCATIONS_NAME_ENG + " text not null," + LOCATIONS_NAME_GEO + " text not null, " 
			+ LOCATIONS_NAME_RUS + " text not null);";
	public static final String DAYS_CREATE = "create table if not exists "
			+ DAYS_TABLE + " (" + DAYS_ID + " integer primary key," + DAYS_NAME_ENG
			+ " text not null," + DAYS_NAME_GEO + " text not null," + DAYS_NAME_RUS + " text not null);";
			

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
		db.execSQL(CATEGORIES_CREATE);
		db.execSQL(DOOR_TYPES_CREATE);
		db.execSQL(DRIVE_TYPES_CREATE);
		db.execSQL(LOCATIONS_CREATE);
		db.execSQL(DAYS_CREATE);
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
		db.execSQL("DROP TABLE IF EXISTS " + CATEGORIES_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + DOOR_TYPES_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + DRIVE_TYPES_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + LOCATIONS_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + DAYS_TABLE);
	}

}
