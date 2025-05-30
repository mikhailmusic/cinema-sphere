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
  movieStatus: MovieStatus;
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
  movieStatus: MovieStatus;
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


export const SessionStatus = {
  ACTIVE: "ACTIVE",
  CANCELLED: "CANCELLED",
} as const;
export type SessionStatus = typeof SessionStatus[keyof typeof SessionStatus];
export const sessionStatusOptions = [
  { label: "Активен", value: SessionStatus.ACTIVE },
  { label: "Отменён", value: SessionStatus.CANCELLED },
];



export type SessionDto = {
  id: number;
  startTime: string;
  status: SessionStatus;
  hallName: string;
  seatCount: number;
};
export type SessionAddDto = {
  hallId: number;
  movieId: number;
  startTime: string;
  status: SessionStatus;
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

