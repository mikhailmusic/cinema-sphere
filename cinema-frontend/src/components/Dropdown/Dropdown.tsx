import { useState, useRef, useEffect } from "react";
import Input from "../Input/Input";
import ChevronDown from "../../assets/icons/ChevronDown";

interface DropdownOption {
  label: string;
  value: string;
}

interface DropdownProps {
  name: string;
  label?: string;
  options: DropdownOption[];
  value: string;
  onChange: (value: string) => void;
  placeholder?: string;
  required?: boolean;
  error?: string;
}

export default function Dropdown({
  name,
  label,
  options = [],
  value,
  onChange,
  placeholder = "Выберите...",
  required = false,
  error = ""
}: DropdownProps) {
  const [open, setOpen] = useState(false);
  const wrapperRef = useRef<HTMLDivElement>(null);

  const toggleOpen = () => setOpen((prev) => !prev);

  const handleSelect = (optionValue: string) => {
    onChange(optionValue);
    setOpen(false);
  };

  const selectedOption = options.find((opt) => opt.value === value);

  useEffect(() => {
    function handleClickOutside(event: MouseEvent) {
      if (wrapperRef.current && !wrapperRef.current.contains(event.target as Node)) {
        setOpen(false);
      }
    }

    if (open) {
      document.addEventListener("mousedown", handleClickOutside);
    } else {
      document.removeEventListener("mousedown", handleClickOutside);
    }

    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, [open]);

  return (
    <div className="dropdown-wrapper" ref={wrapperRef}>
      <Input
        name={name}
        label={label}
        value={selectedOption?.label || ""}
        onChange={() => {}}
        placeholder={placeholder}
        readOnly
        required={required}
        error={error}
        icon={<ChevronDown className={`dropdown-arrow ${open ? "rotated" : ""}`} />}
        onClick={toggleOpen}
      />
      {open && (
        <ul className="dropdown-list">
          {options.map((option) => (
            <li key={option.value} onClick={() => handleSelect(option.value)} className={option.value === value ? "active" : ""}>
              {option.label}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}
