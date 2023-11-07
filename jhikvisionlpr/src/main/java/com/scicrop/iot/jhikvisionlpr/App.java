package com.scicrop.iot.jhikvisionlpr;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpHost;

public class App {

	public static void main(String[] args) throws IOException {
		String user = args[0];
		String password = args[1];
		int secondsDelay = -3;
		String httpHost = args[2];
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
