import fetchWrapper from './fetchWrapper'
import { clearAuth, clearAuthToken, refreshAuth, setAuth } from './contextHelpers'
import { endpoints } from './enums/endpoints'

export const login = async (username, password) => {
  const response = await fetchWrapper(endpoints.login.auth, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ login: username, password }),
  })

  const data = await response.json()

  if (!response.ok) {
    throw new Error(data.messages[0])
  }

  setAuth(data)

  return data
}

export const refreshToken = async (refreshToken) => {
  clearAuthToken()

  const response = await fetchWrapper(endpoints.login.refresh, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ refreshToken }),
  })

  const data = await response.json()

  if (!response.ok) {
    throw new Error(data.messages[0])
  }

  refreshAuth(data)
  return data
}

export const logout = async () => {
  const response = await fetchWrapper(endpoints.login.logout, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
  })

  const data = await response.json()

  if (!response.ok) {
    throw new Error(data.messages[0])
  }
  clearAuth()
  return data
}

export const forgot = async (user) => {
  const response = await fetchWrapper(endpoints.recoveryPassword.recoveryPassword(user), {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
  })

  const data = await response.json()

  if (!response.ok) {
    throw new Error(data.messages[0])
  }

  return data
}

export const recovery = async (token, password) => {
  const response = await fetchWrapper(endpoints.recoveryPassword.base, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ token, password }),
  })

  const data = await response.json()

  if (!response.ok) {
    throw new Error(data.messages[0])
  }

  clearAuth()
  return data
}
