export default {
  props: {
    type: { type: String, default: 'primary' },
    disabled: { type: Boolean, default: false },
  },
  emits: ['click'],
  setup(props, { emit }) {
    function handleClick(event) {
      if (!props.disabled) emit('click', event)
    }

    return {
      handleClick,
    }
  },
}
