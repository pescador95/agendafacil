import { AGENDA_FACIL_AUTH } from './enums/enums'

export const clearAuth = () => {
  clearAuthToken()
}

export const setAuth = async (param) => {
  const data = param.data
  await setAuthData({
    accessToken: data.accessToken,
    refreshToken: data.refreshToken,
    organizacaoContexto: data.organizacaoDefault,
    login: data.login,
    group: data.roles,
    admin: data.admin,
  })
}

export const refreshAuth = async (param) => {
  const data = param.data
  await setAuthData({
    accessToken: data.accessToken,
    refreshToken: data.refreshToken,
    login: data.login,
    group: data.roles,
    admin: data.admin,
  })
}

export const setAuthData = async (data) => {
  const authData = data
  localStorage.setItem(AGENDA_FACIL_AUTH, JSON.stringify(authData))
}

export const setOrganizacaoContexto = (id) => {
  const authData = getAuthData()
  authData.organizacaoContexto = id
  localStorage.setItem(AGENDA_FACIL_AUTH, JSON.stringify(authData))
}

export const getAccessToken = () => {
  const authData = getAuthData()
  return authData.accessToken
}

export const getRefreshToken = () => {
  const authData = getAuthData()
  return authData.refreshToken
}

export const getAuthTokens = () => {
  const authData = getAuthData()
  return {
    accessToken: authData.accessToken,
    refreshToken: authData.refreshToken,
  }
}

export const clearAuthToken = () => {
  const authData = getAuthData()
  delete authData.accessToken
  localStorage.setItem(AGENDA_FACIL_AUTH, JSON.stringify(authData))
}

export const clearRefreshToken = () => {
  const authData = getAuthData()
  delete authData.refreshToken
  localStorage.setItem(AGENDA_FACIL_AUTH, JSON.stringify(authData))
}

export const getOrganizacaoContexto = () => {
  const authData = getAuthData()
  return authData.organizacaoContexto
}

export const clearOrganizacaoContexto = () => {
  const authData = getAuthData()
  delete authData.organizacaoContexto
  localStorage.setItem(AGENDA_FACIL_AUTH, JSON.stringify(authData))
}

export const getAuthData = () => {
  const authData = localStorage.getItem(AGENDA_FACIL_AUTH)
  return authData ? JSON.parse(authData) : {}
}

export const getTenant = () => {
  const host = window.location.host
  const subdomain = host.split('.')[0]
  return subdomain
}

export const getPathParam = (param) => {
  const queryParams = new URLSearchParams(window.location.search)
  let result = queryParams.get(param)
  return result
}

export const getUserRole = () => {
  const authData = getAuthData()
  return authData.group
}

export const isAdmin = () => {
  const authData = getAuthData()
  return authData.admin
}

export const getLogin = async () => {
  const authData = getAuthData()
  return authData.login
}
