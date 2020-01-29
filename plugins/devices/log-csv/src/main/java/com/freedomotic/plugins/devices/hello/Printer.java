package com.freedomotic.plugins.devices.hello;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Printer {
	
	String eventName;
	String time;
	String eventType;
	//object.name, event.name
	String subjectName;
	String objectAction;
	String objectBehavior;
	String objectLocationX;
	String objectLocationY; 
	String triggerName;
	String triggerAction;
	String zoneName;
	String temperature;
	String luminosity;
	String zoneDescription;
	String zoneType;
	String personId;
	String powered;
	String openness;
	
	
	
	public Printer() {
		super();
	}



	public String getPowered() {
		return powered;
	}



	public void setPowered(String powered) {
		this.powered = powered;
	}



	public String getOpenness() {
		return openness;
	}



	public void setOpenness(String openness) {
		this.openness = openness;
	}



	public String getEventName() {
		return eventName;
	}



	public void setEventName(String eventName) {
		this.eventName = eventName;
	}



	public String getTime() {
		return time;
	}



	public void setTime(String string) {
		this.time = string;
	}



	public String getEventType() {
		return eventType;
	}



	public void setEventType(String eventType) {
		this.eventType = eventType;
	}



	public String getSubjectName() {
		return subjectName;
	}



	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}



	public String getObjectAction() {
		return objectAction;
	}



	public void setObjectAction(String objectAction) {
		this.objectAction = objectAction;
	}



	public String getObjectBehavior() {
		return objectBehavior;
	}



	public void setObjectBehavior(String objectBehavior) {
		this.objectBehavior = objectBehavior;
	}



	public String getObjectLocationX() {
		return objectLocationX;
	}



	public void setObjectLocationX(String objectLocationX) {
		this.objectLocationX = objectLocationX;
	}



	public String getObjectLocationY() {
		return objectLocationY;
	}



	public void setObjectLocationY(String objectLocationY) {
		this.objectLocationY = objectLocationY;
	}



	public String getTriggerName() {
		return triggerName;
	}



	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}



	public String getTriggerAction() {
		return triggerAction;
	}



	public void setTriggerAction(String triggerAction) {
		this.triggerAction = triggerAction;
	}



	public String getZoneName() {
		return zoneName;
	}



	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}



	public String getTemperature() {
		return temperature;
	}



	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}



	public String getLuminosity() {
		return luminosity;
	}



	public void setLuminosity(String luminosity) {
		this.luminosity = luminosity;
	}



	public String getZoneDescription() {
		return zoneDescription;
	}



	public void setZoneDescription(String zoneDescription) {
		this.zoneDescription = zoneDescription;
	}



	public String getZoneType() {
		return zoneType;
	}



	public void setZoneType(String zoneType) {
		this.zoneType = zoneType;
	}



	public String getPersonId() {
		return personId;
	}



	public void setPersonId(String personId) {
		this.personId = personId;
	}



	@Override
	public String toString() {
		return  eventName + "," + time + "," + eventType + ","
				+ subjectName + "," + objectAction + "," + objectBehavior
				+ "," + objectLocationX + "," + objectLocationY + ","
				+ triggerName + "," + triggerAction + "," + zoneName + ","
				+ temperature + "," + luminosity + "," + powered + ","
				+ openness + "," + zoneDescription + ","
				+ zoneType + "," + personId;
	}



	public Printer(String eventName, String time, String eventType, String subjectName, String objectAction,
			String objectBehavior, String objectLocationX, String objectLocationY, String triggerName,
			String triggerAction, String zoneName, String temperature, String luminosity, String powered, String openness, 
			String zoneDescription, String zoneType, String personId) {
		super();
		this.eventName = eventName;
		this.time = time;
		this.eventType = eventType;
		this.subjectName = subjectName;
		this.objectAction = objectAction;
		this.objectBehavior = objectBehavior;
		this.objectLocationX = objectLocationX;
		this.objectLocationY = objectLocationY;
		this.triggerName = triggerName;
		this.triggerAction = triggerAction;
		this.zoneName = zoneName;
		this.temperature = temperature;
		this.luminosity = luminosity;
		this.powered = powered;
		this.openness = openness;
		this.zoneDescription = zoneDescription;
		this.zoneType = zoneType;
		this.personId = personId;
	}
}