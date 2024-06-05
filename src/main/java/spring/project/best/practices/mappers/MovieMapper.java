package spring.project.best.practices.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import spring.project.best.practices.controllers.MainController;
import spring.project.best.practices.entity.MovieEntity;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    MainController.Movie toDto(MovieEntity movieEntity);

}
