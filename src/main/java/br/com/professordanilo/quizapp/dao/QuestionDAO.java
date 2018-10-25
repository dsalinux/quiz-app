package br.com.professordanilo.quizapp.dao;

import br.com.professordanilo.quizapp.entity.Question;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Query;

public class QuestionDAO extends GenericDAO<Question, Integer> implements Serializable{

    private static final long serialVersionUID = -4896248144617745372L;
    
    public Question selectRandomQuestion(List<String> subjects, Integer questionLevel){
        Query query = getSession().createQuery("select q from Question as q where q.subject in :subjects and q.questionLevel = :questionLevel order by q.selected, RANDOM()");
        query.setParameter("subjects", subjects);
        query.setParameter("questionLevel", questionLevel);
        query.setMaxResults(1);
        Question q = (Question) query.getSingleResult();
        q.setSelected(q.getSelected()+1);
        q = save(q);
        return q;
    }
    
    public List<String> findSubjects(){
        List<String> subjects;
        Query query = getSession().createQuery("select distinct(q.subject) from Question as q ");
        subjects = query.getResultList();
        return subjects;
    }
    
    public List<Integer> findQuestionLevels(){
        List<Integer> questionLevels;
        Query query = getSession().createQuery("select distinct(q.questionLevel) from Question as q ");
        questionLevels = query.getResultList();
        return questionLevels;
    }
    
}
