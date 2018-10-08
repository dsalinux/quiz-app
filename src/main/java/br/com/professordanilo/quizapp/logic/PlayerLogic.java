package br.com.professordanilo.quizapp.logic;

import br.com.professordanilo.quizapp.entity.Player;
import br.com.professordanilo.quizapp.util.ContextDAO;
import br.com.professordanilo.quizapp.util.exception.BusinessException;
import br.com.professordanilo.quizapp.util.exception.SystemException;
import java.io.Serializable;
import java.util.List;

public class PlayerLogic implements GenericLogic<Player, Integer>{

    @Override
    public Player save(Player entity) throws BusinessException, SystemException {
        entity = ContextDAO.getPlayerDAO().save(entity);
        return entity;
    }

    @Override
    public void delete(Player entity) throws BusinessException, SystemException {
        ContextDAO.getPlayerDAO().delete(entity);
    }

    @Override
    public void delete(Integer idEntity) throws BusinessException, SystemException {
        ContextDAO.getPlayerDAO().delete(new Player(idEntity));
    }

    @Override
    public Player getEntity(Integer idEntity) throws BusinessException, SystemException {
        return ContextDAO.getPlayerDAO().findById(idEntity);
    }

    @Override
    public List<Player> findAll() throws BusinessException, SystemException {
        return ContextDAO.getPlayerDAO().findAll();
    }

    @Override
    public List<Player> findByExample(Player entity) throws BusinessException, SystemException {
        return ContextDAO.getPlayerDAO().findByExample(entity, new String[]{"name"});
    }
    
}
