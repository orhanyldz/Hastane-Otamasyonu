package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import Model.*;
import Helper.*;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTable;

public class HastaGUI extends JFrame {

	private JPanel w_pane;
	private static Hasta hasta = new Hasta();
	private Clinic clinic = new Clinic();
	private JTable table_doctor;
	private DefaultTableModel doctorModel;
	private Object[] doctorData = null;
	private JTable table_whour;
	private Whour whour = new Whour();
	private DefaultTableModel whourModel;
	private Object[] whourData = null;
	private int selectDoctorID = 0;
	private String selectDoctorName = null;
	private JTable table_appoint;
	private DefaultTableModel appointModel;
	private Object[] appointData = null;
	private Appointment appoint = new Appointment();
	private JTextField fld_appointID;
	private JTextField fld_doctorID;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HastaGUI frame = new HastaGUI(hasta);
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
	public HastaGUI(Hasta hasta) throws SQLException {

		doctorModel = new DefaultTableModel();
		Object[] colDoctor = new Object[2];
		colDoctor[0] = "ID";
		colDoctor[1] = "Ad Soyad";
		doctorModel.setColumnIdentifiers(colDoctor);
		doctorData = new Object[2];

		whourModel = new DefaultTableModel();
		Object[] colWhour = new Object[2];
		colWhour[0] = "ID";
		colWhour[1] = "Tarih";
		whourModel.setColumnIdentifiers(colWhour);
		whourData = new Object[2];

		appointModel = new DefaultTableModel();
		Object[] colAppoint = new Object[4];
		colAppoint[0] = "ID";	
		colAppoint[1] = "Doktor ID";	
		colAppoint[2] = "Doktor";
		colAppoint[3] = "Tarih";
		appointModel.setColumnIdentifiers(colAppoint);
		appointData = new Object[4];
		for (int i = 0; i < appoint.getHastaList(hasta.getId()).size(); i++) {
			appointData[0] = appoint.getHastaList(hasta.getId()).get(i).getId();
			appointData[1] = appoint.getHastaList(hasta.getId()).get(i).getDoctorID();
			appointData[2] = appoint.getHastaList(hasta.getId()).get(i).getDoctorName();
			appointData[3] = appoint.getHastaList(hasta.getId()).get(i).getAppDate();
			appointModel.addRow(appointData);
		}

		setTitle("Hastane Y\u00F6netim Sistemi");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 500);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Ho\u015Fgeldiniz Say\u0131n   " + hasta.getName());
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblNewLabel.setBounds(10, 11, 266, 31);
		w_pane.add(lblNewLabel);

		JButton btnNewButton = new JButton("\u00C7\u0131k\u0131\u015F");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI login = new LoginGUI();
				login.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btnNewButton.setBounds(423, 18, 111, 23);
		w_pane.add(btnNewButton);

		JTabbedPane w_tab = new JTabbedPane(JTabbedPane.TOP);
		w_tab.setBounds(10, 66, 524, 394);
		w_pane.add(w_tab);

		JPanel w_appointment = new JPanel();
		w_appointment.setBackground(Color.WHITE);
		w_tab.addTab("Randevu Sistemi", null, w_appointment, null);
		w_appointment.setLayout(null);

		JScrollPane w_scrollDoctor = new JScrollPane();
		w_scrollDoctor.setBounds(10, 29, 149, 326);
		w_appointment.add(w_scrollDoctor);

		table_doctor = new JTable(doctorModel);
		w_scrollDoctor.setViewportView(table_doctor);

		JLabel lblNewLabel_1 = new JLabel("Doktor Listesi");
		lblNewLabel_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(10, 4, 113, 21);
		w_appointment.add(lblNewLabel_1);

		JLabel lblNewLabel_1_3 = new JLabel("Polikinlik Ad\u0131");
		lblNewLabel_1_3.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblNewLabel_1_3.setBounds(169, 4, 120, 21);
		w_appointment.add(lblNewLabel_1_3);

