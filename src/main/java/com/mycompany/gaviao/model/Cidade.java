package com.mycompany.gaviao.model;

import javax.persistence.*;

@Entity
@Table(name = "cidades")
public class Cidade {
    private int id;
    private String nome;
    private Estado estado;
    private int ativo;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "nome")
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_id")
    public Estado getEstado() {
        return estado;
    }
    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Column(name = "ativo")
    public int getAtivo() {
        return ativo;
    }
    public void setAtivo(int ativo) {
        this.ativo = ativo;
    }
}
