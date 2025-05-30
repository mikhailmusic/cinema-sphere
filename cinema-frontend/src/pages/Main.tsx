import { useEffect, useState, useCallback } from "react";
import { deleteMovie, getAllMovies } from "../api/movieApi";
import MovieRecord from "../components/MovieRecord/MovieRecord";
import MovieFormModal from "../components/Modal/MovieFormModal";
import Button from "../components/Button/Button";
import type { MovieDto } from "../api/types";

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

  return (
    <main className="main-content">
      <section className="section">
        <div className="heading-button">
          <h3>Сейчас в прокате</h3>
          <Button onClick={openAddModal}>Добавить фильм</Button>
        </div>

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

      {isModalOpen && <MovieFormModal movie={editingMovie} onClose={closeModal} onSaved={handleSaved} />}
    </main>
  );
}
