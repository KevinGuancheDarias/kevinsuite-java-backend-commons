package com.kevinguanchedarias.kevinsuite.commons.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import com.kevinguanchedarias.kevinsuite.commons.entity.IEntity;

@Transactional
public abstract class CommonDao<E extends IEntity> {

	private E e;
	private Class<E> typeClass;
	private SessionFactory sessionFactory;

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Black magic constructor!
	 * 
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @author Kevin Guanche Darias
	 */
	public CommonDao(Class<E> sourceClass) throws InstantiationException, IllegalAccessException {
		typeClass = sourceClass;
		e = typeClass.newInstance();
	}

	/**
	 * Will return the left side of the MySQL LIMIT , "The start position"
	 * 
	 * @param page
	 * @param pageSize
	 * @author Kevin Guanche Darias
	 */
	private Integer getStartPointByPage(Integer page, Integer pageSize) {
		Integer validPage = page < 1 ? 1 : page;
		validPage--;
		return validPage * pageSize;
	}

	/**
	 * 
	 * @param start
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	private List<E> getByLimit(Integer start, Integer pageSize) {
		return getSession().createCriteria(typeClass).addOrder(Order.desc(e.getPrimaryKey())).setFirstResult(start)
				.setMaxResults(pageSize).list();
	}

	/**
	 * Will return the hibernate session (So you will can execute hibernate
	 * commands
	 * 
	 * @author Kevin Guanche Darias
	 */
	protected Session getSession() {
		return entityManager.unwrap(Session.class);
	}

	/**
	 * Will return object by primary key
	 * 
	 * @param id
	 * @return
	 * @author Kevin Guanche Darias
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public E getById(Object id) {
		Criteria crit = getSession().createCriteria(typeClass);

		crit.add(Restrictions.eqOrIsNull(e.getPrimaryKey(), id));

		return (E) crit.uniqueResult();
	}

	/**
	 * Will get all Notice: Sorts by primary key
	 * 
	 * @return
	 * @author Kevin Guanche Darias
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<E> getAll() {
		return getSession().createCriteria(typeClass).addOrder(Order.desc(e.getPrimaryKey())).list();
	}

	/**
	 * Will get by range, using primary key as sort
	 * 
	 * @param page
	 * @param pageSize
	 * @return
	 * @see https://websvn.kevinguanchedarias.com/filedetails.php?repname=homepage&path=%2Fajax%2Fblog%2Flib%2Fblogentry.class.php
	 * @author Kevin Guanche Darias
	 */
	public List<E> getUsingPage(Integer page, Integer pageSize) {
		Integer start = getStartPointByPage(page, pageSize);
		return getByLimit(start, pageSize);
	}

	/**
	 * Will return number of rows in the database
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Long countRows() {
		return (Long) getSession().createCriteria(typeClass).setProjection(Projections.rowCount()).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public E saveOrUpdate(E element) {
		return (E) getSession().merge(element);
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
