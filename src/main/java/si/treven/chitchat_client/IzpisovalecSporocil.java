package si.treven.chitchat_client;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IzpisovalecSporocil extends TimerTask {
	final Logger logger = LoggerFactory.getLogger(IzpisovalecSporocil.class);
	
	
	private ChitChatGui chat;
	
	public IzpisovalecSporocil(ChitChatGui chat){
		this.chat = chat;
	}
	
	public void activate() {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(this, 1000, 1000);
	}
	
	@Override
	public void run() {
		try {
			String sporocilo = ServerChat.recieveMessages(chat.inputVzdevek.getText());
			if (sporocilo.length() > 2) {
				chat.addMessage("sporočila", sporocilo);
			}
		} catch (ClientProtocolException e) {
			logger.error("Uporabnik {} ne more prejeti sporočil", chat.inputVzdevek.getText());
		} catch (URISyntaxException e) {
			logger.error("Uporabnik {} ne more prejeti sporočil", chat.inputVzdevek.getText());
		} catch (IOException e) {
			logger.error("Uporabnik {} ne more prejeti sporočil", chat.inputVzdevek.getText());
		}
		
		
	}

}
