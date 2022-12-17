package internal.classes;

import com.fasterxml.jackson.databind.node.ArrayNode;
import output.Output;
import run.program.ProgramInfo;

public class UpgradesPage implements Page {

    private static final int PREMIUM_PRICE = 10;

    /**
     * Completes the action of buying tokens by a user. It checks if the user
     * has enough money on balance and buys the specified amount of tokens.
     * If the user hasn't enough money it throws an error.
     * @param currentAction The current action.
     * @param programInfo The ProgramInfo object that stores the current user,
     *                    the current page and the movie on seeDetails page.
     * @param output The output ArrayNode where the output is written.
     */
    public void buyTokens(final Action currentAction, final ProgramInfo programInfo,
                                                                final ArrayNode output) {
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
        programInfo.getCurrentUser()
                .setTokensCount(currentCount + countToAdd);
        programInfo.getCurrentUser().getCredentials()
                .setBalance(String.valueOf(balance - countToAdd));

    }

    /**
     * Buys the premium account for the user. First it checks if the user has
     * enough tokens for the premium account and throws an error if the condition
     * is not completed.
     * @param currentAction The current action.
     * @param programInfo The ProgramInfo object that stores the current user,
     *                    the current page and the movie on seeDetails page.
     * @param output The output ArrayNode where the output is written.
     */
    public void buyPremium(final Action currentAction, final ProgramInfo programInfo,
                                                                final ArrayNode output) {
        int currentCount = programInfo.getCurrentUser().getTokensCount();

        if (currentCount - PREMIUM_PRICE < 0) {
            System.out.println("ERROR!!! NOT ENOUGH TOKENS TO BUY PREMIUM\n");

            //sets output for user null (we need this kind of output in some cases)
            programInfo.getCurrentUser().setUserNullOutput(true);
            Output.display(programInfo.getCurrentUser(), Output.TYPE.ERRROR, output);
            programInfo.getCurrentUser().setUserNullOutput(false);
            return;
        }

        programInfo.getCurrentUser().getCredentials().setAccountType("premium");
        programInfo.getCurrentUser().setTokensCount(currentCount - PREMIUM_PRICE);
    }

    /**
     * Executes the "buy tokens" or "buy premium account" depending on the
     * currentAction. If the currentAction contains another action it throws
     * an error.
     * @param currentAction The current action.
     * @param programInfo The ProgramInfo object that stores the current user,
     *                    the current page and the movie on seeDetails page.
     * @param output The output ArrayNode where the output is written.
     */
    @Override
    public void executeAction(final Action currentAction, final ProgramInfo programInfo,
                                                                    final ArrayNode output) {
        switch (currentAction.getFeature()) {
            case "buy tokens" -> buyTokens(currentAction, programInfo, output);
            case "buy premium account" -> buyPremium(currentAction, programInfo, output);
            default -> {
                Output.outputError(programInfo, output, programInfo.getCurrentUser());
                System.out.println("ERROR WRONG COMMAND\n");
            }
        }
    }
}
