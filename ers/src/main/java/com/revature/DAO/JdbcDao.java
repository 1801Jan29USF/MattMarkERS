package com.revature.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.beans.Reimbursement;
import com.revature.beans.User;

public class JdbcDao {
	private Logger log = Logger.getRootLogger();
	static {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	// add a new user to the database
	public int saveUser(User u) {
		
		try (Connection con = DriverManager.getConnection(
				"jdbc:oracle:thin:@orcl.c5yldq6qf75t.us-east-2.rds.amazonaws.com:1521:orcl", "ers", "pass")) {
			log.trace("connection established");
			PreparedStatement ps = con.prepareStatement("INSERT INTO ers_users "
					+ "(ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id, ers_users_id) "
					+ "VALUES (?,?,?,?,?,?,?)");
			ps.setString(1, u.getUsername());
			ps.setString(2, u.getPassword());
			ps.setString(3, u.getFirstName());
			ps.setString(4, u.getLastName());
			ps.setString(5, u.getEmail());
			ps.setInt(6, u.getRole());
			ps.setInt(7, u.getId());
			int s = ps.executeUpdate();
			log.debug(s + " rows inserted");

		} catch (SQLException e) {
			log.warn("failed");
		}
		return 0;
	}

	// authenticate a user and get their balance
	public User getUser(String u, String p) {
		try (Connection con = DriverManager.getConnection(
				"jdbc:oracle:thin:@orcl.c5yldq6qf75t.us-east-2.rds.amazonaws.com:1521:orcl", "ers", "pass")) {
			log.trace("connection established");
			PreparedStatement ps = con
					.prepareStatement("SELECT * FROM ers_users WHERE ers_username = ? AND ers_password = ?");
			ps.setString(1, u);
			ps.setString(2, p);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				User user = new User(rs.getString("ers_username"), rs.getString("ers_password"),
						rs.getString("user_first_name"), rs.getString("user_last_name"), rs.getString("user_email"),
						rs.getInt("user_role_id"), rs.getInt("ers_users_id"));
				log.trace("username " + user.getUsername());
				return user;
			} else {
				log.trace("user not found");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("failed");
		}

		return null;
	}

	// save a new reimbursement
	public boolean saveReimbursement(Reimbursement r, User u) {
		
		Calendar calendar = Calendar.getInstance();
		Timestamp currentTimestamp = new Timestamp(calendar.getTime().getTime());
		try (Connection con = DriverManager.getConnection(
				"jdbc:oracle:thin:@orcl.c5yldq6qf75t.us-east-2.rds.amazonaws.com:1521:orcl", "ers", "pass")) {
			log.trace("connection established");
			PreparedStatement ps = con.prepareStatement("INSERT INTO ers_reimbursement "
					+ "(reimb_amount, reimb_description, reimb_submitted, reimb_author, reimb_status_id, reimb_type_id) " + "VALUES (?,?,?,?,1,?)");
			ps.setDouble(1, r.getAmount());
			ps.setString(2, r.getDescription());
			ps.setTimestamp(3, currentTimestamp);
			ps.setInt(4,  u.getId());
			ps.setInt(5,  r.getType());

			int s = ps.executeUpdate();
			log.debug(s + " rows inserted");
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("failed");
		}
		return false;
	}

	// get a list of pending reimbursements
	public List<Reimbursement> getPending() {
		try (Connection con = DriverManager.getConnection(
				"jdbc:oracle:thin:@orcl.c5yldq6qf75t.us-east-2.rds.amazonaws.com:1521:orcl", "ers", "pass")) {
			log.trace("connection established");
			PreparedStatement ps = con.prepareStatement(
					"SELECT * FROM ers_reimbursement INNER JOIN ers_users ON ers_users_id = reimb_author WHERE reimb_status_id = 1");
			ResultSet rs = ps.executeQuery();
			List<Reimbursement> rList = new ArrayList<>();
			while (rs.next()) {
				Reimbursement r = new Reimbursement(rs.getInt("reimb_id"), rs.getDouble("reimb_amount"), rs.getTimestamp("reimb_submitted"),
						rs.getTimestamp("reimb_resolved"), rs.getString("reimb_description"), rs.getInt("reimb_status_id"),
						rs.getInt("reimb_type_id"), rs.getString("user_first_name") + " " + rs.getString("user_last_name"), "");
				log.trace("reimbursement " + r.getaName());
				rList.add(r);
			}
			return rList;
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("failed");
		}

		return null;
	}

	// get a list of reimbursements by user
	public List<Reimbursement> getByUser(User u) {
		try (Connection con = DriverManager.getConnection(
				"jdbc:oracle:thin:@orcl.c5yldq6qf75t.us-east-2.rds.amazonaws.com:1521:orcl", "ers", "pass")) {
			log.trace("connection established");
			PreparedStatement ps = con.prepareStatement("SELECT * FROM ers_reimbursement " +
					"RIGHT OUTER JOIN ers_users author " +
					"ON ers_reimbursement.reimb_author = author.ers_users_id " +
					"FULL OUTER JOIN ers_users resolv " +
					"ON ers_reimbursement.reimb_resolver = resolv.ers_users_id " +
					"WHERE author.ers_users_id = ?");
			ps.setInt(1, u.getId());
			ResultSet rs = ps.executeQuery();
			log.trace("user: " + u.getUsername());
			List<Reimbursement> rList = new ArrayList<>();
			while (rs.next()) {
				Reimbursement r = new Reimbursement(rs.getInt("reimb_id"), rs.getDouble("reimb_amount"), rs.getTimestamp("reimb_submitted"),
						rs.getTimestamp("reimb_resolved"), rs.getString("reimb_description"), rs.getInt("reimb_status_id"),
						rs.getInt("reimb_type_id"), rs.getString(21), rs.getString(22));
				log.trace("reimbursement " + r.getSubmitted());
				rList.add(r);
			}
			return rList;
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("failed");
		}

		return null;
	}

	// update reimbursement status
	public boolean update(Reimbursement r, User u) {
		
		Calendar calendar = Calendar.getInstance();
		Timestamp currentTimestamp = new Timestamp(calendar.getTime().getTime());
		try (Connection con = DriverManager.getConnection(
				"jdbc:oracle:thin:@orcl.c5yldq6qf75t.us-east-2.rds.amazonaws.com:1521:orcl", "ers", "pass")) {
			log.trace("connection established");
			PreparedStatement ps = con.prepareStatement("UPDATE ers_reimbursement "
					+ "SET reimb_status_id = ?, reimb_resolved = ?, reimb_resolver = ? "
					+ "WHERE reimb_id = ?");
			ps.setInt(1, r.getStatus());
			ps.setTimestamp(2, currentTimestamp);
			ps.setInt(3, u.getId());
			ps.setInt(4, r.getId());

			int s = ps.executeUpdate();
			log.debug(s + " rows updated");
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("failed");
		}
		return false;

	}

}
