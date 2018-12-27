package com.mycompany.gaviao.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clientes")
public class Cliente {

    private Long id;
    private String nome;
    private String rg;
    private String cpf;
    //     private ClienteEndereco endereco;
    private int ativo;

    private List<ClienteEndereco> enderecos = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "nome")
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Column(name = "rg")
    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    @Column(name = "cpf")
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @OneToMany(mappedBy = "cliente")
    public List<ClienteEndereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<ClienteEndereco> enderecos) {
        this.enderecos = enderecos;
    }

    @Column(name = "ativo")
    public int getAtivo() {
        return ativo;
    }
    public void setAtivo(int ativo) {
        this.ativo = ativo;
    }
}
