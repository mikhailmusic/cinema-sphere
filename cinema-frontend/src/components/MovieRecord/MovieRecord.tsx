import { useState } from "react";
import { formatDuration } from "../../utils/dateUtils";
import { getImageUrl } from "../../api/movieApi";
import MovieModal from "../Modal/MovieModal";
import Dots from "../../assets/icons/Dots";
import type { MovieDto } from "../../api/types";

type MovieRecordProps = {
  movie: MovieDto;
  onDelete: (movie: MovieDto) => void;
  onDetails: (movie: MovieDto) => void;
};

export default function MovieRecord({ movie, onDelete, onDetails }: MovieRecordProps) {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const posterUrl = getImageUrl(movie.image);

  return (
    <>
      <article className="movie-record" onClick={() => setIsModalOpen(true)}>
        <div className="movie-info">
          <img src={posterUrl} alt={`Постер фильма ${movie.title}`} className="movie-poster" />
          <div className="movie-details">
            <h4 className="movie-title">{movie.title}</h4>
            <p>
              {movie.releaseYear} <span>•</span> {formatDuration(movie.duration)}
            </p>
            <p>
              <strong>Возраст:</strong> {movie.ageRating}+
            </p>
            <p>
              <strong>Язык:</strong> {movie.language}
            </p>

            <p>
              <strong>Режиссёр:</strong> {movie.director}
            </p>

            <p>
              <strong>Жанр:</strong> {movie.genre}
            </p>
            <p>
              <strong>Количество сеансов:</strong> {movie.sessionList?.length ?? 0}
            </p>
          </div>
        </div>

        <div className="movie-actions" onClick={(e) => e.stopPropagation()}>
          <button className="menu-button" aria-label="Открыть меню">
            <Dots />
          </button>
          <ul className="dropdown-menu" onClick={(e) => e.stopPropagation()}>
            <li onClick={() => onDetails(movie)}>Изменить</li>
            <li className="danger" onClick={() => onDelete(movie)}>
              Удалить
            </li>
          </ul>
        </div>
      </article>

      {isModalOpen && (
        <MovieModal
          onClose={() => setIsModalOpen(false)}
          movie={movie}
          onDelete={() => {
            onDelete(movie);
            setIsModalOpen(false);
          }}
          onEdit={(m) => {
            onDetails(m);
            setIsModalOpen(false);
          }}
        />
      )}
    </>
  );
}
