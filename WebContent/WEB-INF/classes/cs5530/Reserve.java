package cs5530;

import java.sql.ResultSet;
import java.sql.Statement;

public class Reserve {
	public Reserve() {		
	}
	
	/*public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(THname);
		sb.append(",");
		sb.append(startDate);
		sb.append(",");
		sb.append(endDate);
		sb.append(",");
		sb.append(cost);
		return sb.toString();
	}*/
	public String reserve(String login, String startDate, String endDate, String name, String cost, Statement stmt) {
		
		
		String gethid = String.format("SELECT hid from TH where name LIKE '%s%s%s'", '%',name,'%');
		String output="";
		ResultSet rs0 = null;
		ResultSet rs = null;
		
		try {
			
			int hid=0;
			rs0=stmt.executeQuery(gethid);
			if(rs0.next())
				hid=rs0.getInt(1);
			
			String checkAvaliableTimesSql = String.format("select p.pid,p.start,p.end from Period p, Available a where a.hid=%s and p.pid=a.pid and p.start<='%s' and p.end>='%s'",hid,startDate,endDate);
			rs = stmt.executeQuery(checkAvaliableTimesSql);
			if (rs.next()) {
				String pid = rs.getString("pid");
				String start =rs.getString("start");
				String end = rs.getString("end");
				
				String updatePeriod1=String.format("INSERT INTO Period (start,end) VALUES ('%s',DATE_SUB('%s',INTERVAL 1 DAY))", start,startDate);
				String updateAvailable1=String.format("INSERT INTO Available (hid,pid) VALUES (%s,(SELECT pid from Period where start='%s' and end=DATE_SUB('%s',INTERVAL 1 DAY)))", hid,start,startDate);
				String updatePeriod=String.format("UPDATE Period SET start='%s',end='%s' where pid='%s'", startDate,endDate,pid);
				String removeAvailable=String.format("DELETE from Available where pid=%s", pid);
				String updatePeriod2= String.format("INSERT INTO Period (start,end) VALUES (DATE_ADD('%s',INTERVAL 1 DAY),'%s')", endDate,end);
				String updateAvailable2=String.format("INSERT INTO Available (hid,pid) VALUES (%s,(SELECT pid from Period where start=DATE_ADD('%s',INTERVAL 1 DAY) and end='%s'))", hid,endDate,end);
				
				if(start.equals(startDate) == false){
					stmt.executeUpdate(updatePeriod1);
					stmt.executeUpdate(updateAvailable1);
				}
					
				if(end.equals(endDate)== false)
				{
					stmt.executeUpdate(updatePeriod2);
					stmt.executeUpdate(updateAvailable2);
				}
					
				stmt.executeUpdate(updatePeriod);
				stmt.executeUpdate(removeAvailable);
				
				String addReservationSql = String.format("INSERT INTO Reserve (login,hid,pid,cost) VALUES ('%s',%s,%s,%s)",login,hid,pid,cost);
				stmt.executeUpdate(addReservationSql);
				output+="Reservation has been added";
			}

			else {
				output+=name+" is not availble during this period";
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
			} catch (Exception e) {
				System.out.println("cannot close resultset");
			}
		}
		return output;
	}
}
