package com.scicrop.iot.jhikvisionlpr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
public class BeanToXMLUtil {
public static Document convertBeanToXML (Plates platesPlatesEntity) throws Exception {
Document inputDoc = XMLUtil.createDocument("Plates");  Element platesElm=inputDoc.getDocumentElement(); 


  for (int i=0; i<platesPlatesEntity.getPlates().size(); i++ ) { 
Plate platePlatesEntity=platesPlatesEntity.getPlates().get(i); 
  Element plateElm=XMLUtil.createChildElement(platesElm,"Plate");
plateElm.setTextContent(platePlatesEntity.getTextContent());

plateElm.setAttribute("IsMulti",platePlatesEntity.getIsMulti());
plateElm.setAttribute("captureTime",platePlatesEntity.getCaptureTime());
plateElm.setAttribute("country",platePlatesEntity.getCountry());
plateElm.setAttribute("direction",platePlatesEntity.getDirection());
plateElm.setAttribute("laneNo",platePlatesEntity.getLaneNo());
plateElm.setAttribute("matchingResult",platePlatesEntity.getMatchingResult());
plateElm.setAttribute("picName",platePlatesEntity.getPicName());
plateElm.setAttribute("plateNumber",platePlatesEntity.getPlateNumber());

}//REPEAt elm 
 
 return inputDoc; 
 } 
 }