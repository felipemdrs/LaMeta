import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.List;

import models.Duracao;
import models.Meta;
import models.Prioridade;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;

import org.junit.Before;
import org.junit.Test;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.test.Helpers;
import controllers.MetaController;

public class MetaControllerTest {

	private static GenericDAO dao = new GenericDAOImpl();

	/*
	@Test
	public void deveAdicionarUmaMetaAoBD() {
		Helpers.running(Helpers.fakeApplication(Helpers.inMemoryDatabase()),
				new Runnable() {
					public void run() {
						JPA.withTransaction(new play.libs.F.Callback0() {

							@Override
							public void invoke() throws Throwable {
							}

						});
					}
				});
	}
	*/
	
	private int i = 0;
	
	@Before
	public void setUp(){
		i = 0;
	}
	
	@Test
	public void deveAdicionarUmaMetaAoBD() {
		Helpers.running(Helpers.fakeApplication(Helpers.inMemoryDatabase()),
				new Runnable() {
					public void run() {
						JPA.withTransaction(new play.libs.F.Callback0() {

							@Override
							public void invoke() throws Throwable {
								assertEquals(0, MetaController.getMetas().get(0).size());
								
								criaMeta(new Meta("Minha " + String.valueOf(i++)
										+ "ª meta fake para meu projeto de SI1", Prioridade.BAIXA,
										Duracao.UMA_SEMANA));
								
								assertEquals(1, MetaController.getMetas().get(0).size());
								assertEquals(1, MetaController.getMetas().get(0).get(0).getId());
								
								criaMeta(new Meta("Minha " + String.valueOf(i++)
										+ "ª meta fake para meu projeto de SI1", Prioridade.BAIXA,
										Duracao.UMA_SEMANA));

								assertEquals(2, MetaController.getMetas().get(0).size());
								assertEquals(1, MetaController.getMetas().get(0).get(0).getId());
								assertEquals(2, MetaController.getMetas().get(0).get(1).getId());
							}

						});
					}
				});
	}
	
	@Test
	public void deveMarcarComoAlcancadaNoBD() {
		Helpers.running(Helpers.fakeApplication(Helpers.inMemoryDatabase()),
				new Runnable() {
					public void run() {
						JPA.withTransaction(new play.libs.F.Callback0() {

							@Override
							public void invoke() throws Throwable {
								criaMeta(new Meta("Minha " + String.valueOf(i++)
										+ "ª meta fake para meu projeto de SI1", Prioridade.BAIXA,
										Duracao.UMA_SEMANA));
								
								assertFalse(MetaController.getMetas().get(0).get(0).isAlcancada());
								MetaController.alcancada(1, true);
								assertTrue(MetaController.getMetas().get(0).get(0).isAlcancada());
								MetaController.alcancada(1, false);
								assertFalse(MetaController.getMetas().get(0).get(0).isAlcancada());
							}

						});
					}
				});
	}
	
	@Test
	public void deveOrganizarPorSemana() {
		Helpers.running(Helpers.fakeApplication(Helpers.inMemoryDatabase()),
			new Runnable() {
				public void run() {
					JPA.withTransaction(new play.libs.F.Callback0() {
						@Override
						public void invoke() throws Throwable {							
							criaMeta(new Meta("Minha " + String.valueOf(i++)
									+ "ª meta fake para meu projeto de SI1", Prioridade.BAIXA,
									Duracao.UMA_SEMANA));
							criaMeta(new Meta("Minha " + String.valueOf(i++)
									+ "ª meta fake para meu projeto de SI1", Prioridade.ALTA,
									Duracao.UMA_SEMANA));
							criaMeta(new Meta("Minha " + String.valueOf(i++)
									+ "ª meta fake para meu projeto de SI1", Prioridade.MEDIA,
									Duracao.UMA_SEMANA));
							criaMeta(new Meta("Minha " + String.valueOf(i++)
									+ "ª meta fake para meu projeto de SI1", Prioridade.ALTA,
									Duracao.UMA_SEMANA));
							
							List<List<Meta>> metasSemanais = MetaController.getMetas();
							
							Iterator<Meta> iterator = metasSemanais.get(0).iterator();
							Meta meta = iterator.next();
							assertEquals("Minha 1ª meta fake para meu projeto de SI1", meta.getDescricao());
							assertEquals(Prioridade.ALTA, meta.getPrioridade());
							meta = iterator.next();
							assertEquals("Minha 3ª meta fake para meu projeto de SI1", meta.getDescricao());
							assertEquals(Prioridade.ALTA, meta.getPrioridade());
							meta = iterator.next();
							assertEquals("Minha 2ª meta fake para meu projeto de SI1", meta.getDescricao());
							assertEquals(Prioridade.MEDIA, meta.getPrioridade());
							meta = iterator.next();
							assertEquals("Minha 0ª meta fake para meu projeto de SI1", meta.getDescricao());
							assertEquals(Prioridade.BAIXA, meta.getPrioridade());
						}
					});
				}
			});

	}
	
	@Test
	public void deveRemoverUmaMetaDoBD() {
		Helpers.running(Helpers.fakeApplication(Helpers.inMemoryDatabase()),
				new Runnable() {
					public void run() {
						JPA.withTransaction(new play.libs.F.Callback0() {

							@Override
							public void invoke() throws Throwable {
								criaMeta(new Meta("Minha " + String.valueOf(i++)
										+ "ª meta fake para meu projeto de SI1", Prioridade.BAIXA,
										Duracao.UMA_SEMANA));
								
								assertEquals(1, MetaController.getMetas().get(0).size());
								assertEquals(1, MetaController.getMetas().get(0).get(0).getId());
								
								MetaController.deletarMeta((long) 1);
								
								assertEquals(0, MetaController.getMetas().get(0).size());
							}

						});
					}
				});
	}

	@Transactional
	private static void criaMeta(Meta meta) {
		dao.persist(meta);
		dao.flush();
	}

}
