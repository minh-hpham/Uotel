package cs5530;

import java.io.BufferedReader;
import java.io.InputStreamReader;
// Add keywords
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

public class THBrowsing {
    public THBrowsing() {
    }

    public String THBrowse(String priceStart, String priceEnd, String address, String category,
			   ArrayList<String> keywords, String choice, Statement stmt) {
	ResultSet rs = null;
	StringBuilder browsing = new StringBuilder();
	browsing.append("SELECT S1.name,S1.category,S1.address,S1.price,S1.phone,S1.URL FROM ");

	browsing.append("(SELECT * from TH ");
	if(!priceStart.isEmpty() || !priceEnd.isEmpty() || !address.isEmpty() || !category.isEmpty())
	    browsing.append(" WHERE ");
	if (!priceStart.isEmpty()) {
	    browsing.append("price>=");
	    browsing.append(priceStart);
	}
	if (!priceEnd.isEmpty()) {
	    browsing.append(" and price<=");
	    browsing.append(priceEnd);
	}
	if (!category.isEmpty()) {
	    browsing.append(String.format(" and category='%s'", category));
	}
	if (!address.isEmpty()) {
	    browsing.append(String.format(" and address LIKE '%s%s%s'", '%', address, '%'));
	}
	browsing.append(") as S1");
	for (int i = 2; i < keywords.size() + 2; i++) {
	    browsing.append(String.format(
					  ", (SELECT h.hid FROM HashKeywords h, Keywords k where h.wid=k.wid and k.word='%s') AS S%s",
					  keywords.get(i - 2), i));
	}
	if (choice.equals("2")) {
	    browsing.append(" ,Feedback f ");
	} else if (choice.equals("3")) {
	    browsing.append(" ,Feedback f, Rates r ");
	}
	// where clause
	browsing.append(" WHERE ");
	if (keywords.size() == 1) {
	    browsing.append("S1.hid=S2.hid ");
	} else {
	    for (int i = 2; i < keywords.size() + 1; i++) {
		browsing.append(String.format("S1.hid=S%s.hid AND ", i));
	    }
	    browsing.append(String.format("S1.hid=S%s.hid ", keywords.size() + 1));
	}
	if (choice.equals("1")) {
	    browsing.append(" ORDER BY S1.price ");
	} else if (choice.equals("2")) {
	    browsing.append("AND S1.hid=f.hid ");
	    browsing.append(" GROUP BY S1.hid ORDER BY AVG(f.score) DESC");
	} else if (choice.equals("3")) {
	    browsing.append("AND S1.hid=f.hid AND f.fid=r.fid");
	    browsing.append(" GROUP BY S1.hid ORDER BY AVG(r.rating) DESC");
	}
	String sql = browsing.toString();
	System.out.println(sql);
	StringBuilder output = new StringBuilder();
	try {
	    rs = stmt.executeQuery(sql);
	    if (rs.next()) {
		output.append("<h1><center>The TH Fit Your Criteria</center></h1><table>");
		output.append("<tr> <th>category</th>  <th> name</th>  <th> URL</th>  <th> address</th> <th>  phone</th>  <th> price</th> </tr>");
		output.append("<tr><td>");
		output.append(rs.getString("category"));
		output.append("</td><td>");
		output.append(rs.getString("name"));
		output.append("</td><td>");
		output.append(rs.getString("URL"));
		output.append("</td><td>");
		output.append(rs.getString("address"));
		output.append("</td><td>");
		output.append(rs.getString("phone"));
		output.append("</td><td>");
		output.append(rs.getString("price"));
		output.append("</td></tr>");
	    } else {
		output.append("No place fits your criteria");
	    }
	    while (rs.next()) {
		output.append("<tr><td>");
		output.append(rs.getString("category"));
		output.append("</td><td>");
		output.append(rs.getString("name"));
		output.append("</td><td>");
		output.append(rs.getString("URL"));
		output.append("</td><td>");
		output.append(rs.getString("address"));
		output.append("</td><td>");
		output.append(rs.getString("phone"));
		output.append("</td><td>");
		output.append(rs.getString("price"));
		output.append("</td></tr>");
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
