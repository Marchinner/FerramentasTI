package utilz;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.*;

public class Email {
    private String sex;
    private String username;
    private String userPassword;
    private String userPrinters;
    private String userPin;
    private boolean hasJrti;
    private String jrtiUsername;
    private String jrtiPassword;
    private boolean hasSysemp;
    private String sysempUsername;
    private String sysempPassword;
    private String newUserEmail;
    private String newUserSenderPassword;
    private String password;
    private Properties properties = new Properties();
    private String cC = "emailcc@email.com";
    private String itemNumber;
    private String itemActualPlace;
    private String itemNewPlace;
    private String itemPlace;
    private String itemDescription;
    private final String FROM = "emailfrom@email.com";
    private final String USERNAME = "emailusername@email.com";
    private final String HOST = "smtp.server.com";
    private final Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
        protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
            return new javax.mail.PasswordAuthentication(USERNAME, password);
        }
    });

    public Email(String password, String itemNumber, String itemActualPlace, String itemNewPlace, String itemDescription) {
        this.password = password;
        this.itemNumber = itemNumber;
        this.itemActualPlace = itemActualPlace;
        this.itemNewPlace = itemNewPlace;
        this.itemDescription = itemDescription;
    }

    public Email(String password, String itemNumber, String itemPlace, String itemDescription) {
        this.password = password;
        this.itemNumber = itemNumber;
        this.itemPlace = itemPlace;
        this.itemDescription = itemDescription;
    }

    public Email(String newUserSenderPassword, String newUserEmail, String sex, String username, String userPassword,
                 String userPrinters,
                 String userPin) {
        this.newUserSenderPassword = newUserSenderPassword;
        this.newUserEmail = newUserEmail;
        this.sex = sex;
        this.username = username;
        this.userPassword = userPassword;
        this.userPrinters = userPrinters;
        this.userPin = userPin;
        this.password = newUserSenderPassword;
    }

    public Email(String sex,String username, String userPassword, String userPrinters, String userPin,
                 String jrtiUsername, String jrtiPassword, String newUserEmail, String newUserSenderPassword) {
        this.username = username;
        this.userPassword = userPassword;
        this.userPrinters = userPrinters;
        this.userPin = userPin;
        this.jrtiUsername = jrtiUsername;
        this.jrtiPassword = jrtiPassword;
        this.newUserEmail = newUserEmail;
        this.password = newUserSenderPassword;
    }

    public Email(String username, String userPassword, String userPrinters, String userPin, String sysempUsername, String sysempPassword, String newUserEmail, String newUserSenderPassword) {
        this.username = username;
        this.userPassword = userPassword;
        this.userPrinters = userPrinters;
        this.userPin = userPin;
        this.sysempUsername = sysempUsername;
        this.sysempPassword = sysempPassword;
        this.newUserEmail = newUserEmail;
        this.password = newUserSenderPassword;
    }

    public Email(String username, String userPassword, String userPrinters, String userPin, String jrtiUsername, String jrtiPassword, String sysempUsername, String sysempPassword, String newUserEmail, String newUserSenderPassword) {
        this.username = username;
        this.userPassword = userPassword;
        this.userPrinters = userPrinters;
        this.userPin = userPin;
        this.jrtiUsername = jrtiUsername;
        this.jrtiPassword = jrtiPassword;
        this.sysempUsername = sysempUsername;
        this.sysempPassword = sysempPassword;
        this.newUserEmail = newUserEmail;
        this.newUserSenderPassword = newUserSenderPassword;
        password = newUserSenderPassword;
    }

    public void sendEmail(String to) {
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "false");
        properties.put("mail.smtp.host", HOST);
        properties.put("mail.smtp.port", "smtpPort");
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cC));
            message.setSubject("Movimentação de Patrimônio - ITEM Nº" + itemNumber);
            message.setContent("Detalhes da Movimentação de Patrimônio<br><br>" +
                    "<strong>Item Nº:</strong> " + itemNumber + "<br>" +
                    "<strong>Local Atual:</strong> " + itemActualPlace + "<br>" +
                    "<strong>Local Novo:</strong> " + itemNewPlace + "<br><br>" +
                    "<strong>Descrição:</strong> " + itemDescription, "text/html; charset=utf-8");

            Transport.send(message);
            JOptionPane.showMessageDialog(null, "Email enviado!");
        } catch (MessagingException e) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao tentar enviar a mensagem!");
            throw new RuntimeException(e);
        }
    }

    public void sendEmail(String to, String plaquetaFilePath, String frontalFilePath, String traseiraFilePath) {
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "false");
        properties.put("mail.smtp.host", HOST);
        properties.put("mail.smtp.port", "smtpPort");
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cC));
            message.setSubject("Novo Item de Patrimônio - ITEM Nº" + itemNumber);

            BodyPart messageBodyPart = new MimeBodyPart();

            messageBodyPart.setContent("<strong>Novo Item de Patrimônio:</strong><br>" +
                    "<strong>Nº de Patrimônio:</strong> " + itemNumber + "<br>" +
                    "<strong>Local:</strong> " + itemPlace + "<br>" +
                    "<strong>Descrição:</strong> " + itemDescription + "<br><br>" +
                    "Fotos seguem <strong>abaixo</strong> e em <strong>anexo</strong>.", "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            BodyPart attachmentPlaqueta = new MimeBodyPart();
            FileDataSource source = new FileDataSource(plaquetaFilePath);
            attachmentPlaqueta.setDataHandler(new DataHandler(source));
            attachmentPlaqueta.setFileName(itemNumber + " Plaqueta.png");
            multipart.addBodyPart(attachmentPlaqueta);

            BodyPart attachmentFrontalPhoto = new MimeBodyPart();
            source = new FileDataSource(frontalFilePath);
            attachmentFrontalPhoto.setDataHandler(new DataHandler(source));
            attachmentFrontalPhoto.setFileName(itemNumber + " Frontal.png");
            multipart.addBodyPart(attachmentFrontalPhoto);

            BodyPart attachmentTraseiraPhoto = new MimeBodyPart();
            source = new FileDataSource(traseiraFilePath);
            attachmentTraseiraPhoto.setDataHandler(new DataHandler(source));
            attachmentTraseiraPhoto.setFileName(itemNumber + " Traseira.png");
            multipart.addBodyPart(attachmentFrontalPhoto);


            message.setContent(multipart);

            Transport.send(message);
            JOptionPane.showMessageDialog(null, "Email enviado!");
        } catch (MessagingException e) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao tentar enviar a mensagem!");
            throw new RuntimeException(e);
        }
    }

    public void sendEmail(String to, String pronoun, boolean hasJrti, boolean hasSysemp) {
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "false");
        properties.put("mail.smtp.host", HOST);
        properties.put("mail.smtp.port", "emailPort");

        String jrti = "";
        String sysemp = "";

        if (hasJrti) {
          jrti = "<strong>Credenciais JRTi</strong><br><br>" +
                  "<strong>Usuário:</strong> " + jrtiUsername + "<br>" +
                  "<strong>Senha:</strong> " + jrtiPassword + "<br><br>";
        }
        if (hasSysemp) {
            sysemp = "<strong>Credenciais Sysemp</strong><br>" +
            "<strong>Usuário:</strong> " + sysempUsername + "<br>" +
                    "<strong>Senha:</strong> " + sysempPassword + "<br><br>";
        }

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cC));
            message.setSubject("Bem-vind" + pronoun + " ao [NOME DA EMPRESA]!");
            message.setContent("<strong>Bem-vind" + pronoun + " ao [NOME DA EMPRESA]!</strong><br><br>" +
                    "Somos da equipe de T.I e viemos te desejar boas-vindas! Segue abaixo as suas credenciais de " +
                    "acesso que irá utilizar:<br><br>" +
                    "<strong>E-Mail:</strong> " + to + "<br><br>" +
                    "<strong>Login no computador:</strong><br><br>" +
                    "<strong>Usuário:</strong> " + username + "<br>" +
                    "<strong>Senha:</strong> " + userPassword + "<br><br>" +
                    "<strong>Impressoras:</strong> " + userPrinters + "<br>" +
                    "<strong>PIN de Impressão:</strong> " + userPin + "<br><br>" +
                    jrti +
                    sysemp +
                    "Por favor, anote estas informações e deixe este e-mail guardado em um local seguro caso " +
                    "necessite revisar estas informações!<br>" +
                    "Qualquer dúvida ou problemas relacionados ao nosso setor, favor abrir um chamado através do " +
                    "e-mail <a href='mailto:setorti@suaempresa.com'>setorti@suaempresa.com</a>" +
                            ".<br><br>" +
                            "<strong>Observação:</strong> para ser atendido, é <span style='color:red'>CRUCIAL</span>" +
                            " a abertura de chamados via e-mail pois controlamos e planejamos todos os atendimentos e" +
                            " procedimentos.<br><br>" +
                            "Att,<br><br>" +
                            "<strong>Departamento de Tecnologia da Informação, [NOME DA EMPRESA]</strong>",
                    "text/html; charset=utf-8");

            Transport.send(message);
            JOptionPane.showMessageDialog(null, "Email enviado!");
        } catch (MessagingException e) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao tentar enviar a mensagem!");
            throw new RuntimeException(e);
        }
    }
}
