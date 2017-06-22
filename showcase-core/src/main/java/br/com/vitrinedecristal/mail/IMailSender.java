package br.com.vitrinedecristal.mail;

import br.com.vitrinedecristal.exception.BusinessException;

/**
 * Interface com os métodos básicos para envio de e-mail.
 */
public interface IMailSender {

	boolean sendWelcomeMail(String name, String emailDestino, String token) throws BusinessException;

	boolean sendNegotiationOwnerMail(String nomeInteressado, String nomeProprietario, String emailDestino, Long negotiationId) throws BusinessException;

	boolean sendRecoveryPasswordMail(String name, String emailDestino, String token) throws BusinessException;

}