const verifyPasswords = (password, confirmpassword, minLength) => {
  return (
    verifyMinLengthPass(password, minLength) ||
    verifyMinLengthPass(confirmpassword, minLength) ||
    password.localeCompare(confirmpassword)
  )
}

const verifyMinLengthPass = (password, minLength) => {
  return !password || password.length < minLength
}

export default verifyPasswords

export { verifyMinLengthPass }
