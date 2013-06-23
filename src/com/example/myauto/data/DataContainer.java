package com.example.myauto.data;

import java.util.ArrayList;

import com.example.myauto.item.CarFacade;

/**
 * DataContainer class is intended to store *only* the NEW list.
 * @author user
 *
 */
public class DataContainer {
	private static ArrayList<CarFacade> newList;
	
	public static void setNewList(ArrayList<CarFacade> carList){
		newList = carList;
	}
	
	public static ArrayList<CarFacade> getNewList(){
		return newList;
	}
	
	public static boolean hasNewList(){
		return newList != null;
	}
}
