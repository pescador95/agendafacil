import { ref } from 'vue'
import InputField from '@/components/inputField/InputField.vue'
import Button from '@/components/button/Button.vue'
import Select from '@/components/select/Select.vue'
import { forgot } from '../../utils/authService'
import Toast from '@/components/toast/Toast.vue'

const email = ref('')

const handleSubmmit = async () => {
  try {
    let response = await forgot(email.value)
    Toast.methods.success(response.messages[0])
  } catch (error) {
    Toast.methods.error(error)
  }
}
export default {
  components: {
    InputField,
    Button,
    Select,
  },
  setup() {
    return {
      email,
      handleSubmmit,
    }
  },
}
