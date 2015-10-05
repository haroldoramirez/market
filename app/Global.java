import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.libs.F;
import play.mvc.Http;
import play.mvc.Result;

import static play.mvc.Results.notFound;

public class Global extends GlobalSettings {

    @Override
    public F.Promise<Result> onHandlerNotFound(Http.RequestHeader request) {
        return F.Promise.<Result> pure(notFound(views.html.paginaNaoEncontrada.render(request.uri())));
    }

    @Override
    public void onStart(Application app) {
        Logger.info("Application has started");
    }

    @Override
    public void onStop(Application app) {
        Logger.info("Application shutdown...");
    }
}
