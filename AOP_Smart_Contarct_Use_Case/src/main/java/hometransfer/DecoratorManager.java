package hometransfer;

import org.hyperledger.fabric.contract.Context;


import java.util.ArrayList;
import java.util.List;

public class DecoratorManager {

    private HomeTransferInterface chaincode;
    private  List<AbstractDecorator> decorators;


    public DecoratorManager(HomeTransferInterface chaincode) {
        this.chaincode = chaincode;
        this.decorators = new ArrayList<>();
    }

    public void addDecorator(AbstractDecorator decorator) {
        decorators.add(decorator);
    }

    public HomeTransferInterface composeChaincodeBasedOnMetadata(Context ctx) {

        System.out.println("i entered composeChaincodeBasedOnMetadata ");
        HomeTransferInterface decoratedChaincode = chaincode;
        for (AbstractDecorator decorator : decorators) {
            if (decorator.shouldApplyForTransaction(ctx)) {
                decoratedChaincode = decorator.decorate(decoratedChaincode);
            }
        }
        System.out.println("i left composeChaincodeBasedOnMetadata");
        System.out.println(decoratedChaincode);
        return decoratedChaincode;
    }



}
