USE itstornei;

INSERT INTO utente
(nome, cognome, email, password_hash, ruolo, data_registrazione)
VALUES
('Alice', 'Admin', 'alice.admin@esempio.com', SHA2('Admin1', 256), 'ADMIN', '2025-01-10 09:00:00'),
('Marco', 'Admin', 'marco.admin@esempio.com', SHA2('Admin2', 256), 'ADMIN', '2025-01-11 09:00:00'),
('Luca', 'Rossi', 'luca.rossi@esempio.com', SHA2('Utente1', 256), 'UTENTE', '2025-02-01 10:00:00'),
('Giulia', 'Bianchi', 'giulia.bianchi@esempio.com', SHA2('Utente2', 256), 'UTENTE', '2025-02-02 10:00:00'),
('Andrea', 'Verdi', 'andrea.verdi@esempio.com', SHA2('Utente3', 256), 'UTENTE', '2025-02-03 10:00:00'),
('Sara', 'Neri', 'sara.neri@esempio.com', SHA2('Utente4', 256), 'UTENTE', '2025-02-04 10:00:00'),
('Andrea', 'Gallo', 'andrea.gallo@esempio.com', SHA2('Utente5', 256), 'UTENTE', '2025-02-05 10:00:00'),
('Elena', 'Fontana', 'elena.fontana@esempio.com', SHA2('Utente6', 256), 'UTENTE', '2025-02-06 10:00:00'),
('Davide', 'Conti', 'davide.conti@esempio.com', SHA2('Utente7', 256), 'UTENTE', '2025-02-07 10:00:00'),
('Chiara', 'Ricci', 'chiara.ricci@esempio.com', SHA2('Utente8', 256), 'UTENTE', '2025-02-08 10:00:00'),
('Simone', 'Marino', 'simone.marino@esempio.com', SHA2('Utente9', 256), 'UTENTE', '2025-02-09 10:00:00'),
('Francesca', 'Greco', 'francesca.greco@esempio.com', SHA2('Utente10', 256), 'UTENTE', '2025-02-10 10:00:00'),
('Alessandro', 'Bruno', 'alessandro.bruno@esempio.com', SHA2('Utente11', 256), 'UTENTE', '2025-02-11 10:00:00'),
('Martina', 'Moretti', 'martina.moretti@esempio.com', SHA2('Utente12', 256), 'UTENTE', '2025-02-12 10:00:00'),
('Federico', 'Lombardi', 'federico.lombardi@esempio.com', SHA2('Utente13', 256), 'UTENTE', '2025-02-13 10:00:00'),
('Valentina', 'Barbieri', 'valentina.barbieri@esempio.com', SHA2('Utente14', 256), 'UTENTE', '2025-02-14 10:00:00'),
('Nicola', 'Pellegrini', 'nicola.pellegrini@esempio.com', SHA2('Utente15', 256), 'UTENTE', '2025-02-15 10:00:00'),
('Irene', 'Rinaldi', 'irene.rinaldi@esempio.com', SHA2('Utente16', 256), 'UTENTE', '2025-02-16 10:00:00'),
('Stefano', 'Caruso', 'stefano.caruso@esempio.com', SHA2('Utente17', 256), 'UTENTE', '2025-02-17 10:00:00'),
('Beatrice', 'Ferrari', 'beatrice.ferrari@esempio.com', SHA2('Utente18', 256), 'UTENTE', '2025-02-18 10:00:00'),
('Pietro', 'Costa', 'pietro.costa@esempio.com', SHA2('Utente19', 256), 'UTENTE', '2025-02-19 10:00:00'),
('Noemi', 'Giordano', 'noemi.giordano@esempio.com', SHA2('Utente20', 256), 'UTENTE', '2025-02-20 10:00:00');

