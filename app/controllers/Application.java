package controllers;

import static play.data.Form.form;
import models.Duracao;
import models.Meta;
import models.Prioridade;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;
import models.exceptions.MetaInvalidaException;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {

	private final static Form<Meta> META_FORM = form(Meta.class);
	private static GenericDAO dao = new GenericDAOImpl();
	private static boolean criouMetasFake = false;

	@Transactional
	public static Result index() {

		if (!criouMetasFake) {
			criarMetasFake();
		}

		return ok(index.render(MetaController.getMetas(), MetaController.todosStatus(), META_FORM));
	}

	private static void criarMetasFake() {
		try {
			int i = 0;

			criaMeta(new Meta("Minha " + String.valueOf(i++)
					+ "ª meta fake para meu projeto de SI1", Prioridade.ALTA,
					Duracao.UMA_SEMANA));
			criaMeta(new Meta("Minha " + String.valueOf(i++)
					+ "ª meta fake para meu projeto de SI1", Prioridade.BAIXA,
					Duracao.UMA_SEMANA));
			criaMeta(new Meta("Minha " + String.valueOf(i++)
					+ "ª meta fake para meu projeto de SI1", Prioridade.MEDIA,
					Duracao.UMA_SEMANA));
			criaMeta(new Meta("Minha " + String.valueOf(i++)
					+ "ª meta fake para meu projeto de SI1", Prioridade.BAIXA,
					Duracao.DUAS_SEMANAS));
			criaMeta(new Meta("Minha " + String.valueOf(i++)
					+ "ª meta fake para meu projeto de SI1", Prioridade.MEDIA,
					Duracao.DUAS_SEMANAS));
			criaMeta(new Meta("Minha " + String.valueOf(i++)
					+ "ª meta fake para meu projeto de SI1", Prioridade.ALTA,
					Duracao.DUAS_SEMANAS));
			criaMeta(new Meta("Minha " + String.valueOf(i++)
					+ "ª meta fake para meu projeto de SI1", Prioridade.MEDIA,
					Duracao.DUAS_SEMANAS));
			criaMeta(new Meta("Minha " + String.valueOf(i++)
					+ "ª meta fake para meu projeto de SI1", Prioridade.BAIXA,
					Duracao.TRES_SEMANAS));
			criaMeta(new Meta("Minha " + String.valueOf(i++)
					+ "ª meta fake para meu projeto de SI1", Prioridade.BAIXA,
					Duracao.TRES_SEMANAS));
			criaMeta(new Meta("Minha " + String.valueOf(i++)
					+ "ª meta fake para meu projeto de SI1", Prioridade.ALTA,
					Duracao.TRES_SEMANAS));
			criouMetasFake = true;
		} catch (MetaInvalidaException _) {
		}
	}

	@Transactional
	private static void criaMeta(Meta meta) {
		dao.persist(meta);
		dao.flush();
	}
	
	public static GenericDAO getDao(){
		return dao;
	}
}
