import { useState } from "react";
import ChevronLeft from "../../assets/icons/ChevronLeft";
import ChevronRight from "../../assets/icons/ChevronRight";

const monthNames: readonly string[] = [
  "Январь",
  "Февраль",
  "Март",
  "Апрель",
  "Май",
  "Июнь",
  "Июль",
  "Август",
  "Сентябрь",
  "Октябрь",
  "Ноябрь",
  "Декабрь"
] as const;

const weekdays: readonly string[] = ["Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс"] as const;

interface CalendarProps {
  selectedDate?: Date;
  onDateSelect: (date: Date) => void;
  startDate?: Date;
}

export default function Calendar({ selectedDate, onDateSelect, startDate }: CalendarProps) {
  const [currentDate, setCurrentDate] = useState<Date>(selectedDate ?? new Date());

  const getDaysInMonth = (year: number, month: number): number => new Date(year, month + 1, 0).getDate();
  const getFirstWeekday = (year: number, month: number): number => (new Date(year, month, 1).getDay() + 6) % 7;

  const handleDayClick = (day: number): void => {
    const selected = new Date(currentDate.getFullYear(), currentDate.getMonth(), day);
    onDateSelect(selected);
  };

  const renderDays = (): React.ReactNode[] => {
    const year = currentDate.getFullYear();
    const month = currentDate.getMonth();
    const totalDays = getDaysInMonth(year, month);
    const firstDay = getFirstWeekday(year, month);
    const today = new Date();

    const days: React.ReactNode[] = [];

    for (let i = 0; i < firstDay; i++) {
      days.push(<div key={`empty-${i}`} className="calendar-day empty" />);
    }

    for (let day = 1; day <= totalDays; day++) {
      const currentDayDate = new Date(year, month, day);

      const isToday = day === today.getDate() && month === today.getMonth() && year === today.getFullYear();

      const isSelected =
        selectedDate !== undefined &&
        day === selectedDate.getDate() &&
        month === selectedDate.getMonth() &&
        year === selectedDate.getFullYear();

      const isDisabled = startDate !== undefined && currentDayDate < startDate;

      days.push(
        <div
          key={day}
          className={[
            "calendar-day",
            isToday ? "today" : "",
            isSelected ? "selected" : "",
            isDisabled ? "disabled" : ""
          ]
            .filter(Boolean)
            .join(" ")}
          onClick={isDisabled ? undefined : () => handleDayClick(day)}
          role="button"
          tabIndex={isDisabled ? -1 : 0}
          onKeyDown={(e) => {
            if (!isDisabled && (e.key === "Enter" || e.key === " ")) {
              e.preventDefault();
              handleDayClick(day);
            }
          }}
          aria-disabled={isDisabled}
          aria-selected={isSelected}
        >
          {day}
        </div>
      );
    }

    return days;
  };

  const prevMonth = (): void => {
    setCurrentDate(new Date(currentDate.getFullYear(), currentDate.getMonth() - 1, 1));
  };

  const nextMonth = (): void => {
    setCurrentDate(new Date(currentDate.getFullYear(), currentDate.getMonth() + 1, 1));
  };

  return (
    <div className="calendar" role="application" aria-label="Календарь">
      <div className="calendar-header">
        <button type="button" onClick={prevMonth} aria-label="Предыдущий месяц" className="calendar-nav">
          <ChevronLeft />
        </button>
        <span aria-live="polite" aria-atomic="true" className="calendar-current-month">
          {monthNames[currentDate.getMonth()]} {currentDate.getFullYear()}
        </span>
        <button type="button" onClick={nextMonth} aria-label="Следующий месяц" className="calendar-nav">
          <ChevronRight />
        </button>
      </div>

      <div className="calendar-weekdays" role="row">
        {weekdays.map((day) => (
          <div key={day} className="calendar-weekday" role="columnheader">
            {day}
          </div>
        ))}
      </div>

      <div className="calendar-grid" role="grid">
        {renderDays()}
      </div>
    </div>
  );
}
