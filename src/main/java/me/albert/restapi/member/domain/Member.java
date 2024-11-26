package me.albert.restapi.member.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class Member {

    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String additionalName;
    private String username;
    private LocalDate birthDate;
    private boolean isAllowedToAddToTeam;
    private Long phoneNumber;
    private String email;
    private String overview;
    private String password;
}
