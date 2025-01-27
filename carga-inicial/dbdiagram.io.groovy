Table "genero" {
  "id" int8 [pk, not null]
  "genero" varchar(255)
}

Table "statusagendamento" {
  "id" int8 [pk, not null]
  "status" varchar(255)
}

Table "tipoagendamento" {
  "id" int8 [pk, not null]
  "tipoagendamento" varchar(255)
}

Table "tipocontrato" {
  "id" int8 [pk, not null]
  "descricao" varchar(255)
  "tipocontrato" varchar(255)
}

Table "agendamento" {
  "id" int8 [pk, not null]
  "ativo" bool
  "compreferencia" bool
  "dataacao" timestamp
  "dataagendamento" date
  "horarioagendamento" time
  "nomepessoa" varchar(255)
  "nomeprofissional" varchar(255)
  "systemdatedeleted" timestamp
  "agendamentooldid" int8
  "organizacaoid" int8
  "pessoaid" int8
  "profissionalid" int8
  "statusagendamentoid" int8
  "tipoagendamentoid" int8
  "usuarioid" int8

Indexes {
  (dataagendamento, horarioagendamento, pessoaid, profissionalid, organizacaoid, statusagendamentoid, ativo) [type: btree, name: "iagendamentoak1"]
}
}

Table "atendimento" {
  "id" int8 [pk, not null]
  "atividade" varchar(255)
  "ativo" bool
  "avaliacao" varchar(255)
  "dataacao" timestamp
  "dataatendimento" timestamp
  "evolucaosintomas" varchar(255)
  "systemdatedeleted" timestamp
  "historicopessoaid" int8
  "usuarioid" int8

Indexes {
  (dataatendimento, historicopessoaid, ativo) [type: btree, name: "iatendimentoak1"]
}
}

Table "configuradoragendamento" {
  "id" int8 [pk, not null]
  "agendadomingomanha" bool
  "agendadomingonoite" bool
  "agendadomingotarde" bool
  "agendamanha" bool
  "agendanoite" bool
  "agendasabadomanha" bool
  "agendasabadonoite" bool
  "agendasabadotarde" bool
  "agendatarde" bool
  "atendedomingo" bool
  "atendesabado" bool
  "configuradororganizacao" bool
  "dataacao" timestamp
  "horaminutointervalo" time
  "horaminutotolerancia" time
  "horariofimmanha" time
  "horariofimnoite" time
  "horariofimtarde" time
  "horarioiniciomanha" time
  "horarioinicionoite" time
  "horarioiniciotarde" time
  "nome" varchar(255)
  "organizacaoid" int8
  "profissionalid" int8
  "usuarioid" int8
}

Table "configuradoragendamentoespecial" {
  "id" int8 [pk, not null]
  "dataacao" timestamp
  "datafim" date
  "datainicio" date
  "nome" varchar(255)
  "organizacaoid" int8
  "profissionalid" int8
  "usuarioid" int8

Indexes {
  (datainicio, datafim, profissionalid, organizacaoid) [type: btree, name: "iconfiguradoragendamentoespecialak1"]
}
}

Table "configuradoragendamentoespecialusuario" {
  "profissionalid" int8 [not null]
  "configuradoragendamentoespecialid" int8 [not null]
}

Table "configuradorausencia" {
  "id" int8 [pk, not null]
  "dataacao" timestamp
  "datafimausencia" date
  "datainicioausencia" date
  "horafimausencia" time
  "horainicioausencia" time
  "nomeausencia" varchar(255)
  "observacao" varchar(255)
  "usuarioid" int8

Indexes {
  (datainicioausencia, datafimausencia) [type: btree, name: "iconfiguradorausenciaak1"]
}
}

Table "configuradorausenciausuario" {
  "configuradorausenciaid" int8 [not null]
  "usuarioid" int8 [not null]
}

Table "configuradorespecialtipoagendamento" {
  "configagendamentoespecialid" int8 [not null]
  "tipoagendamentoid" int8 [not null]
}

Table "configuradorferiado" {
  "id" int8 [pk, not null]
  "dataacao" timestamp
  "dataferiado" date
  "horafimferiado" time
  "horainicioferiado" time
  "nomeferiado" varchar(255)
  "observacao" varchar(255)
  "usuarioid" int8

Indexes {
  (dataferiado, horainicioferiado, horafimferiado) [type: btree, name: "iconfiguradorferiadoak1"]
}
}

