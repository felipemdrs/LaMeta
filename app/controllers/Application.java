package controllers;

import static play.data.Form.form;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

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
	
	@Transactional
	public static Result index() {
/*
		Meta meta = null;

		try {
			meta = new Meta("Minha 1ª meta", Prioridade.ALTA,
					Calendar.getInstance());

			dao.persist(meta);
			dao.merge(meta);
			dao.flush();

		} catch (MetaInvalidaException e) {
		}*/

		List<Meta> me = dao.findAllByClassName("Meta");
		
		String ok = "";
		for (Meta meta2 : me) {
			ok += String.valueOf(meta2.getDescricao()) + " ";
		}
		System.out.println("ok "+ok);
		return ok(index.render(ok, signupForm));
	}
	
	public static Result submit() {
		return ok(index.render("ok", signupForm));
	}

	private static GenericDAO dao = new GenericDAOImpl();

}
