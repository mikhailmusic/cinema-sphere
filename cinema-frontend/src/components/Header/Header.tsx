import React, { useState } from "react";
import Burger from "../../assets/icons/BurgerMenu";


const Header: React.FC = () => {
  const [isMenuOpen, setIsMenuOpen] = useState<boolean>(false);

  const toggleMenu = (): void => {
    setIsMenuOpen((prev) => !prev);
  };

  return (
    <header className="header">
      <div className="nav">
        <a href="/" className="logo">Кинотеатр «Сфера»</a>
        <nav>
          <ul className={`nav-list ${isMenuOpen ? "active" : ""}`}>
            <li><a href="#active" onClick={() => setIsMenuOpen(false)}>В прокате</a></li>
            <li><a href="#planned" onClick={() => setIsMenuOpen(false)}>Скоро в кино</a></li>
            <li><a href="#no-active-sessions" onClick={() => setIsMenuOpen(false)}>Без сеансов</a></li>
            <li><a href="#archived" onClick={() => setIsMenuOpen(false)}>Архив</a></li>
          </ul>
        </nav>

        <button className={`menu-toggle ${isMenuOpen ? "active" : ""}`} onClick={toggleMenu}>
          <Burger />
        </button>
      </div>
    </header>
  );
};

export default Header;
