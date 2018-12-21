package com.mycompany.gaviao.service;

import com.mycompany.gaviao.model.Cidade;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class CidadeDAO implements IService {
    protected Session session;
    protected SessionFactory sessionFactory;

    public Session getSession(){
        if(sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory.openSession();
    }

    @Override
    public void create(Object objCidade) throws Exception {
        try {
            session = getSession();
            session.beginTransaction();
            session.save(objCidade);
            session.getTransaction().commit();
        }catch (Exception e){
            throw new Exception("Erro ao criar nova cidade!" + e);
        }finally {
            session.close();
        }
    }

    @Override
    public List<Cidade> retreaveAll() throws Exception {
        try{
            session = getSession();
            session.beginTransaction();
            List<Cidade> cidades =  session.createCriteria(Cidade.class, "c").createAlias("c.estado", "e").list();
            return cidades;
        }catch (Exception e){
            throw new Exception("Erro ao pesquisar todas as cidades!" + e);
        }finally {
            session.close();
        }
    }

    @Override
    public Object retreaveById(int id) throws Exception {
        try{
            session = getSession();
            session.beginTransaction();
            Query query = session.createQuery(" from Cidade where id =" + id);
            return query.uniqueResult();
        }catch (Exception e){
            throw new Exception(" Erro ao pesquisar cidade pelo id!" + e);
        }finally {
            session.close();
        }
    }
    public List<Cidade> retreaveByNome(String nome) throws Exception {
        try {
            session = getSession();
            session.beginTransaction();
            Query query = session.createQuery("from Cidade where nome LIKE '" + nome + "%'");
            List result = query.list();
            return result;
        }catch (Exception e){
            throw new Exception("Erro ao pesquisar cidade pelo nome!"+ e);
        }
    }

    @Override
    public void update(Object objCidade) throws Exception {
        try{
            Cidade cidade = (Cidade) objCidade;
            session = getSession();
            session.beginTransaction();
            session.saveOrUpdate(cidade);
//            Query query = session.createQuery("from Cidade set nome= :NOME, estado= : ESTADO where id =" + cidade.getId());
//            query.setParameter(":NOME", cidade.getNome());
//            query.setParameter(":ESTADO", cidade.getEstado());
//            query.executeUpdate();
            session.getTransaction().commit();
        }catch (Exception e){
            throw new Exception("Erro ao alterar cidade!" + e);
        }finally {
            session.close();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        try {
            session = getSession();
            session.beginTransaction();
            Query query = session.createQuery("from Cidade where id =" + id);
            query.executeUpdate();
            session.getTransaction().commit();
        }catch (Exception e){
            throw new Exception("Erro ao deletar cidade!" + e);
        }finally {
            session.close();
        }
    }
}
