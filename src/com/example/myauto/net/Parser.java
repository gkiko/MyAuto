package com.example.myauto.net;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.*;

import com.example.myauto.item.CarFacade;
import com.example.myauto.item.CarImageable;
import com.example.myauto.item.CarItem;
import com.example.myauto.item.Item;

public class Parser {
	private SAXParser saxParser;
	private DefaultHandler handler;
	private DefaultHandler handler2;
	private ArrayList<CarFacade> elements;
	private Item singleItem;
	public static String splitBy = ",";
	
	private static final List<String> tagNames = Arrays
			.asList("id", "date", "photo", "price", "customs", "views",
					"photos", "photosCnt", "changable", "auction", "forrent",
					"manufacturer", "model", "year", "category", "odometer",
					"cylinders", "drive", "doors", "color", "airbags", "vin",
					"windows", "conditioner", "climat", "leather", "disks",
					"navigation", "centralLock", "hatch", "boardcomp",
					"hydraulics", "chairWarming", "obsIndicator", "turbo",
					"clientName", "phone", "wheel", "engine", "fuel", "gear",
					"location", "desc", "dealer");
	private static final boolean[] tagStauses = new boolean[tagNames.size()];

	private final String idTag = "id";
	private final String photoTag = "photo";
	private final String priceTag = "price";
	private final String manufacturerTag = "manufacturer";
	private final String modelTag = "model";
	private final String yearTag = "year";

	public Parser() {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			saxParser = factory.newSAXParser();
			handler = new DefaultHandler() {
				CarFacade cf;

				boolean tId = false;
				boolean tPhoto = false;
				boolean tPrice = false;
				boolean tMake = false;
				boolean tModel = false;
				boolean tYear = false;

				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
						throws SAXException {
					if (qName.equalsIgnoreCase(idTag)) {
						cf = new CarFacade(new CarImageable(), new CarItem());
						tId = true;
					} else {
						if (qName.equalsIgnoreCase(photoTag)) {
							tPhoto = true;
						} else {
							if (qName.equalsIgnoreCase(priceTag)) {
								tPrice = true;
							} else {
								if (qName.equalsIgnoreCase(manufacturerTag)) {
									tMake = true;
								} else {
									if (qName.equalsIgnoreCase(modelTag)) {
										tModel = true;
									} else {
										if (qName.equalsIgnoreCase(yearTag)) {
											tYear = true;
										}
									}
								}
							}
						}
					}
				}

				public void endElement(String uri, String localName,
						String qName) throws SAXException {
				}

				public void characters(char ch[], int start, int length)
						throws SAXException {
					String value = new String(ch, start, length);
					setValToField(value, cf);
				}
				
				private void setValToField(String value, CarFacade cf){
					if (tId) {
						elements.add(cf);
						cf.setValueToProperty(CarItem.ID, value);
						tId = false;
					} else {
						if (tPhoto) {
							cf.setURL(value);
							tPhoto = false;
						} else {
							if (tPrice) {
								cf.setValueToProperty(CarItem.PRICE, value);
								tPrice = false;
							} else {
								if (tMake) {
									cf.setValueToProperty(CarItem.MAKE, value);
									tMake = false;
								} else {
									if (tModel) {
										cf.setValueToProperty(CarItem.MODEL, value);
										tModel = false;
									} else {
										if (tYear) {
											cf.setValueToProperty(CarItem.YEAR, value);
											tYear = false;
										}
									}
								}
							}
						}
					}
				}
			};
			
			handler2 = new DefaultHandler() {
				String value;
				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
						throws SAXException {
				}

				private boolean itIsTag(String tag) {
					return tagNames.contains(tag);
				}

				private boolean tagIsFirstInList(String tag) {
					return tagNames.get(0).equals(tag);
				}

				private void openTag(String tag) {
					tagStauses[tagNames.indexOf(tag)] = true;
				}

				public void endElement(String uri, String localName,
						String qName) throws SAXException {
					if(itIsTag(qName))
						singleItem.setValueToProperty(qName, value);
				}

				public void characters(char ch[], int start, int length)
						throws SAXException {
					value = new String(ch, start, length);
				}
				
				private String getOpenedTag(){
					return tagNames.get(getTagIndex());
				}
				
				private int getTagIndex(){
					for(int i=0;i<tagStauses.length;i++){
						if(tagStauses[i])
							return i;
					}
					return -1;
				}
				
				private void closeTag(String tag){
					tagStauses[tagNames.indexOf(tag)] = false;
				}
			};
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public ArrayList<CarFacade> parse(String srcToParse) {
		elements = new ArrayList<CarFacade>();
		try {
			saxParser.parse(new InputSource(new StringReader(srcToParse)), handler);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return elements;
	}
	
	public Item parse2(String xmlToParse) {
		singleItem = new CarItem();
		try {
			saxParser.parse(new InputSource(new StringReader(xmlToParse)),
					handler2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return singleItem;
	}
}
