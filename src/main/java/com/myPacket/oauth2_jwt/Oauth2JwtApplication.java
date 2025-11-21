package com.myPacket.oauth2_jwt;

import com.myPacket.oauth2_jwt.models.Role;
import com.myPacket.oauth2_jwt.models.UserEntity;
import com.myPacket.oauth2_jwt.models.enums.ERole;
import com.myPacket.oauth2_jwt.repositories.IUserEntityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class Oauth2JwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(Oauth2JwtApplication.class, args);
	}


	@Bean
	CommandLineRunner init(IUserEntityRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			/* Create ROLES */
			Role rolDirector = new Role();
			rolDirector.setRol(ERole.DIRECTOR);

			Role rolEstudiante = new Role();
			rolEstudiante.setRol(ERole.ESTUDIANTE);

			Role rolVisitante = new Role();
			rolVisitante.setRol(ERole.VISITANTE);
			

			/* CREATE USERS */
			UserEntity userGustavo = new UserEntity();
			userGustavo.setUsername("gustavo");
			userGustavo.setPassword(passwordEncoder.encode("1234"));
			userGustavo.setEnabled(true);
			userGustavo.setAccountNoExpired(true);
			userGustavo.setAccountNoLocked(true);
			userGustavo.setCredentialNoExpired(true);
			userGustavo.setRoles(Set.of(rolDirector));

			UserEntity userLuciano = new UserEntity();
			userLuciano.setUsername("luciano");
			userLuciano.setPassword(passwordEncoder.encode("1234"));
			userLuciano.setEnabled(true);
			userLuciano.setAccountNoExpired(true);
			userLuciano.setAccountNoLocked(true);
			userLuciano.setCredentialNoExpired(true);
			userLuciano.setRoles(Set.of(rolEstudiante));

			UserEntity userAndrea = new UserEntity();
			userAndrea.setUsername("andrea");
			userAndrea.setPassword(passwordEncoder.encode("1234"));
			userAndrea.setEnabled(true);
			userAndrea.setAccountNoExpired(true);
			userAndrea.setAccountNoLocked(true);
			userAndrea.setCredentialNoExpired(true);
			userAndrea.setRoles(Set.of(rolVisitante));


			userRepository.saveAll(List.of(userGustavo, userLuciano, userAndrea));
		};
	}

}
