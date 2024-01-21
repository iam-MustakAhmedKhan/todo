package todo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.regex.Pattern;

import javax.swing.*;

public class Login extends JFrame {
	
	Container conatiner;
	JPanel loginForm;
	JLabel formTitle,email,password,askForAccount;
	Font titleFont = new Font("arial", Font.BOLD, 20);
	Font textFieldFont = new Font("arial",Font.PLAIN,15);
	Font labelFont = new Font("arial",Font.BOLD,15);
	JTextField emailField;
	JPasswordField passwordField;
	JButton loginButton,signupButton;
	Color btnColor = new Color(1, 113, 211);
	
	
	public Login() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800,700);
		setTitle("Log In");
		setLayout(null);
		
		initialContent();
		
	}
	
	public void initialContent() {

		conatiner= this.getContentPane();
		
		loginForm = new JPanel();
		loginForm.setBounds(200,100,400,400);
		loginForm.setBackground(Color.white);
		loginForm.setLayout(null);
		
		
		
		formTitle = new JLabel("Login");
		formTitle.setFont(titleFont);
		formTitle.setHorizontalAlignment(SwingConstants.CENTER);
		formTitle.setSize(400,60);
		loginForm.add(formTitle);
		
		
		
		email= new JLabel("Email");
		email.setBounds(50,20,100,100);
		email.setFont(labelFont);
		loginForm.add(email);
		
		emailField = new JTextField();
		emailField.setBounds(50,80,300,40);
		emailField.setMargin(new Insets(0, 5, 0,5));
		emailField.setFont(textFieldFont);
		loginForm.add(emailField);
		
		password= new JLabel("Password");
		password.setBounds(50,100,100,100);
		password.setFont(labelFont);
		loginForm.add(password);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(50,160,300,40);
		passwordField.setMargin(new Insets(0, 5, 0,5));
		passwordField.setFont(textFieldFont);
		loginForm.add(passwordField);
		
		BtnClickHandler btnClickHandler = new BtnClickHandler();
		
		
		loginButton= new JButton("Login");
		loginButton.setBounds(150,230,100,40);
		loginButton.setFocusable(false);
		loginButton.setBorder(null);
		loginButton.setBackground(btnColor);
		loginButton.setForeground(Color.white);
		loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		loginButton.addActionListener(btnClickHandler);
		loginForm.add(loginButton);
		
		
		askForAccount = new JLabel("Don't Have an account?");
		askForAccount.setBounds(100,310,150,50);
		loginForm.add(askForAccount);
		
		signupButton= new JButton("Sign up");
		signupButton.setBounds(220,325,100,20);
		signupButton.setBackground(null);
		signupButton.setBorder(null);
		signupButton.setForeground(Color.blue);
		signupButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		signupButton.setFocusable(false);
		signupButton.setContentAreaFilled(false);
		
		signupButton.addActionListener(btnClickHandler);
		
		loginForm.add(signupButton);
        add(loginForm);
		
	}
	
	public class BtnClickHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				String emailRegex = "^[a-zA-Z0-9]+@[a-z]+.[a-z]+$";
				String passRegex="^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20}$";
				
				String email = emailField.getText();
				String pass= passwordField.getText();
				
				if(e.getSource()==loginButton) {
					
					
					if(!Pattern.matches(emailRegex, email)) {
						JOptionPane.showMessageDialog(null,"Enter a valid email");
					}else if(!Pattern.matches(passRegex, pass)) {
						JOptionPane.showMessageDialog(null, "Password should include Uppercase, Digit and special Character");
					}else {
						
						String searchQuery = "SELECT `name`, `email`, `password` FROM `users` WHERE email='"+email+"' AND password='"+pass+"'";
						
						DbConnect db = new DbConnect();
					 	ResultSet rs = db.getUser(searchQuery);
					 	
					 	while(rs.next()) {

					 		if(rs.getString(1) != null) {
					 			 dispose();
					 			 TodoHome todoPage = new TodoHome(rs.getString(1),rs.getString(2),"all");
					 			 todoPage.setVisible(true);
					 			 todoPage.setLocationRelativeTo(null);
					 			 return;
					 		}
					 		
					 	}
					 	
					 	if(rs.next() == false) {
					 		JOptionPane.showMessageDialog(null, "Invalid Credential");
					 	}
						
					}
					
				}
				
				
				if(e.getSource()==signupButton) {
					dispose();
					Signup signupPage= new Signup();
					signupPage.setVisible(true);
					signupPage.setLocationRelativeTo(null);
				}
				
				
			} catch (Exception e2) {
				System.out.print(e);
			}
			
		}
		
	}
	
}
