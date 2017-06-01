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
	public JLabel uporabnik;

	public ChitChatGui() {
		super();
		this.setTitle("Fmf chat");
		
		Container pane = this.getContentPane();
		pane.setLayout(new GridBagLayout());

		this.output = new JTextArea(20, 40);
		this.output.setEditable(false);
		GridBagConstraints outputConstraint = new GridBagConstraints();
		outputConstraint.gridx = 0;
		outputConstraint.weightx = 1.0;
	    outputConstraint.weighty = 1.0;
		outputConstraint.fill = GridBagConstraints.BOTH;
		outputConstraint.gridy = 1;
		pane.add(output, outputConstraint);
		
		JScrollPane yDrsnik = new JScrollPane(output, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pane.add(yDrsnik, outputConstraint);
		
		this.input = new JTextField(40);
		GridBagConstraints inputConstraint = new GridBagConstraints();
		inputConstraint.gridx = 0;
		inputConstraint.gridy = 2;
		inputConstraint.weightx = 1.0;
		inputConstraint.fill = GridBagConstraints.HORIZONTAL;
		pane.add(input, inputConstraint);
		input.addKeyListener(this);
		
		
		JPanel vzdevek = new JPanel();
		vzdevek.setLayout(new FlowLayout(FlowLayout.LEFT));
		vzdevek.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		JLabel naslovVzdevek = new JLabel("Vzdevek: ");
		vzdevek.add(naslovVzdevek);
		
		inputVzdevek = new JTextField( 15);
		inputVzdevek.addKeyListener(this);
		vzdevek.add(inputVzdevek);
		
		GridBagConstraints vzdevekConstraint = new GridBagConstraints();
		vzdevekConstraint.gridx = 0;
		vzdevekConstraint.weightx = 1.0;
		vzdevekConstraint.fill = GridBagConstraints.BOTH;
		vzdevekConstraint.gridy = 0;
		pane.add(vzdevek, vzdevekConstraint);
		
		JButton prijavaGumb = new JButton("Prijavi se");
		prijavaGumb.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					ServerChat.logIn(inputVzdevek.getText());
					addMessage("ChatServer", "Prijavil si se!");
				} catch (ClientProtocolException e1) {
					addMessage("ChatServer", "Ni se ti uspelo prijaviti!");
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					addMessage("ChatServer", "Ni se ti uspelo prijaviti!");
					e1.printStackTrace();
				} catch (IOException e1) {
					addMessage("ChatServer", "Ni se ti uspelo prijaviti!");
					e1.printStackTrace();
				}
			}
		});
		
		GridBagConstraints prijavaConstraint = new GridBagConstraints();
		prijavaConstraint.gridx = 1;
		prijavaConstraint.weightx = 1.0;
		prijavaConstraint.gridy = 0;
		pane.add(prijavaGumb, prijavaConstraint);
		
		
		JButton odjavaGumb = new JButton("Odjavi se");
		odjavaGumb.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					ServerChat.logOut(inputVzdevek.getText());
					addMessage("ChatServer", "Odjavil si se!");
				} catch (ClientProtocolException e1) {
					addMessage("ChatServer", "Ni se ti uspelo odjaviti!");
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					addMessage("ChatServer", "Ni se ti uspelo odjaviti!");
					e1.printStackTrace();
				} catch (IOException e1) {
					addMessage("ChatServer", "Ni se ti uspelo odjaviti!");
					e1.printStackTrace();
				}
			}
		});
		
		GridBagConstraints odjavaConstraint = new GridBagConstraints();
		odjavaConstraint.gridx = 2;
		odjavaConstraint.weightx = 1.0;
		odjavaConstraint.gridy = 0;
		pane.add(odjavaGumb, odjavaConstraint);


		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e){
				input.requestFocusInWindow();
			}
		});
		
		JLabel uporabnik = new JLabel(System.getProperty("user.name"));
		GridBagConstraints uporabnikConstraint = new GridBagConstraints();
		uporabnikConstraint.gridx = 4;
		uporabnikConstraint.weightx = 1.0;
		uporabnikConstraint.gridy = 0;
		pane.add(uporabnik, uporabnikConstraint);




		
	}

	/**
	 * @param person - the person sending the message
	 * @param message - the message content
	 */
	public void addMessage(String person, String message) {
		String chat = this.output.getText();
		this.output.setText(chat + person + ": " + message + "\n");
	}

	public void dodajUporabnika(String person){
		this.uporabnik.setText("test");
	}

	public void actionPerformed(ActionEvent e) {
	
	}

	
	public void keyTyped(KeyEvent e) {
		if (e.getSource() == this.input) {
			if (e.getKeyChar() == '\n') {
				this.addMessage(inputVzdevek.getText(), this.input.getText());
				this.input.setText("");
			}
		}
		else if(e.getSource() == this.inputVzdevek){
			if (e.getKeyChar() == '\n'){
				this.dodajUporabnika(this.inputVzdevek.getText());
				this.inputVzdevek.setText("");
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


