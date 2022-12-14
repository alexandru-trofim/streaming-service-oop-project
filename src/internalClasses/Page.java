package internalClasses;

import com.fasterxml.jackson.databind.node.ArrayNode;
import databaseElements.User;
import runProgram.ProgramInfo;

public interface Page {
    void executeAction(Action currentAction, ProgramInfo programInfo, ArrayNode output);

}
