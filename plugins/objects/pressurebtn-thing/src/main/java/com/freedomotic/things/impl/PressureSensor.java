package com.freedomotic.things.impl;

import java.util.logging.Logger;

import com.freedomotic.behaviors.BooleanBehaviorLogic;
import com.freedomotic.behaviors.RangedIntBehaviorLogic;
import com.freedomotic.events.ObjectReceiveClick;
import com.freedomotic.model.ds.Config;
import com.freedomotic.model.object.BooleanBehavior;
import com.freedomotic.model.object.RangedIntBehavior;
import com.freedomotic.reactions.Command;
import com.freedomotic.reactions.Trigger;
import com.freedomotic.things.EnvObjectLogic;

public class PressureSensor extends EnvObjectLogic {

    private static final Logger LOG = Logger.getLogger(PressureSensor.class.getName()); 
//    private RangedIntBehaviorLogic pressure;
    protected BooleanBehaviorLogic pressure;
    private static final String BEHAVIOR_PRESSURE = "pressure";

    @Override
    public void init() {
    	pressure = new BooleanBehaviorLogic((BooleanBehavior) getPojo().getBehavior(BEHAVIOR_PRESSURE));
    	//add a listener to values changes
    	pressure.addListener(new BooleanBehaviorLogic.Listener() {
            @Override
            public void onTrue(Config params, boolean fireCommand) {
                if (fireCommand) {
                    executeSetPressedTrue(params);
                } else {
                    setPressed();
                }            
            }

            @Override
            public void onFalse(Config params, boolean fireCommand) {
                if (fireCommand) {
                    executeSetPressedFalse(params);
                } else {
                    setNotPressed();
                }            
            }
        });
        //register this behavior to the superclass to make it visible to it
        registerBehavior(pressure);
        super.init();

    }
   public void executeSetPressedTrue(Config params) {
        boolean executed = executeCommand("pressure true", params);
        if (executed) {
            setPressed();
//            setChanged(true);
        }

    }

    public void setPressed() {
        if (!pressure.getValue()) {
            pressure.setValue(true);
            getPojo().setCurrentRepresentation(1);
            setChanged(true);
        }
    }

    public void executeSetPressedFalse(Config params) {
        boolean executed = executeCommand("pressure false", params);
        if (executed) {
            setNotPressed();
//            setChanged(true);
        }

    }

    public void setNotPressed() {
        if (pressure.getValue()) {
            pressure.setValue(false);
            getPojo().setCurrentRepresentation(0);
            setChanged(true);
        }
    }


    /**
     * Creates user level commands for this class of freedomotic objects
     */
    @Override
    protected void createCommands() {
        super.createCommands();
        
        Command i = new Command();
        i.setName(getPojo().getName() + " set pressed");
        i.setDescription("set the " + getPojo().getName() + " pressed");
        i.setReceiver("app.events.sensors.behavior.request.objects");
        i.setProperty("object", getPojo().getName());
        i.setProperty("behavior", BEHAVIOR_PRESSURE);
        i.setProperty("value", "true");

        Command l = new Command();
        l.setName(getPojo().getName() + " set not pressed");
        l.setDescription("set the " + getPojo().getName() + " not pressed");
        l.setReceiver("app.events.sensors.behavior.request.objects");
        l.setProperty("object", getPojo().getName());
        l.setProperty("behavior", BEHAVIOR_PRESSURE);
        l.setProperty("value", "false");

        commandRepository.create(l);
        commandRepository.create(i);
    }

    @Override
    protected void createTriggers() {
//        Trigger isPressed = new Trigger();
//        isPressed.setName(this.getPojo().getName() + " set pressed");
//        isPressed.setChannel("app.event.sensor.object.behavior.change");
//        isPressed.getPayload().addStatement("object.name", this.getPojo().getName());
//        isPressed.getPayload().addStatement("object.behavior", "true");
//
//        Trigger notPressed = new Trigger();
//        notPressed.setName(this.getPojo().getName() + " set not pressed anymore");
//        notPressed.setChannel("app.event.sensor.object.behavior.change");
//        notPressed.getPayload().addStatement("object.name", this.getPojo().getName());
//        notPressed.getPayload().addStatement("object.behavior", "false");
//        
//        triggerRepository.create(isPressed);
//        triggerRepository.create(notPressed);
//
//        super.createTriggers();

    }
}