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

	public final static Form<Meta> SIGNUP_FORM = form(Meta.class);
	private static boolean criouMetasFake = false;

	@Transactional
	public static Result index() {

		if (!criouMetasFake) {
			criarMetasFake();
		}

		return ok(index.render(MetaController.getMetas(), MetaController.todosStatus(), SIGNUP_FORM));
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
		} catch (MetaInvalidaException _) {
		} finally {
			criouMetasFake = true;
		}
	}

	@Transactional
	private static void criaMeta(Meta meta) {
		dao.persist(meta);
		dao.flush();
	}

	private static GenericDAO dao = new GenericDAOImpl();

}
