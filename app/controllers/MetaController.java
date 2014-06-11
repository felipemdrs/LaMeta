package controllers;

import static play.data.Form.form;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import models.Meta;
import models.MetaComparator;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;
import play.data.Form;
import play.data.validation.ValidationError;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class MetaController extends Controller {

	private static GenericDAO dao = new GenericDAOImpl();
	final static Form<Meta> metaForm = form(Meta.class);

	
	public static Result index(){
		return redirect(controllers.routes.Application.index()); 
	}
	
	@Transactional
	public static Result nova() {
		Form<Meta> loginForm = metaForm.bindFromRequest();

		if (loginForm.hasErrors()) {
			/* Debug */

			String errorMsg = "";
			java.util.Map<String, List<play.data.validation.ValidationError>> errorsAll = loginForm
					.errors();
			for (String field : errorsAll.keySet()) {
				errorMsg += field + " ";
				for (ValidationError error : errorsAll.get(field)) {
					errorMsg += error.message() + ", ";
				}
			}

			System.err.println("Erro no formulário: " + errorMsg);

			return badRequest(index.render(getMetas(), Application.SIGNUP_FORM));
		} else {
			Meta novaMeta = loginForm.get();
			dao.persist(novaMeta);
			dao.merge(novaMeta);
			dao.flush();
			return ok(index.render(getMetas(), Application.SIGNUP_FORM));
		}
	}

	@Transactional
	public static List<List<Meta>> getMetas() {
		List<List<Meta>> metas = new ArrayList<>(5);

		for (int i = 0; i < 6; i++) {
			metas.add(new ArrayList<Meta>());
		}

		List<Meta> todasMetas = dao.findAllByClassName("Meta");

		Collections.sort(todasMetas, new MetaComparator());

		for (Meta meta : todasMetas) {
			metas.get(meta.getDuracao().getTipo()).add(meta);
		}

		return metas;
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

	@Transactional
	public static Result deletarMeta(Long id) {
		System.out.println("pegaa");
			System.out.println("pegoi");
			System.out.println(id);
			System.out.println("du "+ dao.findByEntityId(Meta.class, id).getDuracao());
			dao.removeById(Meta.class, id);
			return ok();
	}
}
