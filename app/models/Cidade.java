package models;

import com.avaje.ebean.Model;
import play.data.format.Formats;
import play.libs.Json;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nome" , "estado_id"})
})
public class Cidade extends Model {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(nullable = false, unique = true, length = 25)
    private String nome;

    @Column(length = 4)
    private String ddd;

    @ManyToOne(cascade = CascadeType.MERGE, optional = false)
    private Estado estado;

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

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
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
