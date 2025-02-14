import LogoutButton from '@/components/logoutButton/LogoutButton.vue'
import Title from '@/components/title/Title.vue'
import Select from '@/components/select/Select.vue'
import { endpoints } from '../../utils/enums/endpoints'
import { getOrganizacaoContexto, setOrganizacaoContexto } from '../../utils/contextHelpers'
import fetchWrapper from '../../utils/fetchWrapper'

export default {
  name: 'Header',
  components: {
    Title,
    Select,
    LogoutButton,
  },
  data() {
    return {
      organizacaoOptions: [],
      filterFields: [
        {
          id: 'organizacaoId',
          type: 'select',
          value: this.organizacaoId,
          options: [],
        }
      ],
    }
  },
  props: {
    pageTitle: {
      type: String,
      required: true,
    },
  },
  mounted() {
    this.updateTitleUnderline()
    this.fetchHeader()
  },
  methods: {
    updateTitleUnderline() {
      const titleElement = this.$el.querySelector('.title h1')
      const underlineElement = this.$el.querySelector('title-underline')
      if (titleElement && underlineElement) {
        const firstWord = titleElement.innerText.split(' ')[0]
        const firstWordWidth = this.getTextWidth(firstWord, getComputedStyle(titleElement).font)
        underlineElement.style.width = `${firstWordWidth / 2}px`
      }
    },
    getTextWidth(text, font) {
      const canvas = document.createElement('canvas')
      const context = canvas.getContext('2d')
      context.font = font
      return context.measureText(text).width
    },
    async fetchOrganizacaoOptions() {

      try {

        const response = await fetchWrapper(endpoints.organizacao.base, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
          },
        })
        const data = await response.json()
        if (!response.ok) {
          throw new Error(data.messages[0])
        }
        this.organizacaoOptions = data.map((organizacao) => ({ value: organizacao.id, text: organizacao.nome }))
        this.options = this.organizacaoOptions
      } catch (error) {
        console.error('Erro ao buscar opções de organização:', error)
      }
    },

    handleOrganizacaoChange(selectedValue) {
      this.filterFields.find((field) => field.id === 'organizacaoId').value = selectedValue?.target.value
      setOrganizacaoContexto(selectedValue?.target.value)
    },
    fetchHeader() {
      this.fetchOrganizacaoOptions()
      this.$refs.organizacaoSelect.setSelect(getOrganizacaoContexto())
    },
  }
}
