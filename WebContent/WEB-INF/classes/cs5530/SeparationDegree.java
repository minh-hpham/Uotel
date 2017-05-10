package cs5530;

import java.sql.ResultSet;
import java.sql.Statement;

public class SeparationDegree {
	public SeparationDegree() {
	}

	public String degreeOfSeparation(String user1, String user2, Statement stmt) {
		String oneDegree = String.format(
				"select f1.hid from Favorites f1, Favorites f2 where f1.login='%s' and f2.login='%s' and f1.hid=f2.hid",
				user1, user2);
		String twoDegree = String.format("select S1.login from "
				+ "(select f3.login,f3.hid from Favorites f1, Favorites f3 where f1.login='%s' and f3.login !='%s' and f1.hid=f3.hid) as S1,"
				+ "(select f3.login,f3.hid from Favorites f2, Favorites f3 where f2.login='%s' and f3.login !='%s' and f2.hid=f3.hid) as S2"
				+ " where S1.login=S2.login and S1.hid!=S2.hid", user1, user1, user2, user2);
		String output = "";
		ResultSet rs = null;
		ResultSet rs2 = null;
		try {
			rs = stmt.executeQuery(oneDegree);
			
			if (!rs.next()) {
				rs2 = stmt.executeQuery(twoDegree);
				if (rs2.next()) {
					output += "Yes";
				} else {
					output += "No";
				}

			} else {

				return "No";
			}
			rs.close();
			rs2.close();
		} catch (Exception e) {
			System.out.println("cannot execute the query");
		} finally {
			try {
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
