import { Injectable } from '@angular/core';

import { HttpClient, HttpHeaders } from '@angular/common/http';

import { User } from '../models/user';
import {url} from '../../../../environments/environment.prod';
import {map} from "rxjs/operators";
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  public jwtHelper: JwtHelperService;

  constructor(private http: HttpClient) { }

  login(user: User) {
    let username = user.login;
    let password = user.password;
    return this.http.post<any>('http://localhost:8080/api/v1/login',{username,password}).pipe(
      map(
        userData => {
          let tokenStr= 'Bearer '+userData.token;
          sessionStorage.setItem('token', tokenStr);
          return userData;
        }
      )

    );
  }

  register(user: User) {
    return this.http.post<User>(`${url}/registration`, user, this.httpOptions);
    }

  confirmMail(token: string) {
    return this.http.get<boolean>(`${url}/registration/${token}`);
  }

  resetPass(email: string){
      return this.http.post<string>(`${url}/pass-recovery`, email, this.httpOptions);
  }

  confirmResetPass(token: string) {
    return this.http.get<boolean>(`${url}/pass-recovery/${token}`);
  }

  createNewPass(token: string, newpass: string){
    return this.http.put<string>(`${url}/pass-recovery/${token}`, newpass, this.httpOptions);
  }

  public isAuthenticated(): boolean {
    const token = sessionStorage.getItem('token');
    // Check whether the token is expired and return
    // true or false
    return !this.jwtHelper.isTokenExpired(token);
  }
}
