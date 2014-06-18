package controllers;

import static play.data.Form.form;

import java.util.Collections;
import java.util.List;

import models.Meta;
import models.MetaComparator;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MetaController extends Controller {

	final static Form<Meta> metaForm = form(Meta.class);

	public static Result index() {
		return redirect(controllers.routes.Application.index());
	}

	@Transactional
	public static Result nova() {
		Form<Meta> metaFormRequest = metaForm.bindFromRequest();

		if (metaFormRequest.hasErrors()) {
			return redirect(controllers.routes.Application.index());
		} else {
			Meta novaMeta = metaFormRequest.get();
			Application.getDao().persist(novaMeta);
			Application.getDao().merge(novaMeta);
			Application.getDao().flush();
			return redirect(controllers.routes.Application.index());
		}
	}
	
	@Transactional
	public static Result getMetasPorSemana(int num) {
		List<Meta> metas = Application.getDao().findByAttributeName("Meta", "duracao", String.valueOf(num));

		Collections.sort(metas, new MetaComparator());

		ObjectMapper mapper = new ObjectMapper();
		try {
			return ok(mapper.writeValueAsString(metas));
		} catch (JsonProcessingException _) {
			return badRequest();
		}
	}

	@Transactional
	public static Result alcancada(long id, boolean alcancada) {
		Meta meta = Application.getDao().findByEntityId(Meta.class, id);

		meta.setAlcancada(alcancada);
		Application.getDao().persist(meta);
		Application.getDao().flush();
		return ok();
	}

	@Transactional
	public static Result deletarMeta(Long id) {
		Application.getDao().removeById(Meta.class, id);
		return ok();
	}
}
