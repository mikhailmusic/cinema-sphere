import { useEffect, useState, useCallback } from "react";
import { getAllMovies } from "../api/movieApi";
import MovieRecord from "../components/MovieRecord/MovieRecord";
import MovieFormModal from "../components/Modal/MovieFormModal";
import Button from "../components/Button/Button";
import type { MovieDto } from "../api/types";

export default function Main() {
  const [movies, setMovies] = useState<MovieDto[]>([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingMovie, setEditingMovie] = useState<MovieDto | null>(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const refreshMovies = useCallback(async () => {
    setLoading(true);
    setError(null);
    try {
      const allMovies = await getAllMovies();
      if (Array.isArray(allMovies)) {
        setMovies(allMovies);
      } else {
        setMovies([]);
      }
    } catch {
      setError("Не удалось загрузить фильмы. Попробуйте позже.");
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

  const handleDelete = useCallback((movieId: number) => {
    console.log(`Delete movie ${movieId}`);
  }, []);

  const handleSaved = useCallback((savedMovie: MovieDto) => {
    setMovies((prev) => {
      if (editingMovie) {
        return prev.map((m) => (m.id === savedMovie.id ? savedMovie : m));
      } else {
        return [...prev, savedMovie];
      }
    });
    closeModal();
  }, [editingMovie, closeModal]);

  return (
    <main className="main-content">
      <section className="section">
        <div className="heading-button">
          <h3>Сейчас в прокате</h3>
          <Button onClick={openAddModal}>Добавить фильм</Button>
        </div>

        {error && <p className="error-message">{error}</p>}

        <div className="movie-list">
          {loading ? (
            <p>Загрузка...</p>
          ) : movies.length > 0 ? (
            movies.map((movie) => (
              <MovieRecord
                key={movie.id}
                movie={movie}
                onDetails={() => openEditModal(movie)}
                onDelete={() => handleDelete(movie.id)}
              />
            ))
          ) : (
            <p>Фильмы не найдены</p>
          )}
        </div>
      </section>

      {isModalOpen && (
        <MovieFormModal
          movie={editingMovie}
          onClose={closeModal}
          onSaved={handleSaved}
        />
      )}
    </main>
  );
}
