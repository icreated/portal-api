package co.icreated.portal.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;

import org.compiere.model.MClient;
import org.compiere.util.CLogger;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

  CLogger log = CLogger.getCLogger(EmailService.class);

  Properties ctx;
  ServletContext servletContext;

  MClient client = null;


  /**
   *
   * @param ctx
   * @param servletContext
   */
  public EmailService(Properties ctx, ServletContext servletContext) {
    this.ctx = ctx;
    this.servletContext = servletContext;
    client = MClient.get(ctx);
  }


  /**
   *
   * @param templateFile
   * @param bindingParams
   * @return
   */
  public String getMsgBody(String templateFile, Object... bindingParams) {

    InputStream inputStream = servletContext.getResourceAsStream(templateFile);
    String bodyEmail =
        new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines()
            .collect(Collectors.joining("\n"));

    // Binding variables: Name, Frontend URL for changing password
    return MessageFormat.format(bodyEmail, bindingParams);
  }


  /**
   *
   * @param to
   * @param subject
   * @param htmlMessage
   * @return
   */
  public boolean sendEmail(String to, String subject, String htmlMessage) {

    return client.sendEMail(to, subject, htmlMessage, null, true);
  }


  /**
   *
   * @param to
   * @param subject
   * @param htmlMessage
   * @param attachment
   * @return
   */
  public boolean sendEmail(String to, String subject, String htmlMessage, File attachment) {

    return client.sendEMail(to, subject, htmlMessage, attachment, true);
  }


}
