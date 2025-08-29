package com.cotodel.hrms.web.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.aad.msal4j.ClientCredentialFactory;
import com.microsoft.aad.msal4j.ClientCredentialParameters;
import com.microsoft.aad.msal4j.ConfidentialClientApplication;
import com.microsoft.aad.msal4j.IAuthenticationResult;
import com.microsoft.aad.msal4j.IClientCredential;
public class GraphMailSender {

	private static final String CLIENT_ID = "e608daad-9600-456e-b120-2acae62dd334";
    private static final String CLIENT_SECRET = "s7m8Q~d16I.ecEUM-FkZDIAyll-MvbJW5Ho6~b.U";
    private static final String TENANT_ID = "6e4c94b6-aefb-4501-81f8-03c7f97d6541";
    private static final String SENDER_USER = "support@cotodel.com";
    private static final Set<String> SCOPE = Collections.singleton("https://graph.microsoft.com/.default");
    private static final ObjectMapper JSON = new ObjectMapper();

    public static void main(String[] args) throws Exception {
        String accessToken = acquireToken();
        String recipient = "atulyadavmca@gmail.com";
        String subject = "Test Mail via Microsoft Graph (Java 8)";
        String body  = "<!DOCTYPE html>\r\n"
				+ "<html>\r\n"
				+ "<head>\r\n"
				+ "  <meta charset=\"UTF-8\">\r\n"
				+ "  <title>Cotodel Emailer</title>\r\n"
				+ "  <style>\r\n"
				+ "    body {\r\n"
				+ "      margin: 0;\r\n"
				+ "      padding: 0;\r\n"
				+ "      background-color: #f5f7f9;\r\n"
				+ "      font-family: Arial, sans-serif;\r\n"
				+ "    }\r\n"
				+ "    table {\r\n"
				+ "      border-collapse: collapse;\r\n"
				+ "      width: 100%;\r\n"
				+ "    }\r\n"
				+ "    .container {\r\n"
				+ "      max-width: 600px;\r\n"
				+ "      margin: auto;\r\n"
				+ "      background-color: #ffffff;\r\n"
				+ "    }\r\n"
				+ "    .header {\r\n"
				+ "      background-color: #166534;\r\n"
				+ "      padding: 20px;\r\n"
				+ "      text-align: center;\r\n"
				+ "      color: #ffffff;\r\n"
				+ "    }\r\n"
				+ "    .header img {\r\n"
				+ "      max-height: 40px;\r\n"
				+ "    }\r\n"
				+ "    .hero {\r\n"
				+ "      text-align: center;\r\n"
				+ "      padding: 20px;\r\n"
				+ "    }\r\n"
				+ "    .hero img {\r\n"
				+ "      max-width: 100%;\r\n"
				+ "      height: auto;\r\n"
				+ "    }\r\n"
				+ "    .content {\r\n"
				+ "      padding: 20px;\r\n"
				+ "      color: #333333;\r\n"
				+ "      font-size: 14px;\r\n"
				+ "      line-height: 1.6;\r\n"
				+ "    }\r\n"
				+ "    .content h1 {\r\n"
				+ "      color: #166534;\r\n"
				+ "      font-size: 20px;\r\n"
				+ "    }\r\n"
				+ "    .btn {\r\n"
				+ "      display: inline-block;\r\n"
				+ "      background-color: #16a34a;\r\n"
				+ "      color: #ffffff;\r\n"
				+ "      text-decoration: none;\r\n"
				+ "      padding: 12px 24px;\r\n"
				+ "      border-radius: 6px;\r\n"
				+ "      margin-top: 20px;\r\n"
				+ "      font-size: 14px;\r\n"
				+ "    }\r\n"
				+ "    .footer {\r\n"
				+ "      text-align: center;\r\n"
				+ "      padding: 20px;\r\n"
				+ "      font-size: 12px;\r\n"
				+ "      color: #777777;\r\n"
				+ "      background-color: #f9fafb;\r\n"
				+ "    }\r\n"
				+ "    .footer a {\r\n"
				+ "      margin: 0 8px;\r\n"
				+ "      color: #777777;\r\n"
				+ "      text-decoration: none;\r\n"
				+ "    }\r\n"
				+ "  </style>\r\n"
				+ "</head>\r\n"
				+ "<body>\r\n"
				+ "  <table role=\"presentation\" class=\"container\">\r\n"
				+ "    <tr>\r\n"
				+ "      <td class=\"header\">\r\n"
				+ "        <img src=\"https://yourcdn.com/logo.png\" alt=\"Cotodel Logo\"><br>\r\n"
				+ "        <p style=\"margin: 10px 0; font-size: 14px;\">Business expenses made seamless like your personal UPI spends</p>\r\n"
				+ "      </td>\r\n"
				+ "    </tr>\r\n"
				+ "    <tr>\r\n"
				+ "      <td class=\"hero\">\r\n"
				+ "        <img src=\"https://yourcdn.com/illustration.png\" alt=\"Illustration\">\r\n"
				+ "      </td>\r\n"
				+ "    </tr>\r\n"
				+ "    <tr>\r\n"
				+ "      <td class=\"content\">\r\n"
				+ "        <h1>Welcome, [Organization Name]!</h1>\r\n"
				+ "        <p>You have successfully signed up on Cotodel platform!</p>\r\n"
				+ "        <p>Our team will get in touch with you soon to activate your account. Start managing your corporate expenses with ease by administering allowances for your employees.</p>\r\n"
				+ "        <p>If you would want to schedule a demo with our team, please choose any slot as per your convenience.</p>\r\n"
				+ "        <p>Sincerely,<br>Team Cotodel</p>\r\n"
				+ "        <a href=\"#\" class=\"btn\">Schedule Demo</a>\r\n"
				+ "      </td>\r\n"
				+ "    </tr>\r\n"
				+ "    <tr>\r\n"
				+ "      <td class=\"footer\">\r\n"
				+ "        <p><strong>cotodel</strong><br>\r\n"
				+ "          Address: WeWork, Eleva Centre, Malviya Nagar, New Delhi - 110017</p>\r\n"
				+ "        <p>&copy; 2025 Cotodel. All rights reserved.</p>\r\n"
				+ "        <p>\r\n"
				+ "          <a href=\"#\">LinkedIn</a> |\r\n"
				+ "          <a href=\"#\">Twitter</a> |\r\n"
				+ "          <a href=\"#\">Instagram</a>\r\n"
				+ "        </p>\r\n"
				+ "      </td>\r\n"
				+ "    </tr>\r\n"
				+ "  </table>\r\n"
				+ "</body>\r\n"
				+ "</html>\r\n"
				+ "";
        sendMail(accessToken, SENDER_USER, recipient, subject, body);
        System.out.println("Done.");
    }
    
