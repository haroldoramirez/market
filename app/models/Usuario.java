package models;

import com.avaje.ebean.Model;
import play.data.format.Formats;
import play.libs.Json;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Usuario extends Model {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String email;

    @Column(nullable = false, length = 60)
    private String senha;

    @Column(nullable = false)
    private Integer privilegio;

    @Formats.DateTime(pattern="dd-MM-yyyy")
    private Date dataCadastro;

    @Formats.DateTime(pattern="dd-MM-yyyy")
    private Date dataAlteracao;

    @Column(nullable = false)
    private Boolean padraoDoSistema;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Integer getPrivilegio() {
        return privilegio;
    }

    public void setPrivilegio(Integer privilegio) {
        this.privilegio = privilegio;
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

    public Boolean getPadraoDoSistema() {
        return padraoDoSistema;
    }

    public void setPadraoDoSistema(Boolean padraoDoSistema) {
        this.padraoDoSistema = padraoDoSistema;
    }

    @Override
    public String toString() {
        return Json.toJson(this).toString();
    }
}
