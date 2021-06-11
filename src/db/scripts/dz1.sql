SELECT p.name, c.name, c.id AS id_company FROM person AS p
INNER JOIN company AS c ON c.id = p.company_id
WHERE c.id != 5