import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.Date;

import models.Duracao;
import models.Meta;
import models.Prioridade;
import models.exceptions.MetaInvalidaException;

import org.junit.Before;
import org.junit.Test;

public class MetaTest {

	private Meta meta = null;

	@Before
	public void setUp() {
		meta = null;
	}

	@Test
	public void deveCriarUmaMeta() {
		try {
			meta = new Meta("Minha 1ª meta", Prioridade.ALTA, Duracao.UMA_SEMANA);
		} catch (MetaInvalidaException e) {
			fail();
		}
	}

	@Test
	public void deveOcorrerExceptionSeAlgumParametroForNulo() {
		try {
			meta = new Meta(null, Prioridade.ALTA, Duracao.UMA_SEMANA);
			fail();
		} catch (MetaInvalidaException e) {
			assertEquals("Parametro nulo", e.getMessage());
		}
		try {
			meta = new Meta("Minha 1ª meta", null, Duracao.UMA_SEMANA);
			fail();
		} catch (MetaInvalidaException e) {
			assertEquals("Parametro nulo", e.getMessage());
		}
		try {
			meta = new Meta("Minha 1ª meta", Prioridade.ALTA, null);
			fail();
		} catch (MetaInvalidaException e) {
			assertEquals("Parametro nulo", e.getMessage());
		}

		assertEquals(null, meta);
	}

	@Test
	public void deveTrocarSeAlcancouOuNaoAMeta() {
		try {
			meta = new Meta("Minha 1ª meta", Prioridade.ALTA, Duracao.UMA_SEMANA);
		} catch (MetaInvalidaException e) {
			fail();
		}

		assertFalse(meta.isAlcancada());
		meta.setAlcancada(true);
		assertTrue(meta.isAlcancada());
		meta.setAlcancada(false);
		assertFalse(meta.isAlcancada());
	}

}
