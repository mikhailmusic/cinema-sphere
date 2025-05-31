import React, { useState, useEffect } from "react";
import BaseModal from "../base/BaseModal";
import Input from "../../Input/Input";
import Dropdown from "../../Dropdown/Dropdown";
import Textarea from "../../Textarea/Textarea";
import FileUpload from "../../FileUpload/FileUpload";
import Button from "../../Button/Button";

import type { MovieAddDto, LanguageDto, GenreDto, MovieDto } from "../../../api/types";
import { movieStatusOptions, MovieStatus } from "../../../api/types";
import { updateMovie, addMovie, getAllLanguages, getAllGenres, getImageUrl } from "../../../api";

interface Props {
  movie?: MovieDto | null;
  onClose: () => void;
  onSaved: (movie: MovieDto) => void;
}

export default function MovieFormModal({ movie = null, onClose, onSaved }: Props) {
  const [languages, setLanguages] = useState<LanguageDto[]>([]);
  const [genres, setGenres] = useState<GenreDto[]>([]);

  const [title, setTitle] = useState("");
  const [director, setDirector] = useState("");
  const [duration, setDuration] = useState(0);
  const [releaseYear, setReleaseYear] = useState(new Date().getFullYear());
  const [ageRating, setAgeRating] = useState(0);
  const [languageId, setLanguageId] = useState(-1);
  const [genreId, setGenreId] = useState(-1);
  const [movieStatus, setMovieStatus] = useState<MovieStatus>(MovieStatus.ACTIVE);
  const [description, setDescription] = useState("");
  const [image, setImage] = useState<File | string | null>(null);

  const [errors, setErrors] = useState<{
    title?: string;
    director?: string;
    duration?: string;
    releaseYear?: string;
    ageRating?: string;
    languageId?: string;
    genreId?: string;
    movieStatus?: string;
    description?: string;
    image?: string;
  }>({});

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

      setTitle(movie.title);
      setDirector(movie.director);
      setDuration(movie.duration);
      setReleaseYear(movie.releaseYear);
      setAgeRating(movie.ageRating);
      setLanguageId(lang?.id ?? -1);
      setGenreId(gen?.id ?? -1);
      setMovieStatus(movie.movieStatus as MovieStatus);
      setDescription(movie.description);

      setImage(getImageUrl(movie.image) ?? null);
    } else {
      setTitle("");
      setDirector("");
      setDuration(0);
      setReleaseYear(new Date().getFullYear());
      setAgeRating(0);
      setLanguageId(-1);
      setGenreId(-1);
      setMovieStatus(MovieStatus.ACTIVE);
      setDescription("");
      setImage(null);
    }

    setErrors({});
  }, [movie, languages, genres]);

  useEffect(() => {
    let objectUrl: string | null = null;
    if (image instanceof File) {
      objectUrl = URL.createObjectURL(image);
      return () => {
        if (objectUrl) URL.revokeObjectURL(objectUrl);
      };
    }
  }, [image]);

  const validate = (): boolean => {
    const newErrors: typeof errors = {};

    if (languageId === -1) newErrors.languageId = "Пожалуйста, выберите язык";
    if (genreId === -1) newErrors.genreId = "Пожалуйста, выберите жанр";
    if (!title.trim()) newErrors.title = "Введите название фильма";
    if (duration <= 0) newErrors.duration = "Введите корректную длительность";
    if (ageRating < 0) newErrors.ageRating = "Возрастной рейтинг не может быть отрицательным";
    if (!movieStatus) newErrors.movieStatus = "Пожалуйста, выберите статус фильма";

    if (!image) {
      newErrors.image = "Пожалуйста, загрузите изображение";
    }

    setErrors(newErrors);

    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!validate()) return;

    const movieDto: MovieAddDto = {
      languageId,
      genreId,
      title: title.trim(),
      duration,
      releaseYear,
      director: director.trim(),
      description: description.trim(),
      ageRating,
      movieStatus,
      image: image instanceof File ? image : null
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
  };

  return (
    <BaseModal onClose={onClose} title={movie ? "Редактировать фильм" : "Новый фильм"}>
      <form className="modal-form" onSubmit={handleSubmit} noValidate>
        <Input name="title" label="Название" value={title} onChange={(e) => setTitle(e.target.value)} required error={errors.title} />

        <Input
          name="director"
          label="Режиссер"
          value={director}
          onChange={(e) => setDirector(e.target.value)}
          required
          error={errors.director}
        />

        <div className="modal-input-group">
          <Input
            name="duration"
            label="Длительность (мин)"
            type="number"
            value={duration}
            onChange={(e) => setDuration(Number(e.target.value))}
            min={1}
            required
            error={errors.duration}
          />
          <Input
            name="releaseYear"
            label="Год выхода"
            type="number"
            value={releaseYear}
            onChange={(e) => setReleaseYear(Number(e.target.value))}
            min={1800}
            max={new Date().getFullYear() + 5}
            required
            error={errors.releaseYear}
          />
        </div>

        <Input
          name="ageRating"
          label="Возрастной рейтинг"
          type="number"
          value={ageRating}
          onChange={(e) => setAgeRating(Number(e.target.value))}
          min={0}
          required
          error={errors.ageRating}
        />

        <Dropdown
          name="movie-language"
          label="Язык"
          options={languages.map((l) => ({ label: l.name, value: String(l.id) }))}
          value={languageId === -1 ? "" : String(languageId)}
          onChange={(value) => setLanguageId(Number(value))}
          placeholder="Выберите язык"
          required
          error={errors.languageId}
        />

        <Dropdown
          name="movie-genre"
          label="Жанр"
          options={genres.map((g) => ({ label: g.name, value: String(g.id) }))}
          value={genreId === -1 ? "" : String(genreId)}
          onChange={(value) => setGenreId(Number(value))}
          placeholder="Выберите жанр"
          required
          error={errors.genreId}
        />

        <Dropdown
          name="movie-status"
          label="Статус"
          options={movieStatusOptions}
          value={movieStatus}
          onChange={(value: string) => setMovieStatus(value as MovieStatus)}
          placeholder="Выберите статус"
          required
          error={errors.movieStatus}
        />

        <Textarea
          name="description"
          label="Описание"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          required
          error={errors.description}
        />

        <FileUpload label="Постер" file={image} onChange={setImage} error={errors.image} />

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
