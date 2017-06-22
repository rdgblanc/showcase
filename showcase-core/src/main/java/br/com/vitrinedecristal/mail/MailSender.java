package br.com.vitrinedecristal.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import br.com.vitrinedecristal.exception.BusinessException;

/**
 * Classe responsável pela implementação dos métodos básicos de envio de e-mail definidos em {@link IMailSender}.
 */
public class MailSender implements IMailSender {

	private static Logger logger = Logger.getLogger(MailSender.class);

	private Properties emailProperties = new Properties();

	private Session emailSession = Session.getDefaultInstance(emailProperties, new javax.mail.Authenticator() {
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication("upvitrinedecristal@gmail.com", "unibrasil");
		}
	});

	/**
	 * Inicializa o MailSender.
	 */
	public MailSender() {
		this.emailProperties.put("mail.smtp.host", "smtp.gmail.com");
		this.emailProperties.put("mail.smtp.socketFactory.port", "465");
		this.emailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		this.emailProperties.put("mail.smtp.auth", "true");
		this.emailProperties.put("mail.smtp.port", "465");
	}

	private boolean sendMail(String assunto, String emailDestino, String body) {
		try {
			logger.info("Enviando email para " + emailDestino);

			Message message = new MimeMessage(this.emailSession);
			// message.setFrom(new InternetAddress("from@no-spam.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailDestino));
			message.setText(body);

			Transport.send(message);
			logger.info("Email enviado com sucesso!");

			return true;
		} catch (Exception e) {
			logger.error("Não foi possível enviar o e-mail para: " + emailDestino, e);
			return false;
		}
	}

	@Override
	public boolean sendWelcomeMail(String name, String emailDestino, String token) throws BusinessException {
		String assunto = "Bem vindo ao Vitrine de Cristal";

		StringBuffer messageStr = new StringBuffer("Olá " + name + "!\n\n");
		messageStr.append("Seja bem vindo ao Vitrine de Cristal.\n");
		messageStr.append("Finalizando seu cadastro você poderá usufruir de todas as opções que o Vitrine oferece.\n\n");
		messageStr.append("Acesso o link abaixo para validar seu cadastro:\n");
		// messageStr.append("http://www.vitrinedecristal.com.br/#/token/welcome/" + token);
		messageStr.append("http://localhost:8080/showcase/#/token/welcome/" + token);

		return this.sendMail(assunto, emailDestino, messageStr.toString());
	}

	@Override
	public boolean sendNegotiationOwnerMail(String nomeInteressado, String nomeProprietario, String emailDestino, Long negotiationId) throws BusinessException {
		String assunto = "Demonstraram interesse em seu produto - Vitrine de Cristal";

		StringBuffer messageStr = new StringBuffer("Olá " + nomeProprietario + "!\n\n");
		messageStr.append("O " + nomeInteressado + " demonstrou interesse em seu produto e abriu uma negociação.\n");
		messageStr.append("Você pode respondê-lo aceitando ou recusando a proposta feita.\n\n");
		messageStr.append("Acesso o link abaixo para visualizar a negociação:\n");
		// messageStr.append("http://www.vitrinedecristal.com.br/#/negotiation/owner/" + negotiationId);
		messageStr.append("http://localhost:8080/showcase/#/negotiation/owner/" + negotiationId);

		return this.sendMail(assunto, emailDestino, messageStr.toString());
	}

	@Override
	public boolean sendRecoveryPasswordMail(String name, String emailDestino, String token) throws BusinessException {
		String assunto = "Recuperação de senha - Vitrine de Cristal";

		StringBuffer messageStr = new StringBuffer("Olá " + name + "!\n\n");
		messageStr.append("Vimos que solicitou a recuperação de sua senha.\n");
		messageStr.append("Será necessário informar uma nova senha para continuar a usufruir todos os serviçoes que o Vitrine oferece.\n\n");
		messageStr.append("Acesso o link abaixo para cadastrar sua nova senha:\n");
		// messageStr.append("http://www.vitrinedecristal.com.br/#/recovery/password/" + token);
		messageStr.append("http://localhost:8080/showcase/#/recovery/password/" + token);

		return this.sendMail(assunto, emailDestino, messageStr.toString());
	}

}
