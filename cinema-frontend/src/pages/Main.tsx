import { useEffect, useState, useCallback } from "react";
import { deleteMovie, getAllMovies } from "../api/movieApi";
import MovieRecord from "../components/MovieRecord/MovieRecord";
import MovieFormModal from "../components/Modal/form/MovieFormModal";
import Button from "../components/Button/Button";
import { type MovieDto, type SessionDto, SessionStatus } from "../api/types";

export default function Main() {
  const [movies, setMovies] = useState<MovieDto[]>([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingMovie, setEditingMovie] = useState<MovieDto | null>(null);

  const refreshMovies = useCallback(async () => {
    try {
      const allMovies = await getAllMovies();
      if (Array.isArray(allMovies)) {
        setMovies(allMovies);
      } else {
        setMovies([]);
      }
    } catch (err) {
      console.error("Ошибка при загрузке фильмов:", err);
      setMovies([]);
    }
  }, []);

  useEffect(() => {
    refreshMovies();
  }, [refreshMovies]);

  const openAddModal = useCallback(() => {
    setEditingMovie(null);
    setIsModalOpen(true);
  }, []);

  const openEditModal = useCallback((movie: MovieDto) => {
    setEditingMovie(movie);
    setIsModalOpen(true);
  }, []);

  const closeModal = useCallback(() => {
    setEditingMovie(null);
    setIsModalOpen(false);
  }, []);

  const handleDelete = useCallback(async (movieId: number) => {
    if (!window.confirm("Вы уверены, что хотите удалить этот фильм?")) return;

    try {
      await deleteMovie(movieId);
      setMovies((prev) => prev.filter((m) => m.id !== movieId));
    } catch (err) {
      console.error("Ошибка при удалении фильма:", err);
      alert("Ошибка при удалении фильма");
    }
  }, []);

  const handleSaved = useCallback(
    (savedMovie: MovieDto) => {
      setMovies((prev) => {
        if (editingMovie) {
          return prev.map((m) => (m.id === savedMovie.id ? savedMovie : m));
        } else {
          return [...prev, savedMovie];
        }
      });
      closeModal();
    },
    [editingMovie, closeModal]
  );
  const handleSessionsUpdated = useCallback((movieId: number, updatedSession: SessionDto) => {
    setMovies((prev) =>
      prev.map((movie) => {
        if (movie.id !== movieId) return movie;

        const sessions = movie.sessionList ?? [];
        const index = sessions.findIndex((s) => s.id === updatedSession.id);

        let newSessionList;
        if (index === -1) {
          newSessionList = [...sessions, updatedSession];
        } else {
          newSessionList = [...sessions];
          newSessionList[index] = updatedSession;
        }
        return { ...movie, sessionList: newSessionList };
      })
    );
  }, []);
  function handleSessionDeleted(movieId: number, sessionId: number) {
    setMovies((prevMovies) =>
      prevMovies.map((m) =>
        m.id === movieId
          ? {
              ...m,
              sessionList: m.sessionList?.filter((s) => s.id !== sessionId) || []
            }
          : m
      )
    );
  }

  const statusSections: { [key in MovieDto["movieStatus"]]: string } = {
    ACTIVE: "Сейчас в прокате",
    PLANNED: "Скоро в кино",
    ARCHIVED: "Архивные фильмы"
  };

  const moviesWithoutActiveSessions = movies.filter((movie) => {
    const sessions = movie.sessionList ?? [];
    if (sessions.length === 0) return true;

    return sessions.every((s) => s.status === SessionStatus.CANCELLED);
  });

  const moviesWithActiveSessions = movies.filter((movie) => !moviesWithoutActiveSessions.includes(movie));

  return (
    <main className="main-content">
      <div className="heading-button">
        <h2>Фильмы</h2>
        <Button onClick={openAddModal}>Добавить фильм</Button>
      </div>

      <div className="movie-list">
        {movies.length === 0 ? (
          <p>Фильмы не найдены</p>
        ) : (
          <>
            {Object.entries(statusSections).map(([statusKey, title]) => {
              const sectionMovies = moviesWithActiveSessions.filter((m) => m.movieStatus === statusKey);
              if (sectionMovies.length === 0) return null;

              return (
                <section key={statusKey} id={statusKey.toLowerCase()} className="record-group">
                  <h3>{title}</h3>
                  {sectionMovies.map((movie) => (
                    <MovieRecord
                      key={movie.id}
                      movie={movie}
                      onDetails={() => openEditModal(movie)}
                      onDelete={() => handleDelete(movie.id)}
                      onSessionUpdated={handleSessionsUpdated} onSessionDeleted={handleSessionDeleted}
                    />
                  ))}
                </section>
              );
            })}
            {moviesWithoutActiveSessions.length > 0 && (
              <section id="no-active-sessions" className="record-group no-active-sessions">
                <h3>Фильмы без активных сеансов</h3>
                {moviesWithoutActiveSessions.map((movie) => (
                  <MovieRecord
                    key={movie.id}
                    movie={movie}
                    onDetails={() => openEditModal(movie)}
                    onDelete={() => handleDelete(movie.id)}
                    onSessionUpdated={handleSessionsUpdated}
                    onSessionDeleted={handleSessionDeleted}
                  />
                ))}
              </section>
            )}
          </>
        )}
      </div>

      {isModalOpen && <MovieFormModal movie={editingMovie} onClose={closeModal} onSaved={handleSaved} />}
    </main>
  );
}
