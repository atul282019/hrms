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
        String body = "Hello from Java 8 using MSAL4J and Microsoft Graph.";
        sendMail(accessToken, SENDER_USER, recipient, subject, body);
        System.out.println("Done.");
    }
    
//    public static String sendEmail() {
//    	String result="FAIL";
//    	try {
//    		 String accessToken = acquireToken();
//    	     String recipient = "fakhruddeen.ahmad@cotodel.com";
//    	     String subject = "Test Mail via Microsoft Graph (Java 8)";
//    	     String body = "Hello from Java 8 using MSAL4J and Microsoft Graph.";
//
//    	     sendMail(accessToken, SENDER_USER, recipient, subject, body);
//    	     result="SUCCESS";
//    	     System.out.println("Done.");
//		} catch (Exception e) {
//			result="FAIL";
//		}     
//
//       
//        return result;
//    }

    private static String acquireToken() throws Exception {
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

    private static void sendMail(String accessToken, String senderUserPrincipalName,
                                 String to, String subject, String bodyText) throws Exception {

        String url = "https://graph.microsoft.com/v1.0/users/" + senderUserPrincipalName + "/sendMail";

        // Construct the email payload
        Map<String, Object> message = new HashMap<String, Object>();

        Map<String, Object> messageBody = new HashMap<String, Object>();
        messageBody.put("contentType", "Text");
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
    }
    }