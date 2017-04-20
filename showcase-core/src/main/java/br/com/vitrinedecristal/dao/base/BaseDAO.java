package br.com.vitrinedecristal.dao.base;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Transient;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.apache.log4j.Logger;

/**
 * Representação base para uma classe DAO (Data Access Object). Inclui as funcionalidades básicas.
 */
public abstract class BaseDAO<B extends Serializable, T extends IID<B>> implements IBaseDAO<B, T> {

	private static Logger logger = Logger.getLogger(BaseDAO.class);

	/**
	 * EntityManager - gerencia a entidade
	 */
	private EntityManager manager;

	/**
	 * Classe da entidade que será manipulada
	 */
	private Class<T> entityClass;

	/**
	 * Construtor básico para classe de acesso a dados (DAO)
	 * 
	 * @param manager - EntityManager
	 */
	@SuppressWarnings("unchecked")
	public BaseDAO(EntityManager manager) {
		if (manager == null) {
			throw new IllegalArgumentException("manager deve ser informado.");
		}

		this.manager = manager;
		this.entityClass = (Class<T>) TypeUtils.getRawType(BaseDAO.class.getTypeParameters()[1], this.getClass());
	}

	public EntityManager getEntityManager() {
		return this.manager;
	}

	/**
	 * Retorna o nome da classe da entidade que representa o DAO. Pode ser usado para criar as queries.
	 * 
	 * @return o nome da classe entidade.
	 */
	public String getEntityName() {
		return ClassUtils.getSimpleName(entityClass);
	}

	public T save(T t) {
		if (t == null) {
			throw new IllegalArgumentException("A entidade a ser salva deve ser informada.");
		}

		if (t.getId() == null) {
			this.getEntityManager().persist(t);
		} else {
			this.getEntityManager().merge(t);
		}

		return t;
	}

	public void remove(T t) {
		if (t == null) {
			throw new IllegalArgumentException("A entidade a ser excluída deve ser informada.");
		}

		if (t.getId() == null) {
			throw new IllegalArgumentException("O ID da entidade a ser excluída deve ser informada.");
		}

		t = this.getEntityManager().find(this.entityClass, t.getId());
		this.getEntityManager().remove(t);
	}

	public T findByPrimaryKey(B b) {
		return this.getEntityManager().find(entityClass, b);
	}

	public List<T> find() {
		return find(null, null, null);
	}

	public List<T> find(T filter) {
		return find(filter, null, null);
	};

	public List<T> find(int offset, int limit) {
		return find(null, offset, limit);
	}

	@SuppressWarnings("unchecked")
	public List<T> find(T filter, Integer offset, Integer limit) {
		Map<String, Object> parameters = getParameters(filter);
		String listQuery = getQuery(parameters, false);
		Query query = this.getEntityManager().createQuery(listQuery);

		setParameters(query, parameters);

		if (offset != null) {
			query.setFirstResult(offset);
		}

		if (limit != null) {
			query.setMaxResults(limit);
		}

		return query.getResultList();
	};

	public int count() {
		return count(null);
	}

	public int count(T filter) {
		Map<String, Object> parameters = getParameters(filter);
		String listQuery = getQuery(parameters, true);
		Query query = this.getEntityManager().createQuery(listQuery);

		setParameters(query, parameters);

		return ((Number) query.getSingleResult()).intValue();
	}

	/**
	 * Monta e retorna uma query para consulta com JPA. O map passado por parâmetro será usado para montar a condição <code>WHERE</code>.
	 * 
	 * @param parameters os parâmetros que serão usados na condição <code>WHERE</code>.
	 * @param count indica se a consulta da query será um <code>COUNT</code>.
	 * @return uma query.
	 * @throws IllegalArgumentException se o parâmetro for <code>null</code>.
	 */
	private String getQuery(Map<String, Object> parameters, boolean count) {
		if (parameters == null) {
			throw new IllegalArgumentException("O map com os parâmetros não deve ser null.");
		}

		StringBuffer query = new StringBuffer();
		List<String> conditions = new ArrayList<String>();

		query.append("SELECT ");
		query.append(count ? "COUNT(e) " : "e ");
		query.append("FROM " + entityClass.getSimpleName() + " e ");

		for (String name : parameters.keySet()) {
			conditions.add(name + " = :" + name);
		}

		if (!conditions.isEmpty()) {
			query.append("WHERE ");
			query.append(StringUtils.join(conditions, " AND "));
		}

		query.append(" ORDER BY e.id ASC");

		return query.toString();
	}

	/**
	 * Adiciona o nome de cada campo do objeto passado por parâmetro em um map, associando com o respectivo valor do campo. Só serão adicionados os campos que:
	 * <ul>
	 * <li>Não são estáticos.
	 * <li>O valor é diferente de <code>null</code>.
	 * <li>Não possuem a anotação {@link Transient}.
	 * </ul>
	 * 
	 * @param filter o objeto do qual os campos serão lidos. Se for <code>null</code> será retornado um {@link HashMap} vazio.
	 * @return um map com o nome dos campos e o respectivo valor.
	 */
	private Map<String, Object> getParameters(T filter) {
		Map<String, Object> parameters = new HashMap<String, Object>();

		if (filter == null) {
			return parameters;
		}

		for (Field field : filter.getClass().getDeclaredFields()) {
			if (Modifier.isStatic(field.getModifiers()) || field.isAnnotationPresent(Transient.class)) {
				continue;
			}

			try {
				String fieldName = field.getName();
				Object fieldValue = FieldUtils.readField(field, filter, true);

				if (fieldValue != null) {
					parameters.put(fieldName, fieldValue);
				}
			} catch (IllegalAccessException e) {
				logger.error("Não foi possível ler o valor do campo '" + field.getName() + "' de " + filter, e);
			}
		}

		return parameters;
	}

	private void setParameters(Query query, Map<String, Object> parameters) {
		if (parameters == null) {
			return;
		}

		for (String name : parameters.keySet()) {
			query.setParameter(name, parameters.get(name));
		}
	}

}
