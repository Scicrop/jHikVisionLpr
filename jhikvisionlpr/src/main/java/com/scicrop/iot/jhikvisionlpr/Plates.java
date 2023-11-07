package com.scicrop.iot.jhikvisionlpr;


import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;



public class Plates {

	String version="";
	String xmlns="";
	String textContent="";
	List< Plate> platePlatesEntityList ;
	public void setVersion(String version) { 
		this.version=version;
	} 
	@XmlAttribute(name = "version")
	public String getVersion() { 
		return version;
	} 
	public void setXmlns(String xmlns) { 
		this.xmlns=xmlns;
	} 
	@XmlAttribute(name = "xmlns")
	public String getXmlns() { 
		return xmlns;
	} 
	public void setTextContent(String textContent) { 
		this.textContent=textContent;
	} 
	@XmlAttribute(name = "TextContent")
	public String getTextContent() { 
		return textContent;
	} 
	public void setPlates(List<Plate> platePlatesEntityList) { 
		this.platePlatesEntityList=platePlatesEntityList;
	} 
	@XmlElement(name = "Plate")
	public List<Plate> getPlates() { 
		if(platePlatesEntityList == null)
			platePlatesEntityList=new ArrayList<Plate>(); 
		return platePlatesEntityList;
	} 

}