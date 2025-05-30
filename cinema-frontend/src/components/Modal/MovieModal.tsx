import { formatDuration } from "../../utils/dateUtils";
import BaseModal from "./BaseModal";
import { getImageUrl } from "../../api/movieApi";
import Pencil from "../../assets/icons/Pencil";
import Trash from "../../assets/icons/Trash";
import type { MovieDto } from "../../api/types";

interface MovieModalProps {
  movie: MovieDto;
  onClose: () => void;
  onEdit: (movie: MovieDto) => void;
  onDelete: (movieId: number) => void;
}

export default function MovieModal({ movie, onClose, onEdit, onDelete }: MovieModalProps) {
  const posterUrl = getImageUrl(movie.image);

  const handleDelete = () => {
    onDelete(movie.id);
  };

  const handleEdit = () => {
    onEdit(movie);
  };

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
            <strong>Продолжительность:</strong> {formatDuration(movie.duration)}
          </p>
          <p>
            <strong>Режиссёр:</strong> {movie.director}
          </p>
          <p>
            <strong>Год выхода:</strong> {movie.releaseYear}
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
            <strong>Статус:</strong> {movie.movieStatus}
          </p>
        </div>
      </div>

      {movie.description && <p className="modal-description">{movie.description}</p>}

      {movie.sessionList?.length > 0 && (
        <div className="modal-sessions">
          <h4>Сеансы:</h4>
          <ul>
            {movie.sessionList.map((session) => (
              <li key={session.id}>
                {session.startTime} — {session.hallName}
              </li>
            ))}
          </ul>
        </div>
      )}
    </BaseModal>
  );
}
