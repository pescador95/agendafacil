export default {
  props: {
    id: { type: String, required: true },
    label: { type: String, required: false },
    modelValue: { type: String, default: '' },
  },
  emits: ['update:modelValue'],
  setup(props, { emit }) {
    function updateValue(newValue) {
      emit('update:modelValue', newValue)
    }

    return {
      props,
      updateValue,
    }
  },
}
