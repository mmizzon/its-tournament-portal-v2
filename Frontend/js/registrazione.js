const API = "http://localhost:8080/api";

async function registrati() {
  let nome = document.getElementById("nome").value;
  let cognome = document.getElementById("cognome").value;
  let email = document.getElementById("email").value;
  let password = document.getElementById("password").value;

  if (password.length < 8) {
    alert("La password deve avere almeno 8 caratteri");
    return;
  }

  let risposta = await fetch(API + "/auth/registrazione", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ nome: nome, cognome: cognome, email: email, password: password })
  });

  if (risposta.ok) {
    alert("Registrazione fatta! Ora puoi accedere");
    window.location.href = "login.html";
  } else {
    alert("Errore nella registrazione, controlla i dati");
  }
}