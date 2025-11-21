package com.myPacket.oauth2_jwt.repositories;

import com.myPacket.oauth2_jwt.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("userEntityRepository")
public interface IUserEntityRepository extends JpaRepository<UserEntity, Long> {
    public Optional<UserEntity> findByUsername(String username);
}
