package br.com.professordanilo.quizapp.util;

import br.com.professordanilo.quizapp.logic.EventLogic;
import br.com.professordanilo.quizapp.logic.PlayerLogic;
import br.com.professordanilo.quizapp.logic.QuestionLogic;
import br.com.professordanilo.quizapp.logic.TournamentLogic;

public class ContextLogic {

    private static EventLogic eventLogic;
    private static PlayerLogic playerLogic;
    private static QuestionLogic questionLogic;
    private static TournamentLogic tournamentLogic;

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

    public static QuestionLogic getQuestionLogic() {
        if(questionLogic == null){
            questionLogic = new QuestionLogic();
        }
        return questionLogic;
    }
    
    public static TournamentLogic getTournamentLogic() {
        if(tournamentLogic == null){
            tournamentLogic = new TournamentLogic();
        }
        return tournamentLogic;
    }
    
    
}
