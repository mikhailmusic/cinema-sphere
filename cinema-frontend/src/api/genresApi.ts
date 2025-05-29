import api from './axios';
import type { GenreDto, GenreAddDto } from './types';

export const getAllGenres = async (): Promise<GenreDto[]> => {
  const response = await api.get<GenreDto[]>('/genres');
  return response.data;
};

export const addGenre = async (data: GenreAddDto): Promise<GenreDto> => {
  const response = await api.post<GenreDto>('/genres', data);
  return response.data;
};
