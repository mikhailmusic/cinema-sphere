export const formatDate = (dateStr: string): string => {
  const date = new Date(dateStr);
  if (isNaN(date.getTime())) return "";
  return date.toLocaleDateString("ru-RU", {
    year: "numeric",
    month: "long",
    day: "numeric"
  });
};

export const formatDuration = (minutes: number): string => {
  const h = Math.floor(minutes / 60);
  const m = minutes % 60;
  return [h > 0 ? `${h} ч` : null, m > 0 ? `${m} мин` : null].filter(Boolean).join(" ");
};

export const formatDateTime = (isoString: string): string => {
  const date = new Date(isoString);
  if (isNaN(date.getTime())) return "";

  const now = new Date();
  const year = date.getFullYear();
  const sameYear = year === now.getFullYear();

  const dateOptions: Intl.DateTimeFormatOptions = {
    day: "numeric",
    month: "long",
    ...(sameYear ? {} : { year: "numeric" })
  };
  const datePart = new Intl.DateTimeFormat("ru-RU", dateOptions).format(date);

  const hours = date.getHours().toString().padStart(2, "0");
  const minutes = date.getMinutes().toString().padStart(2, "0");

  return `${datePart} ${hours}:${minutes}`;
};
