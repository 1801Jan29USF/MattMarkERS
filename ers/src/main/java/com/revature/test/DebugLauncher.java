package com.revature.test;

import com.revature.DAO.JdbcDao;
import com.revature.beans.User;

public class DebugLauncher {
	
	public static void main(String[] args) {
		
		JdbcDao dao = new JdbcDao();
		User u = new User("mattm", "pass", "Matt", "Mark", "mattm@gmail.com", 1, 0);
		dao.saveUser(u);
		u = new User("rickv", "pass", "Rick", "Vero", "rickv@gmail.com", 1, 0);
		dao.saveUser(u);
		u = new User("adrian", "pass", "Adrian", "Something", "adr@gmail.com", 1, 0);
		dao.saveUser(u);
		u = new User("bcross", "pass", "Brandon", "Cross", "bcr@gmail.com", 1, 0);
		dao.saveUser(u);
		u = new User("blake", "pass", "Blake", "Kruple", "Blake@gmail.com", 2, 0);
		dao.saveUser(u);
	}
}
