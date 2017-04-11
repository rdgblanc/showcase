package br.com.vitrinedecristal.application;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Classe para inicializar os beans do módulo Business. Os beans no contexto do JPA são carregados do arquivo {@link #FILE_PATH}.
 */
public class ApplicationBeanFactory {

	/** Arquivo de configuração dos beans no contexto do JPA. */
	private static final String FILE_PATH = "applicationContext.xml";

	private static ApplicationContext context;

	/**
	 * Efetua o lookup do bean usando o contexto do JPA.
	 * 
	 * @param clazz classe do bean.
	 * @return instância do bean.
	 * @see BeanFactory#getBean(Class)
	 */
	public static <T> T getBean(Class<T> clazz) {
		if (context == null) {
			context = new ClassPathXmlApplicationContext(FILE_PATH);
		}
		return context.getBean(clazz);
	}

}
