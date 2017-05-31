package si.treven.chitchat_client;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;

public class ServerChat {

	
	/**
	 * @param sender
	 * @param receiver
	 * @param content
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static void sendPrivateMessage(String sender, String receiver, String content) throws URISyntaxException, ClientProtocolException, IOException {
		URI uri = new URIBuilder("http://chitchat.andrej.com/messages")
				.addParameter("username", sender)
				.build();

		String message = "{ \"global\" : true, \"recipient\":"+ receiver + " \"text\" :" + content +  "}";

		String responseBody = Request.Post(uri)
				.bodyString(message, ContentType.APPLICATION_JSON)
				.execute()
				.returnContent()
				.asString();

		System.out.println(responseBody);
	}
	
	/**
	 * @param sender
	 * @param content
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static void sendGlobalMessage(String sender, String content) throws URISyntaxException, ClientProtocolException, IOException {
		URI uri = new URIBuilder("http://chitchat.andrej.com/messages")
				.addParameter("username", sender)
				.build();

		String message = "{ \"global\" : true, \"text\" :" + content +  "}";

		String responseBody = Request.Post(uri)
				.bodyString(message, ContentType.APPLICATION_JSON)
				.execute()
				.returnContent()
				.asString();

		System.out.println(responseBody);
	}
	
	/**
	 * @param me
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String recieveMessages(String me) throws URISyntaxException, ClientProtocolException, IOException{
		URI uri = new URIBuilder("http://chitchat.andrej.com/messages")
				.addParameter("username", me)
				.build();

		String responseBody = Request.Get(uri)
				.execute()
				.returnContent()
				.asString();

		return responseBody;
	}
	
	/**
	 * @param username
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static void logIn(String username) throws URISyntaxException, ClientProtocolException, IOException{
		URI uri = new URIBuilder("http://chitchat.andrej.com/users")
		          .addParameter("username", username)
		          .build();

		  String responseBody = Request.Post(uri)
		                               .execute()
		                               .returnContent()
		                               .asString();

		  System.out.println(responseBody);
	}
	
	/**
	 * @param username
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static void logOut(String username) throws URISyntaxException, ClientProtocolException, IOException{
		URI uri = new URIBuilder("http://chitchat.andrej.com/users")
		          .addParameter("username", username)
		          .build();

		  String responseBody = Request.Delete(uri)
		                               .execute()
		                               .returnContent()
		                               .asString();

		  System.out.println(responseBody);
	}
	
	/**
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static void getUsers() throws URISyntaxException, ClientProtocolException, IOException{
		URI uri =  new URIBuilder("http://chitchat.andrej.com/users")
				.build();
		String responseBody = Request.Get(uri)
									 .execute()
									 .returnContent()
									 .asString();
		System.out.println(responseBody);
	}
}