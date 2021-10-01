package org.tom.jasyncmail.model;

import java.io.Serializable;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.tom.jasyncmail.properties.MailProperties;
import org.tom.jasyncmail.util.LoggerHelper;

/**
 *
 * @author Tommaso Ribaudo
 */
public class Mail implements Serializable {

  private static final long serialVersionUID = 1L;
  private final String to;
  private final String subject;
  private final String body;
  private int numberOfTries;

  public Mail(String to, String subject, String body) {
    this.to = to;
    this.subject = subject;
    this.body = body;
    this.numberOfTries = 0;
  }

  public MimeMessage getMimeMessage(Session session)
    throws AddressException, MessagingException {
    numberOfTries++;
    //LoggerHelper.printWithTime(Mail.class.getName() + " - Try #" + numberOfTries, true);
    MimeMessage message = new MimeMessage(session);
    message.setFrom(
      new InternetAddress(MailProperties.getInstance().getEmail())
    );
    message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
    message.setSubject(subject);
    message.setText(body);
    return message;
  }

  public String getTo() {
    return to;
  }

  public String getSubject() {
    return subject;
  }

  public String getBody() {
    return body;
  }

  public int getNumberOfTries() {
    return numberOfTries;
  }
}
