import { Injectable } from '@angular/core';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { User } from '../models/user';
import {url} from "../../../../environments/environment.prod";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  login(user: User): Observable<User>{
        return this.http.post<User>(`${url}/login`, user, this.httpOptions);
    }

  register(user: User) {
        return this.http.post<User>(`${url}/registration`, user, this.httpOptions);
    }

  confirmMail (token: string) {
    return this.http.get<boolean>(`${url}/registration/${token}`);
  }

  resetPass(email: string){
      return this.http.post<string>(`${url}/pass-recovery`, email, this.httpOptions);
  }

  confirmResetPass (token: string) {
    return this.http.get<boolean>(`${url}/pass-recovery/${token}`);
  }

  createNewPass(token: string, newpass: string){
    return this.http.put<string>(`${url}/pass-recovery/${token}`, newpass, this.httpOptions);
  }
}
