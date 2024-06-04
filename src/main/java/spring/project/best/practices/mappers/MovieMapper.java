package spring.project.best.practices.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import spring.project.best.practices.controllers.MainController;
import spring.project.best.practices.entity.MovieEntity;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    MainController.Movie toDto(MovieEntity movieEntity);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    MovieEntity toEntity(MainController.Movie movie);
}
