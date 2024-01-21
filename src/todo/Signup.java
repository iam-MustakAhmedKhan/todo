package todo;

import java.awt.*;
import java.awt.event.*;
import java.util.regex.Pattern;

import javax.swing.*;

import java.sql.*;

public class Signup extends JFrame {
	
	Container conatiner;
	JPanel signupForm;
	JLabel formTitle,email,password,askForAccount,name,confirmPass;
	Font titleFont = new Font("arial", Font.BOLD, 20);
	Font textFieldFont = new Font("arial",Font.PLAIN,15);
	Font labelFont = new Font("arial",Font.BOLD,15);
	JTextField emailField,nameFiled;
	JPasswordField passwordField,confirmPassField;
	JButton loginButton,signupButton;
	Color btnColor = new Color(1, 113, 211);
	
	
	public Signup() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800,700);
		setTitle("Sign up");
		setLayout(null);
		initialContent();
		
	}
	
	public void initialContent() {

		conatiner= this.getContentPane();
		
		signupForm = new JPanel();
		signupForm.setBounds(200,50,400,500);;
		signupForm.setBackground(Color.white);
		signupForm.setLayout(null);
		
		
		
		formTitle = new JLabel("Sign up");
		formTitle.setFont(titleFont);
		formTitle.setHorizontalAlignment(SwingConstants.CENTER);
		formTitle.setSize(400,60);
		signupForm.add(formTitle);
		
		
		name= new JLabel("Name");
		name.setBounds(50,20,100,100);
		name.setFont(labelFont);
		signupForm.add(name);
		
		nameFiled = new JTextField();
		nameFiled.setBounds(50,80,300,40);
		nameFiled.setMargin(new Insets(0, 5, 0,5));
		nameFiled.setFont(textFieldFont);
		signupForm.add(nameFiled);
		
		email= new JLabel("Email");
		email.setBounds(50,90,100,100);
		email.setFont(labelFont);
		signupForm.add(email);
		
		emailField = new JTextField();
		emailField.setBounds(50,150,300,40);
		emailField.setMargin(new Insets(0, 5, 0,5));
		emailField.setFont(textFieldFont);
		signupForm.add(emailField);
		
		password= new JLabel("Password");
		password.setBounds(50,160,100,100);
		password.setFont(labelFont);
		signupForm.add(password);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(50,220,300,40);
		passwordField.setMargin(new Insets(0, 5, 0,5));
		passwordField.setFont(textFieldFont);
		signupForm.add(passwordField);
		
		confirmPass= new JLabel("Confirm Password");
		confirmPass.setBounds(50,230,200,100);
		confirmPass.setFont(labelFont);
		signupForm.add(confirmPass);
		
		confirmPassField = new JPasswordField();
		confirmPassField.setBounds(50,290,300,40);
		confirmPassField.setMargin(new Insets(0, 5, 0,5));
		confirmPassField.setFont(textFieldFont);
		signupForm.add(confirmPassField);
		
		BtnClickHandler btnClickHandler = new BtnClickHandler();
		
		
		signupButton= new JButton("Sign up");
		signupButton.setBounds(150,350,100,40);
		signupButton.setFocusable(false);
		signupButton.setBorder(null);
		signupButton.setBackground(btnColor);
		signupButton.setForeground(Color.white);
		signupButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		signupButton.addActionListener(btnClickHandler);
		signupForm.add(signupButton);
		
		
		askForAccount = new JLabel("Already Have an account?");
		askForAccount.setBounds(100,410,150,50);
		signupForm.add(askForAccount);
		
		loginButton= new JButton("Sign In");
		loginButton.setBounds(220,425,100,20);
		loginButton.setBackground(null);
		loginButton.setBorder(null);
		loginButton.setForeground(Color.blue);
		loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		loginButton.setFocusable(false);
		loginButton.setContentAreaFilled(false);
		
		loginButton.addActionListener(btnClickHandler);
		
		signupForm.add(loginButton);
        add(signupForm);
		
	}
	
	public class BtnClickHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			String nameRegex= "^[a-zA-Z]{3,10}";
			String emailRegex = "^[a-zA-Z0-9]+@[a-z]+.[a-z]+$";
			String passRegex="^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20}$";
			
			String name = nameFiled.getText();
			String email = emailField.getText();
			String password = passwordField.getText();
			String confirmPass = confirmPassField.getText();
			
			
			try {
				
				if(e.getSource()==signupButton) {
					
					if(!Pattern.matches(nameRegex, name)) {
						JOptionPane.showMessageDialog(null,"Name Should between 3-10 charecters");
					}else if(!Pattern.matches(emailRegex, email)) {
						JOptionPane.showMessageDialog(null,"Enter a valid email");
					}else if(!Pattern.matches(passRegex, password)) {
						JOptionPane.showMessageDialog(null, "Password should include Uppercase, Digit and special Character");
					}else if(!password.equals(confirmPass)) {
						JOptionPane.showMessageDialog(null, "Password and Confirm Password do not match");
					}else {
						
						try {
						
							String insertQuery = "INSERT INTO `users`(`name`, `email`, `password`) VALUES('"+name+"','"+email+"','"+password+"')";
							
							DbConnect db = new DbConnect();
							int a = db.registerUser(insertQuery);
							if(a==1) {
								JOptionPane.showMessageDialog(null,"User created Successfully");
								dispose();
								Login login = new Login();
								login.setVisible(true);
								login.setLocationRelativeTo(null);
								
							}else {
								JOptionPane.showMessageDialog(null,"User creation Failed");
							}
							
						} catch (Exception e2) {
							// TODO: handle exception
							
							System.out.print(e2);
						}
						
					}
					
				}
				
				
				if(e.getSource()==loginButton) {
					dispose();
					Login loginPage = new Login();
					loginPage.setVisible(true);
					loginPage.setLocationRelativeTo(null);
				}
				
				
			} catch (Exception e2) {
				System.out.print(e2);
			}
			
		}
		
	}
	
}
