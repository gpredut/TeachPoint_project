import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/login`, { username, password });
  }

  register(
    name: string,
    surname: string,
    username: string,
    email: string,
    password: string
  ): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/register`, {
      name,
      surname,
      username,
      email,
      password,
    });
  }
}
