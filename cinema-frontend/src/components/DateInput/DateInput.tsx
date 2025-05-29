import { useState, useRef, useEffect, type ChangeEvent } from "react";
import Input from "../Input/Input";
import Calendar from "../Calendar/Calendar";
import CalendarIcon from "../../assets/icons/Calendar";

interface DateInputProps {
  label?: string;
  value: string;
  onChange: (value: string) => void;
  required?: boolean;
  startDate?: string;
}

export default function DateInput({ label, value, onChange, required = false, startDate }: DateInputProps) {
  const [showCalendar, setShowCalendar] = useState(false);
  const wrapperRef = useRef<HTMLDivElement>(null);

  const handleDateSelect = (date: Date) => {
    if (!(date instanceof Date)) return;

    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const day = String(date.getDate()).padStart(2, "0");

    const formattedDate = `${year}-${month}-${day}`;
    onChange(formattedDate);
    setShowCalendar(false);
  };

  useEffect(() => {
    function handleClickOutside(event: MouseEvent) {
      if (wrapperRef.current && !wrapperRef.current.contains(event.target as Node)) {
        setShowCalendar(false);
      }
    }

    if (showCalendar) {
      document.addEventListener("mousedown", handleClickOutside);
    } else {
      document.removeEventListener("mousedown", handleClickOutside);
    }

    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, [showCalendar]);

  const handleInputChange = (e: ChangeEvent<HTMLInputElement>) => {
    onChange(e.target.value);
  };

  return (
    <div className="date-input-wrapper" ref={wrapperRef}>
      <Input
        name="date"
        label={label}
        type="text"
        value={value ? new Date(value).toLocaleDateString("ru-RU") : ""}
        onChange={handleInputChange}
        placeholder="дд.мм.гггг"
        required={required}
        icon={<CalendarIcon />}
        onClick={() => setShowCalendar((v) => !v)}
        readOnly={true}
      />
      {showCalendar && (
        <div className="calendar-popup">
          <Calendar
            selectedDate={value ? new Date(value) : undefined}
            onDateSelect={handleDateSelect}
            startDate={startDate ? new Date(startDate) : undefined}
          />
        </div>
      )}
    </div>
  );
}
