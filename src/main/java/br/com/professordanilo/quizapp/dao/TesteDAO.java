package br.com.professordanilo.quizapp.dao;

import br.com.professordanilo.quizapp.entity.Event;
import br.com.professordanilo.quizapp.entity.Player;
import br.com.professordanilo.quizapp.entity.Question;
import br.com.professordanilo.quizapp.util.AppIcons;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author danilo
 */
public class TesteDAO {

    public static void main(String[] args) {
        try {
            PlayerDAO dao = new PlayerDAO();
            EventDAO eventDAO = new EventDAO();
            QuestionDAO questionDAO = new QuestionDAO();
            for (int i = 0; i < 10; i++) {
                Player p = new Player();
                p.setName("Player "+i);
                dao.save(p);
            }
            
            Event event = new Event();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(AppIcons.DEFAULT_LOGO_BUFFERED, "jpg", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            event.setLogo(imageInByte);
            event.setName("Semana Nacional de Ciência e tecnologia 2018");
            event.setStopwatch(5);
            event.setTypeCompetidor(Event.TypeCompetidor.SINGLE);
            eventDAO.save(event);
            
            Question question = new Question();
            question.setFalseAnswer1("Errado");
            question.setFalseAnswer2("Errado");
            question.setFalseAnswer3("Errado");
            question.setTrueAnswer("Certo");
            question.setQuestion("Teste para ver se acerta.");
            question.setSubject("Matemática");
            questionDAO.save(question);
            System.out.println("Salvo com sucesso!\n-------------------------------\n");
            List<Player> list = dao.findAll();
            System.out.println(list);
        } catch (IOException ex) {
            Logger.getLogger(TesteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
