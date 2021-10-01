# JAsyncMail
 Java Async Mail System

 This project is a little utility API I wrote based on JavaMail used to send mail asynchronously in my web applications.
 For now it can only handle sending mail to a single recipient, using SMPT.

## Installation

 Download the JAR file and add it to your project libraries.  

## Usage

 The user will interact with the API through the following classes:
    - EmailSender
    - MailProperties
    - Mail

 The first step is to instantiate the properties that will be used to send the mail. This is done by calling the constructor of the MailProperties class.
 
 `MailProperties properties = new MailProperties(SENDER_EMAIL, SENDER_PASSWORD, "smtp.gmail.com", "587"); //only set once at the beginning of your application` 
 *Note: You need to instantiate the properties only once, before the EmailSender is instantiated.*

 The second step is to instantiate the EmailSender. This is done by calling its `getInstance()` method.
 
 `EmailSender emailSenderInstance = EmailSender.getInstance();`

 An email can be sent by calling the `sendEmail()` method of the EmailSender.
 
 `emailSenderInstance.sendEmail(new Mail(RECIPIENT_EMAIL, "sendingAnEmailTest subject", "sendingAnEmailTest Body"));`
