import { logout } from '../../utils/authService'
import { getLogin } from '../../utils/contextHelpers'
import Toast from '@/components/toast/Toast.vue'
import router from '@/router'

export default {
  name: 'LogoutButton',
  data() {
    return {
      userLogin: '',
    }
  },
  methods: {
    async handleLogout() {
      try {
        let response = await logout()
        Toast.methods.success(response.messages[0])
        router.push('/auth')
      } catch (error) {
        Toast.methods.error(error)
      }
    },
  },
  async created() {
    this.userLogin = await getLogin()
  },
}
