package com.mycompany.gaviao.model;

import javax.persistence.*;

@Entity
@Table(name = "estados")
public class
Estado {
    private int id;
    private String nome;
    private Boolean ativo;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    @Column(name = "nome")
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    @Column(name = "ativo")
    public Boolean getAtivo() {
        return ativo;
    }
    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
