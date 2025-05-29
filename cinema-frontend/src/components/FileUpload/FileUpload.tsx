import { useState, useEffect, useRef } from "react";
import Button from "../Button/Button";
import Close from "../../assets/icons/Close";

type FileUploadProps = {
  label?: string;
  file: File | string | null;
  onChange: (file: File | null) => void;
  accept?: string;
  required?: boolean;
  error?: string;
};

export default function FileUpload({ label, file, onChange, accept = "image/*", required = false, error }: FileUploadProps) {
  const fileInputRef = useRef<HTMLInputElement | null>(null);
  const [preview, setPreview] = useState<string | null>(null);
  const [fileName, setFileName] = useState<string>("");

  useEffect(() => {
    if (typeof file === "string") {
      setPreview(file);
      setFileName("");
      return;
    }
    if (file instanceof File) {
      setFileName(file.name);
      const objectUrl = URL.createObjectURL(file);
      setPreview(objectUrl);

      return () => URL.revokeObjectURL(objectUrl);
    }

    setPreview(null);
    setFileName("");
  }, [file]);

  const handleClick = () => {
    fileInputRef.current?.click();
  };

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const selectedFile = e.target.files?.[0] ?? null;
    onChange(selectedFile);
  };

  const handleClear = () => {
    if (fileInputRef.current) {
      fileInputRef.current.value = "";
    }
    setPreview(null);
    setFileName("");
    onChange(null);
  };

  return (
    <div className={`input-wrapper ${error ? "has-error" : ""}`}>
      {label && <label className="input-label">{label}</label>}

      <div className="file-upload-wrapper">
        <input
          type="file"
          ref={fileInputRef}
          onChange={handleFileChange}
          accept={accept}
          required={required && !preview}
          className="file-input"
          style={{ display: "none" }}
        />
        <Button onClick={handleClick}>Выберите файл</Button>

        {fileName && <span className="file-name">{fileName}</span>}
      </div>

      {preview && (
        <div className="image-preview-wrapper">
          <img src={preview} alt="Превью" className="image-preview" />
          <button type="button" className="clear-file-button" onClick={handleClear} aria-label="Очистить изображение">
            <Close />
          </button>
        </div>
      )}

      {error && <div className="input-error-message">{error}</div>}
    </div>
  );
}
