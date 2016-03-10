package com.belk.core.utils;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public class SendEmail {

	private Map<String, String> outputOracleTablesCountMap;

	private Map<String, String> outputDb2TablesCountMap;

	SendEmail(Map<String, String> outputOracleTablesCountMap,
			Map<String, String> outputDb2TablesCountMap) {
		this.outputOracleTablesCountMap = outputOracleTablesCountMap;
		this.outputDb2TablesCountMap = outputDb2TablesCountMap;
	}

	public void sendEmail(String to, String cc, String from, String subject,
			String text, String smtpHost) throws Exception {
		try {
			Properties properties = new Properties();
			properties.setProperty("mail.smtp.host", smtpHost);
			Session emailSession = Session.getDefaultInstance(properties);

			Message emailMessage = new MimeMessage(emailSession);

			StringTokenizer multiTokenizer = new StringTokenizer(to, ",");

			List<String> arrEmails = new ArrayList<String>();
			while (multiTokenizer.hasMoreTokens()) {

				arrEmails.add(multiTokenizer.nextToken());
			}

			String[] mailAddressTo = arrEmails.toArray(new String[arrEmails
					.size()]);

			for (int i = 0; i < mailAddressTo.length; i++) {
				emailMessage.addRecipient(Message.RecipientType.TO,
						new InternetAddress(mailAddressTo[i]));
			}

			if (cc != null && !"".equals(cc)) {
				emailMessage.addRecipient(Message.RecipientType.CC,
						new InternetAddress(cc));
			}
			emailMessage.setFrom(new InternetAddress(from));
			emailMessage.setSubject(subject);
			//emailMessage.setText(getContent());
			
			emailMessage.setContent(getContent(), "text/html");

			emailSession.setDebug(true);

			Transport.send(emailMessage);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public String getContent() throws Exception {
		
		Properties p = new Properties();
		p.setProperty( "resource.loader", "class" );
		p.setProperty( "class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader" );

		Velocity.init(p);

		VelocityContext context = new VelocityContext();

		context.put("outputOracleTablesCountMap", outputOracleTablesCountMap);
		context.put("outputDb2TablesCountMap", outputDb2TablesCountMap);		
		

		Template template = Velocity.getTemplate("email_template.vm");
		StringWriter writer = new StringWriter();
		template.merge(context, writer);
		
		return writer.toString();

	}

}
