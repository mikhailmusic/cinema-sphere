import api, { API_BASE_URL } from './axios'; 
import type { MovieDto, MovieAddDto } from './types';

export const getAllMovies = async (): Promise<MovieDto[]> => {
  const response = await api.get<MovieDto[]>('/movies');
  return response.data;
};

export const getMovieById = async (id: number): Promise<MovieDto> => {
  const response = await api.get<MovieDto>(`/movies/${id}`);
  return response.data;
};


export const addMovie = async (data: MovieAddDto): Promise<MovieDto> => {
  const formData = new FormData();

  if (data.image) {
    formData.append('image', data.image);
  }

  const movieDto = { ...data }; // копия без image: File
  delete movieDto.image;

  formData.append(
    'movie',
    new Blob([JSON.stringify(movieDto)], {
      type: 'application/json',
    })
  );

  const response = await api.post<MovieDto>('/movies', formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  });

  return response.data;
};

export const updateMovie = async (id: number, data: MovieAddDto): Promise<MovieDto> => {
  const formData = new FormData();

  if (data.image) {
    formData.append('image', data.image);
  }

  const movieDto = { ...data };
  delete movieDto.image;

  formData.append(
    'movie',
    new Blob([JSON.stringify(movieDto)], {
      type: 'application/json',
    })
  );

  const response = await api.put(`/movies/${id}`, formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  });
  return response.data;
};


export const deleteMovie = async (id: number): Promise<void> => {
  await api.delete(`/movies/${id}`);
};

export const searchMovies = async (keyword: string): Promise<MovieDto[]> => {
  const response = await api.get<MovieDto[]>(`/movies/search?keyword=${keyword}`);
  return response.data;
};


export const getImageUrl = (imageKey?: string | null): string | undefined => {
  if (!imageKey) return undefined;
  if (imageKey.startsWith("http://") || imageKey.startsWith("https://")) {
    return imageKey;
  }
  return `${API_BASE_URL}/images/${imageKey}`;
};
