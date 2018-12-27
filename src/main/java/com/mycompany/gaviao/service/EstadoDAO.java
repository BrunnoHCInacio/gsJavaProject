package com.mycompany.gaviao.service;

import com.mycompany.gaviao.model.Estado;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

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
        Estado estado = (Estado) objEstado;
        estado.setAtivo(1);
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
            return session.createCriteria(Estado.class, "estado")
                    .add(Restrictions.eq("estado.ativo", 1))
                    .list();
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
            return session.createCriteria(Estado.class, "estado")
                    .add(Restrictions.eq("estado.id", id))
                    .add(Restrictions.eq("estado.ativo", 1))
                    .uniqueResult();
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
                return session.createCriteria(Estado.class, "estado")
                        .add(Restrictions.eq("estado.ativo", 1))
                        .add(Restrictions.like("nome", nome +"%"))
                        .list();
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
            session.saveOrUpdate(estado);
            session.getTransaction().commit();
        }catch (Exception e){
            throw new Exception("Erro ao alterar estado!" + e);
        }finally {
            session.close();
        }
    }

    @Override
    public void delete(Object objEstado) throws Exception {
        Estado estado = (Estado) objEstado;
        estado.setAtivo(0);
        try{
            session = getSession();
            session.beginTransaction();
            session.saveOrUpdate(estado);
            session.getTransaction().commit();
        }catch (Exception e){
            throw new Exception("Erro ao deletar estado!" +e);
        }finally {
            session.close();
        }
    }
}
