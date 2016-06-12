import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import net.lingala.zip4j.exception.ZipException;

import javax.swing.JPasswordField;

public class PasswordProtect {

	private JFrame frame;
	private JButton btnNewButton = new JButton("Select folder");
	private JLabel label = new JLabel("No folder selected..");
	private JPasswordField passwordField1 = new JPasswordField();
	private JPasswordField passwordField2 = new JPasswordField();
	private JButton savePassword = new JButton("Password protect folder");
	private String selectedFolder = "";
	private JLabel label_2 = new JLabel("Insert same password.");
	private JLabel label_1 = new JLabel("Insert a password.");
	private final JLabel label_3 = new JLabel("");
	private final JLabel label_4 = new JLabel("");
	private final JLabel label_5 = new JLabel("");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PasswordProtect window = new PasswordProtect();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PasswordProtect() {
		initialize();
		initializeListeners();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);		
		
		btnNewButton.setBounds(10, 11, 142, 23);
		frame.getContentPane().add(btnNewButton);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 45, 414, 2);
		frame.getContentPane().add(separator);		
		
		label.setBounds(162, 15, 262, 14);
		frame.getContentPane().add(label);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 89, 414, 2);
		frame.getContentPane().add(separator_1);
		
		label_1.setBounds(162, 61, 262, 14);
		frame.getContentPane().add(label_1);
				
		passwordField1.setText("");
		passwordField1.setBounds(10, 58, 142, 20);
		frame.getContentPane().add(passwordField1);		
		
		passwordField2.setText("");
		passwordField2.setBounds(10, 102, 142, 20);
		frame.getContentPane().add(passwordField2);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(10, 133, 414, 2);
		frame.getContentPane().add(separator_2);
		
		
		label_2.setBounds(162, 105, 262, 14);
		frame.getContentPane().add(label_2);	
		
		savePassword.setBounds(10, 227, 232, 23);
		//btnNewButton_1.setEnabled(false);
		frame.getContentPane().add(savePassword);
		label_3.setBounds(10, 146, 414, 23);
		
		frame.getContentPane().add(label_3);
		label_4.setBounds(10, 169, 414, 23);
		
		frame.getContentPane().add(label_4);
		label_5.setBounds(10, 193, 414, 23);
		
		frame.getContentPane().add(label_5);
	}
	
	private void initializeListeners(){
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectFolder();
			}
		});
		savePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					saveFolderPassword();
				} catch (ZipException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		passwordField1.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		        System.out.println("Field=");
		      }
		});
		passwordField2.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		        System.out.println("Field=");
		      }
		});
	}
	
	private void selectFolder(){
		File currentDirectory = new File("C:\\");
		 
		JFileChooser fileChooser = new JFileChooser(){
			@Override
			public void approveSelection(){
				if(getSelectedFile().isFile()){
					JOptionPane.showMessageDialog(null, "Selecteer een folder, geen bestand.", "Alert", JOptionPane.INFORMATION_MESSAGE);
					return;
				}else{
					super.approveSelection();
				}
			}
		};
		fileChooser.setCurrentDirectory(currentDirectory);
		fileChooser.setDialogTitle("Select a directory");   
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fileChooser.setFileHidingEnabled(false);
		fileChooser.showDialog(fileChooser, "Select folder");
		
		selectedFolder = fileChooser.getSelectedFile().toString();
		System.out.println("Selectedfolder: " + selectedFolder);
		label.setText("Selected folder: " + selectedFolder);
		
	}

	private void saveFolderPassword() throws ZipException{
		System.out.println();
		if (passwordField1.getPassword().length > 0 && passwordField2.getPassword().length > 0){
			
			if (Arrays.equals(passwordField1.getPassword(), passwordField2.getPassword())){
				//"pass", "D:\\MyFile.zip", "D:\\zip"
				System.out.println("secret password" + " : " + selectedFolder + ".zip" + " : " + selectedFolder);
				ZipEncryptFolder zef = new ZipEncryptFolder(new String(passwordField1.getText()), selectedFolder + ".zip", selectedFolder);
				zef.generateFileList(new File(selectedFolder));
				zef.pack();
				label.setText("No folder selected..");
				label_1.setText("Insert a password.");
				label_2.setText("Insert same password.");
				passwordField1.setText("");
				passwordField2.setText("");
				selectedFolder = "";
				label_3.setText("Folder has been zipped and encrypt.");
				label_4.setText("You can only open files with an unzipper, ");
				label_5.setText("which will request for your password.");				
			}else{
				label_3.setText("Passwords are not the same.");
			}
		}else{
			label_3.setText("Fill in a password.");
		}
		
	}

}

