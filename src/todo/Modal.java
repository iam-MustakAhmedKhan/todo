package todo;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class Modal extends JFrame {

	JPanel taskForm;
	JLabel formTitle,status,taskDes;
	Font titleFont = new Font("arial", Font.BOLD, 20);
	Font textFieldFont = new Font("arial",Font.PLAIN,15);
	Font labelFont = new Font("arial",Font.BOLD,15);
	JTextArea taskField;
	JButton save;
	Color btnColor = new Color(1, 113, 211);
	JComboBox selectBox;
	String statusText[]={"Incomplete","Complete"};
	String author;
	String modalTitle;
	String username;
	String[] taskId =null;
	
	public Modal(String authorEmail,String title,String name,String ...buttunId) {
		author=authorEmail;
		modalTitle= title;
		username = name;
		taskId=buttunId;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800,700);
		setTitle(title);
		setLayout(null);
		initialContent();
	}
	
	public void initialContent() {
		taskForm = new JPanel();
		taskForm.setBounds(200,50,400,500);;
		taskForm.setBackground(Color.white);
		taskForm.setLayout(null);
		
		
		
		formTitle = new JLabel(modalTitle);
		formTitle.setFont(titleFont);
		formTitle.setHorizontalAlignment(SwingConstants.CENTER);
		formTitle.setSize(400,60);
		taskForm.add(formTitle);
		
		
		taskDes= new JLabel("Task Description");
		taskDes.setBounds(50,20,200,100);
		taskDes.setFont(labelFont);
		taskForm.add(taskDes);
		
		taskField = new JTextArea();
		taskField.setBounds(50,80,300,130);
		taskField.setMargin(new Insets(0, 5, 0,5));
		taskField.setFont(textFieldFont);
		taskField.setBorder(BorderFactory.createLineBorder(Color.black));
		taskField.setBorder(BorderFactory.createCompoundBorder(
				taskField.getBorder(), 
		        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		taskField.setLineWrap(true);
		taskForm.add(taskField);
		
		status= new JLabel("Status");
		status.setBounds(50,190,100,100);
		status.setFont(labelFont);
		taskForm.add(status);
		
		selectBox = new JComboBox(statusText);
		selectBox.setBounds(50,250,300,50);
		selectBox.setFont(new Font("arial",Font.PLAIN,15));
		selectBox.setBackground(null);
		selectBox.setFocusable(false);
		selectBox.setBorder(null);
		taskForm.add(selectBox);
		
		SaveHandler saveHandler = new SaveHandler();
		
		
		save= new JButton();
		
		if(modalTitle=="Create Task") {
			save.setText("Save");
		}else {
			save.setText("Update");
		}
		
		save.setBounds(150,350,100,40);
		save.setFocusable(false);
		save.setBorder(null);
		save.setBackground(btnColor);
		save.setForeground(Color.white);
		save.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		save.addActionListener(saveHandler);
		
		
		taskForm.add(save);
		
		
		
		
		if(taskId!=null && taskId[0]!="") {
			
			int convertedId = Integer.parseInt(taskId[0]);
			try {

				String searchQuery = "SELECT `task`, `status`, `author` FROM `tasks` WHERE id='"+convertedId+"'";
				DbConnect db = new DbConnect();
			 	ResultSet rs = db.getUser(searchQuery);
			 	
			 	while(rs.next()) {

			 		taskField.setText(rs.getString(1));
			 		selectBox.setSelectedItem(rs.getString(2));
			 		
			 	}
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
			}
			
		}
	
		
		add(taskForm);
		
		
	
		
		
	}
	
	public class SaveHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			
			try {
				if(e.getSource()==save && save.getText()=="Save") {
					String status = selectBox.getSelectedItem().toString();
					String task= taskField.getText();
					
					String insertQuery = "INSERT INTO `tasks`(`task`, `status`, `author`) VALUES('"+task+"','"+status+"','"+author+"')";
					
					DbConnect db = new DbConnect();
					int a = db.registerUser(insertQuery);
					if(a==1) {
						dispose();
						TodoHome todopage= new TodoHome(username,author,"all" );
						todopage.setVisible(true);
						todopage.setLocationRelativeTo(null);
					}else {
						JOptionPane.showMessageDialog(null,"Task creation Failed");
					}
				}else if(e.getSource()==save && save.getText()=="Update") {
					String status = selectBox.getSelectedItem().toString();
					String task= taskField.getText();
					String updateQuery = "UPDATE `tasks` SET `task`='"+task+"',`status`='"+status+"',`author`='"+author+"' WHERE id='"+taskId[0]+"'";
					
					DbConnect db = new DbConnect();
					int a = db.registerUser(updateQuery);
					if(a==1) {
						dispose();
						TodoHome todopage= new TodoHome(username,author ,"all");
						todopage.setVisible(true);
						todopage.setLocationRelativeTo(null);
					}else {
						JOptionPane.showMessageDialog(null,"Task Update Failed");
					}
					
					
				}
				
				
			} catch (Exception e2) {
				// TODO: handle exception
				System.out.println(e2);
			}
			
		}
		
	}
	
	
	
}
