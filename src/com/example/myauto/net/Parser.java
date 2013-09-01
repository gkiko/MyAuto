package com.example.myauto.net;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.example.myauto.item.CarItem;
import com.example.myauto.item.Item;

public class Parser {
	private XmlPullParser xpp;

	public Parser() {
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			xpp = factory.newPullParser();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
	}

	public void setSourceToParse(String source) throws XmlPullParserException {
		xpp.setInput(new StringReader(source));
	}

	public List<Item> parseAsList() throws XmlPullParserException,
			IOException {
		Item itm = null;
		List<Item> lst = new ArrayList<Item>();
		String lastTagName = "";

		int eventType = xpp.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			if (eventType == XmlPullParser.START_TAG) {
				lastTagName = xpp.getName();
				if (infoStartOrEnd()) {
					itm = new CarItem();
				}
			} else if (eventType == XmlPullParser.END_TAG) {
				if (infoStartOrEnd()) {
					lst.add(itm);
				}
			} else if (eventType == XmlPullParser.TEXT) {
				saveTagValue(itm, lastTagName);
			}
			eventType = xpp.next();
		}
		return lst;
	}
	
	private boolean infoStartOrEnd(){
		return startOrEndTag("car") || startOrEndTag("details");
	}

	private boolean startOrEndTag(String tagName) {
		return tagName != null ? xpp.getName().equals(tagName) : false;
	}

	private void saveTagValue(Item itm, String tagName) {
		if(itm == null)return;
		itm.setValueToProperty(tagName, xpp.getText());
	}
}
