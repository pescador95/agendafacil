import { ref } from 'vue'
import InputField from '@/components/inputField/InputField.vue'
import Button from '@/components/button/Button.vue'
import Select from '@/components/select/Select.vue'
import { login } from '@/utils/authService'
import Toast from '@/components/toast/Toast.vue'
import { MIN_LENGHT_PASS, MIN_LENGHT_USERNAME } from '../../utils/enums/enums'
import router from '@/router'

const user = ref('')
const password = ref('')

const handleLogin = async () => {
  try {
    let response = await login(user.value, password.value)
    Toast.methods.success(response.messages[0])
    router.push('/agendamento')
  } catch (error) {
    Toast.methods.error(error)
  }
}

function passwordVisibility() {
  const passwordInput = document.getElementById('password')
  if (passwordInput) {
    passwordInput.type = passwordInput.type === 'password' ? 'text' : 'password'
  }
}

export const verifyForm = (username, password) => {
  return (
    !username ||
    username.length < MIN_LENGHT_USERNAME ||
    !password ||
    password.length < MIN_LENGHT_PASS
  )
}

export default {
  components: {
    InputField,
    Button,
    Select,
  },
  setup() {
    return {
      user,
      password,
      handleLogin,
      passwordVisibility,
      verifyForm,
    }
  },
}
