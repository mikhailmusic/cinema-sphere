import api from './axios';
import type { SessionAddDto, SessionDto } from './types';

export const getAllSessions = async (): Promise<SessionDto[]> => {
  const response = await api.get<SessionDto[]>('/sessions');
  return response.data;
};

export const getSessionById = async (id: number): Promise<SessionDto> => {
  const response = await api.get<SessionDto>(`/sessions/${id}`);
  return response.data;
};

export const addSession = async (data: SessionAddDto): Promise<SessionDto> => {
  const response = await api.post<SessionDto>('/sessions', data);
  return response.data;
};

export const updateSession = async (id: number, data: SessionAddDto): Promise<SessionDto> => {
  const response = await api.put<SessionDto>(`/sessions/${id}`, data);
  return response.data;
};


export const deleteSession = async (id: number): Promise<void> => {
  await api.delete(`/sessions/${id}`);
};
