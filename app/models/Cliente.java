package models;

import com.avaje.ebean.Model;
import play.data.format.Formats;
import play.libs.Json;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Cliente extends Model {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(nullable = false, unique = true, length = 35)
    private String nome;

    @Column(length = 15)
    private String rg;

    @Column(nullable = false, unique = true, length = 15)
    private String cpf;

    @Column(nullable = false, unique = true, length = 20)
    private String cnpj;

    @Column(length = 15)
    private String ie;

    @Column(length = 35)
    private String email;

    @Column(length = 12)
    private String telefone;

    @Column(length = 12)
    private String celular;

    @Column(length = 35)
    private String contato;

    @Column(length = 35)
    private String logradouro;

    @Column(length = 5)
    private String numero;

    @Column(length = 10)
    private String cep;

    @Column(length = 20)
    private String Bairro;

    @Column(length = 10)
    private String complemento;

    private TipoPessoa tipoPessoa;

    @Column(length = 10)
    private String observacoes;

    @ManyToOne(cascade = CascadeType.MERGE, optional = false)
    private Cidade cidade;

    @Formats.DateTime(pattern="dd-MM-yyyy")
    private Date dataCadastro;

    @Formats.DateTime(pattern="dd-MM-yyyy")
    private Date dataAlteracao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getIe() {
        return ie;
    }

    public void setIe(String ie) {
        this.ie = ie;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getBairro() {
        return Bairro;
    }

    public void setBairro(String bairro) {
        Bairro = bairro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public TipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Date getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Date dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    @Override
    public String toString() {
        return Json.toJson(this).toString();
    }
}
