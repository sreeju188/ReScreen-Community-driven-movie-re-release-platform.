package com.getit.rerelease_movie.repository;
import com.getit.rerelease_movie.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
