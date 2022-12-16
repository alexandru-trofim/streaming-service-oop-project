package internalClasses;

import com.fasterxml.jackson.databind.node.ArrayNode;
import databaseElements.User;
import output.Output;
import runProgram.ProgramInfo;

public class UpgradesPage implements Page{


    public void buyTokens(Action currentAction, ProgramInfo programInfo, ArrayNode output) {
        System.out.println("ENTERED BUY TOKENS\n");
        int countToAdd = currentAction.getCount();
        int currentCount = programInfo.getCurrentUser().getTokensCount();
        int balance = Integer.parseInt(programInfo.getCurrentUser().getCredentials().getBalance());

        if (balance - countToAdd < 0) {
            System.out.println("ERROR!!! NOT ENOUGH MONEY ON BALANCE\n");

            //sets output for user null (we need this kind of output in some cases)
            programInfo.getCurrentUser().setUserNullOutput(true);
            Output.display(programInfo.getCurrentUser(), Output.TYPE.ERRROR, output);
            programInfo.getCurrentUser().setUserNullOutput(false);
            return;
        }
        //change the count and balance
        programInfo.getCurrentUser().setTokensCount(currentCount + countToAdd);
        programInfo.getCurrentUser().getCredentials()
                .setBalance(String.valueOf(balance - countToAdd));

    }

    public void buyPremium(Action currentAction, ProgramInfo programInfo, ArrayNode output) {
        int currentCount = programInfo.getCurrentUser().getTokensCount();
        System.out.println("ENTERED BUY BUY PREMIUM\n");

        if (currentCount - 10 < 0) {
            System.out.println("ERROR!!! NOT ENOUGH TOKENS TO BUY PREMIUM\n");

            //sets output for user null (we need this kind of output in some cases)
            programInfo.getCurrentUser().setUserNullOutput(true);
            Output.display(programInfo.getCurrentUser(), Output.TYPE.ERRROR, output);
            programInfo.getCurrentUser().setUserNullOutput(false);
            return;
        }
        System.out.println("CHANGED PREMIUM TOKENS COUNT " + programInfo.getCurrentUser().getTokensCount() + "\n");
        programInfo.getCurrentUser().getCredentials().setAccountType("premium");
        programInfo.getCurrentUser().setTokensCount(currentCount - 10);


    }
    @Override
    public void executeAction(Action currentAction, ProgramInfo programInfo, ArrayNode output) {
        switch (currentAction.getFeature()) {
            case "buy tokens" -> buyTokens(currentAction, programInfo, output);
            case "buy premium account" -> buyPremium(currentAction, programInfo, output);
        }
    }
}
