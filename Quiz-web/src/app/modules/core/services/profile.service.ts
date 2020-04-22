
import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { Observable } from "rxjs";
import {User} from "../../../models/user";



@Injectable({
  providedIn: 'root'
})
export class ProfileService {
  private url = '/api/v1';
  constructor(private http: HttpClient) { }

  public getUser() : Observable<User>{
    return this.http.get<User>(`${this.url}/profile/`);
  }

  public postUser(user: User) : Observable<User>{
    return this.http.post<User>(`${this.url}/edit/`,user);
  }


}

