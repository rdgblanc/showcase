package br.com.vitrinedecristal.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;

import br.com.vitrinedecristal.vo.BaseVO;

public class ParserUtil {

	private static Logger logger = Logger.getLogger(ParserUtil.class);

	private static final DozerBeanMapper mapper = new DozerBeanMapper();

	/**
	 * Converte uma entidade para um VO.
	 * 
	 * @param object a entidade a ser convertida.
	 * @param voClass classe do VO
	 * @return o VO da entidade ou <tt>null</tt> caso não seja possível converter.
	 * @see {@link #getVO(List)}
	 */
	public static <T, V> V parse(T object, Class<V> voClass) {
		if (object == null) {
			return null;
		}

		V vo = null;

		try {
			vo = mapper.map(object, voClass);
		} catch (Exception e) {
			logger.error("Não foi possível converter o objeto " + ToStringBuilder.reflectionToString(object) + " para VO.", e);
		}

		return vo;
	}

	/**
	 * Converte uma lista de entidades para uma lista de VOs.
	 * 
	 * @param <T>
	 * @param <V>
	 * @param list a lista de entidades a serem convertidas.
	 * @return a lista de VOs.
	 * @see #getVO(T)
	 */
	public static <T, V> List<V> parse(List<T> list, Class<V> voClass) {
		List<V> listVO = new ArrayList<V>();

		if (list == null || list.isEmpty()) {
			return listVO;
		}

		for (T t : list) {
			V vo = ParserUtil.parse(t, voClass);

			if (vo != null) {
				listVO.add(vo);
			}
		}

		return listVO;
	}

	/**
	 * Converte uma lista de VOs para uma lista de entidades.
	 * 
	 * @param <T>
	 * @param listVO a lista de VOs a serem convertidos.
	 * @return a lista de entidades.
	 */
	public static <T> List<T> parse(List<? extends BaseVO<T>> listVO) {
		List<T> list = new ArrayList<T>();

		for (BaseVO<T> vo : listVO) {
			list.add(vo.parse());
		}

		return list;
	}

}
