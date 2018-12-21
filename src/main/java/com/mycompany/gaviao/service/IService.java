package com.mycompany.gaviao.service;

import com.mycompany.gaviao.model.Estado;

import java.util.List;

public interface IService {
    public void create(Object object) throws Exception;
    public List retreaveAll() throws Exception;
    public Object retreaveById(int id) throws Exception;
    public void update(Object objetc) throws Exception;
    public void delete(int id) throws Exception;

}
