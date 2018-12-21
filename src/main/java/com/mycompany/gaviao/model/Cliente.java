package com.mycompany.gaviao.model;

import javax.persistence.*;

@Entity
@Table(name = "clientes")
public class Cliente {

     private Long id;
     private String nome;
     private String rg;
     private String cpf;
     private ClienteEndereco endereco;
     private Boolean ativo;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Column(name= "nome")
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Column(name= "rg")
    public String getRg() {
        return rg;
    }
    public void setRg(String rg) {
        this.rg = rg;
    }

    @Column(name= "cpf")
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_id")
    public ClienteEndereco getEndereco() {
        return endereco;
    }

    public void setEndereco(ClienteEndereco endereco) {
        this.endereco = endereco;
    }

    @Column(name = "ativo")
    public Boolean getAtivo() {
        return ativo;
    }
    public void setAtivo(Boolean ativo) {s
        this.ativo = ativo;
    }
}
