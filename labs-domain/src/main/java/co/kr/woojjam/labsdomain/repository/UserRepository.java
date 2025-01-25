package co.kr.woojjam.labsdomain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.kr.woojjam.labsdomain.domains.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
