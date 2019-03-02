BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS 'support_table' (
	'support_id'	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	'support_title'	TEXT,
	'support_text'	TEXT,
	'support_image'	TEXT
);

CREATE TABLE IF NOT EXISTS 'support_anatomy' (
	'anatomy_id'	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	'anatomy_title'	TEXT,
	'anatomy_subtitle'	TEXT,
	'anatomy_text'	TEXT
);
COMMIT;