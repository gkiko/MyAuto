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
	private final String photoTag = "photo";
	
	public Parser() {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			saxParser = factory.newSAXParser();
			handler = new DefaultHandler() {
				CarFacade cf;
				String value;

				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
						throws SAXException {
					if(tagIsFirstInList(qName)){
						cf = new CarFacade(new CarImageable(), new CarItem());
					}
				}

				public void endElement(String uri, String localName,
						String qName) throws SAXException {
					if(itIsTag(qName)){
						if(tagIsFirstInList(qName)){
							elements.add(cf);
						}
						cf.setValueToProperty(qName, value);
						if(photoTag(qName)){
							cf.setURL(value);
						}
					}
				}

				public void characters(char ch[], int start, int length)
						throws SAXException {
					value = new String(ch, start, length);
				}
				
				private boolean tagIsFirstInList(String tag) {
					return tagNames.get(0).equals(tag);
				}
				
				private boolean itIsTag(String tag) {
					return tagNames.contains(tag);
				}
				
				private boolean photoTag(String tag){
					return tag.equalsIgnoreCase(photoTag);
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

				public void endElement(String uri, String localName,
						String qName) throws SAXException {
					if(itIsTag(qName))
						singleItem.setValueToProperty(qName, value);
				}

				public void characters(char ch[], int start, int length)
						throws SAXException {
					value = new String(ch, start, length);
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
