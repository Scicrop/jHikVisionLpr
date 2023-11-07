package com.scicrop.iot.jhikvisionlpr;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.DigestScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class IsapiComponent {

	public Header getAuthChallengeHeader(String urlStr) {
		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			CloseableHttpResponse response = httpClient.execute(new HttpGet(urlStr));
			return response.getFirstHeader("WWW-Authenticate");
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Plates postWithDigestAuth(Header challengeHeader, HttpHost target, String request, String user, String password, int secondsDelay) throws IOException {

		Plates plates = null;
		
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(
				new AuthScope(target.getHostName(), target.getPort()),
				new UsernamePasswordCredentials(user, password));

		try (CloseableHttpClient httpclient = HttpClients.custom()
				.setDefaultCredentialsProvider(credsProvider)
				.build()) {

			// Create AuthCache instance
			AuthCache authCache = new BasicAuthCache();
			// Generate DIGEST scheme object, initialize it and add it to the local
			// auth cache
			DigestScheme digestAuth = new DigestScheme();
			digestAuth.processChallenge(challengeHeader);
			authCache.put(target, digestAuth);

			// Add AuthCache to the execution context
			HttpClientContext localContext = HttpClientContext.create();
			localContext.setAuthCache(authCache);


				Date now = new Date();
				Calendar cal = Calendar.getInstance();
				cal.setTime(now);
				cal.add(Calendar.SECOND, -3);
				Date plateDetectionDate = cal.getTime();
				String picTime = BasicHelper.getInstance().dateToString(plateDetectionDate, BasicHelper.HIKVISION_PICTIME);
				// https://tpp.hikvision.com/wiki/isapi/anpr/GUID-83D1543A-E59D-4CAE-99AA-B7B53BD6DA8A.html

				String xmlPostBody = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
						+ "<AfterTime version=\"2.0\" xmlns=\"http://www.hikvision.com/ver20/XMLSchema\">\r\n"
						+ "    <picTime>20231106181045</picTime>\r\n"
						+ "</AfterTime>";

				StringEntity requestEntity = new StringEntity(xmlPostBody, ContentType.APPLICATION_XML);
				HttpPost req =   new HttpPost(request);
				req.setEntity(requestEntity);
				System.out.println("Executing request to target " + target + request);
				try (CloseableHttpResponse response = httpclient
						.execute(target, req, localContext)) {
					System.out.println("----------------------------------------");
					System.out.println(response.getStatusLine());
					
					
					String output = EntityUtils.toString(response.getEntity());
					System.out.println(output);
					
					plates = XMLUtil.convertXmlToBeanObject(output);
					
					
					
					
				} catch (Exception e) {
					System.out.println("Error while executing HTTP request");
					e.printStackTrace();
				}
			
		} catch (MalformedChallengeException e) {
			e.printStackTrace();
		}
		return plates;
	}
	
}
