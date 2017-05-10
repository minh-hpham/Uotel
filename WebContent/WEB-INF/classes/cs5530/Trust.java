package cs5530;


import java.sql.Statement;

public class Trust {
	public Trust() {
	}

	public String recordTrust(String user1, String user2,String trust, Statement stmt) {
		int score = 0;
		
		String output = "";
		int rs = 0;
		try {
			if(trust.equals("yes")) score = 1;
			else if (trust.equals("no")) score = -1;
			String addTrust = String.format("INSERT INTO Trust (login1,login2,isTrusted) VALUES ('%s','%s',%s)",user1,user2,score);
			if(score != -1)
			{
				rs = stmt.executeUpdate(addTrust);
				if (rs>0) {
					output += "Your session is recorded";
					System.out.println("You have given feedback for this TH");
				} else {
					System.out.println("cannot execute the query");
				}
			}			
		} catch (Exception e) {
			System.out.println("cannot execute the query");
		} finally {
			try {
			} catch (Exception e) {
				System.out.println("cannot close resultset");
			}
		}
		return output;
	}
}
