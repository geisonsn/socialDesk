package br.edu.ifam.socialdesk.persistence;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;

import org.apache.commons.lang.StringUtils;

import br.gov.frameworkdemoiselle.DemoiselleException;
import br.gov.frameworkdemoiselle.annotation.Name;
import br.gov.frameworkdemoiselle.pagination.Pagination;
import br.gov.frameworkdemoiselle.pagination.PaginationContext;
import br.gov.frameworkdemoiselle.template.JPACrud;
import br.gov.frameworkdemoiselle.util.Beans;
import br.gov.frameworkdemoiselle.util.ResourceBundle;

public abstract class GenericDAO<T, I> extends JPACrud<T, I> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	@Name("demoiselle-jpa-bundle")
	private Instance<ResourceBundle> bundle;

	public void flush() {
		this.getEntityManager().flush();
	}

	/**
	 *
	 * @param jpql
	 * @param params
	 * @param classContext
	 *            - Representa o tipo da classe para realizar a busca do contexto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByJPQL(final String jpql, final Map<String, Object> params, final Class<?> classContext) {
		final Query query = this.createQuery(jpql);

		this.buildParams(params, query);

		this.executeCountPagination(jpql, params, classContext, query);
		return query.getResultList();
	}

	public T getByJPQL(final String jpql, final Map<String, Object> params) {

		final TypedQuery<T> query = this.getEntityManager().createQuery(jpql, this.getBeanClass());
		this.buildParams(params, query);

		return query.getSingleResult();
	}

	protected void executeCountPagination(final String jpql, final Map<String, Object> params, final Class<?> classContext,
			final Query query) {
		if (this.getPagination(classContext) != null) {
			final String jpqlCount = this.createCountQuery(jpql);
			final Query countQuery = this.getEntityManager().createQuery(jpqlCount);

			this.buildParams(params, countQuery);

			final Number cResults = (Number) countQuery.getSingleResult();
			this.getPagination(classContext).setTotalResults(cResults.intValue());
			query.setFirstResult(this.getPagination(classContext).getFirstResult());
			query.setMaxResults(this.getPagination(classContext).getPageSize());

		}
	}

	protected Pagination getPagination(final Class<?> classContext) {
		if (classContext == null) {
			return super.getPagination();
		} else {
			final PaginationContext context = Beans.getReference(PaginationContext.class);
			return context.getPagination(classContext, true);
		}
	}

	protected void buildParams(final Map<String, Object> params, final Query query) {
		if (params != null) {
			for (final Entry<String, Object> param : params.entrySet()) {

				if (param.getValue() instanceof Date) {
					query.setParameter(param.getKey(), (Date) param.getValue(), TemporalType.TIMESTAMP);
				}
				query.setParameter(param.getKey(), param.getValue());
			}
		}
	}

	protected String createCountQuery(final String query) {
		Matcher matcher = Pattern.compile("[Ss][Ee][Ll][Ee][Cc][Tt](.+)[Ff][Rr][Oo][Mm]").matcher(query);
		StringBuffer novaQuery = new StringBuffer();

		if (matcher.find()) {
			final String[] split = query.split(" ");

			if ("distinct".equalsIgnoreCase(split[1])) {
				split[1] = " COUNT(DISTINCT " + split[2] + ") ";
				split[2] = "";
			} else if ("new".equalsIgnoreCase(split[1])) {

				Matcher matcherEntreFrom = Pattern.compile("[Ff][Rr][Oo][Mm](.+)[Wh][Hh][Ee][Rr][Ee]").matcher(query);
				if (matcherEntreFrom.find()) {
					String[] splitFrom = matcherEntreFrom.group(1).split(" ");
					novaQuery.append("SELECT COUNT(" + splitFrom[2] + ") FROM");
				}

				Matcher matcherDepoisFrom = Pattern.compile("(.+)[Ff][Rr][Oo][Mm](.+)").matcher(query);
				if (matcherDepoisFrom.find()) {
					novaQuery.append(matcherDepoisFrom.group(2));
				}

			} else {
				split[1] = " COUNT(" + split[1] + ") ";
			}

			final String result;
			if (novaQuery.length() > 0) {
				result = novaQuery.toString();
			} else {
				result = StringUtils.join(split, " ");
			}

			matcher = Pattern.compile("(.+)[Oo][Rr][Dd][Ee][Rr](.+)").matcher(result);
			if (matcher.find()) {
				return matcher.group(1);
			}

			return result;
		} else {
			throw new DemoiselleException(this.bundle.get().getString("malformed-jpql"));
		}
	}

	protected TypedQuery<T> createNamedQuery(final String named) {
		return this.getEntityManager().createNamedQuery(named, this.getBeanClass());
	}

	protected List<T> findByNamedQuery(final String nameQuery, final Map<String, Object> params, final boolean paginate) {
		final TypedQuery<T> query = this.getEntityManager().createNamedQuery(nameQuery, this.getBeanClass());

		if (params != null) {
			for (final Entry<String, Object> value : params.entrySet()) {
				query.setParameter(value.getKey(), value.getValue());
			}
		}

		if (paginate) {
			this.buildPagination(query);
		}
		return query.getResultList();
	}

	protected List<T> findByNamedQuery(final String nameQuery, final Map<String, Object> params) {
		return this.findByNamedQuery(nameQuery, params, false);
	}

	protected List<T> findByNamedQuery(final String nameQuery, final Map<String, Object> params, final Integer maxResult) {
		final TypedQuery<T> query = this.createNamedQuery(nameQuery);
		this.buildParams(params, query);
		query.setMaxResults(maxResult.intValue());

		return query.getResultList();
	}

	protected T getByNamedQuery(final String nameQuery, final Map<String, Object> params) {
		final TypedQuery<T> query = this.createNamedQuery(nameQuery);
		this.buildParams(params, query);
		return query.getSingleResult();
	}

	protected T getByNamedQuery(final String nameQuery) {
		return this.getByNamedQuery(nameQuery, null);
	}

	private void buildPagination(final TypedQuery<T> typedQuery) {
		if (this.getPagination() != null) {
			this.getPagination().setTotalResults(typedQuery.getResultList().size() + 0);
			typedQuery.setFirstResult(this.getPagination().getFirstResult());
			typedQuery.setMaxResults(this.getPagination().getPageSize());
		}
	}

	public List<T> findByJPQL(final String jpql, final Map<String, Object> params) {
		return this.findByJPQL(jpql, params, null);
	}

	public List<T> findByJPQLComPaginacao(final String jpql, final Map<String, Object> params) {
		return this.findByJPQL(jpql, params, super.getBeanClass());
	}

	protected List<T> findBy(final CriteriaQuery<T> criteriaQuery, final List<Predicate> predicates) {

		return this.findBy(criteriaQuery, predicates, true);
	}

	protected List<T> findBy(final CriteriaQuery<T> criteriaQuery, final List<Predicate> predicates, boolean paginate) {

		final Predicate[] arrayPrecidates = predicates.toArray(new Predicate[0]);

		criteriaQuery.where(arrayPrecidates);

		if (paginate) {
			return this.findByCriteriaQuery(criteriaQuery);
		} else {
			return getEntityManager().createQuery(criteriaQuery).getResultList();
		}

	}

	/**
	 * Atualiza a entidade a partir dos atributos não nulos
	 * 
	 * <p>
	 * Este método atualiza a entidade {@code entity} a partir dos atributos não nulos.
	 * 
	 * @param entity
	 *            entidade contendo os atributos a serem atualizados
	 */
	public void updateNotNullAttributes(T entity) {

		Object entityIdValue = this.getEntityIdValue(entity);
		String entityIdName = this.getEntityIdName();

		Map<String, Object> updateParams = this.getUpdateParams(entity);

		if (updateParams.isEmpty()) {
			return;
		}

		StringBuilder sql = new StringBuilder("UPDATE " + this.getEntityName() + " SET ");

		for (String key : updateParams.keySet()) {
			sql.append(key + " = :" + key + ",");
		}

		sql.delete(sql.length() - 1, sql.length());// remove vírgula excedente

		sql.append(" WHERE " + entityIdName + " = :" + entityIdName);
		updateParams.put(entityIdName, entityIdValue);

		Query query = getEntityManager().createQuery(sql.toString());

		for (Entry<String, Object> param : updateParams.entrySet()) {
			query.setParameter(param.getKey(), param.getValue());
		}

		query.executeUpdate();
	}

	/**
	 * Retorna um mapa ordenado contendo os atributos a serem atualizados
	 * 
	 * @param entity
	 * @return mapa de atributos a serem atualizados ordenados
	 */
	private Map<String, Object> getUpdateParams(T entity) {
		Map<String, Object> params = new HashMap<>();
		final Field[] fields = entity.getClass().getDeclaredFields();

		for (Field field : fields) {

			if (field.isAnnotationPresent(Id.class))
				continue;// não adiciona a chave primária entre os campos a sem atualizados

			if (!field.isAnnotationPresent(Column.class) && !field.isAnnotationPresent(Basic.class)
					&& !field.isAnnotationPresent(Enumerated.class) && !field.isAnnotationPresent(JoinColumn.class)) {
				continue;
			}

			String param = null;
			Object value = null;

			try {
				field.setAccessible(true);
				param = field.getName();
				value = field.get(entity);
			} catch (IllegalArgumentException e) {
				continue;
			} catch (IllegalAccessException e) {
				continue;
			}

			if (value == null) {
				continue;
			}

			params.put(param, value);
		}

		return params;
	}

	/**
	 * Retorna o nome do atributo anotado com {@link Id}
	 * 
	 * @return nome do atributo chave da entidade
	 */
	private String getEntityIdName() {
		Field[] declaredFields = getBeanClass().getDeclaredFields();
		for (Field field : declaredFields) {
			if (!field.isAnnotationPresent(Id.class)) {
				continue;
			}
			field.setAccessible(true);
			return field.getName();
		}

		throw new IllegalStateException("Entidade sem atributo chave anotado com @Id");
	}

	/**
	 * Retorna o valor do atributo anotado com {@link Id}
	 * 
	 * @return nome do atributo chave da entidade
	 */
	private Object getEntityIdValue(T entity) {
		Field[] declaredFields = getBeanClass().getDeclaredFields();
		String name = null;
		Object value = null;
		boolean idNotFound = true;
		for (Field field : declaredFields) {
			if (!field.isAnnotationPresent(Id.class)) {
				continue;
			}
			field.setAccessible(true);
			try {
				name = field.getName();
				value = field.get(entity);
				idNotFound = false;
			} catch (IllegalArgumentException | IllegalAccessException e) {
			}
		}

		if (idNotFound) {
			throw new IllegalStateException("Entidade sem atributo chave anotado com @Id");
		}

		if (value == null) {
			throw new IllegalStateException(new StringBuilder("Atributo [" + name + "] usado no ")
					.append("critério de atualização não pode ser nulo").toString());
		}

		return value;
	}

	/**
	 * Retorna o nome da entidade
	 * 
	 * @return nome da entidade
	 */
	private String getEntityName() {
		Entity entityAnnotation = getBeanClass().getAnnotation(Entity.class);
		String entityName = null;
		if (entityAnnotation != null && entityAnnotation.name() != null && !entityAnnotation.name().trim().equals("")) {
			entityName = entityAnnotation.name();
		} else {
			entityName = getBeanClass().getSimpleName();
		}
		return entityName;
	}

}
