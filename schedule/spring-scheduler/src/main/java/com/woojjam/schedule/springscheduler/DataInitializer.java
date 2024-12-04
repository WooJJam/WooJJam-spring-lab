package com.woojjam.schedule.springscheduler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

	private final String databaseUrl;
	private final String username;
	private final String password;

	public DataInitializer(
		@Value("${DATABASE_URL}") String databaseUrl,
		@Value("${DATABASE_USERNAME}") String username,
		@Value("${DATABASE_PASSWORD}") String password) {
		this.databaseUrl = databaseUrl;
		this.username = username;
		this.password = password;
	}

	@Override
	public void run(final String... args) throws Exception {
		try (Connection conn = DriverManager.getConnection(databaseUrl, username, password)) {
			initUser(conn);
			initMatch(conn);
		} catch (SQLException e) {
			throw new RuntimeException("데이터 초기화 실패", e);
		}
	}

	private void initUser(Connection conn) throws SQLException {
		String sqlUser = "INSERT INTO user (username) VALUES (?)";
		PreparedStatement pstmtUser = conn.prepareStatement(sqlUser);

		for (int i = 1; i <= 10; i++) {
			pstmtUser.setString(1, "username"+i);
			// 유저 데이터 삽입
			pstmtUser.executeUpdate();
		}
		pstmtUser.close();
	}

	private void initMatch(Connection conn) throws SQLException {
		String sqlMatch = "INSERT INTO test_match (total, remain, start_at, end_at) VALUES (?, ?, ?, ?)";
		PreparedStatement pstmtMatch = conn.prepareStatement(sqlMatch);

		LocalDateTime now = LocalDateTime.now();
		Random random = new Random();
		for (int i = 1; i <= 100; i++) {
			LocalDateTime startAt = now.plusMinutes(random.nextInt(30) + 10);
			LocalDateTime endAt = startAt.plusHours(2);

			int total = 10;
			int remain = i % 10;

			pstmtMatch.setInt(1, total);
			pstmtMatch.setInt(2, remain);
			pstmtMatch.setObject(3, startAt);
			pstmtMatch.setObject(4, endAt);

			pstmtMatch.executeUpdate();
		}
		pstmtMatch.close();
	}
}
