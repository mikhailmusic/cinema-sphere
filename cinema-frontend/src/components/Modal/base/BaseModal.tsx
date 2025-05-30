import React from "react";
import Close from "../../../assets/icons/Close";

interface BaseModalProps {
  onClose: () => void;
  children: React.ReactNode;
  title?: string;
  leftContent?: React.ReactNode;
  rightContent?: React.ReactNode;
}

export default function BaseModal({ onClose, children, title, leftContent, rightContent }: BaseModalProps) {
  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-window" onClick={(e) => e.stopPropagation()}>
        <div className="modal-header">
          <div className="modal-header-left">{leftContent}</div>

          {title && (
            <div className="modal-header-title">
              <h3>{title}</h3>
            </div>
          )}

          <div className="modal-header-right">
            {rightContent ?? (
              <button className="icon neutral" onClick={onClose} aria-label="Закрыть модальное окно">
                <Close />
              </button>
            )}
          </div>
        </div>

        <div className="modal-body">{children}</div>
      </div>
    </div>
  );
}
