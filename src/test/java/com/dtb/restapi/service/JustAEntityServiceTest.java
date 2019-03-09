package com.dtb.restapi.service;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import com.dtb.restapi.model.entities.JustAEntity;
import com.dtb.restapi.model.repositories.JustAEntityRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JustAEntityServiceTest {
	@Autowired
	private JustAEntityService service;
	@MockBean
	private JustAEntityRepository repository;

	private JustAEntity entity;
	private static final Log log = LogFactory.getLog(JustAEntityServiceTest.class);

	@Before
	public void init() {
		log.info("Repository Test: Setup entity");
		entity = new JustAEntity();
		entity.setId(Long.valueOf(1));
		entity.setName("Just a Name");
		entity.setEnabled(true);
		List<JustAEntity> entities = new ArrayList<JustAEntity>() {
			{
				add(entity);
			}
		};
		BDDMockito.given(repository.findAll(Mockito.any(Pageable.class))).willReturn(new PageImpl<>(entities));
		BDDMockito.given(repository.save(Mockito.any(JustAEntity.class))).willReturn(entity);
		BDDMockito.given(repository.findById(Mockito.anyLong())).willReturn(Optional.of(entity));
	}

	@Test
	public void testFindAll() {
		assertTrue(service.findAll(PageRequest.of(0, 10)).hasContent());
	}

	@Test
	public void testFindById() {
		assertTrue(service.findById(Long.valueOf(1)).isPresent());
	}

}
