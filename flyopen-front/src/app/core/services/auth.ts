import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of, tap } from 'rxjs';


interface LoginResponse {
  token: string;
  // Add any other properties returned by the login API
}
@Injectable({
  providedIn: 'root',
})
export class AuthService {

  private apiUrl = 'http://localhost:8080/api/auth'; 

  constructor(private http: HttpClient) {}
  
  login(username: string, password: string): Observable<LoginResponse> {
    // Implement your login logic here
    return this.http.post<LoginResponse>(
      `${this.apiUrl}/login`,
       { username, 
        password
       }
      ).pipe(
        tap(response => {
          localStorage.setItem('token', response.token);
        })
      );
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }
}
