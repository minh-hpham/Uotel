package cs5530;
import java.sql.*;
public class Registration {
	public Registration()
	{}
	
	public String register(String username, String password,String name, String address, String phone,  Statement stmt)
	{		
		if(username.isEmpty()||password.isEmpty()||name.isEmpty())
			return "You should provide a login name, a password and a name\n";
		String checkuniqueSql=String.format("SELECT login FROM Users where login ='%s'",username);
	    String addUserSql= String.format("INSERT INTO Users (login,password,name) VALUES ('%s','%s','%s')", username,password,name);
		String output="";
		ResultSet rs=null;
		 	try{
		 		rs=stmt.executeQuery(checkuniqueSql);
		 		if(rs.next())
		 			System.out.println(username +" already exists in the database!");
		 		else 
		 		{
		 			stmt.executeUpdate(addUserSql);
		 			System.out.println(username+" is added to the database");
		 			update(username, password, name, address, phone, stmt);
		 		}
		 		rs.close();
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
		 		}
		 		catch(Exception e)
		 		{
		 			System.out.println("cannot close resultset" + e);
		 		}
		 	}
	    return output;
	}
	public String update(String username, String password,String name, String address, String phone,  Statement stmt)
	{		
		if(username.isEmpty()||password.isEmpty())
			return "You should provide a login name and a password\n";
		String checkuniqueSql=String.format("SELECT login FROM Users where login ='%s'",username);
		String addUserSql= String.format("INSERT INTO Users (login,password,name) VALUES ('%s','%s')", username,password);		 		
		String output="";
		ResultSet rs=null;
		 	try{
		 		rs=stmt.executeQuery(checkuniqueSql);
		 		if(rs.next()){
		 			if(!(password.isEmpty()||password == null))
			 			stmt.executeUpdate("UPDATE Users SET password ='"+password+"' WHERE login ='"+username+"'");// AND hid ='"+thid+"'");// name = tempString;
		 			if(!(name.isEmpty()||name == null))
			 			stmt.executeUpdate("UPDATE Users SET name ='"+name+"' WHERE login ='"+username+"'");// AND hid ='"+thid+"'");// name = tempString;
		 			if(!(address.isEmpty()||address == null))
			 			stmt.executeUpdate("UPDATE Users SET address ='"+address+"' WHERE login ='"+username+"'");// AND hid ='"+thid+"'");// name = tempString;
		 			if(!(phone.isEmpty()||phone==null))
			 			stmt.executeUpdate("UPDATE Users SET phone ='"+phone+"' WHERE login ='"+username+"'");// AND hid ='"+thid+"'");// name = tempString;
		 			/*
		 			 * SHOULDN'T DO THIS BECAUSE THIS QUERY MAY CHANGE ADMIN
		 			 * stmt.executeUpdate("UPDATE Users SET userType =false WHERE login ='"+username+"'");// AND hid ='"+thid+"'");// name = tempString;
		 			 */

		 		}
		 		else 
		 		{
		 			stmt.executeUpdate(addUserSql);
		 			System.out.println(username+" is added to the database");
		 		}
		 		rs.close();
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
		 		}
		 		catch(Exception e)
		 		{
		 			System.out.println("cannot close resultset" + e);
		 		}
		 	}
	    return output;
	}
}
