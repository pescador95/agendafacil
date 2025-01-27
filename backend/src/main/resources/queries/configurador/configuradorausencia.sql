---- para retornar 1 usuario ----
SELECT *
FROM configuradorausencia c
         LEFT JOIN configuradorausenciausuario c2
                   ON c2.configuradorausenciaid = c.id
WHERE c2.usuarioid in (:usuarioid)
  AND COALESCE(c.datainicioausencia <= :datafimausencia, true)
  AND COALESCE(c.datafimausencia >= :datainicioausencia, true);


---- para retornar lista ----

SELECT *
FROM configuradorausencia c
         LEFT JOIN configuradorausenciausuario c2
                   ON c2.configuradorausenciaid = c.id
WHERE COALESCE(c.datainicioausencia <= :datafimausencia, true)
  AND COALESCE(c.datafimausencia >= :datainicioausencia, true);
  