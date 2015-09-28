package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Query;
import models.Pais;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.persistence.PersistenceException;
import java.util.Date;
import java.util.List;

public class PaisController extends Controller {

    public Result inserir() {

        Pais pais = Json.fromJson(request().body().asJson(), Pais.class);

        Pais paisBusca = Ebean.find(Pais.class).where().eq("nome", pais.getNome()).findUnique();

        if (paisBusca != null) {
            Logger.warn("País já cadastrado.");
            return badRequest("O país " + paisBusca.getNome() + " já esta cadastrado.");
        }

        try {
            pais.setDataCadastro(new Date());
            Ebean.save(pais);
            Logger.info("País salvo.");
        } catch (Exception e) {
            Logger.error(e.getMessage());
            return badRequest("Erro interno de sistema.");
        }

        return created(Json.toJson(pais));
    }

    public Result atualizar(Long id) {

        Pais pais = Json.fromJson(request().body().asJson(), Pais.class);

        Pais paisBusca = Ebean.find(Pais.class, id);

        if (paisBusca == null) {
            return notFound("País não encontrado.");
        }

        try {
            pais.setDataAlteracao(new Date());
            Ebean.update(pais);
            Logger.info("País atualizado.");
        } catch (Exception e) {
            Logger.error(e.getMessage());
            return badRequest("Erro interno de sistema.");
        }

        return ok(Json.toJson(pais));
    }

    public Result buscaPorId(Long id) {

        Pais pais = Ebean.find(Pais.class, id);

        if (pais == null) {
            Logger.warn("País não encontrado.");
            return notFound("País não encontrado.");
        }

        return ok(Json.toJson(pais));
    }

    public Result buscaTodos() {
        Logger.info("Busca todos os Países");

        return ok(Json.toJson(Ebean.find(Pais.class)
                .order()
                .asc("nome")
                .findList()));
    }

    public Result remover(Long id) {

        Pais pais = Ebean.find(Pais.class, id);

        if (pais == null) {
            Logger.warn("País não encontrado.");
            return notFound("País não encontrado.");
        }

        try {
            Ebean.delete(pais);
            Logger.info("País removido.");
        } catch (PersistenceException e) {
            Logger.error(e.getMessage());
            return badRequest("Existem Estados que dependem deste País.");
        } catch (Exception e) {
            Logger.error(e.getMessage());
            return badRequest("Erro interno de sistema.");
        }

        return ok(Json.toJson(pais));
    }

    public Result filtra(String filtro) {
        Logger.info("Filtra País.");

        //busca país através do filtro que recebe por parâmetro
        Query<Pais> query = Ebean.createQuery(Pais.class, "find pais where (nome like :nome or ddi like :ddi)");
        query.setParameter("nome", "%" + filtro + "%");
        query.setParameter("ddi", "%" + filtro + "%");
        List<Pais> filtroDePaises = query.findList();

        return ok(Json.toJson(filtroDePaises));
    }
}
