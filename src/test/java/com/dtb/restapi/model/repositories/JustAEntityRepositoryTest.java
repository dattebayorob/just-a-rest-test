package com.dtb.restapi.model.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dtb.restapi.model.entities.JustAEntity;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JustAEntityRepositoryTest {
	@Autowired
	private JustAEntityRepository repository;
	private static final Log log = LogFactory.getLog(JustAEntityRepositoryTest.class);
	private JustAEntity entity;

	@Before
	public void init() {
		log.info("Repository Test: Setup entity");
		entity = JustAEntity.builder()
				.name("Just a Name")
				.enabled(true).build();
		repository.save(entity);
	}

	@After
	public void finish() {
		log.info("Repository Test: TearDown");
		repository.deleteAll();
	}

	@Test
	public void testSave() {
		log.info("Test save");
		assertNotNull(entity.getId());
	}

	@Test
	public void testFindAll() {
		log.info("test findAll");
		assertTrue(repository.findAll(PageRequest.of(0, 10)).hasContent());
	}
	@Test
	public void testFindById() {
		log.info("test findById");
		assertEquals(repository.findById(Long.valueOf(entity.getId())).get().getId(), entity.getId());
	}
	@Test
	public void testFindByName() {
		log.info("test findByName");
		assertFalse(repository.findByName(entity.getName()).isEmpty());
	}
	@Test
	public void testFindByEnabled() {
		log.info("test findByEnabled");
		assertFalse(repository.findByEnabled(true).isEmpty());
	}
}
