<template>
  <div class="agendamento-page">
    <Sidebar />
    <div class="main-content">
      <Header :pageTitle="pageTitle" />
      <div class="content">

        <div class="modal-item">

          <InputField id="nomePessoa" v-model="nomePessoa" placeholder="Nome do Cliente" type="text" />

          <InputField id=" dataAgendamento" v-model="dataAgendamento" placeholder="Data da Consulta" type="date" />

          <Select id="idStatus" ref="statusSelect" :options="statusOptions" placeholder="Status"
            @change="handleStatusChange" />

          <Select id="tipoAgendamentoId" ref="tipoAgendamentoSelect" :options="tipoAgendamentoOptions"
            placeholder="Tipo de Agendamento" @change="handleTipoAgendamentoChange" />

        </div>
      </div>
      <Filter @reset="reset" @search="search" />

      <Table :columns="columns" :data="formattedAppointments" />


      <div class="action">
        <div class="button-container">

          <Button @click="openModal">CADASTRAR</Button>
        </div>
      </div>
    </div>

    <Modal :show="isModalVisible" @close="closeModal">

      <template v-slot:header>
        <Title :pageTitle="pageTitle"></Title>
      </template>

      <template v-slot:body>

        <div class="modal-item">
          <Select id="modalTipoAgendamento" :options="tipoAgendamentoOptions" placeholder="Tipo de Agendamento"
            class="input-modal-field" />

          <Select id="modalCliente" :options="clienteOptions" placeholder="Cliente" class="select-modal-field" />



          <InputField id="modalData" v-model="modalData" placeholder="Data da Consulta" type="date"
            class="input-modal-field" />

          <InputField id="modalHorario" v-model="modalHorario" placeholder="Horário" type="time"
            class="input-modal-field" />



          <Select id="modalOrganizacao" :options="organizacaoOptions" placeholder="Organização"
            class="select-modal-field" />
          <Select id="modalProfissional" :options="profissionalOptions" placeholder="Profissional"
            class="select-modal-field" />

          <div class="item">
            <Select id="modalStatus" :options="statusOptions" placeholder="Status" class="input-full-width" />
          </div>
        </div>
      </template>

      <template v-slot:footer>
        <div class="modal-footer">
          <Button @click="closeModal" class="btn--secondary">Cancelar</Button>
          <Button @click="handleCadastrar">Salvar</Button>
        </div>
      </template>

    </Modal>
  </div>

</template>

<script src="./AgendamentoPage.js"></script>
<style src="./AgendamentoPage.css"></style>
