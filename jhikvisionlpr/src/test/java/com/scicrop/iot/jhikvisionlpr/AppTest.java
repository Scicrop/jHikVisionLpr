package com.scicrop.iot.jhikvisionlpr;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpHost;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AppTest extends TestCase {

	public AppTest( String testName )
	{
		super( testName );
	}

	public static Test suite()
	{
		return new TestSuite( AppTest.class );
	}

	public void testApp() throws IOException{
		
		String user = "";
		String password = "";
		int secondsDelay = -3;
		String httpHost = "";
		String isapiPath = "/ISAPI/Traffic/channels/1/vehicleDetect/plates";
		String urlStr = "http://"+httpHost+isapiPath;
		URL url = new URL(urlStr);
		HttpHost target = new HttpHost(url.getHost(), url.getPort(), url.getProtocol());
		IsapiComponent component = new IsapiComponent();
		Header challengeHeader = component.getAuthChallengeHeader(urlStr);
		if (challengeHeader == null) {
			System.out.println("Setup was unsuccesfull");
			return;
		}
		Plates plates = component.postWithDigestAuth(challengeHeader, target, isapiPath, user, password, secondsDelay);
		List<Plate> lst = plates.getPlates();
		
		for (Plate plate : lst) {
			System.out.println("----------> "+plate.getPlateNumber());
		}
	}
}
