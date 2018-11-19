package elekta.thingworx_to_sql;

import com.thingworx.communications.client.ClientConfigurator;
import com.thingworx.communications.client.ConnectedThingClient;
import com.thingworx.communications.client.things.VirtualThing;
import com.thingworx.communications.client.things.VirtualThingPropertyChangeEvent;
import com.thingworx.communications.client.things.VirtualThingPropertyChangeListener;
import com.thingworx.communications.common.SecurityClaims;

// Refer to the "Steam Sensor Example" section of the documentation
// for a detailed explanation of this example's operation
public class BIThingClient extends ConnectedThingClient {
    private static final String THING_NAME = "BIThing";

	public BIThingClient(ClientConfigurator config) throws Exception {
        super(config);
    }

    // Test example
    public static void main(String[] args) throws Exception {

        // Set the required configuration information
        ClientConfigurator config = new ClientConfigurator();
        // The uri for connecting to Thingworx
        config.setUri("ws://localhost:80/Thingworx/WS");
        // Reconnect every 15 seconds if a disconnect occurs or if initial connection cannot be made
        config.setReconnectInterval(15);

        // Set the security using an Application Key
        String appKey = "69691649-caa5-41dd-a9ca-e1cdd565c1a0";
        SecurityClaims claims = SecurityClaims.fromAppKey(appKey);
        config.setSecurityClaims(claims);

        // Set the name of the client
        config.setName("BIThingClient");
        // This client is a SDK
        config.setAsSDKType();

        // This will allow us to test against a server using a self-signed certificate.
        // This should be removed for production systems.
        config.ignoreSSLErrors(true); // All self signed certs

        // Get the scan rate (milliseconds) that is specific to this example
        // The example will execute the processScanRequest of the VirtualThing
        // based on this scan rate

        // Create the client passing in the configuration from above
        BIThingClient client = new BIThingClient(config);

            int sensorID = 1;
            final BIThing steamSensorThing = new BIThing(THING_NAME, "Steam Sensor #" + sensorID, "SN000" + sensorID, client);
            client.bindThing(steamSensorThing);

            steamSensorThing.addPropertyChangeListener(new VirtualThingPropertyChangeListener() {
                @Override
                public void propertyChangeEventReceived(VirtualThingPropertyChangeEvent evt) {
                    if ("TemperatureLimit".equals(evt.getPropertyDefinition().getName())) {
                        System.out.println(String.format("Temperature limit on %s has been changed to %s°.", steamSensorThing.getName(),
                            evt.getPrimitiveValue().getValue()));
                    }
                }
            });


        try {
            // Start the client
            client.start();
        } catch (Exception eStart) {
            System.out.println("Initial Start Failed : " + eStart.getMessage());
        }

        // As long as the client has not been shutdown, continue
        while (!client.isShutdown()) {
            // Only process the Virtual Things if the client is connected
            if (client.isConnected()) {
                // Loop over all the Virtual Things and process them
                for (VirtualThing thing : client.getThings().values()) {
                    try {
                        thing.processScanRequest();
                    } catch (Exception eProcessing) {
                        System.out.println("Error Processing Scan Request for [" + thing.getName() + "] : " + eProcessing.getMessage());
                    }
                }
            }
            // Suspend processing at the scan rate interval
            Thread.sleep(1000);
        }
    }
}
