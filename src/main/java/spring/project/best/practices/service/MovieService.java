package spring.project.best.practices.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import spring.project.best.practices.entity.Movie;
import spring.project.best.practices.repository.MovieRepository;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MovieService {

    public final MovieRepository movieRepository;

    // only users with the role admin will be able to execute this request
    @PreAuthorize("hasRole('ADMIN')")
    public Page<Movie> findAll (Pageable p) {
        return this.movieRepository.findAll(p);
    }

    public Optional<Movie> findById( UUID id) {
        return this.movieRepository.findMovieById(id);
    }

    public URI save(Movie movie, UriComponentsBuilder builder) {
        Movie movieToSave = this.movieRepository.save(movie);
        return builder.path("/movie/{id}").buildAndExpand(movieToSave.getId()).toUri();
    }

}
