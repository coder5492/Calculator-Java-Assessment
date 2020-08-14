package com.sangeeth.calculator;

import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

public class ResultsMailer {
	private String emailSubject;
	private String emailText;
	private String receiverEmail;
	private String senderEmail;
	private String senderPassword;
	private String smtpHost;
	private String smtpPort;
	private ResultsFile resultsFile;
	static Logger log=Logger.getLogger(CalculatorApplication.class.getName());

	public ResultsMailer(ResultsFile resultsFile) {
		this.resultsFile = resultsFile;
	}
	
	public void mailResults(String receiverEmail) throws IOException, MessagingException {	
		this.setReceiverEmail(receiverEmail);
		this.getMailConfiguration();
		
		Properties properties = this.getSMTPProperties();
		Session session = this.getAuthenticationSession(properties);
		Message message = this.prepareMessage(session);
		
 		log.info("Preparing to send email....");

		try {
			Transport.send(message);
			log.info("Email Sent Successfully!!!!");
		} catch (MessagingException e) {
			log.error(e.getMessage());
			log.debug("Couldn't send Email");
			throw new MessagingException("Couldn't send Email. Please Check internet Connection");
		}
	}
	
	private void getMailConfiguration() throws IOException {
		Configuration config = new Configuration();
		config.getConfiguration();
		this.emailSubject = config.getEmailSubject();
		this.emailText    = config.getEmailText();
		this.senderEmail  = config.getEmailAddress();
		this.senderPassword = config.getPassword();
		this.smtpHost     = config.getHost();
		this.smtpPort     = config.getPort();
	}
	private Message prepareMessage(Session session) {
		Message message = new MimeMessage(session);
		message = this.setEmailFields(message);
		message = this.setTextandAttachment(message);
		return message;
	}
	
	private Message setEmailFields(Message message) {
		try {
			message.setFrom(new InternetAddress(this.senderEmail));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(this.receiverEmail));			
			message.setSubject(this.emailSubject);
			return message;
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Message setTextandAttachment(Message message) {
		Multipart multipart = new MimeMultipart();
        try {
        	MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText(this.emailText);
        	MimeBodyPart attachmentBodyPart= new MimeBodyPart();
            DataSource source = new FileDataSource(this.resultsFile.fileName); 
			attachmentBodyPart.setDataHandler(new DataHandler(source));
			attachmentBodyPart.setFileName(this.resultsFile.fileName); 
	        multipart.addBodyPart(attachmentBodyPart);
	        multipart.addBodyPart(textBodyPart);
	        message.setContent(multipart);
			return message;
		} catch (MessagingException e) {
			e.printStackTrace();
		}
        return null;
	}
	
	private Properties getSMTPProperties() {
		Properties properties = new Properties();	
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", this.smtpHost );
		properties.put("mail.smtp.port",this.smtpPort);
		return properties;
	}
	
	private Session getAuthenticationSession(Properties properties) {
		String myAccountEmail = this.senderEmail;
		String password = this.senderPassword;
		Session session = Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(myAccountEmail, password);
			}
		});
		return session;
	}
	
	private void setReceiverEmail(String receiverEmail) {
		this.receiverEmail = receiverEmail;
	}
}
