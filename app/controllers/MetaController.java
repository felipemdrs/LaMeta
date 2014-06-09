package controllers;

import static play.data.Form.form;
import models.Meta;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

public class MetaController extends Controller {

	private static GenericDAO dao = new GenericDAOImpl();
	final static Form<Meta> metaForm = form(Meta.class);

	@Transactional
	public static Result nova() {
		Form<Meta> loginForm = metaForm.bindFromRequest();

		if (loginForm.hasErrors()) {
			/* Debug
	            java.util.Map<String, List<play.data.validation.ValidationError>> errorsAll = loginForm.errors();
	            for (String field : errorsAll.keySet()) {
	                errorMsg += field + " ";
	                for (ValidationError error : errorsAll.get(field)) {
	                    errorMsg += error.message() + ", ";
	                }
	            }
				return badRequest(index.render(errorMsg, metaForm));
			 */
			return badRequest();
		} else {
			Meta novaMeta = loginForm.get();
			dao.persist(novaMeta);
			dao.merge(novaMeta);
			dao.flush();
			return ok();
		}
	}

	public static void alcancada(long id) {
		mudarStatusDaMeta(true, id);
	}

	public static void naoAlcancada(long id) {
		mudarStatusDaMeta(false, id);
	}

	@Transactional
	private static void mudarStatusDaMeta(boolean foiAlcancada, long id) {
		Meta meta = dao.findByEntityId(Meta.class, id);

		if (alterarStatus(meta, foiAlcancada)) {
			meta.setAlcancada(foiAlcancada);
			dao.persist(meta);
			dao.flush();
		}
	}

	private static boolean alterarStatus(Meta meta, boolean foiAlcancada) {
		return (meta != null && !(meta.isAlcancada() && foiAlcancada));
	}
}
