import React from 'react';

type ButtonProps = {
  variant?: 'primary' | 'secondary' | 'danger' | 'success';
  onClick?: () => void;
  children: React.ReactNode;
  disabled?: boolean,
  submit?: boolean;
};

export default function Button({
  variant = 'primary',
  onClick,
  children,
  disabled = false,
  submit
}: ButtonProps) {
  const classNames = `btn btn-${variant} ${disabled ? 'btn-disabled' : ''}`;

  return (
    <button
      type={submit ? "submit" : "button"}
      className={classNames}
      onClick={disabled ? undefined : onClick}
      disabled={disabled}
    >
      {children}
    </button>
  );
}
