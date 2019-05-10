/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.dao.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

import uk.co.xcordis.optimiser.dao.BaseDao;
import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.CassandraConnector;

/**
 * The <code>BaseDaoImpl</code> class responsible for implement the BaseDao method in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@Repository
@DependsOn(ApplicationConstants.APPLICATIONINITIALIZER_LABEL)
public class BaseDaoImpl<T> implements BaseDao<T> {

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseDaoImpl.class);

	@Autowired
	private CassandraConnector connector;

	private MappingManager manager;
	protected Mapper<T> mapper;
	protected String tableName;

	/**
	 * @return the connector
	 */
	public CassandraConnector getConnector() {

		return connector;
	}

	/**
	 * @return MappingManager
	 */
	protected MappingManager getManager() {

		return manager;
	}

	protected void setMappper() {

		// setMappper
	}

	@PostConstruct
	public void initIt() {

		LOGGER.info(" ==> Method : initIt ==> Enter");

		try {

			manager = new MappingManager(connector.getSession());
			this.setMappper();

		} catch (final Exception e) {
			LOGGER.error(ApplicationConstants.MARKER, " ==> Method : initIt ==> Exception : " + e);
		}

		LOGGER.info(" ==> Method : initIt ==> Exit");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.boson.dao.BaseDao#add(java.lang.Object)
	 */
	@Override
	public void add(final T t) {

		LOGGER.info(" ==> Method : add ==> Called");
		mapper.save(t);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.boson.dao.BaseDao#delete(java.lang.Object)
	 */
	@Override
	public void delete(final T t) {

		LOGGER.info(" ==> Method : delete ==> Called");
		mapper.delete(t);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.boson.dao.BaseDao#update(java.lang.Object)
	 */
	@Override
	public void update(final T t) {

		LOGGER.info(" ==> Method : update ==> Called");
		mapper.save(t);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.boson.dao.BaseDao#findById(java.lang.Object[])
	 */
	@Override
	public Object findById(final Object... objects) {

		LOGGER.info(" ==> Method : findById ==> Called");
		return mapper.get(objects);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.boson.dao.BaseDao#list()
	 */
	@Override
	public List<T> list() {

		LOGGER.info(" ==> Method : list ==> Enter");

		final ResultSet results = connector.getSession().execute("SELECT * FROM " + tableName);
		final List<T> list = mapper.map(results).all();

		LOGGER.info(" ==> Method : list ==> Exit");
		return list;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see uk.co.xcordis.ftl.dao.BaseDao#getCount(java.lang.Object)
	 */
	@Override
	public Long getCount(final String tableName) {

		LOGGER.info(" ==> Method : getCount ==> Enter");
		final ResultSet results = connector.getSession().execute("SELECT COUNT(*) FROM " + tableName);

		LOGGER.info(" ==> Method : getCount ==> Exit");
		return results.one().getLong(0);
	}

}