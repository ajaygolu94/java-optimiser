/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
package uk.co.xcordis.optimiser.dao;

import java.util.List;

/**
 * The <code>BaseDao</code> interface responsible for provide the generic method like (CRUD) to all child class in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
public interface BaseDao<T> {

	/**
	 * This method <code>add</code> performs the operation of adding all the data to the database throughout the application.
	 *
	 * @param t
	 */
	void add(T t);

	/**
	 * This method <code>findById</code> performs the operation of get the data from the database throughout the application.
	 *
	 * @param objects
	 */
	Object findById(Object... objects);

	/**
	 * This method <code>delete</code> performs the operation of deleting the data from the database throughout the application.
	 *
	 * @param t
	 */
	void delete(T t);

	/**
	 * This method <code>update</code> performs the operation of updating the data to the database throughout the application.
	 *
	 * @param t
	 */
	void update(T t);

	/**
	 * This method <code>list</code> performs the operation of getting all the data from database throughout the application.
	 *
	 * @return List<T>
	 */
	List<T> list();

	/**
	 *
	 * This <code>getCount</code> method is used to get count of records in table.
	 *
	 * @param t
	 * @return
	 */
	Long getCount(String tableName);

}