package br.com.vitrinedecristal.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação utilizada para excluir o compo anotado do parser de um VO para uma entidade. Assim campos exclusivos do VO são desconsiderados no parse.
 */
@Target({
		ElementType.FIELD
})
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreVOParser
{
}
