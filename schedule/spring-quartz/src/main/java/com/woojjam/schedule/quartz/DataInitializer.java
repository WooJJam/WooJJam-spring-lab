// package com.woojjam.schedule.quartz;
//
// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.PreparedStatement;
// import java.sql.SQLException;
// import java.util.Locale;
// import java.util.Random;
//
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.stereotype.Component;
//
// @Component
// public class DataInitializer implements CommandLineRunner {
//
// 	private final String databaseUrl;
// 	private final String username;
// 	private final String password;
//
// 	public DataInitializer(
// 		@Value("${DATABASE_URL}") String databaseUrl,
// 		@Value("${DATABASE_USERNAME}") String username,
// 		@Value("${DATABASE_PASSWORD}") String password) {
// 		this.databaseUrl = databaseUrl;
// 		this.username = username;
// 		this.password = password;
// 	}
//
// 	@Override
// 	public void run(final String... args) throws Exception {
// 		try (Connection conn = DriverManager.getConnection(databaseUrl, username, password)) {
// 			initUser(conn);
// 			initMatch(conn);
// 		} catch (SQLException e) {
// 			throw new RuntimeException("데이터 초기화 실패", e);
// 		}
// 	}
//
// 	private void initUser(Connection conn) throws SQLException {
// 		String sqlUser = "INSERT INTO user (username) VALUES (?)";
// 		PreparedStatement pstmtUser = conn.prepareStatement(sqlUser);
//
// 		for (int i = 1; i <= 10; i++) {
// 			pstmtUser.setString(1, "username"+i);
// 			// 유저 데이터 삽입
// 			pstmtUser.executeUpdate();
// 		}
// 		pstmtUser.close();
// 	}
//
// 	private void initUser(Connection conn) throws SQLException {
// 		String sqlUser = "INSERT INTO test_match (username) VALUES (?)";
// 		PreparedStatement pstmtUser = conn.prepareStatement(sqlUser);
//
// 		for (int i = 1; i <= 10; i++) {
// 			pstmtUser.setString(1, "username"+i);
// 			// 유저 데이터 삽입
// 			pstmtUser.executeUpdate();
// 		}
// 		pstmtUser.close();
// 	}
// }
