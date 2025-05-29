import { useState, useEffect, useMemo } from "react";
import BaseModal from "./BaseModal";
import Input from "../Input/Input";
import Dropdown from "../Dropdown/Dropdown";
import Textarea from "../Textarea/Textarea";
import FileUpload from "../FileUpload/FileUpload";
import Button from "../Button/Button";

import type { MovieAddDto, LanguageDto, GenreDto, MovieStatus, MovieDto } from "../../api/types";
import { movieStatusOptions } from "../../api/types";
import { updateMovie, addMovie, getAllLanguages, getAllGenres } from "../../api";

interface Props {
  movie?: MovieDto | null;
  onClose: () => void;
onSaved: (movie: MovieDto) => void;
}

export default function MovieFormModal({ movie = null, onClose, onSaved }: Props) {
  const [languages, setLanguages] = useState<LanguageDto[]>([]);
  const [genres, setGenres] = useState<GenreDto[]>([]);

  const [form, setForm] = useState<Omit<MovieAddDto, "image"> & { image: File | null }>({
    languageId: -1,
    genreId: -1,
    title: "",
    duration: 0,
    releaseYear: new Date().getFullYear(),
    director: "",
    description: "",
    ageRating: 0,
    movieStatus: "" as MovieStatus | "",
    image: null,
  });

  useEffect(() => {
    (async () => {
      const [langs, gens] = await Promise.all([getAllLanguages(), getAllGenres()]);
      setLanguages(langs);
      setGenres(gens);
    })();
  }, []);

  useEffect(() => {
    if (movie) {
      const lang = languages.find((l) => l.name === movie.language);
      const gen = genres.find((g) => g.name === movie.genre);

      setForm({
        languageId: lang?.id ?? -1,
        genreId: gen?.id ?? -1,
        title: movie.title,
        duration: movie.duration,
        releaseYear: movie.releaseYear,
        director: movie.director,
        description: movie.description,
        ageRating: movie.ageRating,
        movieStatus: movie.movieStatus as MovieStatus,
        image: null,
      });
    } else {
      setForm({
        languageId: -1,
        genreId: -1,
        title: "",
        duration: 0,
        releaseYear: new Date().getFullYear(),
        director: "",
        description: "",
        ageRating: 0,
        movieStatus: "" as MovieStatus | "",
        image: null,
      });
    }
  }, [movie, languages, genres]);

  const handleChange = <K extends keyof typeof form>(field: K, value: typeof form[K]) => {
    setForm((prev) => ({ ...prev, [field]: value }));
  };

  const previewImageUrl = useMemo(() => {
    if (form.image instanceof File) {
      return URL.createObjectURL(form.image);
    }
    return movie?.image ?? "";
  }, [form.image, movie?.image]);

  useEffect(() => {
    return () => {
      if (form.image instanceof File) {
        URL.revokeObjectURL(previewImageUrl);
      }
    };
  }, [previewImageUrl, form.image]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (form.languageId === -1) {
      alert("Пожалуйста, выберите язык.");
      return;
    }
    if (form.genreId === -1) {
      alert("Пожалуйста, выберите жанр.");
      return;
    }
    if (!form.title.trim()) {
      alert("Введите название фильма.");
      return;
    }
    if (form.duration <= 0) {
      alert("Введите корректную длительность.");
      return;
    }
    if (form.ageRating < 0) {
      alert("Возрастной рейтинг не может быть отрицательным.");
      return;
    }
    if (!form.movieStatus) {
      alert("Пожалуйста, выберите статус фильма.");
      return;
    }

    const movieDto: MovieAddDto = {
      languageId: form.languageId,
      genreId: form.genreId,
      title: form.title.trim(),
      duration: form.duration,
      releaseYear: form.releaseYear,
      director: form.director.trim(),
      description: form.description.trim(),
      ageRating: form.ageRating,
      movieStatus: form.movieStatus,
      image: form.image ?? null,
    };

    try {
    let savedMovie: MovieDto;

    if (movie && movie.id) {
      savedMovie = await updateMovie(movie.id, movieDto);
    } else {
      savedMovie = await addMovie(movieDto);
    }

    onSaved(savedMovie);
    } catch (error) {
      alert("Ошибка при сохранении фильма");
      console.error(error);
    }
  }

  return (
    <BaseModal onClose={onClose} title={movie ? "Редактировать фильм" : "Новый фильм"}>
      <form className="modal-form" onSubmit={handleSubmit} noValidate>
        <Input
          name="title"
          label="Название"
          value={form.title}
          onChange={(e) => handleChange("title", e.target.value)}
          required
        />
        <Input
          name="director"
          label="Режиссер"
          value={form.director}
          onChange={(e) => handleChange("director", e.target.value)}
          required
        />

        <div className="modal-input-group">
          <Input
            name="duration"
            label="Длительность (мин)"
            type="number"
            value={form.duration}
            onChange={(e) => handleChange("duration", Number(e.target.value))}
            min={1}
            required
          />
          <Input
            name="releaseYear"
            label="Год выхода"
            type="number"
            value={form.releaseYear}
            onChange={(e) => handleChange("releaseYear", Number(e.target.value))}
            min={1800}
            max={new Date().getFullYear() + 5}
            required
          />
        </div>

        <Input
          name="ageRating"
          label="Возрастной рейтинг"
          type="number"
          value={form.ageRating}
          onChange={(e) => handleChange("ageRating", Number(e.target.value))}
          min={0}
          required
        />

        <Dropdown
          label="Язык"
          options={languages.map((l) => ({ label: l.name, value: String(l.id) }))}
          value={form.languageId === -1 ? "" : String(form.languageId)}
          onChange={(val) => handleChange("languageId", Number(val))}
          placeholder="Выберите язык"
          required
        />

        <Dropdown
          label="Жанр"
          options={genres.map((g) => ({ label: g.name, value: String(g.id) }))}
          value={form.genreId === -1 ? "" : String(form.genreId)}
          onChange={(val) => handleChange("genreId", Number(val))}
          placeholder="Выберите жанр"
          required
        />

        <Dropdown
          label="Статус"
          options={movieStatusOptions}
          value={form.movieStatus}
          onChange={(val) => handleChange("movieStatus", val as MovieStatus)}
          placeholder="Выберите статус"
          required
        />

        <Textarea
          name="description"
          label="Описание"
          value={form.description}
          onChange={(e) => handleChange("description", e.target.value)}
          required
        />

        <FileUpload
          label="Постер"
          file={previewImageUrl}
          onChange={(file) => handleChange("image", file)}
        />

        <div className="add-movie-buttons">
          <Button onClick={onClose}>Отменить</Button>
          <Button submit variant="success">
            Сохранить
          </Button>
        </div>
      </form>
    </BaseModal>
  );
}
