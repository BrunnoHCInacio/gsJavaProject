package com.mycompany.gaviao.bean;

import java.util.List;

public interface IBean {
    public void salvar();
    public List pesquisarTodos();
    public void alterar(Object object);
    public void deletar(int id);

}
