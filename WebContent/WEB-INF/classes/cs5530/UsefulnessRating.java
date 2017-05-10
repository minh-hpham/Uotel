package cs5530;
// later add update usefullness rating
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;

public class UsefulnessRating {
	public UsefulnessRating(){}
	public String AddUsefulnessRating(String name, String login,String user, String rating, Statement stmt ){
		ResultSet rs0=null;
		ResultSet rs=null;
		ResultSet rs2=null;
		String getfid = String.format("SELECT fid from Feedback, TH where Feedback.login='%s' and TH.name LIKE '%s%s%s' and TH.hid=Feedback.hid",login, '%', name, '%');
		
		try{
			int fid = 0;
			rs0 = stmt.executeQuery(getfid);
			if (rs0.next())
				fid = rs0.getInt(1);
			
			String addRating = "SELECT * FROM Rates WHERE login = '"+user+"' AND fid = "+fid+"";
			rs=stmt.executeQuery(addRating);
			if(rs.next()){
				return "You have already rated this Feedback";
			}
			else{
				// checks if the person owns the TH
				String CheckOwner = "SELECT TH.login FROM Feedback, TH WHERE fid = "+fid+" AND TH.hid = Feedback.hid";
				rs2 = stmt.executeQuery(CheckOwner);
				while(rs2.next()) 
						if(rs2.getString("login").equals(user))
							return "You own this TH you cannot vote for it";
				Calendar calendar = Calendar.getInstance();
			      java.sql.Date time = new java.sql.Date(calendar.getTime().getTime());
				String addRatingSql="INSERT INTO Rates(login, fid, rating)"
   		 				+ "VALUES ('"+user+"',"+fid+", '"+rating+"')";
				stmt.executeUpdate(addRatingSql);
   		 		return ("Your feedback rating was added");
			}
		}
	 	catch(Exception e)
	 	{
	 		System.out.println("cannot execute the query");
	 	}
	 	finally
	 	{
	 		try{
		 		if (rs!=null && !rs.isClosed())
		 			rs.close();
		 		if (rs2!=null && !rs2.isClosed())
		 			rs2.close();
	 		}
	 		catch(Exception e)
	 		{
	 			System.out.println("cannot close resultset");
	 		}
	 	}
		return "";
	}
	public String RemoveUsefulnessRating(String name, String login,String user,Statement stmt){
		ResultSet rs0=null;
		ResultSet rs=null;
		ResultSet rs2=null;
		String getfid = String.format("SELECT fid from Feedback, TH where Feedback.login='%s' and TH.name LIKE '%s%s%s' and TH.hid=Feedback.hid",login, '%', name, '%');
		
		
		try{
			int fid = 0;
			rs0 = stmt.executeQuery(getfid);
			if (rs0.next())
				fid = rs0.getInt(1);
			String addRating = "SELECT * FROM Rates WHERE login = '"+user+"' AND fid = "+fid+"";
			rs=stmt.executeQuery(addRating);
			if(!rs.next()){
				return "You have not rated this Feedback";
			}
			else{
				// checks if the person owns the TH
				String addRatingSql="DELETE FROM Rates WHERE login = '"+user+"' AND fid = "+fid;
				stmt.executeUpdate(addRatingSql);
   		 		return ("Your feedback rating was added");
			}
		}
	 	catch(Exception e)
	 	{
	 		System.out.println("cannot execute the query");
	 	}
	 	finally
	 	{
	 		try{
		 		if (rs!=null && !rs.isClosed())
		 			rs.close();
		 		if (rs2!=null && !rs2.isClosed())
		 			rs2.close();
	 		}
	 		catch(Exception e)
	 		{
	 			System.out.println("cannot close resultset");
	 		}
	 	}
		return "";
	}
}
