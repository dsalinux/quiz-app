package br.com.professordanilo.quizapp.util;

import br.com.professordanilo.quizapp.dao.EventDAO;
import br.com.professordanilo.quizapp.dao.PlayerDAO;
import br.com.professordanilo.quizapp.dao.QuestionDAO;
import br.com.professordanilo.quizapp.dao.TournamentDAO;

public class ContextDAO {

    private static EventDAO eventDAO;
    private static PlayerDAO playerDAO;
    private static QuestionDAO questionDAO;
    private static TournamentDAO tournamentDAO;

    public static EventDAO getEventDAO() {
        if(eventDAO == null){
            eventDAO = new EventDAO();
        }
        return eventDAO;
    }
    
    public static PlayerDAO getPlayerDAO() {
        if(playerDAO == null){
            playerDAO = new PlayerDAO();
        }
        return playerDAO;
    }

    public static QuestionDAO getQuestionDAO() {
        if(questionDAO == null){
            questionDAO = new QuestionDAO();
        }
        return questionDAO;
    }

    public static TournamentDAO getTournamentDAO() {
        if(tournamentDAO == null){
            tournamentDAO = new TournamentDAO();
        }
        return tournamentDAO;
    }
    
}
