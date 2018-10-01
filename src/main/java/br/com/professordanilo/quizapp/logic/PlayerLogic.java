package br.com.professordanilo.quizapp.logic;

import br.com.professordanilo.quizapp.entity.Player;
import br.com.professordanilo.quizapp.util.exception.BusinessException;
import br.com.professordanilo.quizapp.util.exception.SystemException;
import java.io.Serializable;
import java.util.List;

public class PlayerLogic implements GenericLogic<Player, Integer>{

    @Override
    public Player save(Player entity) throws BusinessException, SystemException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Player entity) throws BusinessException, SystemException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer idEntity) throws BusinessException, SystemException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Player getEntity(Integer idEntity) throws BusinessException, SystemException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Player> findAll() throws BusinessException, SystemException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Player> findByExample(Player entity) throws BusinessException, SystemException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
