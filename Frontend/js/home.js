const API = "http://localhost:8080/api";

async function caricaTornei() {
  let risposta = await fetch(API + "/tornei");
  let tornei = await risposta.json();

  let lista = document.getElementById("lista-tornei");

  for (let i = 0; i < tornei.length; i++) {
    lista.innerHTML += "<div class='card'>"
      + "<h3>" + tornei[i].nome + "</h3>"
      + "<p>Stato: " + tornei[i].stato + "</p>"
      + "<p>Tipo: " + tornei[i].modalita + "</p>"
      + "<p>Iscrizioni fino al: " + tornei[i].dataFineIscrizioni + "</p>"
      + "<a href='torneo.html?id=" + tornei[i].id + "'>Vedi dettagli</a>"
      + "</div>";
  }
}

caricaTornei();