const API = "http://localhost:8080/api";

let id = new URLSearchParams(window.location.search).get("id");
let token = localStorage.getItem("token");

async function carica() {
  let risposta = await fetch(API + "/tornei/" + id);
  let torneo = await risposta.json();

  document.getElementById("dettaglio").innerHTML =
    "<h1>" + torneo.nome + "</h1>"
    + "<p>" + torneo.descrizione + "</p>"
    + "<p>Stato: " + torneo.stato + "</p>";

  let risposta2 = await fetch(API + "/profilo/tornei", {
    headers: { "Authorization": "Bearer " + token }
  });
  let mieiTornei = await risposta2.json();

  let attivi = 0;
  for (let i = 0; i < mieiTornei.length; i++) {
    if (mieiTornei[i].stato == "APERTO" || mieiTornei[i].stato == "IN_CORSO") {
      attivi++;
    }
  }

  if (attivi >= 3) {
    document.getElementById("box-iscrizione").innerHTML =
      "<p style='color:red'>Non puoi iscriverti a più di 3 tornei</p>";
  } else {
    document.getElementById("box-iscrizione").innerHTML =
      "<button onclick='iscriviti()'>Iscriviti</button>";
  }
}

async function iscriviti() {
  let risposta = await fetch(API + "/tornei/" + id + "/iscrizione", {
    method: "POST",
    headers: { "Authorization": "Bearer " + token }
  });

  if (risposta.ok) {
    alert("Iscrizione fatta!");
  } else {
    alert("Errore nell'iscrizione");
  }
}

carica();