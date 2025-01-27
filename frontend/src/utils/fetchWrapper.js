import { refreshToken } from './authService'
import { getAccessToken, getRefreshToken, getTenant } from './contextHelpers'

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL

const fetchWrapper = async (endpoint, options = {}) => {
  const url = new URL(`${API_BASE_URL}${endpoint}`)

  const subdomain = getTenant()

  Object.keys(options.queryParams || {}).forEach((key) =>
    url.searchParams.append(key, options.queryParams[key]),
  )

  const headers = new Headers(options.headers || {})

  if (subdomain) {
    headers.append('X-Tenant', subdomain)
  }

  let accessToken = getAccessToken()

  let newRefreshToken = getRefreshToken()

  if (accessToken) {
    headers.append('Authorization', `Bearer ${accessToken}`)
  }

  let response = await fetch(url, {
    ...options,
    headers,
  })

  if (response.status === 401 && newRefreshToken) {
    try {
      await refreshToken(newRefreshToken)

      response = await fetchWithUpdatedToken(url, options)
    } catch (error) {}
  }

  return response
}

const fetchWithUpdatedToken = async (url, options) => {
  const headers = new Headers(options.headers || {})

  const { accessToken } = getAuthToken()

  if (accessToken) {
    headers.set('Authorization', `Bearer ${accessToken}`)
  }
  return fetch(url, { ...options, headers })
}

export default fetchWrapper