    public static String acquireToken() throws Exception {
        String authority = "https://login.microsoftonline.com/" + TENANT_ID + "/";

        IClientCredential credential = ClientCredentialFactory.createFromSecret(CLIENT_SECRET);
        ConfidentialClientApplication app = ConfidentialClientApplication.builder(CLIENT_ID, credential)
                .authority(authority)
                .build();

        ClientCredentialParameters parameters = ClientCredentialParameters.builder(SCOPE).build();

        CompletableFuture<IAuthenticationResult> future = app.acquireToken(parameters);
        IAuthenticationResult result = future.get();
        System.out.println("Access token acquired. Expires on: " + result.expiresOnDate());
        return result.accessToken();
    }

    public static Map<String, Object> sendMail(String accessToken, String senderUserPrincipalName,
                                 String to, String subject, String bodyText) throws Exception {
    
        String url = "https://graph.microsoft.com/v1.0/users/" + senderUserPrincipalName + "/sendMail";

        // Construct the email payload
        Map<String, Object> message = new HashMap<String, Object>();

        Map<String, Object> messageBody = new HashMap<String, Object>();
        messageBody.put("contentType", "HTML");
        messageBody.put("content", bodyText);

        Map<String, Object> from = new HashMap<String, Object>();
        Map<String, String> fromEmail = new HashMap<String, String>();
        fromEmail.put("address", senderUserPrincipalName);
        from.put("emailAddress", fromEmail);

        Map<String, Object> toRecipient = new HashMap<String, Object>();
        Map<String, String> toEmail = new HashMap<String, String>();
        toEmail.put("address", to);
        toRecipient.put("emailAddress", toEmail);

        List<Map<String, Object>> toRecipients = new ArrayList<Map<String, Object>>();
        toRecipients.add(toRecipient);

        message.put("subject", subject);
        message.put("body", messageBody);
        message.put("toRecipients", toRecipients);
        message.put("from", from);

        Map<String, Object> wrapper = new HashMap<String, Object>();
        wrapper.put("message", message);
        wrapper.put("saveToSentItems", true);

        String requestBody = JSON.writeValueAsString(wrapper);

        // Use Apache HttpClient
        HttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(new URI(url));
        post.setHeader("Authorization", "Bearer " + accessToken);
        post.setHeader("Content-Type", "application/json");
        post.setEntity(new StringEntity(requestBody, "UTF-8"));

        HttpResponse response = client.execute(post);
        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode >= 200 && statusCode < 300) {
            System.out.println("Mail sent. HTTP " + statusCode);
        } else {
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder responseBody = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBody.append(line);
            }
            System.err.println("Failed to send mail. HTTP " + statusCode + " body: " + responseBody.toString());
            throw new RuntimeException("SendMail failed: " + statusCode);
        }
        return message;
    }
    }