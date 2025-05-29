export const formatDate = (dateStr: string): string => {
  const date = new Date(dateStr);
  if (isNaN(date.getTime())) return "";
  return date.toLocaleDateString("ru-RU", {
    year: "numeric",
    month: "long",
    day: "numeric",
  });
};

export const formatDuration = (minutes: number): string => {
  const h = Math.floor(minutes / 60);
  const m = minutes % 60;
  return [
    h > 0 ? `${h} ч` : null,
    m > 0 ? `${m} мин` : null,
  ]
    .filter(Boolean)
    .join(" ");
};

export const formatDateRange = (startDateStr: string, endDateStr: string): string => {
  const startDate = new Date(startDateStr);
  const endDate = new Date(endDateStr);

  if (isNaN(startDate.getTime()) || isNaN(endDate.getTime())) return "";

  const sameYear = startDate.getFullYear() === endDate.getFullYear();

  const startFormatted = startDate.toLocaleDateString("ru-RU", {
    day: "2-digit",
    month: "2-digit",
    ...(sameYear ? {} : { year: "numeric" }),
  });

  const endFormatted = endDate.toLocaleDateString("ru-RU", {
    day: "2-digit",
    month: "2-digit",
    year: "numeric",
  });

  return `${startFormatted} – ${endFormatted}`;
};
