package edu.gae.domain.repository.file;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;

public class GDriveFactory {

	private static final String CLIENT_ID = "";
	private static final String CLIENT_SECRET = "";
	private static final String ACCESS_TOKEN = "";
	private static final String REFRESH_TOKEN = "";
	
	private static Drive driveService = null;
	
	private GDriveFactory() {}
	
	public static Drive getDriveService() {
		if (driveService == null) {
			loadDrive();
		}
		return driveService;
	}
	
	private static void loadDrive() {
		HttpTransport httpTransport = new NetHttpTransport();
		
		JsonFactory jsonFactory = new JacksonFactory();
		
		GoogleCredential credentials = new GoogleCredential.Builder()
	    	.setClientSecrets(CLIENT_ID, CLIENT_SECRET)
	    	.setJsonFactory(jsonFactory).setTransport(httpTransport).build()
	    	.setRefreshToken(REFRESH_TOKEN).setAccessToken(ACCESS_TOKEN);
		
		driveService = new Drive.Builder(httpTransport, jsonFactory, credentials).build();
	}
}

/*
 * https://accounts.google.com/o/oauth2/auth?access_type=offline&scope=https://www.googleapis.com/auth/drive&state=%2Fprofile&redirect_uri=http://localhost:58114/Callback&response_type=code&client_id=228404862472-kpq7d8aqqmup7hk54n5g9k72v36gmc4q.apps.googleusercontent.com
 */

/*
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="http://accounts.google.com/o/oauth2/token" method="post">
	<input type="text" name="code" value="4/aFSHNETBJsTIojXmmljJVhddDUWyKswJJTvSpgqqd3Q#"/> 
	</br>
	<input type="text" name="client_id" value="228404862472-kpq7d8aqqmup7hk54n5g9k72v36gmc4q.apps.googleusercontent.com"/> 
	</br>
	<input type="text" name="client_secret" value="LRuS9KnbioylN0qOu8MAcgxn"/> 
	</br>
	<input type="text" name="redirect_uri" value="http://localhost:58114/Callback"/> 
	</br>
	<input type="text" name="grant_type" value="authorization_code"/> 
	</br>
	<button type="submit">Enviar</button>
</form>
</body>
</html>
 */