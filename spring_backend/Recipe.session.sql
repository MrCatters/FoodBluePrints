SELECT case WHEN count(t)> 0
THEN true ELSE false END
FROM recipes t
WHERE t.id = '2'
AND t.user_id = '2'