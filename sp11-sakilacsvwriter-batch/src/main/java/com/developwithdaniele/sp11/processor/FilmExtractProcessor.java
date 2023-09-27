package com.developwithdaniele.sp11.processor;

import com.developwithdaniele.sp11.dto.FilmDTO;
import com.developwithdaniele.sp11.repository.model.Film;
import com.developwithdaniele.sp11.repository.model.FilmActor;
import org.springframework.batch.item.ItemProcessor;

public class FilmExtractProcessor implements ItemProcessor<Film, FilmDTO> {
    @Override
    public FilmDTO process(Film film) throws Exception {
        FilmDTO filmDTO = new FilmDTO();
        filmDTO.setFilmId(film.getFilmId());
        filmDTO.setTitle(film.getTitle());
        film.setReleaseYear(film.getReleaseYear());
        int i = 1;
        StringBuilder actors = new StringBuilder();
        for (FilmActor filmActor : film.getFilmActors()){
            actors.append(filmActor.getActor().getFirstName()+ " " + filmActor.getActor().getLastName());
            if (i != film.getFilmActors().size()){
                actors.append(",");
            }
            i++;
        }
        filmDTO.setActors(actors.toString());
        return filmDTO;
    }
}
