import { useEffect, useState, useCallback } from "react";
import { deleteMovie, getAllMovies } from "../api/movieApi";
import MovieRecord from "../components/MovieRecord/MovieRecord";
import MovieFormModal from "../components/Modal/form/MovieFormModal";
import Button from "../components/Button/Button";
import type { MovieDto, SessionDto } from "../api/types";

export default function Main() {
  const [movies, setMovies] = useState<MovieDto[]>([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingMovie, setEditingMovie] = useState<MovieDto | null>(null);
  const [loading, setLoading] = useState(false);

  const refreshMovies = useCallback(async () => {
    setLoading(true);
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
    } finally {
      setLoading(false);
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

  const statusSections: { [key in MovieDto["movieStatus"]]: string } = {
    ACTIVE: "Сейчас в прокате",
    PLANNED: "Скоро в кино",
    ARCHIVED: "Архивные фильмы"
  };

  return (
    <main className="main-content">
      <div className="heading-button">
        <h2>Фильмы</h2>
        <Button onClick={openAddModal}>Добавить фильм</Button>
      </div>

      <div className="movie-list">
        {loading ? (
          <p>Загрузка...</p>
        ) : movies.length === 0 ? (
          <p>Фильмы не найдены</p>
        ) : (
          Object.entries(statusSections).map(([statusKey, title]) => {
            const sectionMovies = movies.filter((m) => m.movieStatus === statusKey);
            if (sectionMovies.length === 0) return null;

            return (
              <section key={statusKey} className="record-group">
                <h3>{title}</h3>
                {sectionMovies.map((movie) => (
                  <MovieRecord
                    key={movie.id}
                    movie={movie}
                    onDetails={() => openEditModal(movie)}
                    onDelete={() => handleDelete(movie.id)}
                    onSessionUpdated={handleSessionsUpdated}
                  />
                ))}
              </section>
            );
          })
        )}
      </div>

      {isModalOpen && <MovieFormModal movie={editingMovie} onClose={closeModal} onSaved={handleSaved} />}
    </main>
  );
}
