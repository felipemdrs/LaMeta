import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Calendar;

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
			meta = new Meta("Minha 1ª meta", Prioridade.ALTA,
					Calendar.getInstance());
		} catch (MetaInvalidaException e) {
			fail();
		}
	}

	@Test
	public void deveOcorrerExceptionSeAlgumParametroForNulo() {
		try {
			meta = new Meta(null, Prioridade.ALTA, Calendar.getInstance());
			fail();
		} catch (MetaInvalidaException e) {
			assertEquals("Parametro nulo", e.getMessage());
		}
		try {
			meta = new Meta("Minha 1ª meta", null, Calendar.getInstance());
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
	public void deveOcorrerExceptionSeDataForMaiorQueSeisSemanas() {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, 43);

			meta = new Meta("Minha 1ª meta", Prioridade.ALTA, calendar);
			fail();
		} catch (MetaInvalidaException e) {
			assertEquals("Data inválida", e.getMessage());
		}
	}

	@Test
	public void dataDeveEstarNormalizada() {
		try {
			meta = new Meta("Minha 1ª meta", Prioridade.ALTA,
					Calendar.getInstance());
		} catch (MetaInvalidaException e) {
			fail();
		}

		Calendar dataDeFinalizacao = meta.getDataDeFinalizacao();

		assertEquals(0, dataDeFinalizacao.get(Calendar.HOUR));
		assertEquals(0, dataDeFinalizacao.get(Calendar.MINUTE));
		assertEquals(0, dataDeFinalizacao.get(Calendar.SECOND));
		assertEquals(0, dataDeFinalizacao.get(Calendar.MILLISECOND));

		// Teste para garantir que o teste não está sendo executado às
		// 00:00:00:00

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR, 1);
		calendar.add(Calendar.MINUTE, 1);
		calendar.add(Calendar.SECOND, 1);
		calendar.add(Calendar.MILLISECOND, 1);

		try {
			meta = new Meta("Minha 1ª meta", Prioridade.ALTA, calendar);
		} catch (MetaInvalidaException e) {
			fail();
		}

		dataDeFinalizacao = meta.getDataDeFinalizacao();

		assertEquals(0, dataDeFinalizacao.get(Calendar.HOUR));
		assertEquals(0, dataDeFinalizacao.get(Calendar.MINUTE));
		assertEquals(0, dataDeFinalizacao.get(Calendar.SECOND));
		assertEquals(0, dataDeFinalizacao.get(Calendar.MILLISECOND));
	}

	@Test
	public void deveTrocarSeAlcancouOuNaoAMeta() {
		try {
			meta = new Meta("Minha 1ª meta", Prioridade.ALTA,
					Calendar.getInstance());
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
