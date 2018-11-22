package elekta.thingworx_to_sql.bithing;

import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;
import com.thingworx.communications.client.ConnectedThingClient;
import com.thingworx.communications.client.ConnectionException;
import com.thingworx.communications.client.things.VirtualThing;
import com.thingworx.metadata.annotations.ThingworxEventDefinition;
import com.thingworx.metadata.annotations.ThingworxEventDefinitions;
import com.thingworx.metadata.annotations.ThingworxPropertyDefinition;
import com.thingworx.metadata.annotations.ThingworxPropertyDefinitions;

// Refer to the "Steam Sensor Example" section of the documentation
// for a detailed explanation of this example's operation

// Property Definitions
@SuppressWarnings("serial")
@ThingworxPropertyDefinitions(properties = {
        @ThingworxPropertyDefinition(name = "Status", description = "Current Status",
                baseType = "STRING", category = "Status", aspects = { "isReadOnly:true" }), 
        })

// Event Definitions
@ThingworxEventDefinitions(events = { @ThingworxEventDefinition(name = "SteamSensorFault",
        description = "Steam sensor fault", dataShape = "SteamSensor.Fault", category = "Faults",
        isInvocable = true, isPropertyEvent = false) })

// Steam Thing virtual thing class that simulates a Steam Sensor
public class BIThing extends VirtualThing {

	public static final String STATUS_PROPERTY = "Status";

    static Logger log = Logger.getLogger(BIThing.class);
    
    public BIThing(String name, String description, String identifier,
            ConnectedThingClient client) throws Exception {
        super(name, description, identifier, client);

        // Populate the thing shape with the properties, services, and events that are annotated in
        // this code
        super.initializeFromAnnotations();
    }

    // From the VirtualThing class
    // This method will get called when a connect or reconnect happens
    // Need to send the values when this happens
    // This is more important for a solution that does not send its properties on a regular basis
    public void synchronizeState() {
        // Be sure to call the base class
        super.synchronizeState();
        // Send the property values to Thingworx when a synchronization is required
        super.syncProperties();
    }
    
    public void updateStatus(String status) throws Exception {
    	
    	super.processScanRequest();
    	this.updateStatusProperty(status);
    }
    
  public void updateStatusProperty(String status) throws Exception {
    	
	  log.info("Setting Property " + STATUS_PROPERTY + " to " + status);
    	super.setProperty(STATUS_PROPERTY, status);

        updateSubscriptions();
    }

private void updateSubscriptions() throws TimeoutException, ConnectionException, Exception {
	
	log.info("Updating Subscribed Properties and events");
	super.updateSubscribedProperties(15000);
	super.updateSubscribedEvents(60000);
	}
}
