import { Injectable } from '@angular/core';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { User } from '../models/user';
import {url} from '../../../../environments/environment.prod';
import {map} from "rxjs/operators";

export class JwtResponse{
  constructor(
    public jwttoken:string,
  ) {}

}

@Injectable({
  providedIn: 'root'
})

export class AuthenticationService {

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };



  constructor(private http: HttpClient) { }

  login(user: User){
    let username = user.login;
    let password = user.password;
    return this.http.post<any>(`${url}/login`,{username,password}).pipe(
      map(
        userData => {
          sessionStorage.setItem('username',username);
          let tokenStr= 'Bearer '+ userData.token;
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
}
