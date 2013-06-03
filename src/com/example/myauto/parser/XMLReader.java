package com.example.myauto.parser;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.*;

public class XMLReader {
	private SAXParser saxParser;
	private DefaultHandler handler;
	private ArrayList<String> elements;
	public static String splitBy = ",";

	private final String idTag = "id";
	private final String photoTag = "photo";
	private final String priceTag = "price";
	private final String manufacturerTag = "manufacturer";
	private final String modelTag = "model";
	private final String yearTag = "year";

	public XMLReader() {
		elements = new ArrayList<String>();
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			saxParser = factory.newSAXParser();
			handler = new DefaultHandler() {

				boolean tId = false;
				boolean tPhoto = false;
				boolean tPrice = false;
				boolean tManufacturer = false;
				boolean tModel = false;
				boolean tYear = false;

				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
						throws SAXException {
					if (qName.equalsIgnoreCase(idTag)) {
						tId = true;
					} else {
						if (qName.equalsIgnoreCase(photoTag)) {
							tPhoto = true;
						} else {
							if (qName.equalsIgnoreCase(priceTag)) {
								tPrice = true;
							} else {
								if (qName.equalsIgnoreCase(manufacturerTag)) {
									tManufacturer = true;
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
					if (tId) {
						elements.add(new String(ch, start, length));
						tId = false;
					} else {
						if (tPhoto) {
							appendElement(new String(ch, start, length));
							tPhoto = false;
						} else {
							if (tPrice) {
								appendElement(new String(ch, start, length));
								tPrice = false;
							} else {
								if (tManufacturer) {
									appendElement(new String(ch, start, length));
									tManufacturer = false;
								} else {
									if (tModel) {
										appendElement(new String(ch, start, length));
										tModel = false;
									} else {
										if (tYear) {
											appendElement(new String(ch, start, length));
											tYear = false;
										}
									}
								}
							}
						}
					}
				}

				private void appendElement(String string) {
					String old = elements.get(elements.size()-1);
					elements.set(elements.size()-1, old+splitBy+string);
				}

			};
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> parse(String srcToParse) {
		elements.clear();
		try {
			saxParser.parse(new InputSource(new StringReader(srcToParse)), handler);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return elements;
	}
}
