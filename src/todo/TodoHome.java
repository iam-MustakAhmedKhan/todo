package todo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.annotation.processing.Filer;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class TodoHome extends JFrame {
	
	int count=0;
	Container container;
	JPanel sidebar,mainContent;
	int frameHeight;
	Color btnColor = new Color(1, 113, 211);
	String username,email;
	ImageIcon userImage;
	JLabel image,displayName,greeting;
	JButton signOut,allTask,completedTask,incompletedTask,createTask;
	JButton[] update = new JButton[50];
	JButton[] delete = new JButton[50];
	JTextArea taskDes;
	String[] buttunId = new String[50];
	JPanel[] indicator=new JPanel[50];
	String filter="all";
//	boolean inCmp=false;
	

	public TodoHome(String name,String emaill,String comp) {
		username = name;
		email= emaill;
		filter=comp;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1240, 800);
		setTitle("Todo Home");
//		setLayout(null);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		initialContainer();
	}
	
	public void initialContainer() {

		
		frameHeight= getHeight();
		
//		sidebar panel
		sidebar = new JPanel();
		sidebar.setPreferredSize(new Dimension(275, frameHeight));
		sidebar.setBackground(new Color(20, 30, 51));
		sidebar.setLayout(null);
		
//		user image
		userImage= new ImageIcon(getClass().getResource("/img/user.png"));
		image = new JLabel(userImage);
		image.setBounds(0,20,275,100);
		image.setHorizontalAlignment(SwingConstants.CENTER);
		sidebar.add(image);

//		user name
		displayName = new JLabel();
		displayName.setText(username);
		displayName.setBounds(0,100,275,100);
		displayName.setFont(new Font("arial",Font.PLAIN,20));
		displayName.setForeground(Color.white);
		displayName.setHorizontalAlignment(SwingConstants.CENTER);
		sidebar.add(displayName);
		
		
//		action handler
		
		ButtonHandler buttonHandler = new ButtonHandler();
		
//		sign out button

		
		signOut = new JButton("Sign out");
		signOut.setBounds(60,frameHeight/2-200,150,30);
		signOut.setHorizontalAlignment(SwingConstants.CENTER);
		signOut.setFocusable(false);
		signOut.setBorder(null);
		signOut.setBackground(btnColor);
		signOut.setForeground(Color.white);
		signOut.setCursor(new Cursor(Cursor.HAND_CURSOR));
		signOut.addActionListener(buttonHandler);
		sidebar.add(signOut);
		
		
//		separetor
		
		JSeparator separator = new JSeparator();
//		separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
		separator.setBounds(0,250,275,1);
		sidebar.add(separator);
		
		
//		filter buttons
		
		allTask = new JButton("All Task");
		allTask.setBounds(0,300,275,20);
		allTask.setForeground(Color.white);
		allTask.setBackground(null);
		allTask.setBorder(null);
		allTask.setCursor(new Cursor(Cursor.HAND_CURSOR));
		allTask.setFocusable(false);
		allTask.setContentAreaFilled(false);
		allTask.setFont(new Font("arial",Font.PLAIN,15));
		allTask.addActionListener(buttonHandler);
		
		sidebar.add(allTask);
		
		
		completedTask = new JButton("Completed Task");
		completedTask.setBounds(0,340,275,20);
		completedTask.setForeground(Color.white);
		completedTask.setBackground(null);
		completedTask.setBorder(null);
		completedTask.setCursor(new Cursor(Cursor.HAND_CURSOR));
		completedTask.setFocusable(false);
		completedTask.setContentAreaFilled(false);
		completedTask.setFont(new Font("arial",Font.PLAIN,15));
		completedTask.addActionListener(buttonHandler);
		
		sidebar.add(completedTask);
		
		
		incompletedTask = new JButton("Incompleted Task");
		incompletedTask.setBounds(0,380,275,20);
		incompletedTask.setForeground(Color.white);
		incompletedTask.setBackground(null);
		incompletedTask.setBorder(null);
		incompletedTask.setCursor(new Cursor(Cursor.HAND_CURSOR));
		incompletedTask.setFocusable(false);
		incompletedTask.setContentAreaFilled(false);
		incompletedTask.setFont(new Font("arial",Font.PLAIN,15));
		incompletedTask.addActionListener(buttonHandler);
		
		sidebar.add(incompletedTask);
		
		add(sidebar,BorderLayout.WEST);

		
//		main content panel
		
		mainContent = new JPanel();
		mainContent.setBackground(new Color(15, 23, 42));
		mainContent.setBounds(275,0,950,frameHeight);
		mainContent.setLayout(null);
		
//		Greeting
		greeting = new JLabel("Welcome "+username);
		greeting.setBounds(20,0,200,100);
		greeting.setFont(new Font("arial",Font.PLAIN,20));
		greeting.setForeground(Color.white);
		greeting.setHorizontalAlignment(SwingConstants.CENTER);
		mainContent.add(greeting);
		
//		Create Task Button
		
		createTask = new JButton("Create Task");
		createTask.setBounds(750,30,150,30);
		createTask.setHorizontalAlignment(SwingConstants.CENTER);
		createTask.setFocusable(false);
		createTask.setBorder(null);
		createTask.setBackground(btnColor);
		createTask.setForeground(Color.white);
		createTask.setCursor(new Cursor(Cursor.HAND_CURSOR));
		createTask.addActionListener(buttonHandler);
		mainContent.add(createTask);
		
		JSeparator separator1 = new JSeparator();
		separator1.setBounds(0,80,950,1);
		mainContent.add(separator1);
		
//		Contents
		
		
		
		JPanel contentWrap = new JPanel();
		contentWrap.setLayout(new FlowLayout());
		contentWrap.setBackground(new Color(15, 23, 42));
		contentWrap.setBounds(10,90,950,frameHeight);
		
		try {
			String searchQuery;
			
			if(filter=="all") {
				searchQuery =  "SELECT * FROM `tasks` WHERE author='"+email+"'";
			}else if(filter=="complete"){
				
				searchQuery = "SELECT * FROM `tasks` WHERE author='"+email+"' AND status='Complete'";
			}else {
				searchQuery = "SELECT * FROM `tasks` WHERE author='"+email+"' AND status='Incomplete'";
			}
			DbConnect db = new DbConnect();
			ResultSet rs = db.getUser(searchQuery);
			
			while(rs.next()) {
				
				ImageIcon deleteIcon = new ImageIcon(getClass().getResource("/img/delete.png"));
				ImageIcon updateIcon= new ImageIcon(getClass().getResource("/img/edit.png"));
				
				JPanel content = new JPanel();
				content.setLayout(null);
				content.setPreferredSize(new Dimension(300,200));
				content.setBackground(new Color(20, 30, 51));
				
				
				taskDes = new JTextArea();
				taskDes.setSize(300,150);
				taskDes.setBackground(null);
				taskDes.setFocusable(false);
				taskDes.setText(rs.getString(2));
				taskDes.setForeground(Color.white);
				taskDes.setBorder(BorderFactory.createCompoundBorder(
						taskDes.getBorder(), 
				        BorderFactory.createEmptyBorder(10, 10, 10, 10)));
				taskDes.setFont(new Font("arial",Font.PLAIN,15));
				taskDes.setLineWrap(true);
				
				content.add(taskDes);
				
				count = rs.getRow();
				
				delete[count] = new JButton(deleteIcon);
				delete[count].setToolTipText(String.valueOf(rs.getInt(1)));
				delete[count].setBounds(240,150,50,30);
				delete[count].setBackground(null);
				delete[count].setBorder(null);
				delete[count].setForeground(Color.blue);
				delete[count].setCursor(new Cursor(Cursor.HAND_CURSOR));
				delete[count].setFocusable(false);
				delete[count].setContentAreaFilled(false);
				delete[count].addActionListener(buttonHandler);
				
				
					
				content.add(delete[count]);
				
				
				
				update[count] = new JButton(updateIcon);
				update[count].setBounds(200,150,50,30);
				update[count].setToolTipText(String.valueOf(rs.getInt(1)));
				update[count].setBackground(null);
				update[count].setBorder(null);
				update[count].setForeground(Color.blue);
				update[count].setCursor(new Cursor(Cursor.HAND_CURSOR));
				update[count].setFocusable(false);
				update[count].setContentAreaFilled(false);
				update[count].addActionListener(buttonHandler);
				content.add(update[count]);
		
//				complete indicator
				indicator[count]=new JPanel();
				indicator[count].setBounds(50,160,10,10);
				indicator[count].setLayout(null);
				indicator[count].setToolTipText(rs.getString(3));

				
				if(indicator[count].getToolTipText().equals("Complete")) {
					indicator[count].setBackground(Color.GREEN);
				}else {
					indicator[count].setBackground(Color.RED);
				}
	
				
				buttunId[count]=delete[count].getToolTipText();

				content.add(indicator[count]);
				
				
				contentWrap.add(content);

			}
			
			
		} catch (Exception e) {
			System.out.println(e);
		}
	
		

		mainContent.add(contentWrap);
		
		
		add(mainContent,BorderLayout.CENTER);
		
	
		
	}

	public class ButtonHandler implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			try {
				
				if(e.getSource()==signOut) {
					dispose();
					Login loginPage= new Login();
					loginPage.setVisible(true);
					loginPage.setLocationRelativeTo(null);
					
				}else if(e.getSource()==createTask) {
					dispose();
					Modal createTask = new Modal(email,"Create Task",username,"");
					createTask.setVisible(true);
					createTask.setLocationRelativeTo(null);
					
				}else if(e.getSource()==completedTask) {
//							inCmp=false;
				 			dispose();
					 		TodoHome todoPage =new TodoHome(username,email,"complete");
					 		todoPage.setVisible(true);
				 				
				}else if(e.getSource()==allTask) {
//					inCmp=false;
			 			dispose();
				 		TodoHome todoPage =new TodoHome(username,email,"all");
				 		todoPage.setVisible(true);
					
				}else if(e.getSource()==incompletedTask) {
//					inCmp = true;
		 			dispose();
			 		TodoHome todoPage =new TodoHome(username,email,"incomplete");
			 		todoPage.setVisible(true);
//		 		System.out.print(inCmp);
	
		}

					for(int i =1;i<=count;i++) {
						
						if(e.getSource()==delete[i]) {
							
							
							
							String searchQuery = "DELETE FROM `tasks` WHERE id='"+buttunId[i]+"'";
							
							DbConnect db = new DbConnect();
						 	int a = db.registerUser(searchQuery);
						 	
						 	if(a==1) {
						 		
						 		dispose();
						 		TodoHome todoPage =new TodoHome(username,email,"all");
						 		todoPage.setVisible(true);
						 		
						 	}
						 	
						 	
						}else if(e.getSource()==update[i]) {
							dispose();
							
							Modal createTask = new Modal(email,"Update Task",username,buttunId[i]);
							createTask.setVisible(true);
			
							createTask.setLocationRelativeTo(null);
						}
					}
					
					
				
				
				
			} catch (Exception e2) {
				
				System.out.println(e2);
			}
			
		}
		
	}
	
	

}
