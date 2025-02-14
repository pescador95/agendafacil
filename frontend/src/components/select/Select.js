import { ref } from 'vue'

export default {
  props: {
    id: { type: String, required: true },
    label: { type: String, required: false },
    options: { type: Array, required: true },
    placeholder: { type: String, default: 'Selecione uma opção' },
    modelValue: { type: String, default: '' },
  },
  emits: ['update:modelValue'],
  setup(props, { emit }) {
    const selectedValue = ref(props.modelValue)

    const emitChange = () => {
      emit('update:modelValue', selectedValue.value)
    }

    const emitClear = () => {
      selectedValue.value = ''
      emit('update:modelValue', selectedValue.value)
    }

    const resetSelect = () => {
      selectedValue.value = ''
    }

    const setSelect = (selected) => {
      selectedValue.value = selected
    }



    return {
      selectedValue,
      emitChange,
      emitClear,
      resetSelect,
      setSelect,
      props,
    }
  },
}
