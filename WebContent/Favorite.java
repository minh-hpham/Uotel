package cs5530;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;

public class Favorite {
	public Favorite() {
	}

	public String addFavorite(String name, String user, Statement stmt) {
		ResultSet rs = null;
		ResultSet rs2 = null;
		String getID = "SELECT hid FROM TH WHERE name LIKE '%" + name + "%'";
		int hid = 0;
		String output = "";
		try {

			rs = stmt.executeQuery(getID);
			if (!rs.next())
				throw new IllegalArgumentException("This housing is not in our database");
			else {
				hid = rs.getInt(1);
				String favorite = "SELECT * FROM Favorites WHERE login = '" + user + "' AND hid = " + hid;// AND
																											// hid
																											// =
																											// "+hid;
				rs2 = stmt.executeQuery(favorite);

				if (rs2.next()) {
					return ("You have already Favorited this location");
				} else {
					String addTHSql = String.format("INSERT INTO Favorites (hid, login, fvdate) VALUES (%s,'%s',NOW())",
							hid, user);
					stmt.executeUpdate(addTHSql);
					return ("Your new favorite was added");
				}
			}
		} catch (Exception e) {
			output+="Invalid input(s). Please try again";
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

	public String removeFavorite(String name, String user, Statement stmt) {
		ResultSet rs = null;
		ResultSet rs2 = null;
		String findTHID = "SELECT hid FROM TH WHERE name LIKE '%" + name + "%'";
		String output = "";
		try {
			int hid = 0;
			rs = stmt.executeQuery(findTHID);
			if (rs.next())
				hid = rs.getInt(1);
			String CheckIfFav = "SELECT * FROM Favorites WHERE login = '" + user + "' AND hid = " + hid;
			rs2 = stmt.executeQuery(CheckIfFav);
			if (!rs2.next()) {
				System.out.println("You do not have this location favorited");
			} else {
				Calendar calendar = Calendar.getInstance();
				java.sql.Date time = new java.sql.Date(calendar.getTime().getTime());
				String removeTHSql = " DELETE FROM Favorites WHERE login = '" + user + "' AND hid = " + hid;
				stmt.executeUpdate(removeTHSql);
				System.out.println("This housing was unfavorited");
			}
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

	public String viewFavorites(String user, Statement stmt) {
		ResultSet rs = null;
		String favorite = "SELECT * FROM Favorites WHERE login = '" + user + "'";
		try {
			System.out.println("Excecuting " + favorite);
			rs = stmt.executeQuery(favorite);
			if (!rs.next()) {
				System.out.println("You do not have any locations favorited");
			} else {
				Calendar calendar = Calendar.getInstance();
				java.sql.Date time = new java.sql.Date(calendar.getTime().getTime());
				/*
				 * String
				 * displayFavorites=" DELETE FROM Favorites WHERE login = '"
				 * +user+"'"; stmt.executeUpdate(displayFavorites);
				 * System.out.println("hid   login   date Favorited");
				 */
				if (rs.next())
					System.out.println(rs.getInt("hid") + "  " + rs.getString("login") + "    " + rs.getDate("fvdate"));

				while (rs.next()) {
					System.out.println(rs.getInt("hid") + "  " + rs.getString("login") + "    " + rs.getDate("fvdate"));
				}
			}
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
		return user;
	}

}
