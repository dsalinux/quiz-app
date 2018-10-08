package br.com.professordanilo.quizapp.dao;

import br.com.professordanilo.quizapp.util.HibernateConnection;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;

public class GenericDAO<T, I extends Serializable> {
    
    private Session session;
    private Class<T> persistentClass;
    
    public T save(T entity) {
        Transaction transaction = getSession().beginTransaction();
        entity = (T)getSession().merge(entity);
        transaction.commit();
        closeSession();
        return entity;
    }
    
    public void delete(T entity) {
        Transaction transaction = getSession().beginTransaction();
        getSession().delete(entity);
        transaction.commit();
        closeSession();
    }

    public void refresh(T entity){
        getSession().refresh(entity);
        closeSession();
    }
    
    public void initializer(List<T> entitys){
        entitys.forEach((entity) -> {
            Hibernate.initialize(entity);
        });
    }
    
    public T findById(I id) {
        return findById(id, true);
    }

    public T findById(I id, boolean closeSession) {
        T entity = (T) getSession().get(getPersistentClass(), id);
        if(closeSession){
            closeSession();
        }
        return entity;
    }

    public List<T> findByExample(T entity, String... filds) {
        getSession().flush();
        getSession().clear();
        Criteria crit = getSession().createCriteria(getPersistentClass());
        Example example =  Example.create(entity);
        for (String exclude : filds) {
            example.excludeProperty(exclude);
        }
        crit.add(example);
        List l = crit.list();
        closeSession();
        return l;
    }

    public List<T> findAll(){
//        getSession().flush();Quando o cliente clica em editar depois apaga um campo e tenta pesquisar novametne, da erro! por isso foi comentado
        getSession().clear();
        Criteria crit = getSession().createCriteria(getPersistentClass());
        List l = crit.list();
        closeSession();
        return l;
    }
    
    public void clear(){
        getSession().clear();
    }
    
    public Session getSession(){
        if(this.session == null){
            this.session = HibernateConnection.getSessionFactory().openSession();
        }
        return this.session;
    }
    
    public void closeSession(){
        if(this.session != null){
            this.session.close();
            this.session = null;
        }
    }
    
    public Class<T> getPersistentClass(){
        persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return persistentClass;
    }
}
