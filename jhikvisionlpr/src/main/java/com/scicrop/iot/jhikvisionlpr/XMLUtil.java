package com.scicrop.iot.jhikvisionlpr;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLUtil {

	public static Document getDocument() throws Exception {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		return documentBuilder.newDocument();
	}

	public static Document createDocument(String elementName) throws ParserConfigurationException {
		DocumentBuilder dbdr = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = dbdr.newDocument();
		Element ele = doc.createElement(elementName);
		doc.appendChild(ele);
		return doc;
	}

	public static Element createChildElement(Element parentElement, String childName) {
		Element child = null;
		if (parentElement != null && (!"".equals(childName))) {
			child = parentElement.getOwnerDocument().createElement(childName);
			parentElement.appendChild(child);
		}
		return child;
	}

	public static Element getChildElement(Element element, String tagName) throws Exception {

		return getChildElement(element, tagName, true);

	}

	public static Element getChildElement(Element element, String tagName, boolean create) throws Exception {
		Node node = null;
		NodeList nodeList = element.getChildNodes();
		Element childElm = null;
		for (int i = 0; i < nodeList.getLength(); i++) {
			node = nodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE && tagName.equals(node.getNodeName())) {
				childElm = (Element) node;
				break;
			}
		}

		if ((childElm == null) && (create)) {
			childElm = createChildElement(element, tagName);
		}

		return childElm;
	}

	public static List<Element> getChildElements(Element element, String tagName) throws Exception {
		Node node = null;
		List<Element> elementList = new ArrayList();
		NodeList nodeList = element.getChildNodes();
		Element childElm = null;
		for (int i = 0; i < nodeList.getLength(); i++) {
			
			node = nodeList.item(i);
			
			if (node.getNodeType() == Node.ELEMENT_NODE && tagName.equals(node.getNodeName())) {
				childElm = (Element) node;
				elementList.add(childElm);
			}
		}

		return elementList;
	}
	
	public static Document getDocumentFromFile(String fileNameWithPath)
			throws ParserConfigurationException, SAXException, IOException {
		Document retVal = null;
		if (fileNameWithPath != null) {
			String modifiedInXML = fileNameWithPath.trim();
			if (modifiedInXML.length() > 0) {

				FileReader inFileReader = new FileReader(modifiedInXML);
				try {
					InputSource iSource = new InputSource(inFileReader);
					retVal = getDocument(iSource);
				} finally {
					inFileReader.close();
				}
			}
		}
		return retVal;
	}

	public static Document getDocument(InputSource inSource)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();

		DocumentBuilder dbdr = fac.newDocumentBuilder();
		
		return dbdr.parse(inSource);
	}
	
	public static Document getDocument(String xmlStr) throws ParserConfigurationException, SAXException, IOException {
		InputSource is = new InputSource(new StringReader(xmlStr));
		return getDocument(is);
	}

	public static String getFirstLevelTextContent(Node node) {
		NodeList list = node.getChildNodes();
		StringBuilder textContent = new StringBuilder();
		textContent.append("");
		for (int i = 0; i < list.getLength(); ++i) {
			Node child = list.item(i);
			if (child.getNodeType() == Node.TEXT_NODE)
				textContent.append(child.getTextContent());
		}
		return textContent.toString().trim();
	}
	
	public static Plates convertXmlToBeanObject(String xmlStr) throws Exception { 
		return convertXmlToBeanObject(getDocument(xmlStr));
	}
	
	
	public static Plates convertXmlToBeanObject(InputStream is) throws Exception {
		InputSource source = new InputSource(is);
		return convertXmlToBeanObject(getDocument(source));
	}
	
	public static Plates convertXmlToBeanObject(Document doc ) throws Exception { 

		
		
		
		
		Element platesElm = doc.getDocumentElement();
		Plates platesPlatesEntity = new Plates();


	
		


		List<Element> plateNdList = XMLUtil.getChildElements(platesElm, "Plate");
		List<Plate> plates = new ArrayList<Plate> ();
		platesPlatesEntity.setPlates(plates);
		for (Element plateElm: plateNdList) {
			
			Plate plate = new Plate();
			plates.add(plate);
			
			
			NodeList cn = plateElm.getChildNodes();
			
			for(int i = 0; i < cn.getLength(); i++) {
				Node item = cn.item(i);
				if(item.getNodeName().equals("plateNumber")) plate.setPlateNumber(item.getTextContent());
				else if(item.getNodeName().equals("IsMulti")) plate.setIsMulti(item.getTextContent());
				else if(item.getNodeName().equals("captureTime")) plate.setCaptureTime(item.getTextContent());
				else if(item.getNodeName().equals("country")) plate.setCountry(item.getTextContent());
				else if(item.getNodeName().equals("direction")) plate.setDirection(item.getTextContent());
				else if(item.getNodeName().equals("laneNo")) plate.setLaneNo(item.getTextContent());
				else if(item.getNodeName().equals("matchingResult")) plate.setMatchingResult(item.getTextContent());
				else if(item.getNodeName().equals("picName")) plate.setPicName(item.getTextContent());
			}
			

		} 

		return platesPlatesEntity; 
	}

}
