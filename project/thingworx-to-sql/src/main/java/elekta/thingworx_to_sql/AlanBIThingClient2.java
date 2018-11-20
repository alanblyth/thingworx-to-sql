package elekta.thingworx_to_sql;

import com.thingworx.communications.client.ClientConfigurator;
import com.thingworx.communications.client.ConnectedThingClient;
import com.thingworx.communications.client.things.VirtualThing;
import com.thingworx.communications.client.things.VirtualThingPropertyChangeEvent;
import com.thingworx.communications.client.things.VirtualThingPropertyChangeListener;
import com.thingworx.communications.common.SecurityClaims;

// Refer to the "Steam Sensor Example" section of the documentation
// for a detailed explanation of this example's operation
public class AlanBIThingClient2 extends ConnectedThingClient {
    public AlanBIThingClient2(ClientConfigurator config) throws Exception {
        super(config);
    }

    // Test example
    public static void updateStatus(String status) throws Exception {
//        if (args.length < 3) {
//            System.out.println("Required arguments not found!");
//            System.out.println("URI AppKey ScanRate <StartSensor> <Number Of Sensors>");
//            System.out.println("Example:");
//            System.out.println("ws://localhost:80/Thingworx/WS xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx 1000 1 10");
//            return;
//        }
        
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
        config.setName("AlanBIThingGateway");
        // This client is a SDK
        config.setAsSDKType();

        // This will allow us to test against a server using a self-signed certificate.
        // This should be removed for production systems.
        config.ignoreSSLErrors(true); // All self signed certs

        // Get the scan rate (milliseconds) that is specific to this example
        // The example will execute the processScanRequest of the VirtualThing
        // based on this scan rate
        int scanRate = 15000;

        int startSensor = 0;
        int nSensors = 2;


        // Create the client passing in the configuration from above
        AlanBIThingClient2 client = new AlanBIThingClient2(config);
        AlanBIThing steamSensorThing = null;
        
        for (int sensor = 0; sensor < nSensors; sensor++) {
            int sensorID = startSensor + sensor;
            steamSensorThing = new AlanBIThing("AlanBIThing01", "Description", "AlanBIThing01", client);
            client.bindThing(steamSensorThing);

            steamSensorThing.addPropertyChangeListener(new VirtualThingPropertyChangeListener() {
                @Override
                public void propertyChangeEventReceived(VirtualThingPropertyChangeEvent evt) {
                    if ("TemperatureLimit".equals(evt.getPropertyDefinition().getName())) {
                        System.out.println(String.format("Temperature limit on %s has been changed to %s°.", "Name",
                            evt.getPrimitiveValue().getValue()));
                    }
                }
            });
        }

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
                    	steamSensorThing.updateStatus("Hello World!!!!!");
                    } catch (Exception eProcessing) {
                        System.out.println("Error Processing Scan Request for [" + thing.getName() + "] : " + eProcessing.getMessage());
                    }
                }
            }
            // Suspend processing at the scan rate interval
            Thread.sleep(scanRate);
        }
    }
}