Table "configuradorferiadoorganizacao" {
  "configuradorferiadoid" int8 [not null]
  "organizacaoid" int8 [not null]
}

Table "contrato" {
  "id" int8 [pk, not null]
  "ativo" bool
  "consideracoes" varchar(255)
  "dataacao" timestamp
  "datacontrato" date
  "numeromaximosessoes" int4
  "systemdatedeleted" timestamp
  "organizacaoid" int8
  "responsavelid" int8
  "tipocontratoid" int8
  "usuario_id" int8
  "usuarioid" int8
}

Table "endereco" {
  "id" int8 [pk, not null]
  "ativo" bool
  "cep" varchar(255)
  "cidade" varchar(255)
  "complemento" varchar(255)
  "dataacao" timestamp
  "estado" varchar(255)
  "logradouro" varchar(255)
  "numero" int8
  "systemdatedeleted" timestamp
  "usuario_id" int8
  "usuarioid" int8
}

Table "historicopessoa" {
  "id" int8 [pk, not null]
  "ativo" bool
  "comorbidades" varchar(255)
  "dataacao" timestamp
  "diagnosticoclinico" varchar(255)
  "medicamentos" varchar(255)
  "nomepessoa" varchar(255)
  "ocupacao" varchar(255)
  "queixaprincipal" varchar(255)
  "responsavelcontato" varchar(255)
  "systemdatedeleted" timestamp
  "pessoaid" int8
  "usuarioid" int8

Indexes {
  (pessoaid, ativo) [type: btree, name: "ihistoricopessoaak1"]
}
}

Table "organizacao" {
  "id" int8 [pk, not null]
  "ativo" bool
  "celular" varchar(255)
  "cnpj" varchar(255)
  "dataacao" timestamp
  "email" varchar(255)
  "nome" varchar(255)
  "systemdatedeleted" timestamp
  "telefone" varchar(255)
  "enderecoid" int8
  "historicopessoa_id" int8
  "tipoagendamento_id" int8
  "usuario_id" int8
  "usuarioid" int8
}

Table "perfilacesso" {
  "id" int8 [pk, not null]
  "apagar" bool
  "atualizar" bool
  "criar" bool
  "dataacao" timestamp
  "ler" bool
  "nome" varchar(255)
  "usuarioid" int8
}

Table "pessoa" {
  "id" int8 [pk, not null]
  "ativo" bool
  "celular" varchar(255)
  "cpf" varchar(255)
  "dataacao" timestamp
  "datanascimento" date
  "email" varchar(255)
  "nome" varchar(255)
  "systemdatedeleted" timestamp
  "telefone" varchar(255)
  "enderecoid" int8
  "generoid" int8
  "usuarioid" int8

Indexes {
  (nome, telefone, celular, datanascimento, cpf, ativo) [type: btree, name: "ipessoaak1"]
}
}

Table "profile" {
  "id" int8 [pk, not null]
  "datacriado" timestamp
  "filereference" varchar(255)
  "filesize" int8
  "keyname" varchar(255)
  "mimetype" varchar(255)
  "nomecliente" varchar(255)
  "originalname" varchar(255)
  "historicopessoaid" int8
}

Table "role" {
  "id" int8 [pk, not null]
  "admin" bool
  "privilegio" varchar(255)
  "usuario_id" int8
}

Table "rotina" {
  "id" int8 [pk, not null]
  "dataacao" timestamp
  "icon" varchar(255)
  "nome" varchar(255)
  "path" varchar(255)
  "titulo" varchar(255)
  "usuarioid" int8
}

Table "rotinaperfilacesso" {
  "perfilacessoid" int8 [not null]
  "rotinaid" int8 [not null]
}

Table "tipoagendamentoorganizacoes" {
  "tipoagendamentoid" int8 [not null]
  "organizacaoid" int8 [not null]
}

Table "tipoagendamentousuarios" {
  "profissionalid" int8 [not null]
  "tipoagendamentoid" int8 [not null]
}

