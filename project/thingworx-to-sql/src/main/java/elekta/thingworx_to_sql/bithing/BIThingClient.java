package elekta.thingworx_to_sql.bithing;

import org.apache.log4j.Logger;

import com.thingworx.communications.client.ClientConfigurator;
import com.thingworx.communications.client.ConnectedThingClient;
import com.thingworx.communications.common.SecurityClaims;

// Refer to the "Steam Sensor Example" section of the documentation
// for a detailed explanation of this example's operation
public class BIThingClient extends ConnectedThingClient {
	
    static Logger log = Logger.getLogger(BIThingClient.class);

    public BIThingClient(ClientConfigurator config) throws Exception {
    	
        super(config);
        // Set the required configuration information
       // ClientConfigurator config = new ClientConfigurator();
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
        
        try {
            // Start the client
            start();
        } catch (Exception eStart) {
            System.out.println("Initial Start Failed : " + eStart.getMessage());
        }
        
        log.info("Connected: " + isConnected());
    }

    public void updateStatus(String status) throws Exception {

        // Create the client passing in the configuration from above
        BIThing steamSensorThing = null;
        
        steamSensorThing = new BIThing("AlanBIThing01", "Description", "AlanBIThing01", this);
        bindThing(steamSensorThing);

        // As long as the client has not been shutdown, continue
        while (!isShutdown()) {
            // Only process the Virtual Things if the client is connected
            if (isConnected()) {
            
                try {
                	steamSensorThing.updateStatus(status);
                	break;
                } catch (Exception eProcessing) {
                    System.out.println("Error Processing Scan Request for [" + steamSensorThing.getName() + "] : " + eProcessing.getMessage());
                }
            }
            // Suspend processing at the scan rate interval
            else {
            	Thread.sleep(2000);
            }
        }
        log.info("Work Done!");
    }
}