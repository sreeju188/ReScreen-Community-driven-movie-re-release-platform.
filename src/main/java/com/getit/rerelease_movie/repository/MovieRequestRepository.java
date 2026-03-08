package com.getit.rerelease_movie.repository;

import com.getit.rerelease_movie.entity.MovieRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRequestRepository extends JpaRepository<MovieRequest , Long> {
    boolean existsByUserIdAndMovieId(Long userId, Long movieId);
}
