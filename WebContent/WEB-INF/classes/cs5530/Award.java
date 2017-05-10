package cs5530;

import java.sql.ResultSet;
import java.sql.Statement;

public class Award {
	public Award() {
	}

	public String bestUsers(String username, String m, Statement stmt) {
		
		String checkAdmin=String.format("select userType from Users where login='%s'", username);
		String mostTrusted = String
				.format("select login2,SUM(isTrusted) from Trust group by login2 order by SUM(isTrusted) DESC limit %s", m);
		String mostUseful = "select f.login from Rates r,Feedback f where r.fid=f.fid group by f.login order by AVG(r.rating)DESC ";
		String output = "";
		ResultSet rs0 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		try {
			rs0=stmt.executeQuery(checkAdmin);
			if(!rs0.next() || rs0.getInt(1)!=1 )
				return "You are not allowed to access this information";
			
			rs = stmt.executeQuery(mostTrusted);
			output += "Most trusted users: \n";
			while (rs.next()) {				
				output += rs.getString("login2") + "\n";
			}
			rs.close();
			rs2 = stmt.executeQuery(mostUseful);
			output += "Most useful users: \n";
			while (rs2.next()) {
				output += rs2.getString("f.login") + "\n";
			}
		} catch (Exception e) {
			System.out.println("cannot execute the query");
		} finally {
			try {
				if (rs0 != null && !rs0.isClosed())
					rs0.close();
				if (rs != null && !rs.isClosed())
					rs.close();
				if (rs2 != null && !rs2.isClosed())
					rs2.close();
			} catch (Exception e) {
				System.out.println("cannot close resultset");
			}
		}
		return output;
	}
}
