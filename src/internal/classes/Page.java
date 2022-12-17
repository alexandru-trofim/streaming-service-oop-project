package internal.classes;

import com.fasterxml.jackson.databind.node.ArrayNode;
import run.program.ProgramInfo;

public interface Page {
    /**
     * Executes an action on a page. The parameters of the action are given in the
     * currentAction object. There are multiple possible actions on the page and
     * this functions executes the needed action depending on the currentAction.
     * @param currentAction The current action.
     * @param programInfo The ProgramInfo object that stores the current user,
     *                    the current page and the movie on seeDetails page.
     * @param output The output ArrayNode where the output is written.
     */
    void executeAction(Action currentAction, ProgramInfo programInfo, ArrayNode output);

}
