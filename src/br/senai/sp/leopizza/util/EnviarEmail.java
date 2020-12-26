package br.senai.sp.leopizza.util;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class EnviarEmail implements Runnable {

	Email email;
	private String msg;
	private String cliente;
	private String subject;

	public EnviarEmail(String subject, String msg, String cliente) {
		this.msg = msg;
		this.cliente = cliente;
		this.subject = subject;

	}

	@Override
	public void run() {
		email = new SimpleEmail();
		email.setHostName("smtp.mail.yahoo.com");
		email.setStartTLSEnabled(true);
		email.setSmtpPort(587);

		// email.setDebug(true);
		email.setAuthentication("marcopc_1@yahoo.com.br", "fwwuxjahcslqtwls");

		try {
			email.setFrom("marcopc_1@yahoo.com.br");
			email.setSubject(subject);
			email.setMsg(msg);
			email.addTo(cliente);
			email.send();
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
