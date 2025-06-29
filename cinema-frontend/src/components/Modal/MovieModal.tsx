import { useState } from "react";
import { formatDuration, formatDateTime } from "../../utils/dateUtils";
import BaseModal from "./base/BaseModal";
import { addSession, updateSession, deleteSession, getImageUrl } from "../../api";
import Pencil from "../../assets/icons/Pencil";
import Trash from "../../assets/icons/Trash";
import type { MovieDto, SessionDto, SessionAddDto } from "../../api/types";
import { movieStatusOptions, sessionStatusOptions, MovieStatus } from "../../api/types";
import Button from "../Button/Button";
import SessionFormInline from "./SessionFormInline";

interface MovieModalProps {
  movie: MovieDto;
  onClose: () => void;
  onEdit: (movie: MovieDto) => void;
  onDelete: (movieId: number) => void;
  onSessionUpdated: (movieId: number, updatedSession: SessionDto) => void;
  onSessionDeleted: (movieId: number, sessionId: number) => void;
}

export default function MovieModal({ movie, onClose, onEdit, onDelete, onSessionUpdated, onSessionDeleted }: MovieModalProps) {
  const posterUrl = getImageUrl(movie.image);
  const sortedSessions = movie.sessionList
    ? [...movie.sessionList].sort((a, b) => new Date(a.startTime).getTime() - new Date(b.startTime).getTime())
    : [];
  const [addingSession, setAddingSession] = useState(false);
  const [editingSessionId, setEditingSessionId] = useState<number | null>(null);
  const isMovieEditable = movie.movieStatus === MovieStatus.ACTIVE || movie.movieStatus === MovieStatus.PLANNED;

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

  const closeForm = () => {
    setAddingSession(false);
    setEditingSessionId(null);
  };
  const handleSessionSaved = async (sessionData: SessionAddDto, sessionId?: number) => {
    try {
      let updatedSession: SessionDto;

      if (sessionId) {
        updatedSession = await updateSession(sessionId, sessionData);
      } else {
        updatedSession = await addSession(sessionData);
      }

      onSessionUpdated(movie.id, updatedSession);
      closeForm();
    } catch (error) {
      console.error(error);
      alert("Ошибка при сохранении сеанса");
    }
  };
  const handleDeleteSession = async (sessionId: number) => {
    if (!window.confirm("Удалить этот сеанс?")) return;

    try {
      await deleteSession(sessionId);
      onSessionDeleted(movie.id, sessionId);
    } catch (error) {
      console.error(error);
      alert("Ошибка при удалении сеанса");
    }
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
      <section className="modal-info">
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
            <strong>Статус:</strong> {movieStatusOptions.find((opt) => opt.value === movie.movieStatus)?.label || movie.movieStatus}
          </p>
        </div>
      </section>

      {movie.description && (
        <section aria-label="Описание">
          <p className="movie-description">{movie.description}</p>
        </section>
      )}

      <section className="modal-sessions">
        <div className="session-add">
          <h3>Сеансы</h3>
          {isMovieEditable && (
            <Button
              onClick={() => {
                setAddingSession(true);
                setEditingSessionId(null);
              }}
            >
              + Добавить
            </Button>
          )}
        </div>

        {addingSession && isMovieEditable && (
          <SessionFormInline movieId={movie.id} onCancel={closeForm} onSaved={(sessionData) => handleSessionSaved(sessionData)} />
        )}

        {sortedSessions.length > 0 && (
          <ul className="session-list">
            {sortedSessions.map((session, index) => (
              <li key={session.id} className={`session-item session-status-${session.status.toLowerCase()}`}>
                <article
                  onClick={() => {
                    if (!isMovieEditable) return;
                    if (editingSessionId === session.id) {
                      closeForm();
                    } else {
                      setEditingSessionId(session.id);
                      setAddingSession(false);
                    }
                  }}
                  className={`session-info ${!isMovieEditable ? 'session-info-disabled' : ''}`}
                >
                  <div className="session-row">
                    <span className="session-left">
                      {index + 1}. {formatDateTime(session.startTime)}
                    </span>
                    <span className="session-right">
                      {session.hallName}, мест: {session.seatCount}
                    </span>
                  </div>
                  <div className="session-row">
                    <p>
                      Статус сеанса: <strong>{getSessionStatusLabel(session.status)}</strong>
                    </p>
                    {isMovieEditable && (
                      <Button
                        onClick={(e) => {
                          e.stopPropagation();
                          handleDeleteSession(session.id);
                        }}
                        variant="danger"
                      >
                        Удалить
                      </Button>
                    )}
                  </div>
                </article>

                {editingSessionId === session.id && isMovieEditable && (
                  <SessionFormInline
                    movieId={movie.id}
                    session={session}
                    onCancel={closeForm}
                    onSaved={(sessionData) => handleSessionSaved(sessionData, editingSessionId)}
                  />
                )}
              </li>
            ))}
          </ul>
        )}
      </section>
    </BaseModal>
  );
}
