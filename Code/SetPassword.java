import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.sql.*;

import java.sql.DriverManager;

public class SetPassword{
String pass="";
public String fname1="";
   public String lname1="";
    public String uname1="";
    public String lpass1="";
    private JButton[] buttons;
    private JPanel gridPanel;
 JFrame frame = new JFrame("SignUP");
    public SetPassword() {
       
    }
public SetPassword(String fname,String lname,String uname,String gpass){
    gridPanel = new JPanel(new GridLayout(4, 4));
        buttons = getButtons(25);
        layoutButtons();
        frame.add(gridPanel);
        frame.add(getResetButton(), BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    fname1=fname1+fname;
    lname1=lname1+lname;
    uname1=uname1+uname;
    lpass1=lpass1+gpass;
    System.out.println(lpass1);
}
//Code to shuffle buttons
    public void SetPassword() {
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
            final JButton button = new JButton(""+j+"");
            button.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    SetPassword();
                    pass=pass+button.getText();
                    System.out.println("Button " + button.getText() + " pressed");
                }
            });
            buttons[i] = button;
        }
        return buttons;
    }

    private JButton getResetButton() {
        JButton button = new JButton("Signup");
        button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                Connection mycon=null;
        PreparedStatement ps= null; 
                    String query="INSERT INTO `java_login_register`(`u_fname`, `u_lname`, `u_uname`, `u_pass`, `g_pass`) VALUES (?,?,?,?,?)";
        
        String gpass=pass;
        String fname=fname1;
    String lname=lname1;
    String uname=uname1;
     String lpass=lpass1;
        try {
            

            ps = MyConnection.getConnection().prepareStatement(query);
            
           ps.setString(1, fname);
            ps.setString(2, lname);
            ps.setString(3, uname);
            ps.setString(4, lpass);
            ps.setString(5, gpass);
            
            
           
            if(ps.executeUpdate() > 0)
            {frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                JOptionPane.showMessageDialog(null, "New User Added");
                StartForm st = new StartForm();
        st.setVisible(true);
        st.pack();
        st.setLocationRelativeTo(null);
        st.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.dispose();
            }
            
        }
        
        
        catch (SQLException ex) {
            Logger.getLogger(RegisterForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        frame.dispose();
            }
           
            
        });
        return button;
        
        
        
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run() {
                new SetPassword();
            }
        });
    }
}