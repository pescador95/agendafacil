INSERT INTO
    config.TIPOCONTRATO (
        ID,
        TIPOCONTRATO,
        DESCRICAO
    )
VALUES
    (
        1,
        'Sessão única',
        'Permite com que você utilize apenas 1 sessão por Usuário, derrubando a sessão anterior.'
    );

INSERT INTO
    config.TIPOCONTRATO (
        ID,
        TIPOCONTRATO,
        DESCRICAO
    )
VALUES
    (
        2,
        'Sessão Compartilhada',
        'Permite que você utilize o mesmo Usuário para várias sessões simultâneas, porém a cada sessão ativa será contabilizada.'
    );

INSERT INTO
    config.applicationconfig (
        id,
        agendafacilurl,
        emailpassword,
        emailuser,
        quarkusbaseurl,
        quarkusport,
        quarkusurl,
        redisbaseurl,
        scheduleenabled,
        telegrambaseurl,
        whatsappbaseurl,
        profile,
        tracemethods
    )
VALUES
    (
        nextval('config.applicationconfig_id_seq' :: regclass),
        'https://agendafacil.app/',
        'tsysepulluubzkkm',
        'desafiozoo2022@gmail.com',
        'http://localhost:3000',
        3000,
        'localhost',
        'redis://redis-service:6379',
        true,
        'http://telegram-service:5000',
        'http://whatsapp-service:4000',
        'dev',
        false
    );

INSERT INTO
    config.applicationconfig (
        id,
        agendafacilurl,
        emailpassword,
        emailuser,
        quarkusbaseurl,
        quarkusport,
        quarkusurl,
        redisbaseurl,
        scheduleenabled,
        telegrambaseurl,
        whatsappbaseurl,
        profile,
        tracemethods
    )
VALUES
    (
        nextval('config.applicationconfig_id_seq' :: regclass),
        'https://agendafacil.app/',
        'tsysepulluubzkkm',
        'desafiozoo2022@gmail.com',
        'http://agendafacil-backend:3000',
        3000,
        'agendafacil-backend',
        'redis://redis-service:6379',
        true,
        'http://telegram-service:5000',
        'http://whatsapp-service:4000',
        'prod',
        false
    );