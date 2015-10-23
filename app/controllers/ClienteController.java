package controllers;

import actions.PlayAuthenticatedSecured;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.Query;
import models.Cidade;
import models.Cliente;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import javax.persistence.PersistenceException;
import java.util.Date;
import java.util.List;

@Security.Authenticated(PlayAuthenticatedSecured.class)
public class ClienteController extends Controller {

    public Result inserir() {

        Cliente cliente = Json.fromJson(request().body().asJson(), Cliente.class);

        Cliente clienteBuscaCpf = Ebean.find(Cliente.class).where().eq("cpf", cliente.getCpf()).findUnique();

        if (clienteBuscaCpf != null) {
            return badRequest("O Cliente já esta cadastrado");
        }

        Cidade cidade = Ebean.find(Cidade.class, cliente.getCidade().getId());

        cliente.setCidade(cidade);

        try {
            cliente.setDataCadastro(new Date());
            Ebean.save(cliente);
            Logger.info("Cliente salvo.");
        } catch (Exception e) {
            Logger.error(e.getMessage());
            return badRequest("Erro interno de sistema");
        }

        return created(Json.toJson(cliente));
    }

    public Result atualizar(Long id) {

        Cliente cliente = Json.fromJson(request().body().asJson(), Cliente.class);

        Cliente clienteBusca = Ebean.find(Cliente.class, id);

        if (clienteBusca == null) {
            return notFound("O Cliente não foi encontrado");
        }

        Cidade cidade = Ebean.find(Cidade.class, cliente.getCidade().getId());

        cliente.setCidade(cidade);


        try {
            cliente.setDataAlteracao(new Date());
            Ebean.update(cliente);
            Logger.info("Cliente atualizado.");
        } catch (Exception e) {
            Logger.error(e.getMessage());
            return badRequest("Erro interno de sistema");
        }

        return ok(Json.toJson(cliente));
    }

    public Result buscaPorId(Long id) {

        Cliente cliente = Ebean.find(Cliente.class, id);

        if (cliente == null) {
            Logger.warn("Cliente não encontrado.");
            return notFound("Cliente não Encontrado.");
        }

        return ok(Json.toJson(cliente));
    }

    public Result buscaTodos() {
        Logger.info("Busca todos os Clientes");

        return ok(Json.toJson(Ebean.find(Cliente.class)
                .order()
                .asc("nome")
                .findList()));
    }

    public Result remover(Long id) {

        Cliente cliente = Ebean.find(Cliente.class, id);

        if (cliente == null) {
            Logger.warn("Cliente não encontrado.");
            return notFound("Cliente não encontrado");
        }

        try {
            Ebean.delete(cliente);
            Logger.info("Cliente Removido.");
        } catch (PersistenceException e) {
            Logger.error(e.getMessage());
            return badRequest("Existem Vendas que dependem deste Cliente");
        } catch (Exception e) {
            Logger.error(e.getMessage());
            return badRequest("Erro interno de sistema");
        }

        return ok(Json.toJson(cliente));
    }

    public Result filtra(String filtro) {
        Logger.info("Filtra Cliente.");

        Query<Cliente> query = Ebean.createQuery(Cliente.class, "find cliente where (nome like :nome or cpf like :cpf)");
        query.setParameter("nome", "%" + filtro + "%");
        query.setParameter("cpf", "%" + filtro + "%");
        List<Cliente> filtroDeClientes = query.findList();
        return ok(Json.toJson(filtroDeClientes));
    }
}
