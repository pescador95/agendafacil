-- contrato
INSERT INTO
    config.contrato (
        id,
        ativo,
        datacontrato,
        dataterminocontrato,
        numeromaximosessoes,
        tipocontratoid,
        tenant
    )
SELECT
    nextval('config.contrato_id_seq' :: regclass),
    true,
    '2024-04-24',
    '2025-08-24',
    10,
    2,
    'blackbelt'
WHERE
    NOT EXISTS (
        SELECT
            1
        FROM
            config.contrato
        WHERE
            tenant = 'blackbelt'
    );

INSERT INTO
    config.tenant (id, tenant, contratoid)
SELECT
    nextval('config.tenant_id_seq' :: regclass),
    'blackbelt',
    (
        select
            id
        from
            config.contrato
        where
            tenant = 'blackbelt'
    )
WHERE
    NOT EXISTS (
        SELECT
            1
        FROM
            config.tenant
        WHERE
            tenant = 'blackbelt'
    );