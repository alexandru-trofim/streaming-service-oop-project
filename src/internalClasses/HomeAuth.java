package internalClasses;

import com.fasterxml.jackson.databind.node.ArrayNode;
import output.Output;
import runProgram.ProgramInfo;

public class HomeAuth implements Page{

    public HomeAuth() {
    }

    @Override
    public void executeAction(Action currentAction, ProgramInfo programInfo, ArrayNode output) {
        System.out.println("ERROR!! YOU CAN'T DO ACTIONS ON HOME_AUTH PAGE\n");
        programInfo.getCurrentUser().setUserNullOutput(true);
        Output.display(programInfo.getCurrentUser(), Output.TYPE.ERRROR, output);
        programInfo.getCurrentUser().setUserNullOutput(false);

    }
}
