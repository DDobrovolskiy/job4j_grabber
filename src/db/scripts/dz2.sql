SELECT c.name, COUNT(p.name) AS count FROM company AS c
INNER JOIN person AS p ON c.id = p.company_id
GROUP BY c.name
ORDER BY count DESC
LIMIT 1