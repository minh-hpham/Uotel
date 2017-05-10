package cs5530;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.lang.*;
import java.io.*;
public class NewTH {
	public NewTH()
	{}
	/**
	 * This program updates a TH that was already made
	 * @param username -- the login of the user
	 * @param thid -- the id of the th
	 * @param stmt
	 * @param newTH -- checks if it is being called for a newTH or updating an existing one
	 * @return
	 */ 


    public String updateTH(String username,String thname,  String updatedName, String category, String address, String URL, String phone, String year_built, String startDate, String endDate, String price_per_night, String addKeywords,String removeKeywords, Statement stmt)
    {  
	String checkuniqueSql="SELECT * FROM TH WHERE login ='"+username+"' AND name = '"+thname+"'";
	String output="";
	String pid = null;
	ResultSet rs=null;
	ResultSet rs2 = null;
	String pricePerNight="";
	try{
	    rs=stmt.executeQuery(checkuniqueSql);
	    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	    if(!rs.next()){ // TH does not exists
		System.out.println("You do not own this TH");
	    }
	    else 
		{
		    String thid = Integer.toString(rs.getInt("hid"));
		    if(!updatedName.isEmpty())
			stmt.executeUpdate("UPDATE TH SET name ='"+updatedName+"' WHERE login ='"+username+"' AND hid ='"+thid+"'");
		    if(!category.isEmpty())
			stmt.executeUpdate("UPDATE TH SET category ='"+category+"' WHERE login ='"+username+"' AND hid ='"+thid+"'");
		    if(!URL.isEmpty())
			stmt.executeUpdate("UPDATE TH SET URL ='"+URL+"' WHERE login ='"+username+"' AND hid ='"+thid+"'");
		    if(!address.isEmpty())
			stmt.executeUpdate("UPDATE TH SET address ='"+address+"' WHERE login ='"+username+"' AND hid ='"+thid+"'");
		    if(!phone.isEmpty())
			stmt.executeUpdate("UPDATE TH SET phone ='"+phone+"' WHERE login ='"+username+"' AND hid ='"+thid+"'");
		    if(!price_per_night.isEmpty())
			stmt.executeUpdate("UPDATE TH SET price ="+price_per_night+" WHERE login ='"+username+"' AND hid ='"+thid+"'");
		    if(!year_built.isEmpty())
			stmt.executeUpdate("UPDATE TH SET year_built ="+year_built+" WHERE login ='"+username+"' AND hid ="+thid);
		    if(!startDate.isEmpty() && !endDate.isEmpty()){
			String findPID = "SELECT * FROM Available WHERE hid = "+thid;
			System.out.println("Executing: "+ findPID);
			rs2=stmt.executeQuery(findPID);
			if(rs2.next())
			    pid = Integer.toString(rs2.getInt("pid"));
			if(!(startDate.isEmpty() && endDate.isEmpty())){
			Date date1 =new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
			Date date2 =new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
			if(date2.after(date1)){
			    stmt.executeUpdate("UPDATE Period SET start ='"+startDate+"' WHERE pid ="+pid);
			    stmt.executeUpdate("UPDATE Period SET end ='"+endDate+"' WHERE pid ="+pid);
			}
		    }
			else {return("The start date needs to be before the end date");}}
		    if(!pricePerNight.isEmpty())
			stmt.executeUpdate("UPDATE Available SET price_per_night ="+pricePerNight+" WHERE pid ="+pid);
		    if(!addKeywords.isEmpty()){
			String[] keyWordsAddArray = addKeywords.split(",");
			for(int x = 0; x<keyWordsAddArray.length;x++)
			    AddKeywords(thid.toString(),keyWordsAddArray[x],stmt);
		    }
		    if(!removeKeywords.isEmpty()){
			String[] keyWordsRemoveArray = removeKeywords.split(",");
			for(int x = 0; x<keyWordsRemoveArray.length;x++)
			    RemoveKeywords(thid.toString(),keyWordsRemoveArray[x],stmt);
		    }
     
     
		}
	    rs.close();
	    return "TH has been updated";
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
			System.out.println("cannot close resultset");
		    }
	    }
	return "Your TH information has been updated";
    }





	public String deleteTH(String username, String thname, Statement stmt)
	{				
			String output="";
			ResultSet rs=null;
			ResultSet rs2=null;
			ResultSet rs3 = null;
			String checkuniqueSql="SELECT * FROM TH WHERE login ='"+username+"' AND name = '"+thname+"'";
			 	try{
			 		rs=stmt.executeQuery(checkuniqueSql);
			 		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	   		 	if(!rs.next()){ // TH does not exists
	   		 		System.out.println("You do not own this TH");
	   		 	}
	   		 	else 
	   		 	{
	   		 		String thid;
	   		 		 thid = Integer.toString(rs.getInt("hid"));
	   		 		
					 String addTHSql = "DELETE FROM TH WHERE hid = "+thid;
	   		 		stmt.executeUpdate(addTHSql);
	   		 	 addTHSql="DELETE FROM Available WHERE hid = "+thid+"";
   		 		stmt.executeUpdate(addTHSql);
   	  		 	 addTHSql="DELETE FROM Visit WHERE hid = "+thid+"";
   		 		stmt.executeUpdate(addTHSql);
   	  		 	 addTHSql="DELETE FROM Reserve WHERE hid = "+thid+"";
   		 		stmt.executeUpdate(addTHSql);
   	  		 	 addTHSql="DELETE FROM Favorites WHERE hid = "+thid+"";
   		 		stmt.executeUpdate(addTHSql);
   	  		 	 addTHSql="DELETE FROM HashKeywords WHERE hid = "+thid+"";
   		 		stmt.executeUpdate(addTHSql);
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
		    return output;
	}
	/**
	 * Creates a new TH & calls the update TH function to fill in the other parts of the TH
	 * only initializes TH with the login and the TH's name
	 * @param username -- the login
	 * @param THname -- the name of the TH
	 * @param stmt
	 * @return
	 */
	

    public String createTH(String username, String THname, String category, String address, String URL, String phone, String year_built, String startDate, String endDate, String price_per_night,String keywords, Statement stmt)
    {    
	String checkuniqueSql="SELECT * FROM TH WHERE name = '"+THname+"'";
	Integer id=-1;
	String output="";
	ResultSet rs=null;
	ResultSet rs2=null;
	ResultSet rs3=null;
	try{
	    System.out.println(checkuniqueSql);
	    rs3 =stmt.executeQuery(checkuniqueSql);
     
	    if(rs3.next())
		return "There is already a TH named " + THname;
         String addTHSql="INSERT INTO TH(login,name, category, URL, address, phone, price, year_built)"
	     + "VALUES ('"+username+"','"+THname+"','"+category+"', '"+URL+"','"+address+"','"+phone+"', "+price_per_night+", "+year_built+")";
         id = stmt.executeUpdate(addTHSql,Statement.RETURN_GENERATED_KEYS);
         rs=stmt.getGeneratedKeys();
         if(rs.next()) // get hid 
	     id = rs.getInt(1);
         System.out.println(id);
         String makePeriod = "INSERT INTO Period(start, end) VALUES ('2015-01-01', '2015-01-01')";
	 System.out.println(makePeriod);
         int pid =  stmt.executeUpdate(makePeriod,Statement.RETURN_GENERATED_KEYS);
         rs2=stmt.getGeneratedKeys();
         if(rs2.next()) // get hid 
	     pid = rs2.getInt(1);
         System.out.println(pid);
	 String makeAvaliable = "INSERT INTO Available (hid, pid) VALUES ("+id+", "+pid+")";
         System.out.println(makeAvaliable);
         stmt.executeUpdate(makeAvaliable);
         String tempString;
	 BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	 if(!(startDate.isEmpty() ||endDate.isEmpty())){
	 Date date1 =new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
	 Date date2 =new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
	 if(date2.after(date1)){
	     //System.out.println("Why is it here?" + date1.compareTo(date2));
	     stmt.executeUpdate("UPDATE Period SET start ='"+startDate+"' WHERE pid ="+pid);
	     stmt.executeUpdate("UPDATE Period SET end ='"+endDate+"' WHERE pid ="+pid);
	 }
	 else {return("The start date needs to be before the end date");}}
	 String[] keyWordsArray = keywords.split(",");
	 for(int x = 0; x<keyWordsArray.length;x++)
	     AddKeywords(id.toString(),keyWordsArray[x],stmt);
	 return("Your TH has been made");
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
		    if (rs3!=null && !rs3.isClosed())
			rs3.close();
		}
		catch(Exception e)
		    {
			System.out.println("cannot close resultset");
		    }
	    }
	return output;
    }

	/**
	 * This function gets all of the TH's that a person owns
	 * @param user -- the login of the user
	 * @param stmt
	 * @return
	 */
	public String getAllTH(String user, Statement stmt){
	
		ResultSet rs=null;
		String ThList = "SELECT * FROM TH WHERE login = '"+user+"'";
		try{
			rs=stmt.executeQuery(ThList);
			System.out.println("hid  login  name  category  URL  address  phone  price  year_built");
			String temp = "";
			while(rs.next()){
				temp+=(rs.getString("name")+ ",,,"
						+rs.getString("category")+ ",,,"+ rs.getString("URL")+ ",,,"+ rs.getString("address")+",,,"
						+ rs.getString("phone")+",,,"+rs.getInt("price")+",,,"+ rs.getInt("year_built") + "<br>");
			}
			return temp;
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
	 			System.out.println("cannot close resultset");
	 		}
	 	}
		return user;
	}
	public String AddKeywords(String hid,String keyword, Statement stmt){
		Integer id=-1;
		String output="";
		ResultSet rs=null;
		ResultSet rs2=null;
		ResultSet rs3=null;
		ResultSet rs4=null;
		 	try{
		 		String checkKeyword="SELECT * FROM Keywords WHERE word ='"+keyword+"'";
		 		// checks if keyword already exists in the keyword list and adds if it is not
   		 		rs=stmt.executeQuery(checkKeyword);
		 		if(!rs.next()){
		 			String addKeyword="INSERT INTO Keywords(word) "
	   		 				+ "VALUES ('"+keyword+"')";
	   		 		id = stmt.executeUpdate(addKeyword,Statement.RETURN_GENERATED_KEYS);
	   		 		rs2=stmt.getGeneratedKeys();
	   		 		if(rs2.next()) // get hid 
	   		 			id = rs2.getInt(1);
		 		}
		 		else {
		 			id = rs.getInt("wid");
		 		}
		 		
   		 		System.out.println(id);
   		 		// Checks if TH has keyword and if not it is added for that TH
   		 	String checkifThKeyword="SELECT * FROM HashKeywords WHERE wid = "+id+" AND hid = "+hid;
	 		// checks if keyword already exists in the keyword list and adds if it is not
		 		rs3=stmt.executeQuery(checkifThKeyword);
	 		if(!rs3.next()){
	 			System.out.println("added to HashKeywords");
	 			String addKeyword="INSERT INTO HashKeywords(hid, wid) "
   		 				+ "VALUES ("+hid+", "+id+")";
   		 		id = stmt.executeUpdate(addKeyword);
   		 		return "The keyword has been added";
	 		}
	 		else {
	 			return "This keyword already exists with this TH";
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
   		 		if (rs3!=null && !rs3.isClosed())
   		 			rs3.close();
		 		if (rs4!=null && !rs4.isClosed())
   		 			rs4.close();
		 		}
		 		catch(Exception e)
		 		{
		 			System.out.println("cannot close resultset");
		 		}
		 	}
	    return output;
	}
	
	public String RemoveKeywords(String hid,String keyword, Statement stmt){
		Integer id=-1;
		String output="";
		ResultSet rs=null;
		ResultSet rs2=null;
		ResultSet rs3=null;
		ResultSet rs4=null;
		 	try{
		 		String checkKeyword="SELECT * FROM Keywords WHERE word ='"+keyword+"'";
		 		// checks if keyword already exists in the keyword list and adds if it is not
   		 		rs=stmt.executeQuery(checkKeyword);
		 		if(!rs.next()){
		 			return "This word is not a keyword";
		 		}
		 		else {
		 			id = rs.getInt("wid");
		 		}
		 		
   		 		//System.out.println(id);
   		 		// Checks if TH has keyword and if not it is added for that TH
   		 	String checkifThKeyword="SELECT * FROM HashKeywords WHERE wid = "+id;//+"" AND hid = "+hid+"";
	 		// checks if keyword already exists in the keyword list and adds if it is not
		 		rs3=stmt.executeQuery(checkifThKeyword);
	 		if(rs3.next()){
	 			// DELETE FROM Favorites WHERE login = '"+user+"' AND hid = "+hid+"";
	 			String removeKeyword="DELETE FROM HashKeywords WHERE hid = "+hid+" AND wid = "+id;
   		 		stmt.executeUpdate(removeKeyword);
   		 		return "The keyword has been removed";
	 		}
	 		else {
	 			return "This keyword was not found";
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
   		 		if (rs3!=null && !rs3.isClosed())
   		 			rs3.close();
		 		if (rs4!=null && !rs4.isClosed())
   		 			rs4.close();
		 		}
		 		catch(Exception e)
		 		{
		 			System.out.println("cannot close resultset");
		 		}
		 	}
	    return output;
	}
	public String getTHKeywords(String name, Statement stmt){
	    ResultSet rs = null;
	    ResultSet rs2 = null;
	    String getHid = "SELECT * FROM TH WHERE name = '"+name+"'";
	    // rs2 = stmt.executeQuery(getHid);
	    String hid="";
	    //	    return getHid;
		try{
		    //return getHid;
		       rs2 = stmt.executeQuery(getHid);
		     
		       if(!rs2.next()){} else hid = ""+rs2.getInt("hid"); //return hid;
		    String ThList = "SELECT Keywords.word  FROM HashKeywords, Keywords WHERE hid ="+hid+" AND HashKeywords.wid = Keywords.wid";
			rs=stmt.executeQuery(ThList);
			//System.out.println("hid  wid  word ");
			String print = "";
			//	return "check 1";
			while(true){
				if(rs.next()){
			    //System.out.println(rs.getInt("hid")+ "  "+rs.getInt("wid")+ "  "+rs.getString("word"));
				      print = print + rs.getString("word")+ ",";
				    // return rs.getString("word");
				} else {
				    if(print.equals(""))
				    return print;
				    else return print.substring(0,print.length()-1);
				}
			}
			//			return print;
				
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
	 			System.out.println("cannot close resultset");
	 		}
	 	}
		return "SELECT HashKeywords.hid , HashKeywords.wid, Keywords.word  FROM HashKeywords, Keywords WHERE name ="+hid+" AND HashKeywords.wid = Keywords.wid";
	}


    public String getAllTHName(String user, Statement stmt){

	ResultSet rs=null;
	String ThList = "SELECT * FROM TH WHERE login = '"+user+"'";
	try{
	    
	    rs=stmt.executeQuery(ThList);
	    // System.out.println("hid  login  name  category  URL  address  phone  price  year_built");
	    String temp = "";
	    while(rs.next()){
		temp+=(rs.getString("name")+ ",");
	    }
	    return temp;
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
			System.out.println("cannot close resultset");
		    }
	    }
	return user;
    }

}