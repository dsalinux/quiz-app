package br.com.professordanilo.quizapp.logic;

import br.com.professordanilo.quizapp.entity.Question;
import br.com.professordanilo.quizapp.util.ContextDAO;
import br.com.professordanilo.quizapp.util.StringHelper;
import br.com.professordanilo.quizapp.util.exception.BusinessException;
import br.com.professordanilo.quizapp.util.exception.SystemException;
import java.util.List;

public class QuestionLogic implements GenericLogic<Question, Integer>{

    @Override
    public Question save(Question entity) throws BusinessException, SystemException {
        entity = ContextDAO.getQuestionDAO().save(entity);
        return entity;
    }

    @Override
    public void delete(Question entity) throws BusinessException, SystemException {
        ContextDAO.getQuestionDAO().delete(entity);
    }

    @Override
    public void delete(Integer idEntity) throws BusinessException, SystemException {
        ContextDAO.getQuestionDAO().delete(new Question(idEntity));
    }

    @Override
    public Question getEntity(Integer idEntity) throws BusinessException, SystemException {
        return ContextDAO.getQuestionDAO().findById(idEntity);
    }

    @Override
    public List<Question> findAll() throws BusinessException, SystemException {
        return ContextDAO.getQuestionDAO().findAll();
    }

    @Override
    public List<Question> findByExample(Question entity) throws BusinessException, SystemException {
        return ContextDAO.getQuestionDAO().findByExample(entity, new String[]{"name"});
    }
    
    public List<String> findSubjects(){
        return ContextDAO.getQuestionDAO().findSubjects();
    }
    public List<Integer> findQuestionLevels(){
        return ContextDAO.getQuestionDAO().findQuestionLevels();
    }
    
    public Question selectRandomQuestion(List<String> subjects, Integer questionLevel) throws BusinessException{
        if(subjects == null || subjects.size() < 1){
            throw new BusinessException("Selecione ao menos 1 conteúdo para as questões.");
        }
        if(questionLevel == null){
            throw new BusinessException("Selecione o nível da pergunta sorteada.");
        }
        return ContextDAO.getQuestionDAO().selectRandomQuestion(subjects, questionLevel);
    }
}
