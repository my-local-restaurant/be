package me.albert.restapi.member.repository;

import me.albert.restapi.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<Member, Long> {

    boolean existsByUsernameAndPassword(String username, String password);
}