INSERT INTO torneo
(nome, descrizione, data_inizio_iscrizioni, data_fine_iscrizioni, data_inizio_torneo, data_fine_torneo, cancellato)
VALUES
('Scacchi', 'Torneo individuale di scacchi.', '2026-10-01', '2026-10-20', '2026-10-25', '2026-10-25', 0),
('Tennis', 'Torneo individuale di tennis.', '2026-09-01', '2026-09-20', '2026-09-25', '2026-10-15', 1),
('Rainbow Six Siege', 'Torneo a squadre di Rainbow Six Siege.', '2026-10-15', '2026-11-01', '2026-11-10', '2026-11-12', 0),
('Calcetto', 'Torneo di calcetto a squadre.', '2026-08-01', '2026-08-20', '2026-08-25', '2026-09-30', 1),
('Valorant', 'Torneo a squadre di Valorant', '2026-10-01', '2026-10-18', '2026-10-22', '2026-10-23', 0),
('Ping Pong', 'Torneo individuale di ping pong.', '2026-10-10', '2026-10-30', '2026-11-05', '2026-11-05', 0);

INSERT INTO torneo_individuale 
(id_torneo, max_partecipanti)
VALUES
(1, 32),
(2, 16),
(6, 24);

INSERT INTO torneo_squadra 
(id_torneo, max_squadre, componenti)
VALUES
(3, 8, 5),
(4, 12, 5),
(5, 8, 3);

INSERT INTO iscrizione 
(id_utente, id_torneo, data_iscrizione)
VALUES
(3, 1, '2026-10-02 10:00:00'),
(4, 1, '2026-10-03 11:00:00'),
(5, 1, '2026-10-04 12:00:00'),
(6, 2, '2026-09-05 10:00:00'),
(7, 2, '2026-09-06 10:00:00'),
(8, 6, '2026-10-11 10:00:00'),
(9, 6, '2026-10-12 10:00:00');

INSERT INTO squadra (id_torneo, nome, id_creatore, data_creazione)
VALUES
(3, 'I Lupi', 10, '2026-10-16 10:00:00'),
(3, 'Team Beta', 11, '2026-10-16 11:00:00'),
(3, 'I Fulmini', 12, '2026-10-15 13:30:00'),
(4, 'Team Beta', 11, '2026-10-16 11:00:00'),
(4, 'I Fulmini', 12, '2026-08-02 10:00:00'),
(5, 'Triple Threat', 13, '2026-10-02 10:00:00'),
(5, 'Team Beta', 11, '2026-10-16 11:00:00');

INSERT INTO componente_squadra
(id_squadra, id_utente, data_inserimento)
VALUES
(1, 10, '2026-10-16 10:00:00'),
(1, 14, '2026-10-16 10:05:00'),
(1, 15, '2026-10-16 10:06:00'),
(1, 16, '2026-10-16 10:07:00'),
(1, 17, '2026-10-16 10:08:00'),
(2, 11, '2026-10-16 11:00:00'),
(2, 18, '2026-10-16 11:05:00'),
(2, 19, '2026-10-16 11:06:00'),
(2, 20, '2026-10-16 11:07:00'),
(2, 21, '2026-10-16 11:08:00'),
(3, 12, '2026-08-02 10:00:00'),
(3, 3, '2026-08-02 10:05:00'),
(3, 4, '2026-08-02 10:06:00'),
(3, 5, '2026-08-02 10:07:00'),
(3, 6, '2026-08-02 10:08:00'),
(4, 13, '2026-10-02 10:00:00'),
(4, 7, '2026-10-02 10:05:00'),
(4, 8, '2026-10-02 10:06:00'),
(4, 9,  '2026-08-02 10:05:00'),
(4, 12,  '2026-08-02 10:06:00'),
(5, 9,  '2026-08-03 10:00:00'),
(5, 22, '2026-08-03 10:05:00'),
(5, 14, '2026-08-03 10:06:00'),
(5, 15, '2026-08-03 10:07:00'),
(5, 16, '2026-08-03 10:08:00'),
(6, 13, '2026-10-02 10:00:00'),
(6, 7,  '2026-10-02 10:05:00'),
(6, 8,  '2026-10-02 10:06:00'),
(7, 11, '2026-10-16 11:00:00'),
(7, 18, '2026-10-16 11:05:00'),
(7, 19, '2026-10-16 11:06:00');

INSERT INTO impostazione 
(valore) 
VALUES 
(3);