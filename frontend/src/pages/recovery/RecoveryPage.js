import { ref } from 'vue'
import InputField from '@/components/inputField/InputField.vue'
import Button from '@/components/button/Button.vue'
import Select from '@/components/select/Select.vue'
import { recovery } from '../../utils/authService'
import { getPathParam } from '../../utils/contextHelpers'
import { MIN_LENGHT_PASS, TOKEN } from '../../utils/enums/enums'
import Toast from '@/components/toast/Toast.vue'
import verifyPasswords from '../../utils/validators'

const confirmpassword = ref('')
const password = ref('')

const handleSubmmit = async () => {
  try {
    let token = getPathParam(TOKEN)
    let response = await recovery(token, password.value)
    Toast.methods.success(response.messages[0])
  } catch (error) {
    Toast.methods.error(error)
  }
}

const verifyPass = (password, confirmpassword) => {
  return verifyPasswords(password, confirmpassword, MIN_LENGHT_PASS)
}

export default {
  components: {
    InputField,
    Button,
    Select,
  },
  setup() {
    return {
      password,
      confirmpassword,
      handleSubmmit,
      verifyPass,
    }
  },
}