Table "usuario" {
  "id" int8 [pk, not null]
  "alterarsenha" bool
  "ativo" bool
  "bot" bool
  "dataacao" timestamp
  "login" varchar(255) [not null]
  "nomeprofissional" varchar(255)
  "password" varchar(255) [not null]
  "systemdatedeleted" timestamp
  "usuario" varchar(255)
  "usuarioacao" varchar(255)
  "organizacaodefaultid" int8
  "pessoaid" int8

Indexes {
  (pessoaid, login, organizacaodefaultid, ativo) [type: btree, name: "iusuarioak1"]
}
}

Table "usuarioorganizacao" {
  "usuarioid" int8 [not null]
  "organizacaoid" int8 [not null]
}

Table "usuarioroles" {
  "usuarioid" int8 [not null]
  "roleid" int8 [not null]
}

Ref "fk2g04lbruc4t7v13n6qyx5kjj4":"agendamento"."id" < "agendamento"."agendamentooldid"

Ref "fk83p0tic2bioy7sm16ec5x8x11":"usuario"."id" < "agendamento"."profissionalid"

Ref "fk8sdqm28ublsmw1cxoolwre5yn":"usuario"."id" < "agendamento"."usuarioid"

Ref "fkc1nassqr26hhh9kxnpnkpagv8":"tipoagendamento"."id" < "agendamento"."tipoagendamentoid"

Ref "fke7yy6fc5qbx8bwn39yaw4ikyt":"organizacao"."id" < "agendamento"."organizacaoid"

Ref "fkmg08hkncjvmbq6neh35aam5r8":"statusagendamento"."id" < "agendamento"."statusagendamentoid"

Ref "fko2b5cxklxbxetx1ojxacv1p6g":"pessoa"."id" < "agendamento"."pessoaid"

Ref "fkfocgx3x82gp27dm0sil7wrrhg":"historicopessoa"."id" < "atendimento"."historicopessoaid"

Ref "fkoyu7enuii4gh6493ciqhe3kx":"usuario"."id" < "atendimento"."usuarioid"

Ref "fkdkqs7218fyk8a9y42g7903l9d":"organizacao"."id" < "configuradoragendamento"."organizacaoid"

Ref "fkf5nlnm94dbbxlgbahu524j2sc":"usuario"."id" < "configuradoragendamento"."usuarioid"

Ref "fki0gt8dt02iw81h3ikbqi90yet":"usuario"."id" < "configuradoragendamento"."profissionalid"

Ref "fk9cx249kiersdl92v1n26twg6y":"organizacao"."id" < "configuradoragendamentoespecial"."organizacaoid"

Ref "fk9mdv8qt4pftk6atxxm6stk5v9":"usuario"."id" < "configuradoragendamentoespecial"."usuarioid"

Ref "fkt40cp494n5ap8kxkpg4ihjki1":"usuario"."id" < "configuradoragendamentoespecial"."profissionalid"

Ref "fk327bhl0kn3jr3m82f5tq93au7":"usuario"."id" < "configuradoragendamentoespecialusuario"."profissionalid"

Ref "fket1k1wn4c073q19ocfk6we1rd":"configuradoragendamentoespecial"."id" < "configuradoragendamentoespecialusuario"."configuradoragendamentoespecialid"

Ref "fk4f24exncj1blfi5r2blr2cyf3":"usuario"."id" < "configuradorausencia"."usuarioid"

Ref "fkho4totln732thp7rle83lonfa":"usuario"."id" < "configuradorausenciausuario"."usuarioid"

Ref "fkicvph8o40ecv827ayyq7o0ybt":"configuradorausencia"."id" < "configuradorausenciausuario"."configuradorausenciaid"

Ref "fk9ph6b7ric1baw3evahgb6ofh9":"configuradoragendamentoespecial"."id" < "configuradorespecialtipoagendamento"."configagendamentoespecialid"

Ref "fkk8afec11q5r2exljx71vy9jsh":"tipoagendamento"."id" < "configuradorespecialtipoagendamento"."tipoagendamentoid"

Ref "fk7f60tqa3cct64w0va4411whdw":"usuario"."id" < "configuradorferiado"."usuarioid"

Ref "fkkf0hf3akr6sqxwhaq8ihqglo":"organizacao"."id" < "configuradorferiadoorganizacao"."organizacaoid"

Ref "fkswyrx81t8veixn4ti7dtihixc":"configuradorferiado"."id" < "configuradorferiadoorganizacao"."configuradorferiadoid"

Ref "fk1vxrtsoahfn7dpq6i0o2sooh4":"usuario"."id" < "contrato"."responsavelid"

