package br.com.professordanilo.quizapp.logic;

import br.com.professordanilo.quizapp.entity.Event;
import br.com.professordanilo.quizapp.util.ContextDAO;
import br.com.professordanilo.quizapp.util.exception.BusinessException;
import br.com.professordanilo.quizapp.util.exception.SystemException;
import java.util.List;

public class EventLogic implements GenericLogic<Event, Integer>{

    @Override
    public Event save(Event entity) throws BusinessException, SystemException {
        entity = ContextDAO.getEventDAO().save(entity);
        return entity;
    }

    @Override
    public void delete(Event entity) throws BusinessException, SystemException {
        ContextDAO.getEventDAO().delete(entity);
    }

    @Override
    public void delete(Integer idEntity) throws BusinessException, SystemException {
        ContextDAO.getEventDAO().delete(new Event(idEntity));
    }

    @Override
    public Event getEntity(Integer idEntity) throws BusinessException, SystemException {
        return ContextDAO.getEventDAO().findById(idEntity);
    }

    @Override
    public List<Event> findAll() throws BusinessException, SystemException {
        return ContextDAO.getEventDAO().findAll();
    }

    @Override
    public List<Event> findByExample(Event entity) throws BusinessException, SystemException {
        return ContextDAO.getEventDAO().findByExample(entity, new String[]{"name"});
    }
    
}
