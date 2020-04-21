import { Injectable } from '@angular/core';

import { HttpClient, HttpHeaders } from '@angular/common/http';
 import { Observable, of } from 'rxjs';
// import { catchError, tap } from 'rxjs/operators';
import { map } from 'rxjs/operators';

import { User } from '../models/user';

@Injectable({
  providedIn: 'root'
})

export class AuthenticationService {
  private url = '/api/v1';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };



  constructor(private http: HttpClient) { }

  login(/*username, password*/ user: User): Observable<User>{

    let username = user.login;
    let password = user.password;

    return this.http.post<any>(`${this.url}/api/v1/login`,{username, password},this.httpOptions).pipe(
      map(
        userData => {
          sessionStorage.setItem('username',username);
          let tokenStr= 'Bearer '+ userData.token;
          sessionStorage.setItem('token', tokenStr);
          return userData;
        }
      )

    );

        //return this.http.post<User>(`${this.url}/login`, user, this.httpOptions);
    }

  register(user: User) {
        return this.http.post<User>(`${this.url}/registration`, user, this.httpOptions);
    }

  confirmMail (token: string) {
    return this.http.get<boolean>(`${this.url}/registration/${token}`);
  }

  resetPass(email: string){
      return this.http.post<string>(`${this.url}/pass-recovery`, email, this.httpOptions);
  }

  confirmResetPass (token: string) {
    return this.http.get<boolean>(`${this.url}/pass-recovery/${token}`);
  }

  createNewPass(token: string, newpass: string){
    return this.http.put<string>(`${this.url}/pass-recovery/${token}`, newpass, this.httpOptions);
  }
}
