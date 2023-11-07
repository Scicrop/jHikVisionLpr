package com.scicrop.iot.jhikvisionlpr;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Plate")

public class Plate {

   private String isMulti;
   private  String captureTime;
   private String country;
   private String direction;
   private String laneNo;
   private String matchingResult;
   private String picName;
   private String plateNumber;
   private  String textContent;
  public void setIsMulti(String isMulti) { 
		this.isMulti=isMulti;
	} 
    @XmlElement(name = "IsMulti")
    public String getIsMulti() { 
		return isMulti;
	} 
  public void setCaptureTime(String captureTime) { 
		this.captureTime=captureTime;
	} 
    @XmlElement(name = "captureTime")
    public String getCaptureTime() { 
		return captureTime;
	} 
  public void setCountry(String country) { 
		this.country=country;
	} 
    @XmlElement(name = "country")
    public String getCountry() { 
		return country;
	} 
  public void setDirection(String direction) { 
		this.direction=direction;
	} 
    @XmlElement(name = "direction")
    public String getDirection() { 
		return direction;
	} 
  public void setLaneNo(String laneNo) { 
		this.laneNo=laneNo;
	} 
    @XmlElement(name = "laneNo")
    public String getLaneNo() { 
		return laneNo;
	} 
  public void setMatchingResult(String matchingResult) { 
		this.matchingResult=matchingResult;
	} 
    @XmlElement(name = "matchingResult")
    public String getMatchingResult() { 
		return matchingResult;
	} 
  public void setPicName(String picName) { 
		this.picName=picName;
	} 
    @XmlElement(name = "picName")
    public String getPicName() { 
		return picName;
	} 
  public void setPlateNumber(String plateNumber) { 
		this.plateNumber=plateNumber;
	} 
    @XmlElement(name = "plateNumber")
    public String getPlateNumber() { 
		return plateNumber;
	} 
  public void setTextContent(String textContent) { 
		this.textContent=textContent;
	} 
    @XmlElement(name = "TextContent")
    public String getTextContent() { 
		return textContent;
	} 

}