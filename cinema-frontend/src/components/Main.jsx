import "../assets/App.css";
import bgImage from "../assets/hall.jpg";

function Main() {
  return (
    <main>
      <section className="banner">
          <img src={bgImage} alt="background" className="banner-image" />
          <div className="overlay">
            <h1>Кинотеатр</h1>
            <p>Добро пожаловать в наш кинотеатр, где мы с гордостью демонстрируем лучшие фильмы. Приглашаем вас ознакомиться с нашей афишей.</p>
          </div>
      </section>
      
      <section>
        <h3></h3>
      </section>
      
      <section className="rental">
        <h3>Сейчас в прокате</h3>
      </section>
      <section>
        <h3>Скоро в кино</h3>
      </section>
      <section>
        <h3>Архив фильмов</h3>
      </section>
    </main>
  );
}

export default Main;
