package cs5530;

import java.sql.ResultSet;
import java.sql.Statement;

public class Feedback {
	public Feedback() {
	}

	public String recordfb(String login, String name, String text, String score, Statement stmt) {
		if (score.isEmpty())
			return "You must provide a score";
		String gethid = String.format("SELECT hid from TH where name LIKE '%s%s%s'", '%', name, '%');

		String output = "";
		ResultSet rs0 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		try {
			int hid = 0;
			rs0 = stmt.executeQuery(gethid);
			if (rs0.next())
				hid = rs0.getInt(1);

			String checkUniqueTH = String.format("select fid from Feedback where login='%s' and hid=%s", login, hid);

			rs = stmt.executeQuery(checkUniqueTH);
			if (rs.next()) {
				System.out.println("You have given feedback for this TH");
			} else {
				// TODO: ADD COST FOR STAYTING PERIOD
				String addfb = String.format("INSERT INTO Feedback (login,hid,score,fbdate) VALUES ('%s',%s,%s,NOW())",
						login, hid, score);
				// stmt.executeUpdate(addfb);
				int fid = 0;
				fid = stmt.executeUpdate(addfb, Statement.RETURN_GENERATED_KEYS);
				rs1 = stmt.getGeneratedKeys();
				if (rs1.next()) // get hid
					fid = rs1.getInt(1);

				if (!text.isEmpty())
					stmt.executeUpdate(String.format("UPDATE Feedback SET text='%s' where fid=%s", text, fid));
				output += "Your session is recorded";
			}
			rs.close();
		} catch (Exception e) {
			System.out.println("cannot execute the query");
		} finally {
			try {
				if (rs != null && !rs.isClosed())
					rs.close();
				if (rs0 != null && !rs0.isClosed())
					rs0.close();
				if (rs1 != null && !rs1.isClosed())
					rs1.close();
			} catch (Exception e) {
				System.out.println("cannot close resultset");
			}
		}
		return output;
	}

	public String usefulfb(String name, String n, Statement stmt) {
		String gethid = String.format("SELECT hid from TH where name LIKE '%s%s%s'", '%', name, '%');
		// TODO: THIS QUERY MAY NOT BE CORRECT
		
		String output = "";
		ResultSet rs = null;
		ResultSet rs0 = null;
		try {
			int hid = 0;
			rs0 = stmt.executeQuery(gethid);
			if (rs0.next())
				hid = rs0.getInt(1);
			
			String sql = String.format("select R.fid, F.login, F.text from Feedback F, Rates R where F.hid=%s and F.fid=R.fid group by R.fid order by AVG(R.rating) DESC limit %s", hid, n);
			
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				output += rs.getString("F.login") + "   " + rs.getString("F.text")+ "\n";
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
