CREATE TABLE tarefas
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    titulo       VARCHAR(55) NOT NULL,
    feito        TINYINT(0),
    data_cadastro DATETIME DEFAULT CURRENT_TIMESTAMP
);
