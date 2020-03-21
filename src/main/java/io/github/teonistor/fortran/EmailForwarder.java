package io.github.teonistor.fortran;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Attachments;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
public class EmailForwarder {

    private final String apiKey;

    public EmailForwarder(final @Value("${sendgrid.api.key}") String apiKey) {
        this.apiKey = apiKey;

    }

//    void forward() throws IOException {
//        SendGrid sg = new SendGrid(apiKey);
//        final Request request = new Request();
//
//
//        Email from = new Email("test@example.com");
//        String subject = "Sending with SendGrid is Fun";
//        Email to = new Email("nistorteodor6a@gmail.com");
//        Content content = new Content("text/plain", "and easy to do anywhere, even with Java");
//        Mail mail = new Mail(from, subject, to, content);
////mail.addAttachments(new Attachments().);
////
////new Attachments.Builder().
//
//        request.setMethod(Method.POST);
//        request.setEndpoint("mail/send");
//        request.setBody(mail.build());
//        Response response = sg.api(request);
//        System.out.println(response.getStatusCode());
//        System.out.println(response.getBody());
//        System.out.println(response.getHeaders());
//    }
}
