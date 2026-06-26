import client from './client'
import type { LoginRequest, RegisterRequest, AuthResponse } from '../types'

export function login(data: LoginRequest) {
  return client.post<AuthResponse>('/auth/login', data)
}

export function register(data: RegisterRequest) {
  return client.post<AuthResponse>('/auth/register', data)
}

export function logout(email: string) {
  return client.post<void>('/auth/logout', { email })
}