		JComboBox select_clinic = new JComboBox();
		select_clinic.setBounds(169, 23, 135, 29);
		select_clinic.addItem("--Polinik Seç");
		for (int i = 0; i < clinic.getList().size(); i++) {
			select_clinic.addItem(new Item(clinic.getList().get(i).getId(), clinic.getList().get(i).getName()));
		}
		select_clinic.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (select_clinic.getSelectedIndex() != 0) {
					JComboBox c = (JComboBox) e.getSource();
					Item item = (Item) c.getSelectedItem();
					DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel();
					clearModel.setRowCount(0);

					try {
						for (int i = 0; i < clinic.getClinicDoctorList(item.getKey()).size(); i++) {
							doctorData[0] = clinic.getClinicDoctorList(item.getKey()).get(i).getId();
							doctorData[1] = clinic.getClinicDoctorList(item.getKey()).get(i).getName();
							doctorModel.addRow(doctorData);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel();
					clearModel.setRowCount(0);
				}

			}
		});
		w_appointment.add(select_clinic);

		JLabel lblNewLabel_1_3_1 = new JLabel("Doktor Se\u00E7");
		lblNewLabel_1_3_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblNewLabel_1_3_1.setBounds(169, 77, 95, 21);
		w_appointment.add(lblNewLabel_1_3_1);

		JButton btn_selDoctor = new JButton("Se\u00E7");
		btn_selDoctor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table_doctor.getSelectedRow();
				if (row >= 0) {
					String value = table_doctor.getModel().getValueAt(row, 0).toString();
					int id = Integer.parseInt(value);
					DefaultTableModel clearModel = (DefaultTableModel) table_whour.getModel();
					clearModel.setRowCount(0);

					try {
						for (int i = 0; i < whour.getWhourList(id).size(); i++) {
							whourData[0] = whour.getWhourList(id).get(i).getId();
							whourData[1] = whour.getWhourList(id).get(i).getWdate();
							whourModel.addRow(whourData);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					table_whour.setModel(whourModel);
					selectDoctorID = id;
					selectDoctorName = table_doctor.getModel().getValueAt(row, 1).toString();
					// System.out.println(selectDoctorID+"-"+selectDoctorName);

				} else {
					Helper.showMsg("Lütfen Bir Doktor Seçiniz");
				}
			}
		});
		btn_selDoctor.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btn_selDoctor.setBounds(169, 109, 135, 29);
		w_appointment.add(btn_selDoctor);

		JScrollPane w_scrollWhour = new JScrollPane();
		w_scrollWhour.setBounds(314, 29, 195, 326);
		w_appointment.add(w_scrollWhour);

		table_whour = new JTable(whourModel);
		w_scrollWhour.setViewportView(table_whour);
		table_whour.getColumnModel().getColumn(0).setPreferredWidth(3);

		JLabel lblNewLabel_1_1 = new JLabel("Doktor Listesi");
		lblNewLabel_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblNewLabel_1_1.setBounds(314, 4, 113, 21);
		w_appointment.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_3_1_1 = new JLabel("Randevu Al");
		lblNewLabel_1_3_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblNewLabel_1_3_1_1.setBounds(169, 173, 95, 21);
		w_appointment.add(lblNewLabel_1_3_1_1);

		JButton btn_addAppoint = new JButton("Randevu Al");
		btn_addAppoint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow = table_whour.getSelectedRow();
				if (selRow >= 0) {
					String date = table_whour.getModel().getValueAt(selRow, 1).toString();
					try {
						boolean control = hasta.addAppointment(selectDoctorID, hasta.getId(), selectDoctorName,
								hasta.getName(), date);
						if (control) {
							Helper.showMsg("success");
							hasta.updateWhourStatus(selectDoctorID, date);
							updateWhourModel(selectDoctorID);
							updateAppointModel(hasta.getId());

						} else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				} else {
					Helper.showMsg("Lütfen Gecerli Bir Tarih Giriniz");
				}
			}
		});
		btn_addAppoint.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btn_addAppoint.setBounds(169, 205, 135, 29);
		w_appointment.add(btn_addAppoint);

		JPanel w_appoint = new JPanel();
		w_appoint.setBackground(Color.WHITE);
		w_tab.addTab("Randevularým", null, w_appoint, null);
		w_appoint.setLayout(null);

		JScrollPane w_scrollAppoint = new JScrollPane();
		w_scrollAppoint.setBounds(10, 11, 349, 344);
		w_appoint.add(w_scrollAppoint);

		table_appoint = new JTable(appointModel);
		w_scrollAppoint.setViewportView(table_appoint);
		table_appoint.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				try {
					fld_appointID.setText(table_appoint.getValueAt(table_appoint.getSelectedRow(), 0).toString());
					fld_doctorID.setText(table_appoint.getValueAt(table_appoint.getSelectedRow(),1).toString());

				} catch (Exception e2) {
					// TODO: handle exception
				}

			}

		});

		JLabel lblNewLabel_1_2_1 = new JLabel("Randevu ID");
		lblNewLabel_1_2_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblNewLabel_1_2_1.setBounds(369, 11, 139, 29);
		w_appoint.add(lblNewLabel_1_2_1);

		fld_appointID = new JTextField();
		fld_appointID.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		fld_appointID.setColumns(10);
		fld_appointID.setBounds(369, 43, 139, 29);
		w_appoint.add(fld_appointID);

		JButton btn_delRandevu = new JButton("Sil");
		btn_delRandevu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				if (fld_appointID.getText().length() == 0 || fld_doctorID.getText().length()==0) {
					Helper.showMsg("Lütfen Silinecek Doktoru Seciniz");
				} else {
					int selRow = table_appoint.getSelectedRow();
					if (Helper.confirm("sure")) {
						String date = table_appoint.getModel().getValueAt(selRow, 3).toString();	
						int selectID = Integer.parseInt(fld_appointID.getText());
						int SelID = Integer.parseInt(fld_doctorID.getText());
						try {
							boolean  appoint= hasta.guncelWhourStatus(SelID, date);
							if(appoint) {
								fld_doctorID.setText(null);
							}
						} catch (SQLException e2) {
							
							e2.printStackTrace();
						}
						try {						
							boolean control = hasta.deleteAppointment(selectID);
							
							if (control) {
								Helper.showMsg("success");
								fld_appointID.setText(null);									
								updateWhourModel(selectDoctorID);
								updateAppointModel(hasta.getId());
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		});
		btn_delRandevu.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		btn_delRandevu.setBounds(369, 120, 142, 29);
		w_appoint.add(btn_delRandevu);
		
		fld_doctorID = new JTextField();
		fld_doctorID.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 15));
		fld_doctorID.setColumns(10);
		fld_doctorID.setBounds(369, 83, 139, 29);
		w_appoint.add(fld_doctorID);
	}

	public void updateWhourModel(int doctor_id) throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_whour.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < whour.getWhourList(doctor_id).size(); i++) {
			whourData[0] = whour.getWhourList(doctor_id).get(i).getId();
			whourData[1] = whour.getWhourList(doctor_id).get(i).getWdate();
			whourModel.addRow(whourData);
		}
	}

	public void updateAppointModel(int hasta_id) throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_appoint.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < appoint.getHastaList(hasta_id).size(); i++) {
			appointData[0] = appoint.getHastaList(hasta_id).get(i).getId();
			appointData[1] = appoint.getHastaList(hasta_id).get(i).getDoctorID();
			appointData[2] = appoint.getHastaList(hasta_id).get(i).getDoctorName();
			appointData[3] = appoint.getHastaList(hasta_id).get(i).getAppDate();
			appointModel.addRow(appointData);
		}
	}
}
