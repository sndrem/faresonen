package sim.tv2.no.tippeligaen.fotballxtra.dbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import sim.tv2.no.tippeligaen.fotballxtra.match.Match;


public class DatabaseConnection {
	
	private static DatabaseConnection instance = null;
	private Connection conn;
	
	private String addMatchQuery = "INSERT INTO Matches "
			+ "(date, round, tournament, hometeam, awayteam, time, channel, matchurl, arena, referee) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

	private DatabaseConnection() {
		connect();
	}
	
	public static synchronized DatabaseConnection getInstance() {
		if(instance == null) {
			instance = new DatabaseConnection();
		}
		return instance;
	}
	
	private boolean connect() {
		String host = "localhost";
		String dbName = "Tippeligaen";
		int port = 3306;
		String mySQLUrl = "jdbc:mysql://" + host + ":" + port + "/" + dbName;
		Properties userInfo = new Properties();
		userInfo.put("user", "root");
		userInfo.put("password", "");
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver lastet");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			setConn(DriverManager.getConnection(mySQLUrl, userInfo));
			System.out.println("Connected to " + mySQLUrl);
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void addMatch(Match match) {
		PreparedStatement insertStatement;
		
		try {
			insertStatement = this.conn.prepareStatement(addMatchQuery);
			insertStatement.setString(1, match.getMatchDate());
			insertStatement.setString(2, match.getRound());
			insertStatement.setString(3, match.getTournament());
			insertStatement.setString(4, match.getHomeTeam());
			insertStatement.setString(5, match.getAwayTeam());
			insertStatement.setString(6, match.getTime());
			insertStatement.setString(7, match.getChannels());
			insertStatement.setString(8, match.getMatchUrl());
			insertStatement.setString(9, match.getArena());
			insertStatement.setString(10, match.getReferee());
			insertStatement.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return the conn
	 */
	public Connection getConn() {
		return conn;
	}

	/**
	 * @param conn the conn to set
	 */
	public void setConn(Connection conn) {
		this.conn = conn;
	}

}
