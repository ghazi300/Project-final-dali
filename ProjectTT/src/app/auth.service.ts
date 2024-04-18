import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { LoginDto, SignUpDto } from './auth.dto';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8089/pi/api/auth';
  private readonly USER_ROLES_KEY = 'userRoles';

  constructor(private http: HttpClient) { }

  login(loginDto: LoginDto): Observable<any> {
    console.log('Sending login request:', loginDto);
    return this.http.post<any>(`${this.baseUrl}/signin`, loginDto)
      .pipe(
        tap(response => {
          console.log('Login response:', response);
          this.setUserRoles(response.roles);
        }),
        catchError(this.handleError)
      );
  }

  signup(signUpDto: SignUpDto): Observable<any> {
    console.log('Sending signup request:', signUpDto);
    return this.http.post<any>(`${this.baseUrl}/signup`, signUpDto)
      .pipe(
        tap(response => console.log('Signup response:', response)),
        catchError(this.handleError)
      );
  }
  forgotPassword(email: string) {
    return this.http.post(`${this.baseUrl}/forgot-password`, { email });
  }
  private setUserRoles(roles: string[]) {
    localStorage.setItem(this.USER_ROLES_KEY, JSON.stringify(roles));
    sessionStorage.setItem(this.USER_ROLES_KEY, JSON.stringify(roles));
  }

  getUserRoles(): string[] {
    let rolesString = localStorage.getItem(this.USER_ROLES_KEY);
    if (!rolesString) {
      rolesString = sessionStorage.getItem(this.USER_ROLES_KEY);
    }
    return rolesString ? JSON.parse(rolesString) : [];
  }

  isAdmin(): boolean {
    const roles = this.getUserRoles();
    return roles.includes('ROLE_ADMIN');
  }

  isUser(): boolean {
    const roles = this.getUserRoles();
    return roles.includes('ROLE_USER');
  }

  isLoggedIn(): boolean {
    const roles = this.getUserRoles();
    return roles.includes('ROLE_ADMIN') || roles.includes('ROLE_USER');
  }

  getUserProfile(): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/profile`);
  }

  private handleError(error: any) {
    console.error('Error:', error);
    return throwError(error);
  }
}
