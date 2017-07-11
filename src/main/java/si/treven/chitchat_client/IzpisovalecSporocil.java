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

/**
 * Razred, ki skrbi, da se Chat odjemalec osvežuje vsako sekundo
 */
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
						if (!sporocilo.getGlobal()){
							chat.addMessage(sporocilo.getSender(), sporocilo.getSender(), sporocilo.getText());
						}
						else{
							chat.addMessage(sporocilo.getSender(), "Skupinski pogovor", sporocilo.getText());
						}
					}
				}
				chat.output.setText(chat.sporocila.get(chat.napisTrenutnoOkno.getText()));


		} catch (ClientProtocolException e) {
			logger.info("ClientProtocolException");
		} catch (IOException e) {
			logger.info("IOException");
		} catch (URISyntaxException e) {
			logger.info("URISyntaxException");
		}

		ArrayList<Uporabnik> seznamUporabnikov = ServerChat.getUsers();
		chat.newUsers();
		for(Uporabnik uporabnik: seznamUporabnikov) {
			chat.addUser(uporabnik.getUsername());
		}
	}
}
