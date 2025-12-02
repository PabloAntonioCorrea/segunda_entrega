import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { env } from '../../environment/environment';
import { Usuario } from '../models/usuario';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly TOKEN_KEY = 'auth_token';
  private readonly USER_KEY = 'auth_user';

  constructor(
    private httpClient: HttpClient,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  login(username: string, password: string) {
    return this.httpClient.post<any>(env.apiUrl + '/auth/login', {
      username,
      password,
    });
  }

  register(username: string, password: string) {
    return this.httpClient.post<any>(env.apiUrl + '/auth/register', {
      username,
      password,
    });
  }

  logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    localStorage.removeItem(this.USER_KEY);
    this.router.navigate(['/login']);
  }

  setToken(token: string) {
    localStorage.setItem(this.TOKEN_KEY, token);
    const user = this.userFromToken(token);
    if (user) {
      localStorage.setItem(this.USER_KEY, JSON.stringify(user));
    }
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  isLogged(): boolean {
    return !!this.getToken();
  }

  getUser(): Usuario | null {
    const raw = localStorage.getItem(this.USER_KEY);
    if (!raw) return null;
    try {
      return JSON.parse(raw) as Usuario;
    } catch {
      return null;
    }
  }

  private userFromToken(token: string): Usuario | null {
    try {
      const payload = token.split('.')[1];
      if (!payload) return null;
      const decoded = JSON.parse(atob(payload));
      return {
        id: decoded.sub ?? undefined,
        email: decoded.sub ?? undefined,
        role: decoded.ROLE ?? undefined,
      } as Usuario;
    } catch (e) {
      return {
        email: 'usuario@restaurante.com',
      } as Usuario;
    }
  }

  redirect() {
    this.route.queryParams.subscribe((params) => {
      const returnUrl = params['returnUrl'] || '/home';
      this.router.navigateByUrl(returnUrl);
    });
  }
}

