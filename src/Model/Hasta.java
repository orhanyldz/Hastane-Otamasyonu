package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import Helper.*;
import Model.*;

import javax.swing.JOptionPane;

public class Hasta extends User {

	Connection con = conn.connDb();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement prepareStatement = null;

	public Hasta() {
		super();

	}

	public Hasta(int id, String tcno, String name, String password, String type) {
		super(id, tcno, name, password, type);

	}
	
	public boolean deleteRandevu(int id) throws SQLException {

		String query = "DELETE FROM  appointment WHERE id = ? ";
		boolean key = false;
		try {
			st.getConnection().createStatement();
			prepareStatement = con.prepareStatement(query);
			prepareStatement.setInt(1, id);
			prepareStatement.executeUpdate();
			key = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (key)
			return true;
		else
			return false;
	}

	
	
	
	

	public boolean register(String tcno, String password, String name) throws SQLException {
		int key = 0;
		boolean duplicate = false;
		String query = "INSERT INTO user" + "(tcno,password,name,type) VALUES" + "(?,?,?,?)";

		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM user WHERE tcno ='" + tcno + "'");
			while (rs.next()) {
				duplicate = true;
				Helper.showMsg("BU TC'no  Numarasýna Kayýt Yapýlmýþtýr.");
				break;
			}
			if (!duplicate) {
				prepareStatement = con.prepareStatement(query);
				prepareStatement.setString(1, tcno);
				prepareStatement.setString(2, password);
				prepareStatement.setString(3, name);
				prepareStatement.setString(4, "hasta");
				prepareStatement.executeUpdate();
				key = 1;
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
		if (key == 1)
			return true;
		else
			return false;
	}

	public boolean addAppointment(int doctor_id, int hasta_id, String doctor_name, String hasta_name, String appDate)
			throws SQLException {
		int key = 0;

		String query = "INSERT INTO appointment" + "(doctor_id,doctor_name,hasta_id,hasta_name,app_date) VALUES"
				+ "(?,?,?,?,?)";

		try {

			prepareStatement = con.prepareStatement(query);
			prepareStatement.setInt(1, doctor_id);
			prepareStatement.setString(2, doctor_name);
			prepareStatement.setInt(3, hasta_id);
			prepareStatement.setString(4, hasta_name);
			prepareStatement.setString(5, appDate);
			prepareStatement.executeUpdate();
			key = 1;

		} catch (SQLException e) {

			e.printStackTrace();
		}
		if (key == 1)
			return true;
		else
			return false;
	}

	public boolean updateWhourStatus(int doctor_id, String wdate) throws SQLException {
		int key = 0;
		String query = "UPDATE whour SET status = ? WHERE doctor_id=? AND wdate=?";

		try {

			prepareStatement = con.prepareStatement(query);
			prepareStatement.setString(1, "p");
			prepareStatement.setInt(2, doctor_id);
			prepareStatement.setString(3, wdate);
			prepareStatement.executeUpdate();
			key = 1;

		} catch (SQLException e) {

			e.printStackTrace();
		}
		if (key == 1)
			return true;
		else
			return false;
	}
	
	
	
	public boolean deleteAppointment(int id) throws SQLException {

		String query = "DELETE FROM appointment  WHERE id = ? ";
		boolean key = false;
		try {
			st = con.createStatement();
			prepareStatement = con.prepareStatement(query);
			prepareStatement.setInt(1, id);
			prepareStatement.executeUpdate();
			key = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (key)
			return true;
		else
			return false;
	}
	public boolean guncelWhourStatus(int doctor_id, String wdate) throws SQLException {
		int key = 0;
		String query = "UPDATE whour SET status = ? WHERE doctor_id=? AND wdate=?";

		try {

			prepareStatement = con.prepareStatement(query);
			prepareStatement.setString(1, "a");
			prepareStatement.setInt(2, doctor_id);
			prepareStatement.setString(3, wdate);
			prepareStatement.executeUpdate();
			key = 1;

		} catch (SQLException e) {

			e.printStackTrace();
		}
		if (key == 1)
			return true;
		else
			return false;
	}
	
	
}
