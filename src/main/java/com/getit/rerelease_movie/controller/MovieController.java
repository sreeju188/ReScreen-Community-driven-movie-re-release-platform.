package com.getit.rerelease_movie.controller;


import com.getit.rerelease_movie.entity.Movie;
import com.getit.rerelease_movie.entity.MovieRequest;
import com.getit.rerelease_movie.service.MovieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    @PostMapping
    public Movie addMovie(@RequestBody Movie movie) {
        return service.addMovie(movie);
    }

    @GetMapping
    public List<Movie> getAllMovies() {
        return service.getAllMovies();
    }

    @PostMapping("/{movieId}/request/{userId}")
    public MovieRequest requestMovie(@PathVariable Long movieId,
                                     @PathVariable Long userId) {
        return service.requestMovie(movieId, userId);
    }


}
