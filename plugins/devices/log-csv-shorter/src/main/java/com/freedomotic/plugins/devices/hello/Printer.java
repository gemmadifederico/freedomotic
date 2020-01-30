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
	//object.name, event.name
	String subjectName;
	String objectBehavior;
	
	public Printer() {
		super();
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


	public String getSubjectName() {
		return subjectName;
	}



	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	
	public String getObjectBehavior() {
		return objectBehavior;
	}



	public void setObjectBehavior(String objectBehavior) {
		this.objectBehavior = objectBehavior;
	}

	@Override
	public String toString() {
		return  eventName + "," + time + "," + subjectName + "," + objectBehavior;
	}


	public Printer(String eventName, String time, String subjectName, String objectBehavior) {
		super();
		this.eventName = eventName;
		this.time = time;
		this.subjectName = subjectName;
		this.objectBehavior = objectBehavior;
	}
}