import api from './axios';
import type { HallDto, HallAddDto } from './types';

export const getAllHalls = async (): Promise<HallDto[]> => {
  const response = await api.get<HallDto[]>('/halls');
  return response.data;
};

export const addHall = async (data: HallAddDto): Promise<HallDto> => {
  const response = await api.post<HallDto>('/halls', data);
  return response.data;
};

export const updateHall = async (id: number, data: HallAddDto): Promise<void> => {
  await api.put(`/halls/${id}`, data);
};
