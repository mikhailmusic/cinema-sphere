import "../assets/App.css";

function Header() {
    return (
      <header className="header">
        <div className="nav">
          <a href="/" className="logo">Кинотеатр</a>
          <nav>
            <ul className="nav-list">
              <li><a href="#">Расписание</a></li>
              <li><a href="#">Фильмы</a></li>
              <li><a href="#">Контакты</a></li>
            </ul>
          </nav>
        </div>
      </header>
    );
}

export default Header;
