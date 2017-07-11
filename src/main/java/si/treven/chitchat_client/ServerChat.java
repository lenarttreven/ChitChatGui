package si.treven.chitchat_client;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Razred, ki je zadolžen za komunikacijo s strežnikom.
 */
public class ServerChat {
	static final Logger logger = LoggerFactory.getLogger(ServerChat.class);

	/**
	 * @param sender
	 * @param receiver
	 * @param content
	 */
	public static void sendPrivateMessage(String sender, String receiver, String content) {
		ObjectMapper mapper = new ObjectMapper();

		URI uri = null;
		String responseBody = null;
		try {
			uri = new URIBuilder("http://chitchat.andrej.com/messages")
                    .addParameter("username", sender)
                    .build();

			String message =  mapper.writeValueAsString(new PoslanoSporocilo(receiver, content));

			responseBody = Request.Post(uri)
					.bodyString(message, ContentType.APPLICATION_JSON)
					.execute()
					.returnContent()
					.asString();

		} catch (URISyntaxException e) {
			logger.info("URISyntaxException");
		} catch (ClientProtocolException e1) {
			logger.info("ClientProtocolException");
		} catch (IOException e1) {
			logger.info("IOException");
		}
			logger.info(responseBody);
	}

	/**
	 * @param sender
	 * @param content
	 */
	public static void sendGlobalMessage(String sender, String content)  {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(new ISO8601DateFormat());

		URI uri = null;
		String responseBody = "";
		try {
			uri = new URIBuilder("http://chitchat.andrej.com/messages")
                    .addParameter("username", sender)
                    .build();
			String message = mapper.writeValueAsString(new PoslanoSporocilo(content));

			responseBody = Request.Post(uri)
					.bodyString(message, ContentType.APPLICATION_JSON)
					.execute()
					.returnContent()
					.asString();
			logger.info(responseBody);
		} catch (URISyntaxException e) {
			logger.info("URISyntaxException");
		} catch (ClientProtocolException e) {
			logger.info("ClientProtocolException");
		} catch (IOException e) {
			logger.info("IOException");
		}


	}

	/**
	 * @param me
	 * @return vrne seznam vseh prejetih Sporočil
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static ArrayList<PrejetoSporocilo> receiveMessages(String me) throws URISyntaxException, ClientProtocolException, IOException{

			URI uri = null;
			uri = new URIBuilder("http://chitchat.andrej.com/messages")
                    .addParameter("username", me)
                    .build();
			String responseBody = Request.Get(uri)
					.execute()
					.returnContent()
					.asString();
			return vSeznamSporocil(responseBody);

	}

	/**
	 * @param neurejenoSporocilo
	 * @return seznam PrejetihSporocil
	 * @throws IOException
	 */
    private static ArrayList<PrejetoSporocilo> vSeznamSporocil(String neurejenoSporocilo) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new ISO8601DateFormat());

        TypeReference<List<PrejetoSporocilo>> t = new TypeReference<List<PrejetoSporocilo>>() { };
        ArrayList<PrejetoSporocilo> prejetaSporocila = mapper.readValue(neurejenoSporocilo, t);

        return prejetaSporocila;
    }
	
	/**
	 * @param username
	 */
	public static void logIn(String username) {
		URI uri = null;
		try {
			uri = new URIBuilder("http://chitchat.andrej.com/users")
                      .addParameter("username", username)
                      .build();
			String responseBody = Request.Post(uri)
					.execute()
					.returnContent()
					.asString();
			logger.info(responseBody);
		} catch (URISyntaxException e) {
			logger.info("URISyntaxException");
		} catch (ClientProtocolException e) {
			logger.info("ClientProtocolException");
		} catch (IOException e) {
			logger.info("IOException");
		}

	}
	
	/**
	 * @param username
	 */
	public static void logOut(String username){
		URI uri = null;
		try {
			uri = new URIBuilder("http://chitchat.andrej.com/users")
                      .addParameter("username", username)
                      .build();
			String responseBody = Request.Delete(uri)
					.execute()
					.returnContent()
					.asString();

			logger.info(responseBody);
		} catch (URISyntaxException e) {
			logger.info("URISyntaxException");
		} catch (ClientProtocolException e) {
			logger.info("ClientProtocolException");
		} catch (IOException e) {
			logger.info("IOException");
		}
	}

	/**
	 * @return vrne seznam vseh prijavljenih uporabnikov
	 */
	public static ArrayList<Uporabnik> getUsers(){
		URI uri = null;
		try {
			uri = new URIBuilder("http://chitchat.andrej.com/users")
                    .build();
			String responseBody = Request.Get(uri)
					.execute()
					.returnContent()
					.asString();
			return vSeznamUporabnikov(responseBody);
		} catch (URISyntaxException e) {
			logger.info("URISyntaxException");
		} catch (ClientProtocolException e) {
			logger.info("ClientProtocolException");
		} catch (IOException e) {
			logger.info("IOException");
		}
	return null;
	}


	/**
	 * @param uporabniki
	 * @return seznam Uporabnikov
	 * @throws IOException
	 */
	private static ArrayList<Uporabnik> vSeznamUporabnikov(String uporabniki) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new ISO8601DateFormat());

        TypeReference<List<Uporabnik>> t = new TypeReference<List<Uporabnik>>() { };
        ArrayList<Uporabnik> urejeniUporabniki = mapper.readValue(uporabniki, t);

        return urejeniUporabniki;
    }
}
