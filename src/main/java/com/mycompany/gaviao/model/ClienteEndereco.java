package com.mycompany.gaviao.model;

import javax.persistence.*;

@Entity
@Table(name= "cliente_enderecos")
public class ClienteEndereco {

    private int id;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private Cidade cidade;
    private String pais;
    private String cep;
    private Boolean ativo;

    private Cliente cliente;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Column(name="logradouro")
    public String getLogradouro() {
        return logradouro;
    }
    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    @Column(name = "numero")
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    @Column(name = "complemento")
    public String getComplemento() {
        return complemento;
    }
    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    @Column(name = "bairro")
    public String getBairro() { return bairro; }
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cidade_id")
    public Cidade getCidade() {
        return cidade;
    }
    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    @Column(name = "pais")
    public String getPais() {
        return pais;
    }
    public void setPais(String pais) {
        this.pais = pais;
    }

    @Column(name = "cep")
    public String getCep() {
        return cep;
    }
    public void setCep(String cep) {
        this.cep = cep;
    }

    @Column(name = "ativo")
    public Boolean getAtivo() {
        return ativo;
    }
    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
