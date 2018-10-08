package br.com.professordanilo.quizapp.util;

import br.com.professordanilo.quizapp.dao.EventDAO;
import br.com.professordanilo.quizapp.dao.PlayerDAO;
import br.com.professordanilo.quizapp.dao.QuestionDAO;
import br.com.professordanilo.quizapp.logic.EventLogic;
import br.com.professordanilo.quizapp.logic.PlayerLogic;

public class ContextLogic {

    private static EventLogic eventLogic;
    private static PlayerLogic playerLogic;
    private static QuestionDAO questionDAO;

    public static EventLogic getEventLogic() {
        if(eventLogic == null){
            eventLogic = new EventLogic();
        }
        return eventLogic;
    }
    
    public static PlayerLogic getPlayerLogic() {
        if(playerLogic == null){
            playerLogic = new PlayerLogic();
        }
        return playerLogic;
    }
    
}
