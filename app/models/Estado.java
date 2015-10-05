package models;

import com.avaje.ebean.Model;
import play.data.format.Formats;
import play.libs.Json;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nome" , "pais_id"})
})
public class Estado extends Model {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(nullable = false, length = 25)
    private String nome;

    @Column(length = 3)
    private String uf;

    @ManyToOne(cascade = CascadeType.MERGE, optional = false)
    private Pais pais;

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

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
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
