import React, { type ChangeEvent } from "react";

interface TextareaProps {
  label?: string;
  name?: string;
  value: string;
  onChange: (e: ChangeEvent<HTMLTextAreaElement>) => void;
  rows?: number;
  required?: boolean;
  error?: string;
  readOnly?: boolean;
}

const Textarea: React.FC<TextareaProps> = ({
  label,
  name,
  value,
  onChange,
  rows = 4,
  required = false,
  error = "",
  readOnly = false
}) => {
  return (
<div  className={`input-wrapper ${readOnly ? "readonly" : ""}`}>
      {label && <label className="input-label" htmlFor={name}>{label}</label>}
      <textarea
        id={name}
        name={name}
        className={`input-field text-area-field ${error ? "input-error" : ""}`}
        value={value}
        onChange={onChange}
        rows={rows}
        required={required}
        readOnly={readOnly}
      />
      {error && <span className="input-error-message">{error}</span>}
    </div>
  );
};

export default Textarea;
