import { ref } from 'vue';
import Button from '@/components/button/Button.vue';
import InputField from '@/components/inputField/InputField.vue';
import Select from '@/components/select/Select.vue';
import Title from '@/components/title/Title.vue';

export default {
  name: 'Modal',
  components: {
    Button,
    InputField,
    Select,
    Title
  },
  props: {
    show: {
      type: Boolean,
      required: true
    }
  },
}
