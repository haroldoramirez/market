package controllers;

import actions.PlayAuthenticatedSecured;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.Query;
import models.Estado;
import models.Pais;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import javax.persistence.PersistenceException;
import java.util.Date;
import java.util.List;

@Security.Authenticated(PlayAuthenticatedSecured.class)
public class EstadoController extends Controller {

    public Result inserir() {

        Estado estado = Json.fromJson(request().body().asJson(), Estado.class);

        Pais pais = Ebean.find(Pais.class, estado.getPais().getId());

        estado.setPais(pais);

        try {
            estado.setDataCadastro(new Date());
            Ebean.save(estado);
            Logger.info("Estado salvo.");
        } catch (PersistenceException e) {
            Logger.error(e.getMessage());
            return badRequest("O estado '" + estado.getNome() + "' já esta cadastrado. Selecione outro País.");
        } catch (Exception e) {
            Logger.error(e.getMessage());
            return badRequest("Erro interno de sistema.");
        }

        return created(Json.toJson(estado));
    }

    public Result atualizar(Long id) {

        Estado estado = Json.fromJson(request().body().asJson(), Estado.class);

        Estado estadoBusca = Ebean.find(Estado.class, id);

        if (estadoBusca == null) {
            return notFound("Estado não encontrado.");
        }

        Pais pais = Ebean.find(Pais.class, estado.getPais().getId());

        estado.setPais(pais);

        try {
            estado.setDataAlteracao(new Date());
            Ebean.update(estado);
            Logger.info("Estado atualizado.");
        } catch (Exception e) {
            Logger.error(e.getMessage());
            return badRequest("Erro interno de sistema.");
        }

        return ok(Json.toJson(estado));
    }

    public Result buscaPorId(Long id) {

        Estado estado = Ebean.find(Estado.class, id);

        if (estado == null) {
            Logger.warn("Estado não encontrado.");
            return notFound("Estado não encontrado.");
        }

        return ok(Json.toJson(estado));
    }

    public Result buscaTodos() {
        Logger.info("Busca todos os Estados.");

        return ok(Json.toJson(Ebean.find(Estado.class)
                .order()
                .asc("nome")
                .findList()));
    }

    public Result remover(Long id) {

        Estado estado = Ebean.find(Estado.class, id);

        if (estado == null) {
            Logger.warn("Estado não encontrado.");
            return notFound("Estado não encontrado.");
        }

        try {
            Ebean.delete(estado);
            Logger.info("Estado removido.");
        } catch (PersistenceException e) {
            Logger.error(e.getMessage());
            return badRequest("Existem Cidades que dependem deste Estado.");
        } catch (Exception e) {
            Logger.error(e.getMessage());
            return badRequest("Erro interno de sistema.");
        }

        return ok(Json.toJson(estado));
    }

    public Result filtra(String filtro) {
        Logger.info("Filtra Estado.");

        Query<Estado> query = Ebean.createQuery(Estado.class, "find estado where (nome like :nome or pais.nome like :paisNome)");
        query.setParameter("nome", "%" + filtro + "%");
        query.setParameter("paisNome", "%" + filtro + "%");
        List<Estado> filtroDeEstados = query.findList();

        return ok(Json.toJson(filtroDeEstados));
    }
}
