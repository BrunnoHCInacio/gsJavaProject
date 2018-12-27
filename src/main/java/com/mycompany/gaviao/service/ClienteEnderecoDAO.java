package com.mycompany.gaviao.service;

import com.mycompany.gaviao.model.Cliente;
import com.mycompany.gaviao.model.ClienteEndereco;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import java.util.List;

public class ClienteEnderecoDAO implements IService {

    protected Session session;
    protected SessionFactory sessionFactory;


    public Session getSession(){
        if(sessionFactory == null){
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory.openSession();
    }


    @Override
    public void create(Object objClienteEndereco) throws Exception {
        ClienteEndereco endereco = (ClienteEndereco) objClienteEndereco;
        endereco.setAtivo(1);
        try {
            session = getSession();
            session.beginTransaction();
            session.save(endereco);
            session.getTransaction().commit();

        }catch (Exception e){
            throw new Exception("Erro ao criar novo endereço, ", e);
        }
    }

    @Override
    public List<ClienteEndereco> retreaveAll() throws Exception {
        try{
            session = getSession();
            session.beginTransaction();
            List<ClienteEndereco> lista =  session.createCriteria(ClienteEndereco.class, "endereco")
                    .add(Restrictions.eq("endereco.ativo", 1))
                    .createAlias("endereco.cidade", "cidade", JoinType.LEFT_OUTER_JOIN)
                    .createAlias("endereco.cliente", "cliente")
                    .list();
            return lista;
        }catch (Exception e){
            throw new Exception("Erro ao listar todos os endereços, ", e);
        }finally {
            session.close();
        }
    }
    public List retreaveByLogradouro(String nome) throws Exception {
        try {
            if(StringUtils.isNotBlank(nome)) {
                session = getSession();
                session.beginTransaction();
                return session.createCriteria(ClienteEndereco.class, "endereco")
                        .add(Restrictions.eq("endereco.ativo", 1))
                        .add(Restrictions.like("logradouro", nome + "%"))
                        .list();
//                Query query = session.createQuery("from ClienteEndereco where logradouro LIKE '"+nome+"%' ");
//                return query.list();
            }else{
                return null;
            }
        }catch (Exception e){
            throw new Exception("Erro ao pesquisar endereco pelo logradouro! ", e);

        }finally {
            session.close();
        }
    }

    @Override
    public Object retreaveById(int id) throws Exception {
        try {
            session = getSession();
            session.beginTransaction();
            return session.createCriteria(ClienteEndereco.class, "endereco")
                    .add(Restrictions.eq("endereco.ativo", 1))
                    .add(Restrictions.eq("endereco.id", id))
                    .uniqueResult();
//            Query query = session.createQuery("from ClienteEndereco where id =" + id);
//            ClienteEndereco endereco = (ClienteEndereco) query.uniqueResult();
//            return endereco;
        }catch (Exception e){
            throw new Exception("Erro ao pesquisar endereço por id", e);
        }finally {
            session.close();
        }
    }

    public List<ClienteEndereco> retreaveByClienteId(Long clienteId) throws Exception {
        try {
            session = getSession();
            session.beginTransaction();
            List<ClienteEndereco> list =  session.createCriteria(ClienteEndereco.class, "endereco")
                    .createAlias("endereco.cliente", "cliente", JoinType.LEFT_OUTER_JOIN)
                    .createAlias("endereco.cidade", "cidade", JoinType.LEFT_OUTER_JOIN)
                    .add(Restrictions.eq("endereco.ativo", 1))
                    .add(Restrictions.eq("cliente.id", clienteId))
                    .list();

            return list;
        } catch (Exception e){
            throw new Exception("Erro ao Listar clienteEndereco por ClienteId", e);
        } finally {
            session.close();
        }
    }

    @Override
    public void update(Object objEndereco) throws Exception {
        try {
            ClienteEndereco endereco = (ClienteEndereco) objEndereco;
            endereco.setAtivo(1);
            session = getSession();
            session.beginTransaction();
            session.saveOrUpdate(endereco);
            session.getTransaction().commit();
        }catch (Exception e){
            throw new Exception("Erro ao alterar o endereço!", e);
        }finally {
            session.close();
        }
    }

    @Override
    public void delete(Object objClienteEndereco) throws Exception {
        ClienteEndereco endereco = (ClienteEndereco) objClienteEndereco;
        endereco.setAtivo(0);
        try {
            session = getSession();
            session.beginTransaction();
            session.saveOrUpdate(endereco);
            session.getTransaction().commit();
        }catch (Exception e){
            throw new Exception("Error ao deletar endereço! ", e);
        }finally {
            session.close();
        }
    }
}
