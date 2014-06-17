package controllers;

import static play.data.Form.form;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import models.Meta;
import models.MetaComparator;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import scala.Tuple2;

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
	public static String todosStatus(){
		List<Tuple2<Integer, Integer>> status = new ArrayList<>(5);
		List<List<Meta>> todasMetas = getMetas();

		Integer alcancadas;

		for (int i = 0; i < todasMetas.size(); i++) {
			alcancadas = 0;
			for (Meta meta : todasMetas.get(i)) {
				if (meta.isAlcancada()) {
					alcancadas++;
				}
			}
			status.add(new Tuple2<Integer, Integer>(todasMetas.get(i).size()
					- alcancadas, alcancadas));
		}
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(status);
		} catch (JsonProcessingException _) {
			return null;
		}
	}

	@Transactional
	public static List<List<Meta>> getMetas() {
		List<List<Meta>> metas = new ArrayList<>(5);

		for (int i = 0; i < 6; i++) {
			metas.add(new ArrayList<Meta>());
		}

		List<Meta> todasMetas = Application.getDao().findAllByClassName("Meta");

		Collections.sort(todasMetas, new MetaComparator());

		for (Meta meta : todasMetas) {
			metas.get(meta.getDuracao().getTipo()).add(meta);
		}

		return metas;
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
