package com.email;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {

	public static void main(String[] args) {
		
		
		if(args[0] == null)
	    {
	        System.out.println("Proper Usage is: SendEmail args[0] args[1]");
	        System.exit(0);
	    }

		SendEmail emailsender = new SendEmail();

		Properties prop = emailsender.loadProperties();

		String mailSmtpHost = prop.getProperty("mailSmtpHost");

		String mailTo = prop.getProperty("mailTo");
		String mailCc = prop.getProperty("mailCc");
		String mailFrom = prop.getProperty("mailFrom");

		String hostname = args[1];
		
		String env="";
		
		
		if(hostname!=null && !"".equals(hostname)) {
			
			if(hostname.toUpperCase().contains("SIT02")) {
				env= "SIT";
			} else if(hostname.toUpperCase().contains("DV")) { 
				env= "DEV";
			} else if(hostname.toUpperCase().contains("PROD")) { 
				env= "PROD";
			}
		}
		
		if ("1".equals(args[0])) {	

			String mailSubject = env + "- Wesphere portal server started";
			String mailText = emailsender.getMailContentForStart(hostname);

			emailsender.sendEmail(mailTo, mailCc, mailFrom, mailSubject,
					mailText, mailSmtpHost);

		} else if ("0".equals(args[0])) {

			String mailSubject = env
					+ "- Wesphere portal server has stopped";
			String mailText = emailsender.getMailContentForStop(hostname);

			emailsender.sendEmail(mailTo, mailCc, mailFrom, mailSubject,
					mailText, mailSmtpHost);
		}

	}

	/*private Properties loadProperties() {

		Properties props = new Properties();
		InputStream input = null;

		try {

			InputStream stream = getClass().getClassLoader().getResourceAsStream("email.properties");

			// load a properties file
			props.load(stream);
		
			// get the property value and print it out
			System.out.println(" mailSmtpHost:" + props.getProperty("mailSmtpHost"));
			System.out.println(" mailTo:" + props.getProperty("mailTo"));
			System.out.println(" mailCc:" +  props.getProperty("mailCc"));
			System.out.println(" mailFrom:" +  props.getProperty("mailFrom"));

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return props;
	}*/
	
	private Properties loadProperties() {

		//to load application's properties, we use this class
	    Properties emailProperties = new Properties();

	    File jarPath=null;


		try {
			
			
			jarPath=new File(SendEmail.class.getProtectionDomain().getCodeSource().getLocation().getPath());
	        String propertiesPath=jarPath.getParentFile().getAbsolutePath();
	        System.out.println(" propertiesPath=>"+propertiesPath);
	        emailProperties.load(new FileInputStream(propertiesPath+"/email.properties"));		  

		    System.out.println(" mailSmtpHost:" + emailProperties.getProperty("mailSmtpHost"));
			System.out.println(" mailTo:" + emailProperties.getProperty("mailTo"));
			System.out.println(" mailCc:" +  emailProperties.getProperty("mailCc"));
			System.out.println(" mailFrom:" +  emailProperties.getProperty("mailFrom"));


		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			
		}

		return emailProperties;
	}

	public void sendEmail(String to, String cc, String from, String subject,
			String text, String smtpHost) {
		try {
			Properties properties = new Properties();
			properties.setProperty("mail.smtp.host", smtpHost);
			Session emailSession = Session.getDefaultInstance(properties);

			Message emailMessage = new MimeMessage(emailSession);
			
			StringTokenizer multiTokenizer = new StringTokenizer(to,",");
			
			List<String> arrEmails = new ArrayList<String>();			
			while (multiTokenizer.hasMoreTokens())
			{
			   	    
			    arrEmails.add(multiTokenizer.nextToken());
			}
			
			String[] mailAddressTo= arrEmails.toArray(new String[arrEmails.size()]);			
			
			
	        for(int i=0;i<mailAddressTo.length;i++){	             
	             emailMessage.addRecipient(Message.RecipientType.TO,
	 					new InternetAddress(mailAddressTo[i]));
	        }
			
			
			if(cc !=null && !"".equals(cc)) {
				emailMessage.addRecipient(Message.RecipientType.CC, new  InternetAddress(cc));
			}
			emailMessage.setFrom(new InternetAddress(from));
			emailMessage.setSubject(subject);
			emailMessage.setText(text);

			emailSession.setDebug(true);

			Transport.send(emailMessage);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	
	private String getMailContentForStart(String hostname) {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("\n");
		sb.append("Hostname : " + hostname);
		sb.append("\n");
		sb.append("Server WebSphere_Portal started and open for e-business" );
		sb.append("\n");
		
		return sb.toString();
		
	}
	
	
	private String getMailContentForStop(String hostname) {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("\n");
		sb.append("Hostname : " + hostname);
		sb.append("\n");
		sb.append("Server WebSphere_Portal has stopped." );
		sb.append("\n");
		
		return sb.toString();
		
	}
}
