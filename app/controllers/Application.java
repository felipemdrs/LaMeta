package controllers;

import static play.data.Form.form;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import models.Duracao;
import models.Meta;
import models.Prioridade;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;
import models.exceptions.MetaInvalidaException;
import play.*;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {

	final static Form<Meta> signupForm = form(Meta.class);
	private static boolean criouMetasFake = false;

	@Transactional
	public static Result index() {
		/*
		 * Meta meta = null;
		 * 
		 * try { meta = new Meta("Minha 1ª meta", Prioridade.ALTA,
		 * Calendar.getInstance());
		 * 
		 * dao.persist(meta); dao.merge(meta); dao.flush();
		 * 
		 * } catch (MetaInvalidaException e) { }
		 */

		if (!criouMetasFake) {
			criarMetasFake();
		}

		List<Meta> metas = dao.findAllByClassName("Meta");
		
		System.out.println(metas.size());

		return ok(index.render(MetaController.getMetas(), signupForm));
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
					Duracao.TRES_SEMANAS));
			criaMeta(new Meta("Minha " + String.valueOf(i++)
					+ "ª meta fake para meu projeto de SI1", Prioridade.MEDIA,
					Duracao.TRES_SEMANAS));
			criaMeta(new Meta("Minha " + String.valueOf(i++)
					+ "ª meta fake para meu projeto de SI1", Prioridade.BAIXA,
					Duracao.QUATRO_SEMANAS));
			criaMeta(new Meta("Minha " + String.valueOf(i++)
					+ "ª meta fake para meu projeto de SI1", Prioridade.BAIXA,
					Duracao.CINCO_SEMANAS));
			criaMeta(new Meta("Minha " + String.valueOf(i++)
					+ "ª meta fake para meu projeto de SI1", Prioridade.ALTA,
					Duracao.SEIS_SEMANAS));
			System.out.println("Criando");
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
