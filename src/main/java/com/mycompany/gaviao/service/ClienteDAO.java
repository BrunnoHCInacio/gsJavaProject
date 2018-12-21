package com.mycompany.gaviao.service;
import com.mycompany.gaviao.model.Cliente;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.List;

public class ClienteDAO {

    protected Session session;
    protected static SessionFactory sessionFactory;


    protected Session getSession() throws Exception {
        try {
            if(sessionFactory == null) {
                sessionFactory = new Configuration().configure().buildSessionFactory();
            }
            return sessionFactory.openSession();
        } catch (Exception ex) {
            throw new Exception("Erro ao abrir sessão com o banco!", ex);
        }
    }

    public void create(Cliente cliente) throws Exception {
        try {
            session = getSession();
            session.beginTransaction();
            session.save(cliente);
            session.getTransaction().commit();
            System.out.println("Cliente salvo");
        }catch(Exception ex){

            throw new Exception("Erro ao criar novo cliente!",ex);
        } finally {
            session.close();
        }
    }
    public List<Cliente> retreaveAll() throws Exception {
        List resultList;
        try {
            session = getSession();
            session.beginTransaction();
            List<Cliente> clientes = session.createCriteria(Cliente.class, "cliente").createAlias("cliente.endereco", "endereco" ).list();
            return clientes;
        }catch (Exception ex){
             throw new Exception("Erro ao pesquisar todos os clientes!", ex);
        }finally {
            session.close();
        }
    }
    public Cliente retreaveById(int id) throws Exception {
        try {
            session = getSession();
            session.beginTransaction();
            Query query = session.createQuery("from Cliente where id =" + id);
            Cliente cliente = (Cliente) query.uniqueResult();
            return cliente;
        }catch (Exception ex){
            throw new Exception("Erro ao pesquisar cliente pelo id!", ex);
        }finally {
            session.close();
        }
    }
    public List<Cliente> retreaveByNome(String nome) throws Exception {
        try {
            session = getSession();
            session.beginTransaction();
            Query query = session.createQuery("from Cliente where nome LIKE '" + nome + "%'");
            List resultList = query.list();
            return resultList;
        }catch (Exception ex){
            throw new Exception("Erro ao pesquisar cliente pelo nome!", ex);
        }finally {
            session.close();

        }
    }
    public void update(Cliente cliente) throws Exception {
        try {
            session = getSession();
            session.beginTransaction();
            Query query = session.createQuery("update Cliente set nome = :NOME, cpf = :CPF, rg = :RG where id =" + cliente.getId());
            query.setParameter("NOME", cliente.getNome());
            query.setParameter("CPF", cliente.getCpf());
            query.setParameter("RG", cliente.getRg());
            int result = query.executeUpdate();
            session.getTransaction().commit();
        }catch (Exception ex){
            throw new Exception("Erro ao alterar cliente", ex);
        }finally {
            session.close();
        }
    }
    public void delete(int id) throws Exception {
        try {
            session = getSession();
            session.beginTransaction();
            Query query = session.createQuery("delete Cliente where id =" + id);
            query.executeUpdate();
            session.getTransaction().commit();
        }catch (Exception ex){
            throw new Exception("Erro ao deletar cliente!", ex);
        }finally {
            session.close();

        }
    }

}