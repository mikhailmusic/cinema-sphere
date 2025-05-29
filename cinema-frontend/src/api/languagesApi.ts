import api from './axios';
import type { LanguageDto, LanguageAddDto } from './types';

export const getAllLanguages = async (): Promise<LanguageDto[]> => {
  const response = await api.get<LanguageDto[]>('/languages');
  return response.data;
};

export const addLanguage = async (data: LanguageAddDto): Promise<LanguageDto> => {
  const response = await api.post<LanguageDto>('/languages', data);
  return response.data;
};
