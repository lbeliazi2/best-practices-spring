package spring.project.best.practices.controllers;

import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import spring.project.best.practices.entity.Movie;
import spring.project.best.practices.repository.MovieRepository;
import spring.project.best.practices.service.MovieService;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MainController {

    public final MovieService movieService;

    @GetMapping("/movies")
    private ResponseEntity<Page<Movie>> findAll (@PageableDefault(value = 1, page = 0) Pageable p) {
        Page<Movie> movies = this.movieService.findAll(p);
        return ResponseEntity.ok(movies);
    }


    @GetMapping("/movie/{id}")
    private ResponseEntity<Movie> findMovie (@PathVariable("id") UUID id) {
        Optional<Movie> movieOptional = this.movieService.findById(id);
        // use this
        // you can also use orElseThrow to throw a specific error
        return movieOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/movie")
    private ResponseEntity<Movie> findMovie (Movie movie, UriComponentsBuilder builder) {
        URI location = this.movieService.save(movie, builder);
        return ResponseEntity.created(location).build();
    }

    // DONT FORGET
    // for GET --> ResponseEntity.ok
    // for POST --> ResponseEntity.created(urlLocation).build()

}