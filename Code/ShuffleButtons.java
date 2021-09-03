/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rajendra Sharma
 */
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ShuffleButtons {
String pass="";
String uname="";
int OTP;
int i=0;
    private JButton[] buttons;
    private JPanel gridPanel;

    public ShuffleButtons() {
        
    }
    JFrame frame = new JFrame("Login");
public ShuffleButtons(String username) {
        gridPanel = new JPanel(new GridLayout(4, 4));
        buttons = getButtons(25);
        layoutButtons();

        
        frame.add(gridPanel);
        frame.add(getResetButton(), BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        uname=username;
    }
    public void shuffleButtons() {
        if (buttons != null) {
            Collections.shuffle(Arrays.asList(buttons));
            layoutButtons();
        }
    }

    public void layoutButtons() {
        gridPanel.removeAll();
        for (JButton button : buttons) {
            gridPanel.add(button);
        }
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private JButton[] getButtons(int size) {
        JButton[] buttons = new JButton[size];
        for (int i = 0; i < size; i++) {
            int z=i+65;
            char j=(char)z;
            final JButton button = new JButton("" + j);
            button.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    shuffleButtons();
                    pass=pass+button.getText();
                    System.out.println("Button " + button.getText() + " pressed");
                }
            });
            buttons[i] = button;
        }
        return buttons;
    }

    private JButton getResetButton() {
        JButton button = new JButton("Login");
        button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                PreparedStatement ps;
        ResultSet rs;
        String gpass = pass;
                  String query = "SELECT * FROM `java_login_register` WHERE `u_uname` =? AND `g_pass` =?";
        
        try {
            ps = MyConnection.getConnection().prepareStatement(query);
            
            
            ps.setString(1, uname);
            ps.setString(2,gpass);
            
            rs = ps.executeQuery();
            
            if(rs.next())
            {
                //OTP generation code
                 String smtp_host = "smtp.gmail.com";  					
	String from_userName = "ruchisharma20122001@gmail.com";		
	String from_passWord = "rama4776";						
	String show_name = "Verification";
        String recipients=uname;
        String sendSubject="Verify";
        Random rand = new Random();
            OTP=rand.nextInt(999999);
           
            String sendText = "Hey your OTP is: "+OTP;

        try {			
			Properties props = System.getProperties();
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", smtp_host);
			props.put("mail.smtp.user", from_userName);
			props.put("mail.smtp.password", from_passWord);
			props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
			props.setProperty("mail.smtp.socketFactory.fallback", "false"); 
			props.setProperty("mail.smtp.port", "465"); 
			props.setProperty("mail.smtp.socketFactory.port", "465"); 
			props.put("mail.smtp.auth", "true");
			Session session = Session.getDefaultInstance(props, null);
			session.setDebug(false);
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from_userName));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipients));
			message.setFrom(new InternetAddress(show_name + "<" + from_userName + ">"));	
			message.setSubject(sendSubject);
			message.setContent(sendText, "text/html;charset=utf-8");			
			Transport transport = session.getTransport("smtp");
			transport.connect(smtp_host, from_userName, from_passWord);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
                        new OTPForm(OTP).setVisible(true);
                        OTPForm otp  = new OTPForm(OTP);
        
        otp.pack();
        otp.setLocationRelativeTo(null);
        otp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.dispose();
		} 
        catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("failure! ");
                        frame.dispose();
                        StartForm st = new StartForm();
        st.setVisible(true);
        st.pack();
        st.setLocationRelativeTo(null);
        st.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

		}
                
            
        } 
            else{
                
                pass="";
                    JOptionPane.showMessageDialog(null, "Incorrect Username Or Password", "Login Failed", 2);
                    
            i++;    
            }
        } 
        
         catch (SQLException ex) {
            Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(i>2){
            
            JOptionPane.showMessageDialog(null, "Incorrect Username Or Password .Total attempts achieved.", "Login Failed", 2);
            StartForm st = new StartForm();
        st.setVisible(true);
        st.pack();
        st.setLocationRelativeTo(null);
        st.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.dispose();
        }
        
    }
            
            
        });
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run() {
                new ShuffleButtons();
            }
        });
    }
}
