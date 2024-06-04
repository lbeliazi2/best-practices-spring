package spring.project.best.practices.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.project.best.practices.controllers.MainController;
import spring.project.best.practices.entity.MovieEntity;

import java.util.Optional;
import java.util.UUID;

// if you want a normal repository you can use CRUDRepository --> doesnt automatically have PagingAndSorting
// if you want both you can also use Jpa
// if you want pages and sorting you can use PagingAndSorting
@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, UUID> {

    Page<MovieEntity> findAll(Pageable p);
    Optional<MainController.Movie> findMovieById(UUID id);
    MovieEntity save(MainController.Movie movie);

}
