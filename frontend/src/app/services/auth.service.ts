import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpHeaders,
  HttpErrorResponse,
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

interface User {
  id: number;
  name: string;
  surname: string;
  username: string;
  email: string;
  role: string;
}

interface AuthResponse {
  token?: string;
  message?: string;
  error?: string;
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<AuthResponse> {
    return this.http
      .post<AuthResponse>(`${this.apiUrl}/login`, { username, password })
      .pipe(catchError(this.handleError));
  }

  register(
    name: string,
    surname: string,
    username: string,
    email: string,
    password: string,
    roleName: string
  ): Observable<AuthResponse> {
    const payload = {
      username,
      password,
      name,
      surname,
      email,
      roleName: `ROLE_${roleName}`,
    };

    console.log('Register payload:', payload);

    return this.http
      .post<AuthResponse>(`${this.apiUrl}/register`, payload)
      .pipe(catchError(this.handleError));
  }

  getUsers(): Observable<User[]> {
    const token = this.getToken();
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.http
      .get<User[]>(`${this.apiUrl}/users`, { headers })
      .pipe(catchError(this.handleError));
  }

  saveToken(token: string): void {
    localStorage.setItem('token', token);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getUserRole(): string | null {
    const token = this.getToken();
    if (token) {
      const payload = this.decodeToken(token);
      return payload?.role || null;
    }
    return null;
  }

  private decodeToken(token: string): any {
    const payload = token.split('.')[1];
    if (payload) {
      return JSON.parse(atob(payload));
    }
    return null;
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    let errorMessage = 'An unknown error occurred!';
    if (error.error instanceof ErrorEvent) {
      errorMessage = `An error occurred: ${error.error.message}`;
    } else {
      errorMessage = `Server returned code: ${error.status}, error message is: ${error.message}`;
    }
    console.error(errorMessage);
    return throwError(errorMessage);
  }
}
