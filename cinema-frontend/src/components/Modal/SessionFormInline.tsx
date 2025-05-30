import React, { useState, useEffect } from "react";
import Dropdown from "../Dropdown/Dropdown";
import Button from "../Button/Button";
import Input from "../Input/Input";
import { getAllHalls } from "../../api/hallsApi";
import { type HallDto, type SessionDto, type SessionAddDto, sessionStatusOptions, SessionStatus } from "../../api/types";

interface Props {
  movieId: number;
  session?: SessionDto | null;
  onCancel: () => void;
  onSaved: (sessionDto: SessionAddDto) => void;
}

export default function SessionFormInline({ movieId, session = null, onCancel, onSaved }: Props) {
  const [halls, setHalls] = useState<HallDto[]>([]);
  const [startTime, setStartTime] = useState<string>(session?.startTime ?? "");
  const [hallId, setHallId] = useState<number>(-1);
  const [status, setStatus] = useState<SessionStatus>(session?.status ?? SessionStatus.ACTIVE);

  const [errors, setErrors] = useState<{
    startTime?: string;
    hallId?: string;
    status?: string;
  }>({});

  useEffect(() => {
    (async () => {
      try {
        const hallsData = await getAllHalls();
        setHalls(hallsData);
        if (session?.hallName) {
          const hall = hallsData.find((h) => h.name === session.hallName);
          setHallId(hall ? hall.id : -1);
        }
      } catch {
        alert("Ошибка при загрузке залов");
      }
    })();
  }, [session]);

  const validate = () => {
    const newErrors: typeof errors = {};

    if (!startTime) newErrors.startTime = "Пожалуйста, выберите дату и время";
    if (hallId === -1) newErrors.hallId = "Пожалуйста, выберите зал";
    if (!status) newErrors.status = "Пожалуйста, выберите статус";

    setErrors(newErrors);

    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    if (!validate()) return;

    const sessionDto: SessionAddDto = {
      hallId,
      movieId,
      startTime,
      status
    };

    onSaved(sessionDto);
  };

  return (
    <form className="session-form-inline" onSubmit={handleSubmit} noValidate>
      <Input
        name="dateTime"
        label="Дата и время начала"
        type="datetime-local"
        value={startTime}
        onChange={(e) => setStartTime(e.target.value)}
        required
        error={errors.startTime}
      />

<div className="session-input-group">
      <Dropdown
        label="Зал"
        options={halls.map((h) => ({ label: h.name, value: String(h.id) }))}
        value={hallId === -1 ? "" : String(hallId)}
        onChange={(val) => setHallId(Number(val))}
        placeholder="Выберите зал"
        required
        error={errors.hallId}
      />

      <Dropdown
        label="Статус"
        options={sessionStatusOptions}
        value={status}
        onChange={(val: string) => setStatus(val as SessionStatus)}
        placeholder="Выберите статус"
        required
        error={errors.status}
      />
</div>


      <div className="add-session-buttons">
        <Button onClick={onCancel}>Отмена</Button>
        <Button submit variant="success">
          Сохранить
        </Button>
      </div>
    </form>
  );
}
