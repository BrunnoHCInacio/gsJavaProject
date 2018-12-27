package com.mycompany.gaviao.service;
import com.mycompany.gaviao.model.Cliente;
import com.mycompany.gaviao.model.ClienteEndereco;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import java.awt.geom.RectangularShape;
import java.util.ArrayList;
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
        cliente.setAtivo(1);
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

    public List retreaveAll() throws Exception {
        try {
            session = getSession();
            session.beginTransaction();
            return session.createCriteria(Cliente.class, "cliente")
                    .add(Restrictions.eq("cliente.ativo", 1))
                    .list();
        }catch (Exception ex){
             throw new Exception("Erro ao pesquisar todos os clientes!", ex);
        }finally {
            session.close();
        }
    }
    public Object retreaveById(Long id) throws Exception {
        try {
            session = getSession();
            session.beginTransaction();
            Cliente cliente = (Cliente) session.createCriteria(Cliente.class, "cliente")
                    .add(Restrictions.eq("cliente.ativo", 1))
                    .add(Restrictions.eq("cliente.id", id))
                    .uniqueResult();

            cliente.setEnderecos(carregarClienteEnderecoList(cliente.getId()));


            return cliente;
//            Query query = session.createQuery("from Cliente where id =" + id);
//            Cliente cliente = (Cliente) query.uniqueResult();
//            return cliente;
        }catch (Exception ex){
            throw new Exception("Erro ao pesquisar cliente pelo id!", ex);
        }finally {
            session.close();
        }
    }



    public List<ClienteEndereco> carregarClienteEnderecoList(Long clienteId) throws Exception {
        try{
            ClienteEnderecoDAO enderecoDAO = new ClienteEnderecoDAO();
            List<ClienteEndereco> clienteEnderecoList = clienteEnderecoList = enderecoDAO.retreaveByClienteId(clienteId);
            return clienteEnderecoList;

        } catch (Exception e){
            throw new Exception("Erro ao Carregar Lista de Cliente Endereço", e);
        }

    }
    public List<Cliente> retreaveByNome(String nome) throws Exception {
        try {
            session = getSession();
            session.beginTransaction();
            return session.createCriteria(Cliente.class, "cliente")
                    .add(Restrictions.eq("cliente.ativo", 1))
                    .add(Restrictions.like("nome", nome + "%"))
                    .list();
//            Query query = session.createQuery("from Cliente where nome LIKE '" + nome + "%'");
//            List resultList = query.list();
//            return resultList;
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
            session.saveOrUpdate(cliente);
//            Query query = session.createQuery("update Cliente set nome = :NOME, cpf = :CPF, rg = :RG where id =" + cliente.getId());
//            query.setParameter("NOME", cliente.getNome());
//            query.setParameter("CPF", cliente.getCpf());
//            query.setParameter("RG", cliente.getRg());
//            int result = query.executeUpdate();
            session.getTransaction().commit();
        }catch (Exception ex){
            throw new Exception("Erro ao alterar cliente", ex);
        }finally {
            session.close();
        }
    }
    public void delete(Cliente cliente) throws Exception {
        cliente.setAtivo(0);
        try {
            session = getSession();
            session.beginTransaction();
            session.saveOrUpdate(cliente);
//            Query query = session.createQuery("delete Cliente where id =" + id);
//            query.executeUpdate();
            session.getTransaction().commit();
        }catch (Exception ex){
            throw new Exception("Erro ao deletar cliente!", ex);
        }finally {
            session.close();

        }
    }

}