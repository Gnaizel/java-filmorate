### Диаграмма базы данных

[https://github.com/Gnaizel/Filmorate/blob/12-sprint-data-base/src/Diogram.png](https://github.com/Gnaizel/Filmorate/blob/12-sprint-data-base/src/Diogram.png)

### Примеры запросов

• Пример 1: Получение всех фильмов
SELECT * FROM films;
• Пример 2: Получение всех пользователей
SELECT * FROM users;
• Пример 3: Получение топ N самых популярных фильмов
SELECT f.name, COUNT(l.user_di) AS like_count
FROM films f
JOIN like l ON f.id = l.film_di
GROUP BY f.name
ORDER BY like_count DESC
LIMIT N;

• Пример 4: Получение списка общих друзей с другим пользователем

SELECT u.name
FROM users u
JOIN friends f1 ON u.id = f1.user_id_2
JOIN friends f2 ON u.id = f2.user_id_2
WHERE f1.user_id_1 = {user_id_1} 
AND f2.user_id_1 = {user_id_2}
GROUP BY u.name;
