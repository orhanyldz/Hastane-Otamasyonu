package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import Model.*;
import Helper.*;

public class Bashekim extends User {

	Connection con = conn.connDb();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement prepareStatement = null;

	public Bashekim(int id, String tcno, String name, String password, String type) {
		super(id, tcno, name, password, type);
	}

	public Bashekim() {
	}

	public ArrayList<User> getDoctorList() throws SQLException {
		ArrayList<User> list = new ArrayList<>();

		User obj;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM user WHERE type = 'doktor'");
			while (rs.next()) {
				obj = new User(rs.getInt("id"), rs.getString("tcno"), rs.getString("name"), rs.getString("password"),
						rs.getString("type"));
				list.add(obj);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public ArrayList<User> getClinicDoctorList(int clinic_id) throws SQLException {
		ArrayList<User> list = new ArrayList<>();

		User obj;
		try {
			st = con.createStatement();
			rs = st.executeQuery(
					"SELECT u.id,u.tcno,u.type,u.name,u.password FROM worker w LEFT JOIN user u ON w.user_id =u.id WHERE clinic_id="
							+ clinic_id);
			while (rs.next()) {
				obj = new User(rs.getInt("u.id"), rs.getString("u.tcno"), rs.getString("u.name"),
						rs.getString("u.password"), rs.getString("u.type"));
				list.add(obj);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public boolean addDoctor(String tcno, String password, String name) throws SQLException {

		String query = "INSERT INTO user" + "(tcno,password,name,type) VALUES" + "(?,?,?,?)";
		boolean key = false;
		try {
			st.getConnection().createStatement();
			prepareStatement = con.prepareStatement(query);
			prepareStatement.setString(1, tcno);
			prepareStatement.setString(2, password);
			prepareStatement.setString(3, name);
			prepareStatement.setString(4, "doktor");
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

	public boolean deleteDoctor(int id) throws SQLException {

		String query = "DELETE FROM user WHERE id = ? ";
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

	public boolean updateDoctor(int id, String tcno, String password, String name) throws SQLException {

		String query = "UPDATE user SET name=?,tcno=?,password=? WHERE id =? ";
		boolean key = false;
		try {
			st = con.createStatement();
			prepareStatement = con.prepareStatement(query);
			prepareStatement.setString(1, name);
			prepareStatement.setString(2, tcno);
			prepareStatement.setString(3, password);
			prepareStatement.setInt(4, id);
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

	public boolean addWorker(int user_id, int clinic_id) throws SQLException {

		String query = "INSERT INTO worker " + "(user_id,clinic_id) VALUES" + "(?,?)";
		boolean key = false;
		int count = 0;

		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM worker WHERE clinic_id= " + clinic_id + " AND user_id= " + user_id);
			while (rs.next()) {
				count++;
			}
			if (count == 0) {
				prepareStatement = con.prepareStatement(query);
				prepareStatement.setInt(1, user_id);
				prepareStatement.setInt(2, clinic_id);
				prepareStatement.executeUpdate();
			}
			key = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (key)
			return true;
		else
			return false;
	}

}
