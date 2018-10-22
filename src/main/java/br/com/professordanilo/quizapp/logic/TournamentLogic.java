package br.com.professordanilo.quizapp.logic;

import br.com.professordanilo.quizapp.entity.Tournament;
import br.com.professordanilo.quizapp.util.ContextDAO;
import br.com.professordanilo.quizapp.util.exception.BusinessException;
import br.com.professordanilo.quizapp.util.exception.SystemException;
import java.util.List;

public class TournamentLogic implements GenericLogic<Tournament, Integer>{

    @Override
    public Tournament save(Tournament entity) throws BusinessException, SystemException {
        entity = ContextDAO.getTournamentDAO().save(entity);
        return entity;
    }

    @Override
    public void delete(Tournament entity) throws BusinessException, SystemException {
        ContextDAO.getTournamentDAO().delete(entity);
    }

    @Override
    public void delete(Integer idEntity) throws BusinessException, SystemException {
        ContextDAO.getTournamentDAO().delete(new Tournament(idEntity));
    }

    @Override
    public Tournament getEntity(Integer idEntity) throws BusinessException, SystemException {
        return ContextDAO.getTournamentDAO().findById(idEntity);
    }

    @Override
    public List<Tournament> findAll() throws BusinessException, SystemException {
        return ContextDAO.getTournamentDAO().findAll();
    }

    @Override
    public List<Tournament> findByExample(Tournament entity) throws BusinessException, SystemException {
        return ContextDAO.getTournamentDAO().findByExample(entity, new String[]{"name"});
    }
    
}
