CREATE TABLE IF NOT EXISTS Events
(
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id INTEGER NOT NULL,
    file_id INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users (id),
    foreign key (file_id) references Files (id)
    );