select *
from configuradorferiado c
         left join configuradorferiadoorganizacao c2
                   on c.id = c2.configuradorferiadoid;


SELECT *
FROM configuradorferiado c
         LEFT JOIN configuradorferiadoorganizacao c2 ON c.id = c2.configuradorferiadoid
WHERE dataferiado = :dataferiado
  AND (c2.organizacaoid IS NULL OR c2.organizacaoid IN (:organizacaoId)
    );