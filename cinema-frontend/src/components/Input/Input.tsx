import React, { type ChangeEvent, type FocusEvent, type MouseEvent, type Ref } from "react";

type InputProps = {
  label?: string;
  name: string;
  type?: string;
  value: string | number;
  onChange: (event: ChangeEvent<HTMLInputElement>) => void;
  placeholder?: string;
  error?: string;
  min?: number | string;
  max?: number | string;
  required?: boolean;
  icon?: React.ReactNode | null;
  onIconClick?: (() => void) | null;
  onFocus?: (event: FocusEvent<HTMLInputElement>) => void;
  readOnly?: boolean;
  inputRef?: Ref<HTMLDivElement> | null;
  onClick?: ((event: MouseEvent<HTMLDivElement>) => void) | null;
};

export default function Input({
  label,
  name,
  type = "text",
  value,
  onChange,
  placeholder = "",
  error = "",
  min,
  max,
  required = false,
  icon = null,
  onIconClick = null,
  onFocus,
  readOnly = false,
  inputRef = null,
  onClick = null
}: InputProps) {
  const renderIcon = () => {
    if (!icon) return null;

    if (typeof onIconClick === "function") {
      return (
        <button type="button" className="input-icon-button" onClick={onIconClick} tabIndex={-1}>
          {icon}
        </button>
      );
    }

    return <span className="input-icon-static">{icon}</span>;
  };

  return (
    <div className="input-wrapper">
      {label && (
        <label htmlFor={name} className="input-label">
          {label}
        </label>
      )}
      <div className={`input-container ${onClick ? "clickable" : ""}`} onClick={onClick ?? undefined} ref={inputRef}>
        <input
          id={name}
          name={name}
          type={type}
          value={value}
          onChange={onChange}
          onFocus={onFocus}
          placeholder={placeholder}
          className={`input-field ${error ? "input-error" : ""}`}
          min={min}
          max={max}
          required={required}
          readOnly={readOnly}
          onWheel={type === "number" ? (e) => e.currentTarget.blur() : undefined}
        />
        {renderIcon()}
      </div>
      {error && <span className="input-error-message">{error}</span>}
    </div>
  );
}
