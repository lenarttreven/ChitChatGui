package si.treven.chitchat_client;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChitChatGui extends JFrame implements ActionListener, KeyListener {
	final Logger logger = LoggerFactory.getLogger(ChitChatGui.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea output;
	public JTextField input;
	public JTextField inputVzdevek;
	public JTextField inputPrejemnik;
	public JTextArea seznamAktivnihUporabnikov;

	public ChitChatGui() {
		super();
		this.setTitle("Fmf chat");
		
		Container pane = this.getContentPane();
		pane.setLayout(new GridBagLayout());

		//Uvodna vrstica

		GridBagConstraints uvodnaVrsticaPostavitev = new GridBagConstraints();
		uvodnaVrsticaPostavitev.gridx = 0;
		uvodnaVrsticaPostavitev.weightx = 1.0;
		uvodnaVrsticaPostavitev.weighty = 0.2;
		uvodnaVrsticaPostavitev.fill = GridBagConstraints.BOTH;
		uvodnaVrsticaPostavitev.gridy = 0;

		Container uvodnaVrstica = new Container();
		uvodnaVrstica.setLayout(new GridBagLayout());
		pane.add(uvodnaVrstica, uvodnaVrsticaPostavitev);


		JPanel vzdevek = new JPanel();
		vzdevek.setLayout(new FlowLayout(FlowLayout.LEFT));
		vzdevek.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		JLabel naslovVzdevek = new JLabel("Vzdevek: ");
		vzdevek.add(naslovVzdevek);

		inputVzdevek = new JTextField(System.getProperty("user-name"),15);
		inputVzdevek.addKeyListener(this);
		vzdevek.add(inputVzdevek);


		GridBagConstraints vzdevekConstraint = new GridBagConstraints();
		vzdevekConstraint.gridx = 0;
		vzdevekConstraint.weightx = 1.0;
		vzdevekConstraint.fill = GridBagConstraints.BOTH;
		vzdevekConstraint.gridy = 0;
		uvodnaVrstica.add(vzdevek, vzdevekConstraint);

		JButton prijavaGumb = new JButton("Prijavi se");
		prijavaGumb.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				ServerChat.logIn(inputVzdevek.getText());
				addMessage("ChatServer", "Prijavil si se!");
			}
		});

		GridBagConstraints prijavaConstraint = new GridBagConstraints();
		prijavaConstraint.gridx = 1;
		prijavaConstraint.weightx = 1.0;
		prijavaConstraint.gridy = 0;
		uvodnaVrstica.add(prijavaGumb, prijavaConstraint);

		JButton odjavaGumb = new JButton("Odjavi se");
		odjavaGumb.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				ServerChat.logOut(inputVzdevek.getText());
				addMessage("ChatServer", "Odjavil si se!");
			}
		});

		GridBagConstraints odjavaConstraint = new GridBagConstraints();
		odjavaConstraint.gridx = 2;
		odjavaConstraint.weightx = 1.0;
		odjavaConstraint.gridy = 0;
		uvodnaVrstica.add(odjavaGumb, odjavaConstraint);


		//Vsebina

		GridBagConstraints vsebinaPogovoraConstraint = new GridBagConstraints();
		vsebinaPogovoraConstraint.gridx = 0;
		vsebinaPogovoraConstraint.weightx = 0.6;
		vsebinaPogovoraConstraint.weighty = 0.8;
		vsebinaPogovoraConstraint.fill = GridBagConstraints.BOTH;
		vsebinaPogovoraConstraint.gridy = 1;


		Container vsebina = new Container();
		vsebina.setLayout(new GridBagLayout());
		pane.add(vsebina, vsebinaPogovoraConstraint);


		output = new JTextArea(20, 40);
		output.setEditable(false);
		GridBagConstraints outputConstraint = new GridBagConstraints();
		outputConstraint.gridx = 0;
		outputConstraint.weightx = 1.0;
	    outputConstraint.weighty = 1.0;
		outputConstraint.fill = GridBagConstraints.BOTH;
		outputConstraint.gridy = 0;
		vsebina.add(output, outputConstraint);
		
		JScrollPane yDrsnik = new JScrollPane(output, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		vsebina.add(yDrsnik, outputConstraint);
		
		input = new JTextField(40);
		GridBagConstraints inputConstraint = new GridBagConstraints();
		inputConstraint.gridx = 0;
		inputConstraint.gridy = 1;
		inputConstraint.weightx = 1.0;
		inputConstraint.fill = GridBagConstraints.HORIZONTAL;
		vsebina.add(input, inputConstraint);
		input.addKeyListener(this);

		JPanel prejemnik = new JPanel();
		prejemnik.setLayout(new FlowLayout(FlowLayout.LEFT));
		prejemnik.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		JLabel naslovPrejemnik = new JLabel("Prejemnik: ");
		prejemnik.add(naslovPrejemnik);

		inputPrejemnik = new JTextField(System.getProperty("user-name"),15);
		inputPrejemnik.addKeyListener(this);
		prejemnik.add(inputPrejemnik);


		GridBagConstraints prejemnikConstraint = new GridBagConstraints();
		prejemnikConstraint.gridx = 1;
		prejemnikConstraint.weightx = 1.0;
		prejemnikConstraint.fill = GridBagConstraints.BOTH;
		prejemnikConstraint.gridy = 1;
		vsebina.add(prejemnik, prejemnikConstraint);



		//Aktivni uporabniki


		GridBagConstraints aktivniUporabnikiConstraint = new GridBagConstraints();
		aktivniUporabnikiConstraint.gridx = 1;
		aktivniUporabnikiConstraint.weightx = 0.4;
		aktivniUporabnikiConstraint.fill = GridBagConstraints.BOTH;
		aktivniUporabnikiConstraint.gridy = 0;


		Container aktivniUporabniki = new Container();
		aktivniUporabniki.setLayout(new GridBagLayout());
		vsebina.add(aktivniUporabniki, aktivniUporabnikiConstraint);


		JPanel naslovAktivniUporabniki = new JPanel();
		naslovAktivniUporabniki.setLayout(new FlowLayout(FlowLayout.LEFT));
		naslovAktivniUporabniki.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		JLabel napisAktivniUporabniki = new JLabel("Aktivni uporabniki: ");
		naslovAktivniUporabniki.add(napisAktivniUporabniki);


		GridBagConstraints naslovAktivniUporabnikiConstraint = new GridBagConstraints();
		naslovAktivniUporabnikiConstraint.gridx = 0;
		naslovAktivniUporabnikiConstraint.weightx = 1.0;
		naslovAktivniUporabnikiConstraint.fill = GridBagConstraints.BOTH;
		naslovAktivniUporabnikiConstraint.gridy = 0;
		aktivniUporabniki.add(naslovAktivniUporabniki, naslovAktivniUporabnikiConstraint);

		seznamAktivnihUporabnikov = new JTextArea(15, 10);
		output.setEditable(false);
		GridBagConstraints seznamAktivnihUporabnikovConstraint = new GridBagConstraints();
		seznamAktivnihUporabnikovConstraint.gridx = 0;
		seznamAktivnihUporabnikovConstraint.weightx = 1.0;
		seznamAktivnihUporabnikovConstraint.weighty = 1.0;
		seznamAktivnihUporabnikovConstraint.fill = GridBagConstraints.BOTH;
		seznamAktivnihUporabnikovConstraint.gridy = 1;
		aktivniUporabniki.add(seznamAktivnihUporabnikov, seznamAktivnihUporabnikovConstraint);





		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e){
				input.requestFocusInWindow();
			}
		});
		


	}

	/**
	 * @param person - the person sending the message
	 * @param message - the message content
	 */
	public void addMessage(String person, String message) {
		String chat = this.output.getText();
		this.output.setText(chat + person + ": " + message + "\n");
	}

	public void addUser(String uporabnik){
		String uporabniki = this.seznamAktivnihUporabnikov.getText();
		this.seznamAktivnihUporabnikov.setText(uporabniki + uporabnik + "\n");
	}

	public void newUsers(){
		this.seznamAktivnihUporabnikov.setText("");
	}

	public void actionPerformed(ActionEvent e) {
	
	}

	public void keyTyped(KeyEvent e) {
		if (e.getSource() == this.input) {
			if (e.getKeyChar() == '\n') {
				this.addMessage(inputVzdevek.getText(), input.getText());
				ServerChat.sendPrivateMessage(inputVzdevek.getText(),inputPrejemnik.getText(), input.getText());
				this.input.setText("");
			}
		}

	}

	
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


}


