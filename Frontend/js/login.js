const API = "http://localhost:8080/api";

async function login() {
  let email = document.getElementById("email").value;
  let password = document.getElementById("password").value;

  let risposta = await fetch(API + "/auth/login", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ email: email, password: password })
  });

  if (risposta.ok) {
    let dati = await risposta.json();
    localStorage.setItem("token", dati.token);
    window.location.href = "index.html";
  } else {
    alert("Email o password sbagliati");
  }
}