import React, { type ChangeEvent } from "react";

interface TextareaProps {
  label?: string;
  name?: string;
  value: string;
  onChange: (e: ChangeEvent<HTMLTextAreaElement>) => void;
  rows?: number;
  required?: boolean;
}

const Textarea: React.FC<TextareaProps> = ({
  label,
  name,
  value,
  onChange,
  rows = 4,
  required = false
}) => {
  return (
    <div className="input-wrapper">
      {label && <label className="input-label">{label}</label>}
      <textarea
        className="input-field text-area-field"
        name={name}
        value={value}
        onChange={onChange}
        rows={rows}
        required={required}
      />
    </div>
  );
};

export default Textarea;
