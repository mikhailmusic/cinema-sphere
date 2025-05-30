import { formatDuration } from "../../utils/dateUtils";
import BaseModal from "./base/BaseModal";
import { getImageUrl } from "../../api/movieApi";
import Pencil from "../../assets/icons/Pencil";
import Trash from "../../assets/icons/Trash";
import type { MovieDto } from "../../api/types";
import { movieStatusOptions, sessionStatusOptions } from "../../api/types";
import { formatDateTime } from "../../utils/dateUtils";

interface MovieModalProps {
  movie: MovieDto;
  onClose: () => void;
  onEdit: (movie: MovieDto) => void;
  onDelete: (movieId: number) => void;
}

export default function MovieModal({ movie, onClose, onEdit, onDelete }: MovieModalProps) {
  const posterUrl = getImageUrl(movie.image);
  const sortedSessions = movie.sessionList
    ? [...movie.sessionList].sort((a, b) => new Date(a.startTime).getTime() - new Date(b.startTime).getTime())
    : [];

  const handleDelete = () => {
    onDelete(movie.id);
  };

  const handleEdit = () => {
    onEdit(movie);
  };

  function getSessionStatusLabel(status: string): string {
    const option = sessionStatusOptions.find((opt) => opt.value.toUpperCase() === status.toUpperCase());
    return option ? option.label : status;
  }

  return (
    <BaseModal
      onClose={onClose}
      title={movie.title}
      leftContent={
        <>
          <button className="icon danger" title="Удалить" onClick={handleDelete}>
            <Trash />
          </button>
          <button className="icon primary" title="Редактировать" onClick={handleEdit}>
            <Pencil />
          </button>
        </>
      }
    >
      <div className="modal-info">
        <img src={posterUrl} alt={movie.title} className="modal-poster" />
        <div className="modal-details">
          
          <p>
            <strong>Год выхода:</strong> {movie.releaseYear}
          </p>
          <p>
            <strong>Продолжительность:</strong> {formatDuration(movie.duration)}
          </p>
          <p>
            <strong>Режиссёр:</strong> {movie.director}
          </p>

          <p>
            <strong>Язык:</strong> {movie.language}
          </p>
          <p>
            <strong>Возрастной рейтинг:</strong> {movie.ageRating}+
          </p>
          <p>
            <strong>Жанр:</strong> {movie.genre}
          </p>
          <p>
            <strong>Статус:</strong>{" "}
            {movieStatusOptions.find((opt) => opt.value === movie.movieStatus)?.label || movie.movieStatus}
          </p>
        </div>
      </div>

      {movie.description && <p className="modal-description">{movie.description}</p>}

      {sortedSessions.length > 0 && (
        <div className="modal-sessions">
          <h4>Сеансы</h4>
          <ul className="session-list">
            {sortedSessions.map((session, index) => (
              <li key={session.id} className={`session-item session-status-${session.status.toLowerCase()}`}>
                <div className="session-row">
                  <span className="session-left">
                    {index + 1}. {formatDateTime(session.startTime)}
                  </span>
                  <span className="session-right">
                    {session.hallName}, мест: {session.seatCount}
                  </span>
                </div>
                <p>
                  Статус сеанса: <strong>{getSessionStatusLabel(session.status)}</strong>
                </p>
              </li>
            ))}
          </ul>
        </div>
      )}
    </BaseModal>
  );
}
