package cs5530;

import java.sql.ResultSet;
import java.sql.Statement;

public class THsuggestion {
	public THsuggestion() {
	}

	public String suggestTH(String login, String thname, Statement stmt) {
		String popular = String.format(
				"SELECT t1.name, count(v1.hid) as times from TH t1,Visit v1,(SELECT DISTINCT v.login from Visit v, TH t WHERE v.login !='%s' and t.name LIKE '%s%s%s' and v.hid=t.hid ) as s WHERE s.login=v1.login and v1.hid=t1.hid and v1.hid !=(select hid from TH where name LIKE '%s%s%s') GROUP BY t1.name ORDER BY times DESC",
				login, '%',thname,'%','%',thname,'%');

		StringBuilder output = new StringBuilder();
		ResultSet rs = null;

		try {
			rs = stmt.executeQuery(popular);
			if (rs.next()) {
			    //output.append("Similar users also choose these housings:<br>");
				output.append(rs.getString(1));
				output.append("<br>");
			}

			while (rs.next()) {
				output.append(rs.getString(1));
				output.append("<br>");
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
		return output.toString();
	}
}
