import InputField from '@/components/inputField/InputField'
import Select from '@/components/select/Select.vue'

export default {
  name: 'Filter',
  components: {
    Select,
    InputField,
  },
  props: {
    onSearch: {
      type: Function,
      required: true,
    },
    onReset: {
      type: Function,
      required: true,
    },
  },
  emits: ['search', 'reset'],
  methods: {
    handleSearch() {
      this.onSearch()
    },
    handleReset() {
      this.onReset()
    },
  },
}
