package com.getit.rerelease_movie.service;


import com.getit.rerelease_movie.entity.Movie;
import com.getit.rerelease_movie.entity.MovieRequest;
import com.getit.rerelease_movie.entity.Status;
import com.getit.rerelease_movie.entity.User;
import com.getit.rerelease_movie.repository.MovieRepository;
import com.getit.rerelease_movie.repository.MovieRequestRepository;
import com.getit.rerelease_movie.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository repository;
    private final UserRepository userRepository;
    private final MovieRequestRepository movieRequestRepository;


    public MovieService(MovieRepository repository, UserRepository userRepository, MovieRequestRepository movieRequestRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.movieRequestRepository = movieRequestRepository;
    }

    public Movie addMovie(Movie movie) {

        movie.setRequestCount(0);
        movie.setStatus(Status.PENDING);

        repository.findByTitle(movie.getTitle())
                .ifPresent(m -> {
                    throw new RuntimeException("Movie already exists");
                });

        return repository.save(movie);
    }

    public List<Movie> getAllMovies() {
        return repository.findAll();
    }
    public MovieRequest requestMovie(Long movieId, Long userId) {

        Movie movie = repository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // prevent duplicate request
        if (movieRequestRepository.existsByUserIdAndMovieId(userId, movieId)) {
            throw new RuntimeException("User already requested this movie");
        }

        MovieRequest request = MovieRequest.builder()
                .movie(movie)
                .user(user)
                .build();

        movieRequestRepository.save(request);

        // safe increment
        Integer count = movie.getRequestCount();
        if (count == null) {
            count = 0;
        }

        movie.setRequestCount(count + 1);

        // auto approval
        if (movie.getRequestCount() >= 5) {
            movie.setStatus(Status.APPROVED);
        }

        repository.save(movie);

        return request;
    }
}
