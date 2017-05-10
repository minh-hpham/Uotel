package cs5530;

import java.sql.ResultSet;
import java.sql.Statement;

public class Stays {
	public Stays() {
	}

	public String recordVisit(String login, String name, String startDay, String endDay, String cost,String people,
			Statement stmt) {
		
		String gethid = String.format("SELECT hid from TH where name LIKE '%s%s%s'", '%',name,'%');
		String output = "";
		ResultSet rs0 = null;
		ResultSet rs = null;

		try {
			int hid=0;
			rs0=stmt.executeQuery(gethid);
			if(rs0.next())
				hid=rs0.getInt(1);
			
			String checkPeriod = String.format("select R.pid from Reserve R,Period P where R.login='%s'"
					+ "and R.hid=%s and R.pid=P.pid and P.start >='%s' and P.end<='%s'",login,hid,startDay,endDay);
			
			rs = stmt.executeQuery(checkPeriod);
			if (rs.next()) {
				String pid = rs.getString("R.pid");
				// TODO: ADD COST FOR STAYTING PERIOD
				String addVisit = String.format("INSERT INTO Visit (login,hid,pid) VALUES ('%s',%s,%s)",login,hid,pid);
				stmt.executeUpdate(addVisit);
				if(!cost.isEmpty())
		 			stmt.executeUpdate(String.format("UPDATE Visit SET cost=%s where login='%s' and hid=%s and pid=%s",cost,login,hid,pid));
	 			if(!people.isEmpty())
	 				stmt.executeUpdate(String.format("UPDATE Visit SET people=%s where login='%s' and hid=%s and pid=%s",people,login,hid,pid));
	 			
				output += "Your session is recorded";
			} else {

				System.out.println(login+" didn't not reserve this housing in this period");
			}
			rs.close();
		} catch (Exception e) {
			System.out.println("cannot execute the query");
		} finally {
			try {
				if (rs != null && !rs.isClosed())
					rs.close();
			} catch (Exception e) {
				System.out.println("cannot close resultset");
			}
		}
		return output;
	}
}
