package controllers;

import akka.util.Crypt;
import com.avaje.ebean.Ebean;
import models.Usuario;
import models.Usuarios;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;

public class LoginController extends Controller {


    private static DynamicForm form = Form.form();

    public Result loginTela() {
        String username = session().get("email");

        //busca o usuário atual que esteja logado no sistema
        Usuario usuarioAtual = Ebean.createQuery(Usuario.class, "find usuario where email = :email")
                .setParameter("email", username)
                .findUnique();

        if (usuarioAtual != null) {
            return ok(views.html.autenticado.render(username));
        }

        return ok(views.html.login.render(form));
    }

    public Result telaAutenticado() {
        String username = session().get("email");

        return ok(views.html.autenticado.render(username));
    }

    public Result telaLogout() {
        return ok(views.html.logout.render());
    }

    public Result autenticar() {

        String username = session().get("email");

        Form<DynamicForm.Dynamic> requestForm = form.bindFromRequest();

        String email = requestForm.data().get("email");
        String senha = requestForm.data().get("senha");

        F.Option<Usuario> talvesUmUsuario = Usuarios.existe(email, Crypt.sha1(senha));

        if (talvesUmUsuario.isDefined()) {
            session().put("email", talvesUmUsuario.get().getEmail());
            return redirect(routes.LoginController.telaAutenticado());
        }

        DynamicForm formDeErro = form.fill(requestForm.data());
        formDeErro.reject("O email ou senha não existem!");
        return forbidden(views.html.login.render(formDeErro));
    }

    public Result logout() {
        session().clear();
        return redirect(routes.LoginController.telaLogout());
    }
}
