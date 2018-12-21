package com.mycompany.gaviao.service;

import com.mycompany.gaviao.model.ClienteEndereco;
import com.mycompany.gaviao.model.Estado;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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
        try {
            session = getSession();
            session.beginTransaction();
            session.save(objClienteEndereco);
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
            List<ClienteEndereco> enderecos = session.createCriteria(ClienteEndereco.class, "endereco").createAlias("endereco.cidade", "cidade").list();
            return enderecos;
        }catch (Exception e){
            throw new Exception("Erro ao listar todos os endereços, ", e);
        }finally {
            session.close();
        }
    }
    public List<ClienteEndereco> retreaveByLogradouro(String nome) throws Exception {
        try {
            if(StringUtils.isNotBlank(nome)) {
                session = getSession();
                session.beginTransaction();
                Query query = session.createQuery("from ClienteEndereco where logradouro LIKE '"+nome+"%' ");
                List resultList = query.list();
                return resultList;
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
            Query query = session.createQuery("from ClienteEndereco where id =" + id);
            ClienteEndereco endereco = (ClienteEndereco) query.uniqueResult();
            return endereco;
        }catch (Exception e){
            throw new Exception("Erro ao pesquisar endereço por id", e);
        }finally {
            session.close();
        }
    }

    @Override
    public void update(Object objEndereco) throws Exception {
        try {
            ClienteEndereco endereco = (ClienteEndereco) objEndereco;
            session = getSession();
            session.beginTransaction();
            Query query = session.createQuery("from ClienteEndereco set" +
                    "logradouro = :LOGRADOURO, numero = :NUMERO, complemento = : COMPLEMENTO" +
                    "bairro = :BAIRRO, cidade = :CIDADE, pais = :PAIS, cep = :CEP where id =" + endereco.getId());
            query.setParameter("LOGRADOURO", endereco.getLogradouro());
            query.setParameter("NUMERO", endereco.getNumero());
            query.setParameter("COMPLEMENTO", endereco.getComplemento());
            query.setParameter("BAIRRO", endereco.getBairro());
            query.setParameter("CIDADE", endereco.getCidade());
            query.setParameter("PAIS", endereco.getPais());
            query.setParameter("CEP", endereco.getCep());
            query.executeUpdate();
            session.getTransaction().commit();
        }catch (Exception e){
            throw new Exception("Erro ao alterar o endereço!", e);
        }finally {
            session.close();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        try {
            session = getSession();
            session.beginTransaction();
            Query query = session.createQuery("from ClienteEndereco where id =" + id);
            query.executeUpdate();
            session.getTransaction().commit();
        }catch (Exception e){
            throw new Exception("Error ao deletar endereço! ", e);
        }finally {
            session.close();
        }
    }
}
