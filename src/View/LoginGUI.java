package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import Helper.*;
import Model.*;

public class LoginGUI extends JFrame {

	private JPanel w_pane;
	private JTextField fld_hastaTc;
	private JTextField fld_doctorTc;
	private JPasswordField fld_doctorPass;
	private DBConnection conn = new DBConnection();
	private JPasswordField fld_hastaPass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginGUI() {
		setResizable(false);
		setTitle("Hastane Y\u00F6netim Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);

		JLabel lbl_logo = new JLabel(new ImageIcon(getClass().getResource("hospital.png")));
		lbl_logo.setBounds(194, 11, 90, 72);
		w_pane.add(lbl_logo);

		JLabel lblNewLabel = new JLabel("Hastane Y\u00F6netim Sistemine Ho\u015Fgeldiniz");
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 20));
		lblNewLabel.setBounds(55, 94, 373, 27);
		w_pane.add(lblNewLabel);

		JTabbedPane w_tabpane = new JTabbedPane(JTabbedPane.TOP);
		w_tabpane.setBounds(10, 132, 474, 228);
		w_pane.add(w_tabpane);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		w_tabpane.addTab("Hasta Giriþ", null, panel, null);
		panel.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("T.C. Numaran\u0131z :");
		lblNewLabel_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(10, 11, 113, 32);
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("\u015Eifre  :");
		lblNewLabel_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblNewLabel_1_1.setBounds(75, 60, 48, 32);
		panel.add(lblNewLabel_1_1);

		fld_hastaTc = new JTextField();
		fld_hastaTc.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		fld_hastaTc.setBounds(133, 11, 221, 32);
		panel.add(fld_hastaTc);
		fld_hastaTc.setColumns(10);

		JButton btn_hastaLogin = new JButton("Giri\u015F Yap");
		btn_hastaLogin.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btn_hastaLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fld_hastaTc.getText().length()==0 || fld_hastaPass.getText().length()==0) {
					Helper.showMsg("fill");					
				}else {
					boolean key= true;
					try {
						Connection con = conn.connDb();
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM user");					
						while (rs.next()) {
							if (fld_hastaTc.getText().equals(rs.getString("tcno"))
									&& fld_hastaPass.getText().equals(rs.getString("password"))) {
								if(rs.getString("type").equals("hasta")){
									Hasta hasta = new Hasta();
									hasta.setId(rs.getInt("id"));
									hasta.setPassword("password");
									hasta.setTcno(rs.getString("tcno"));
									hasta.setName(rs.getString("name"));
									hasta.setType(rs.getString("type"));
									HastaGUI hGUI =new HastaGUI(hasta);
									hGUI.setVisible(true);
									dispose();
									key=false;
								}
							
							}
						}
					} catch (SQLException e1) {

						e1.printStackTrace();
					}
					if(key) {
						Helper.showMsg("Böyle Bir Hasta Bulunamadý Lütfen Kayýt Olunuz");
					}
					
				}
				
			}
		});
		btn_hastaLogin.setBounds(253, 104, 101, 32);
		panel.add(btn_hastaLogin);

		JButton btn_register = new JButton("Kay\u0131t Ol");
		btn_register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterGUI rGUI = new RegisterGUI();
				rGUI.setVisible(true);
				dispose();
			}
		});
		btn_register.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btn_register.setBounds(133, 104, 99, 32);
		panel.add(btn_register);
		
		fld_hastaPass = new JPasswordField();
		fld_hastaPass.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		fld_hastaPass.setBounds(133, 69, 221, 32);
		panel.add(fld_hastaPass);

		JPanel w_doctorLogin = new JPanel();
		w_doctorLogin.setBackground(Color.WHITE);
		w_tabpane.addTab("Doktor Giriþ", null, w_doctorLogin, null);
		w_doctorLogin.setLayout(null);

		JLabel lblNewLabel_1_2 = new JLabel("T.C. Numaran\u0131z :");
		lblNewLabel_1_2.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblNewLabel_1_2.setBounds(10, 11, 113, 32);
		w_doctorLogin.add(lblNewLabel_1_2);

		JLabel lblNewLabel_1_1_1 = new JLabel("\u015Eifre  :");
		lblNewLabel_1_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblNewLabel_1_1_1.setBounds(75, 60, 48, 32);
		w_doctorLogin.add(lblNewLabel_1_1_1);

		fld_doctorTc = new JTextField();
		fld_doctorTc.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		fld_doctorTc.setColumns(10);
		fld_doctorTc.setBounds(133, 11, 221, 32);
		w_doctorLogin.add(fld_doctorTc);

		JButton btn_doctorLogin = new JButton("Giri\u015F Yap");
		btn_doctorLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_doctorTc.getText().length() == 0 || fld_doctorPass.getText().length() == 0) {
					Helper.showMsg("fill");
				} else {
					try {
						Connection con = conn.connDb();
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM user");
						while (rs.next()) {
							if (fld_doctorTc.getText().equals(rs.getString("tcno"))
									&& fld_doctorPass.getText().equals(rs.getString("password"))) {
								if(rs.getString("type").equals("bashekim")){
									Bashekim bhekim = new Bashekim();
									bhekim.setId(rs.getInt("id"));
									bhekim.setPassword("password");
									bhekim.setTcno(rs.getString("tcno"));
									bhekim.setName(rs.getString("name"));
									bhekim.setType(rs.getString("type"));
									BashekimGUI bGUI =new BashekimGUI(bhekim);
									bGUI.setVisible(true);
									dispose();
								}
								if(rs.getString("type").equals("doktor")) {
									Doctor doctor=new Doctor();
									doctor.setId(rs.getInt("id"));
									doctor.setPassword("password");
									doctor.setTcno(rs.getString("tcno"));
									doctor.setName(rs.getString("name"));
									doctor.setType(rs.getString("type"));
									DoctorGUI dGUI=new DoctorGUI(doctor);
									dGUI.setVisible(true);
									dispose();
								}
							}
						}
					} catch (SQLException e1) {

						e1.printStackTrace();
					}
				}
			}
		});
		btn_doctorLogin.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btn_doctorLogin.setBounds(226, 104, 128, 32);
		w_doctorLogin.add(btn_doctorLogin);

		fld_doctorPass = new JPasswordField();
		fld_doctorPass.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		fld_doctorPass.setBounds(133, 60, 221, 32);
		w_doctorLogin.add(fld_doctorPass);
	}
}
