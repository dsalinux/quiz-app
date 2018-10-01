/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.professordanilo.quizapp.logic;

import br.com.professordanilo.quizapp.util.exception.BusinessException;
import br.com.professordanilo.quizapp.util.exception.SystemException;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author danilo
 */
public interface GenericLogic<E, ID extends Serializable>{
    
    E save(E entity) throws BusinessException, SystemException;
    void delete(E entity) throws BusinessException, SystemException;
    void delete(ID idEntity) throws BusinessException, SystemException;
    E getEntity(ID idEntity) throws BusinessException, SystemException;
    List<E> findAll() throws BusinessException, SystemException;
    List<E> findByExample(E entity) throws BusinessException, SystemException;
    
}
