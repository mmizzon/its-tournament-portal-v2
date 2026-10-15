crea un nuovo file .env NON DEVE ESSERE .env.txt
copia tutto da .env.example e incolla in .env creato
lascia la prima riga e modifica le password delle altre righe
per l'ultima password usa su powershell il codice e copia la stringa prima di $rng.Dispose()



$rng = [System.Security.Cryptography.RandomNumberGenerator]::Create()
$bytes = New-Object byte[] 32
$rng.GetBytes($bytes)
[Convert]::ToBase64String($bytes)
$rng.Dispose()