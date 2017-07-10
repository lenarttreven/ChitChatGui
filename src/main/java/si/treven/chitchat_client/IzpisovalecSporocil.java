package si.treven.chitchat_client;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.security.util.Length;

public class IzpisovalecSporocil extends TimerTask {
	final Logger logger = LoggerFactory.getLogger(IzpisovalecSporocil.class);
	
	
	private ChitChatGui chat;
	private Timer timer;
	
	public IzpisovalecSporocil(ChitChatGui chat){
		this.chat = chat;
	}
	
	public void activate() {
		timer = new Timer();
		timer.scheduleAtFixedRate(this, 1000, 1000);
	}
	
	@Override
	public void run() {
		try {
				ArrayList<PrejetoSporocilo> sporocila = ServerChat.receiveMessages(chat.inputVzdevek.getText());
				if (!sporocila.isEmpty()){
					for(PrejetoSporocilo sporocilo: sporocila){
						chat.addMessage(sporocilo.getSender(), sporocilo.getText());
					}
				}
		} catch (ClientProtocolException e) {
			logger.error("Uporabnik {} ne more prejeti sporočil, ClientProtocolException", chat.inputVzdevek.getText());
		} catch (IOException e) {
			logger.error("Uporabnik {} ne more prejeti sporočil, IOException", chat.inputVzdevek.getText());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		ArrayList<Uporabnik> seznamUporabnikov = ServerChat.getUsers();
		chat.newUsers();
		for(Uporabnik uporabnik: seznamUporabnikov) {
			chat.addUser(uporabnik.getUsername());
		}

	}


}
