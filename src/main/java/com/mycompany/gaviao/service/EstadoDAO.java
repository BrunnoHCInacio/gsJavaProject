package com.mycompany.gaviao.service;

import com.mycompany.gaviao.model.Estado;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class EstadoDAO implements IService {
    protected SessionFactory sessionFactory;
    protected Session session;

    public Session getSession(){
        if(sessionFactory == null){
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory.openSession();
    }

    @Override
    public void create(Object objEstado) throws Exception {
        try {
            session = getSession();
            session.beginTransaction();
            session.save(objEstado);
            session.getTransaction().commit();
        }catch (Exception e){
            throw new Exception("Erro ao criar novo estado!" + e);
        }finally {
            session.close();
        }
    }

    @Override
    public List<Estado> retreaveAll() throws Exception {
        try{
            session = getSession();
            session.beginTransaction();
            Query query = session.createQuery("from Estado");
            List resultList = query.list();
            return resultList;
        }catch (Exception e){
            throw new Exception("Erro ao pesquisar todos os estados! " + e);
        }finally {
            session.close();
        }
    }

    @Override
    public Object retreaveById(int id) throws Exception {
        try{
            session = getSession();
            session.beginTransaction();
            Query query = session.createQuery("from Estado where id =" + id);
            return query.uniqueResult();
        }catch (Exception e){
            throw new Exception("Erro ao pesquisar estado pelo id!"+ id );
        }
        finally {
            session.close();
        }
    }
    public List<Estado> retreaveByNome(String nome) throws Exception {
        try{
            if(StringUtils.isNotBlank(nome)) {
                session = getSession();
                session.beginTransaction();
                Query query = session.createQuery("from Estado where nome LIKE '" + nome + "%'");
                List resultList = query.list();
                return resultList;
            }else {
                return null;
            }
        }catch (Exception e){
            throw new Exception("Erro ao pesquisar estado pelo nome! "+e);
        }
    }

    @Override
    public void update(Object objEstado) throws Exception {
        try {
            Estado estado = (Estado) objEstado;
            session = getSession();
            session.beginTransaction();
            Query query = session.createQuery("from Estado set nome = :NOME where = id" + estado.getId());
            query.setParameter(":NOME", estado.getNome());
            query.executeUpdate();
            session.getTransaction().commit();
        }catch (Exception e){
            throw new Exception("Erro ao alterar estado!" + e);
        }finally {
            session.close();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        try{
            session = getSession();
            session.beginTransaction();
            Query query = session.createQuery("from Estado where id ="+ id);
            query.executeUpdate();
            session.getTransaction().commit();
        }catch (Exception e){
            throw new Exception("Erro ao deletar estado!" +e);
        }finally {
            session.close();
        }
    }
}
