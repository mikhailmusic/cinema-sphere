export type MovieDto = {
  id: number;
  title: string;
  duration: number;
  releaseYear: number;
  director: string;
  image: string;
  description: string;
  ageRating: number;
  language: string;
  genre: string;
  movieStatus: string;
  sessionList: SessionDto[];
};
export type MovieAddDto = {
  languageId: number;
  genreId: number;
  title: string;
  duration: number;
  releaseYear: number;
  director: string;
  image?: File | null;
  description: string;
  ageRating: number;
  movieStatus: string;
};

export const MovieStatus = {
  PLANNED: "PLANNED",
  ACTIVE: "ACTIVE",
  ARCHIVED: "ARCHIVED"
} as const;
export type MovieStatus = typeof MovieStatus[keyof typeof MovieStatus];
export const movieStatusOptions = [
  { label: "Запланирован", value: MovieStatus.PLANNED },
  { label: "Активен", value: MovieStatus.ACTIVE },
  { label: "Архив", value: MovieStatus.ARCHIVED },
];




export type SessionDto = {
  id: number;
  startTime: string;
  status: string;
  hallName: string;
  seatCount: number;
};
export type SessionAddDto = {
  hallId: number;
  movieId: number;
  startTime: string;
  status: string;
}


export type LanguageDto = {
  id: number;
  name: string;
  code: string;
};
export type LanguageAddDto = {
  name: string;
  code: string;
};


export type HallDto = {
  id: number;
  name: string;
  seatCount: number;
};
export type HallAddDto = {
  name: string;
  seatCount: number;
};


export type GenreDto = {
  id: number;
  name: string;
};
export type GenreAddDto = {
  name: string;
};

