package com.freedomotic.plugins.devices.hello;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.freedomotic.api.EventTemplate;
import com.freedomotic.api.Protocol;
import com.freedomotic.app.Freedomotic;
import com.freedomotic.events.LuminosityEvent;
import com.freedomotic.events.MessageEvent;
import com.freedomotic.events.ObjectHasChangedBehavior;
import com.freedomotic.events.PersonEntersZone;
import com.freedomotic.events.PersonExitsZone;
import com.freedomotic.events.TemperatureEvent;
import com.freedomotic.events.ZoneHasChanged;
import com.freedomotic.exceptions.UnableToExecuteException;
import com.freedomotic.reactions.Command;
import com.freedomotic.rules.Statement;
import com.freedomotic.things.ThingRepository;
import com.google.inject.Inject;

/**
 *
 * @author Gemma Di Federico
 */
public class LogCsvS
extends Protocol {

    public static FileWriter filewriter = null;
    public static BufferedWriter bw = null;

    private static final Logger LOG = LoggerFactory.getLogger(LogCsvS.class.getName());
    final int POLLING_WAIT;
    private final int POLLING_TIME = configuration.getIntProperty("polling-time", 2000);


    @Inject
    private ThingRepository thingsRepository;
    private Logger logger;

    /**
     *
     */
    public LogCsvS() {
        //every plugin needs a name and a manifest XML file
        super("LogCsvS", "/log-csv-shorter/log-csv-shorter-manifest.xml");
        //read a property from the manifest file below which is in
        //FREEDOMOTIC_FOLDER/plugins/devices/com.freedomotic.hello/hello-world.xml
        POLLING_WAIT = configuration.getIntProperty("time-between-reads", 1000);
        //POLLING_WAIT is the value of the property "time-between-reads" or 2000 millisecs,
        //default value if the property does not exist in the manifest
        setPollingWait(POLLING_WAIT); //millisecs interval between hardware device status reads
    }

    @Override
    protected void onShowGui() {
        /**
         * uncomment the line below to add a GUI to this plugin the GUI can be
         * started with a right-click on plugin list on the desktop frontend
         * (com.freedomotic.jfrontend plugin)
         */
        // bindGuiToPlugin(new HelloWorldGui(this));
    }

    @Override
    protected void onHideGui() {
        //implement here what to do when the this plugin GUI is closed
        //for example you can change the plugin description
        setDescription("My GUI is now hidden");
    }

    @Override
    protected void onRun() {
        //get and format the current date and time
        //    	  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        //    	  Date date = new Date();
        //create a freedomotic message event
        //    	  MessageEvent message = new MessageEvent(null, "Hello world plugins says current time is " + dateFormat.format(date) + "" + thingsRepository);
        //    	  notifyEvent(message);
        //    	  
        //    	  StringBuilder buffer = new StringBuilder();
        //          for (EnvObjectLogic object : EnvObjectPersistence.getObjectList()) {
        //              buffer.append(object.getPojo().getName() + "\n");
        //              for (BehaviorLogic behavior : object.getBehaviors()) {
        //                  buffer.append("  " + behavior.getName() + ": " + behavior.getValueAsString()  + "\n");
        //              }
        //          }

        //print the string in the Freedomotic log using INFO level
        //LOG.info(buffer.toString());


    }
    public void askSomething() {
        final Command c = new Command();
        c.setName("Ask something silly to user");
        c.setReceiver("app.actuators.frontend.javadesktop.in");
        c.setProperty("question", "<html><h1>Do you like Freedomotic?</h1></html>");
        c.setProperty("options", "Yes, it's good; No, it sucks; I don't know");
        c.setReplyTimeout(10000); //10 seconds

        new Thread(new Runnable() {
            public void run() {
                Command reply = Freedomotic.sendCommand(c);
                if (reply != null) {
                    String userInput = reply.getProperty("result");
                    if (userInput != null) {
                        System.out.println("The reply to the test question is " + userInput);
                    } else {
                        System.out.println("The user has not responded to the question within the given time");
                    }
                } else {
                    System.out.println("Unreceived reply within given time (10 seconds)");
                }
            }
        }).start();
    }

    @Override
    protected void onStart() {
        LOG.info("HelloWorld plugin started");
        try {
            File f = new File(System.getProperty("user.dir").trim(), "myfile.csv");
            String header = null;
//            f.createNewFile();
            // if file doesnt exists, then create it
            if (!f.isFile()) {
                f.createNewFile();
            	header = ("eventName,time,subjectName,objectBehavior");            
            }
            filewriter = new FileWriter(f, true);
            bw = new BufferedWriter(filewriter);
            if(header != null) {
            	bw.write(header);
            	bw.newLine();
            	bw.flush();
            }
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        addEventListener("app.event.sensor.object.behavior.change");
        addEventListener("app.event.sensor.environment.zone.change");
        addEventListener("app.event.sensor.command.change");
        addEventListener("app.event.sensor.reaction.change");
        addEventListener("app.event.sensor.object.change");
        addEventListener("app.event.sensor.temperature");
        addEventListener("app.event.sensor.person.zone.enter");
        addEventListener("app.event.sensor.person.zone.exit");
        addEventListener("app.event.sensor.luminosity");
        addEventListener("app.events.sensors.behavior.request.objects");

        setPollingWait(POLLING_TIME);
        setDescription("HW started");
    }

    @Override
    protected void onStop() {
        try {
            bw.close();
            filewriter.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        setPollingWait(-1); //disable polling        
        //display the default description
        setDescription("HW stopped");

        LOG.info("HelloWorld plugin stopped");
    }

    @Override
    protected void onCommand(Command c)
    throws IOException, UnableToExecuteException {
        LOG.info("HelloWorld plugin receives a command called {} with parameters {}", c.getName(),
            c.getProperties().toString());
    }

    @Override
    protected boolean canExecute(Command c) {
        //don't mind this method for now
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void onEvent(EventTemplate event) {
        MessageEvent message = null;
        List < Statement > payloadLst = (event.getPayload().getStatements());
        Iterator < Statement > it = payloadLst.iterator();
        Printer p = new Printer();
        p.setEventName(event.getEventName());
        // Creating date format 
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        // Creating date from milliseconds 
        Date dateparsed = new Date(event.getCreation());
        // Formatting Date according to the given format 
        p.setTime(format.format(dateparsed));
        if (event instanceof ObjectHasChangedBehavior) {
            //object.currentRepresentation, object.behavior, object.location.x, object.location.y 
            while (it.hasNext()) {
                Statement triggerStatement = it.next();
                if (triggerStatement.getAttribute().equals("object.name")) {
                    p.setSubjectName(triggerStatement.getValue());
                }
                if (triggerStatement.getAttribute().contains("object.behavior")) {
                    p.setObjectBehavior(triggerStatement.getValue());
                } else if (triggerStatement.getAttribute().equals("object.behavior.temperature")) {
                    p.setObjectBehavior(triggerStatement.getValue());
                } else if (triggerStatement.getAttribute().equals("object.behavior.powered")) {
                    p.setObjectBehavior(triggerStatement.getValue());
                }
//              if (triggerStatement.getAttribute().equals("object.behavior.brightness")) {
//              p.setLuminosity(triggerStatement.getValue());
//          }
//                if (triggerStatement.getAttribute().equals("object.behavior.powered")) {
//                    p.setPowered(triggerStatement.getValue());
//                }
//                if (triggerStatement.getAttribute().equals("object.behavior.openness")) {
//                    p.setOpenness(triggerStatement.getValue());
//                }
            }
        } else if (event instanceof TemperatureEvent) {
            //zone, temperature
            while (it.hasNext()) {
                Statement triggerStatement = it.next();
                if (triggerStatement.getAttribute().equals("zone")) {
                    p.setSubjectName(triggerStatement.getValue());
                }
                if (triggerStatement.getAttribute().equals("temperature")) {
                    p.setObjectBehavior(triggerStatement.getValue());
                }
            }
        } else if (event instanceof LuminosityEvent) {
            //zone, luminosity
            while (it.hasNext()) {
                Statement triggerStatement = it.next();
                if (triggerStatement.getAttribute().equals("zone")) {
                    p.setSubjectName(triggerStatement.getValue());
                }
                if (triggerStatement.getAttribute().equals("luminosity")) {
                    p.setObjectBehavior(triggerStatement.getValue());
                }
            }
        } else if (event instanceof PersonEntersZone) {
            //person.id, zone.name
            while (it.hasNext()) {
                Statement triggerStatement = it.next();
                if (triggerStatement.getAttribute().equals("person.id")) {
                    p.setObjectBehavior(triggerStatement.getValue());
                }
                if (triggerStatement.getAttribute().equals("zone.name")) {
                    p.setSubjectName(triggerStatement.getValue());
                }
            }
        } else if (event instanceof PersonExitsZone) {
            //person.id, zone.name
            while (it.hasNext()) {
                Statement triggerStatement = it.next();
                if (triggerStatement.getAttribute().equals("person.id")) {
                    p.setObjectBehavior(triggerStatement.getValue());
                }
                if (triggerStatement.getAttribute().equals("zone.name")) {
                    p.setSubjectName(triggerStatement.getValue());
                }
            }
        } else {
            return;
        }

        try {
            bw.write(p.toString());
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
