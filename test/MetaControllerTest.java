import models.Meta;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;

import org.junit.Test;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.test.Helpers;

public class MetaControllerTest {

	private static GenericDAO dao = new GenericDAOImpl();

	@Test
	public void Test() {
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

	@Transactional
	private static void criaMeta(Meta meta) {
		dao.persist(meta);
		dao.flush();
	}

}
