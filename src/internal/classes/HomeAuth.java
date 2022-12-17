package internal.classes;

import com.fasterxml.jackson.databind.node.ArrayNode;
import output.Output;
import run.program.ProgramInfo;

public class HomeAuth implements Page {

    public HomeAuth() {
    }

    /**
     * There are no possible actions on HOME_AUTH page. This function throws an error
     * when the user tries to execute an action on this page.
     * @param currentAction The current action.
     * @param programInfo The ProgramInfo object that stores the current user,
     *                    the current page and the movie on seeDetails page.
     * @param output The output ArrayNode where the output is written.
     */
    @Override
    public void executeAction(final Action currentAction, final ProgramInfo programInfo,
                                                                    final ArrayNode output) {
        System.out.println("ERROR!! YOU CAN'T DO ACTIONS ON HOME_AUTH PAGE\n");
        Output.outputError(programInfo, output, programInfo.getCurrentUser());
    }
}
