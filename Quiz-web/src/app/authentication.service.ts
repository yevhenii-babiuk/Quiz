import { Injectable } from '@angular/core';

import { HttpClient, HttpHeaders } from '@angular/common/http';

import { User } from './models/user';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private url = 'http://localhost:8080'

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  login(user: User) {
        console.log(user.login + " " + user.password);
        return this.http.post<User>(`${this.url}/login`, user, this.httpOptions);
    }

  register(user: User) {
        return this.http.post<User>(`${this.url}/registration`, user, this.httpOptions);
    }

}
