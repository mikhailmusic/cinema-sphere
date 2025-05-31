import { formatDuration } from "../../utils/dateUtils";
import { getImageUrl } from "../../api/movieApi";
import Dots from "../../assets/icons/Dots";
import { type MovieDto, SessionStatus } from "../../api/types";

type MovieRecordProps = {
  movie: MovieDto;
  onDelete: (movie: MovieDto) => void;
  onEdit: (movie: MovieDto) => void;
  onClick: (movie: MovieDto) => void;
};

export default function MovieRecord({ movie, onDelete, onEdit, onClick }: MovieRecordProps) {
  const posterUrl = getImageUrl(movie.image);

  const classes = ["movie-record", movie.movieStatus.toLowerCase()];
  if ((movie.sessionList ?? []).length === 0 || (movie.sessionList ?? []).every((s) => s.status === SessionStatus.CANCELLED)) {
    classes.push("archived");
  }

  return (
    <>
      <article onClick={() => onClick(movie)} className={classes.join(" ")}>
        <div className="movie-info">
          <img src={posterUrl} alt={`Постер фильма ${movie.title}`} className="movie-poster" />
          <div className="movie-details">
            <h4 className="movie-title">{movie.title}</h4>
            <p>
              {movie.releaseYear} <span>•</span> {formatDuration(movie.duration)}
            </p>
            <div>
              <p>
                <strong>Возраст:</strong> {movie.ageRating}+
              </p>
              <p>
                <strong>Язык:</strong> {movie.language}
              </p>

              <p className="director-info">
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
        </div>

        <div className="movie-actions" onClick={(e) => e.stopPropagation()}>
          <button className="menu-button" aria-label="Открыть меню">
            <Dots />
          </button>
          <ul className="dropdown-menu" onClick={(e) => e.stopPropagation()}>
            <li onClick={() => onEdit(movie)}>Изменить</li>
            <li className="danger" onClick={() => onDelete(movie)}>
              Удалить
            </li>
          </ul>
        </div>
      </article>
    </>
  );
}