Ref "fk56mc6pc3kjogep3axv0ixihxg":"usuario"."id" < "contrato"."usuarioid"

Ref "fk5dgs6aqyilhj4ob3bs43feypx":"organizacao"."id" < "contrato"."organizacaoid"

Ref "fkjk83wy5pq0a7hufodligiop2k":"usuario"."id" < "contrato"."usuario_id"

Ref "fkr9jav3b0ufqxvpx7uoebytedr":"tipocontrato"."id" < "contrato"."tipocontratoid"

Ref "fk6692c20phcmsx61paypl4vt9e":"usuario"."id" < "endereco"."usuarioid"

Ref "fkekdpb8k6gmp3lllla9d1qgmxk":"usuario"."id" < "endereco"."usuario_id"

Ref "fkixvpkabc81pmpxdhhg52cn6qn":"usuario"."id" < "historicopessoa"."usuarioid"

Ref "fkk5jmvo1ewmcaxemjk200nhq1j":"pessoa"."id" < "historicopessoa"."pessoaid"

Ref "fk79ao8fruxwr9cwdlwgtf9724n":"endereco"."id" < "organizacao"."enderecoid"

Ref "fk8sp6c91i3crpl1uytvk42as1s":"historicopessoa"."id" < "organizacao"."historicopessoa_id"

Ref "fkkjppbr7situ7sx52e2wgwareg":"usuario"."id" < "organizacao"."usuario_id"

Ref "fkrfgisc36gs2kemyuwtia69cub":"tipoagendamento"."id" < "organizacao"."tipoagendamento_id"

Ref "fkrqwcrq8s5ko2lc72qrwem3emy":"usuario"."id" < "organizacao"."usuarioid"

Ref "fkkivgafighsla43pcru8bhc0yh":"usuario"."id" < "perfilacesso"."usuarioid"

Ref "fkbie98d5etqcxskb2xm8jkc61q":"usuario"."id" < "pessoa"."usuarioid"

Ref "fkclo3uujbgx4bgduiqmdbmrs2n":"endereco"."id" < "pessoa"."enderecoid"

Ref "fkgdmrbple6d3mi1xld3f434rbd":"genero"."id" < "pessoa"."generoid"

Ref "fkks6eko1lmilkstyuyvxtnh39m":"historicopessoa"."id" < "profile"."historicopessoaid"

Ref "fklhiugv8utqmj6jpd5hwrf1jkr":"usuario"."id" < "role"."usuario_id"

Ref "fk58heyq4lmsll4dt4j77j4taho":"usuario"."id" < "rotina"."usuarioid"

Ref "fk38c297cj9k63yskai25joojw8":"perfilacesso"."id" < "rotinaperfilacesso"."perfilacessoid"

Ref "fkbiyfkt9vx0dee410gif51bh6x":"rotina"."id" < "rotinaperfilacesso"."rotinaid"

Ref "fk4cajrdaqpdunvyt67fmneifve":"tipoagendamento"."id" < "tipoagendamentoorganizacoes"."tipoagendamentoid"

Ref "fko256ufs0dx3tuwkf8aamtgxip":"organizacao"."id" < "tipoagendamentoorganizacoes"."organizacaoid"

Ref "fk9vk62tfpk3edru3d4y8mrc1kc":"usuario"."id" < "tipoagendamentousuarios"."profissionalid"

Ref "fkg3yjrmyyo4q7a3rfsoii28dhq":"tipoagendamento"."id" < "tipoagendamentousuarios"."tipoagendamentoid"

Ref "fk290ceuw920x342vew1xqyhq4d":"pessoa"."id" < "usuario"."pessoaid"

Ref "fk49wo0nex16shj2jyn7j18swef":"organizacao"."id" < "usuario"."organizacaodefaultid"

Ref "fkdfvtxsk957x0163y7a4fcqdl3":"organizacao"."id" < "usuarioorganizacao"."organizacaoid"

Ref "fkhf1dkvuh3a8plauraps5t8lxu":"usuario"."id" < "usuarioorganizacao"."usuarioid"

Ref "fkbb2234orjjwlfy5xbupv95yns":"role"."id" < "usuarioroles"."roleid"

Ref "fkcjrjtwcottdmse4wu618kff27":"usuario"."id" < "usuarioroles"."usuarioid"
