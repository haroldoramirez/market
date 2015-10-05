package controllers;

import actions.PlayAuthenticatedSecured;
import akka.util.Crypt;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.Query;
import models.Usuario;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import javax.persistence.PersistenceException;
import java.util.Date;
import java.util.List;

@Security.Authenticated(PlayAuthenticatedSecured.class)
public class UsuarioController extends Controller {

    public Result inserir() {

        String username = session().get("email");

        Usuario usuario = Json.fromJson(request().body().asJson(), Usuario.class);

        Usuario usuarioBusca = Ebean.find(Usuario.class).where().eq("email", usuario.getEmail()).findUnique();

        //busca o usuário atual que esteja logado no sistema
        Usuario usuarioAtual = Ebean.createQuery(Usuario.class, "find usuario where email = :email")
                .setParameter("email", username)
                .findUnique();

        //verificar se o usuario atual encontrado é administrador
        if (usuarioAtual.getPrivilegio() == 1) {
            return badRequest("Você '" + usuarioBusca.getEmail() + "' não tem privilégios de Administrador.");
        }

        if (usuarioBusca != null) {
            Logger.warn("Usuário já cadastrado.");
            return badRequest("O usuário '" + usuarioBusca.getEmail() + "' já esta cadastrado.");
        }

        try {
            String senha = Crypt.sha1(usuario.getSenha());
            usuario.setSenha(senha);
            usuario.setPadraoDoSistema(false);
            usuario.setDataCadastro(new Date());
            Ebean.save(usuario);
            Logger.info("Usuário salvo.");
        } catch (Exception e) {
            Logger.error(e.getMessage());
            return badRequest("Erro interno de sistema.");
        }

        return created(Json.toJson(usuario));
    }

    public Result atualizar(Long id) {

        String username = session().get("email");

        Usuario usuario = Json.fromJson(request().body().asJson(), Usuario.class);

        Usuario usuarioBusca = Ebean.find(Usuario.class, id);

        //busca o usuário atual que esteja logado no sistema
        Usuario usuarioAtual = Ebean.createQuery(Usuario.class, "find usuario where email = :email")
                .setParameter("email", username)
                .findUnique();

        //verificar se o usuario atual encontrado é administrador
        if (usuarioAtual.getPrivilegio() == 1) {
            return badRequest("Você não tem privilégios de Administrador.");
        }

        if (usuario.getPadraoDoSistema()) {
            return badRequest("Usuário padrão do sistema.");
        }

        if (usuarioBusca == null) {
            return badRequest("Usuário não encontrado.");
        }

        try {
            String senha = Crypt.sha1(usuario.getSenha());
            usuario.setSenha(senha);
            usuario.setDataAlteracao(new Date());
            Ebean.update(usuario);
            Logger.info("Usuario atualizado.");
        } catch (Exception e) {
            return badRequest("Erro interno de sistema");
        }

        return ok(Json.toJson(usuario));
    }

    public Result buscaPorId(Long id) {

        String username = session().get("email");

        //busca o usuário atual que esteja logado no sistema
        Usuario usuarioAtual = Ebean.createQuery(Usuario.class, "find usuario where email = :email")
                .setParameter("email", username)
                .findUnique();

        //verificar se o usuario atual encontrado é administrador
        if (usuarioAtual.getPrivilegio() == 1) {
            return badRequest("Você não tem privilégios de Administrador");
        }

        Usuario usuario = Ebean.find(Usuario.class, id);

        if (usuario == null) {
            Logger.warn("Usuário não encontrado.");
            return notFound("Usuário não encontrado.");
        }

        return ok(Json.toJson(usuario));
    }

    public Result buscaTodos() {

        Logger.info("Busca todos os Usuários.");

        String username = session().get("email");

        //busca o usuário atual que esteja logado no sistema
        Usuario usuarioAtual = Ebean.createQuery(Usuario.class, "find usuario where email = :email")
                .setParameter("email", username)
                .findUnique();

        //verificar se o usuario atual encontrado é administrador
        if (usuarioAtual.getPrivilegio() == 1) {
            return badRequest("Você não tem privilégios de Administrador.");
        }

        //busca todos os usuários menos o usuário padrão do sistema
        Query<Usuario> query = Ebean.createQuery(Usuario.class, "find usuario where (email != 'admin@market.com')");
        List<Usuario> filtroDeUsuarios = query.findList();

        return ok(Json.toJson(filtroDeUsuarios));
    }

    public Result remover(Long id) {

        String username = session().get("email");

        Usuario usuario = Ebean.find(Usuario.class, id);

        //busca o usuário atual que esteja logado no sistema
        Usuario usuarioAtual = Ebean.createQuery(Usuario.class, "find usuario where email = :email")
                .setParameter("email", username)
                .findUnique();

        //verificar se o usuario atual encontrado é administrador
        if (usuarioAtual.getPrivilegio() == 1) {
            return badRequest("Você não tem privilégios de Administrador");
        }

        if (usuario == null) {
            Logger.warn("Usuário não encontrado.");
            return notFound("Usuário não encontrado");
        }

        if (usuario.getPadraoDoSistema()) {
            return badRequest("Usuário padrão do sistema");
        }

        if (usuarioAtual.getEmail().equals(usuario.getEmail())) {
            return badRequest("Não excluir seu próprio usuário enquanto ele estiver autenticado.");
        }

        try {
            Ebean.delete(usuario);
            Logger.info("Usuário removido.");
        }  catch (PersistenceException e) {
            Logger.error(e.getMessage());
            return badRequest("Existem transações no sistema que depende deste usuário.");
        } catch (Exception e) {
            Logger.error(e.getMessage());
            return badRequest("Erro interno de sistema.");
        }

        return ok(Json.toJson(usuario));
    }

    public Result filtra(String filtro) {
        Logger.info("Filtra Usuário.");

        String username = session().get("email");

        //busca o usuário atual que esteja logado no sistema
        Usuario usuarioAtual = Ebean.createQuery(Usuario.class, "find usuario where email = :email")
                .setParameter("email", username)
                .findUnique();

        //verificar se o usuario atual encontrado é administrador
        if (usuarioAtual.getPrivilegio() == 1) {
            return badRequest("Você não tem privilégios de Administrador.");
        }

        Query<Usuario> query = Ebean.createQuery(Usuario.class, "find usuario where (email like :email)");
        query.setParameter("email", "%" + filtro + "%");
        List<Usuario> filtroDeUsuarios = query.findList();

        return ok(Json.toJson(filtroDeUsuarios));
    }
}
