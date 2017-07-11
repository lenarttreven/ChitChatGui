package si.treven.chitchat_client;

/**
 * Razred, ki požene program
 */
public class ChitChatMain {

	public static void main(String[] args) {
		ChitChatGui chatFrame = new ChitChatGui();
		IzpisovalecSporocil izpisovalec = new IzpisovalecSporocil(chatFrame);
		izpisovalec.activate();
		chatFrame.pack();
		chatFrame.setVisible(true);
	}

}
