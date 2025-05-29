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
            <li><a href="#">Расписание</a></li>
            <li><a href="#">Фильмы</a></li>
            <li><a href="#">Контакты</a></li>
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
