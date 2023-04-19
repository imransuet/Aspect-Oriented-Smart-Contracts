package fabricjavaclient;



import org.hyperledger.fabric.gateway.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ClientApp {

    static {
        System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
    }

    public static void main(String[] args) throws Exception {
        // Load a file system based wallet for managing identities.
        Path walletPath = Paths.get("wallet");
        Wallet wallet = Wallets.newFileSystemWallet(walletPath);
        // load a Connection Profile
        Path networkConfigPath = Paths.get("/home", "saif", "fabric-samples", "test-network", "organizations", "peerOrganizations", "org1.example.com", "connection-org1.yaml");

        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, "appUser").networkConfig(networkConfigPath).discovery(true);

        // create a gateway connection
        try (Gateway gateway = builder.connect()) {

            // get the network and contract
            Network network = gateway.getNetwork("samplechannel");
            Contract contract = network.getContract("HomeTransfer");

            byte[] result;

            // Retrieve transient data
            Map<String, byte[]> transientData = getTransientData();

            // Submit the transaction using the transient data
            contract.createTransaction("addNewHome")
                    .setTransient(transientData)
                    .submit("10", "Peaced", "54678", "jack Hobs", "78909");

            result = contract.evaluateTransaction("queryHomeById", "10");
            System.out.println(new String(result));

            // Submit the transaction using the transient data
       /*     contract.createTransaction("changeHomeOwnership")
                    .setTransient(transientData)
                    .submit("4", "Joe");  */

            result = contract.evaluateTransaction("queryHomeById", "4");
            System.out.println(new String(result));
        }
    }

    private static Map<String, byte[]> getTransientData() {
        Map<String, byte[]> transientData = new HashMap<>();
        // Add transient data to control the decorators
        transientData.put("logging", "true".getBytes());
  
        return transientData;
    }
}