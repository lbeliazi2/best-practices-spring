package spring.project.best.practices.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import spring.project.best.practices.controllers.MainController;
import spring.project.best.practices.entity.MovieEntity;
import spring.project.best.practices.mappers.MovieMapper;
import spring.project.best.practices.repository.MovieRepository;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {

    public final MovieRepository movieRepository;
    public final MovieMapper movieMapper;

    // only users with the role admin will be able to execute this request
    @PreAuthorize("hasRole('ADMIN')")
    public Page<MainController.Movie> findAll(Pageable p) {
        Page<MovieEntity> movies = this.movieRepository.findAll(p);
        List<MainController.Movie> listMovies = movies.getContent().stream().map(this.movieMapper::toDto).collect(Collectors.toList());
        return new PageImpl<>(listMovies,p,movies.getTotalElements());
    }

    public Optional<MainController.Movie> findById(UUID id) {
        return this.movieRepository.findMovieById(id);
    }

    public URI save(MainController.Movie movie, UriComponentsBuilder builder) {
        MovieEntity movieEntityToSave = this.movieRepository.save(movie);
        return builder.path("/movie/{id}").buildAndExpand(movieEntityToSave.getId()).toUri();
    }

}
