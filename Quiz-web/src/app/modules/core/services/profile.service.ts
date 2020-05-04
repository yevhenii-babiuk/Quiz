
import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { Observable } from "rxjs";
import {User} from "../models/user";

@Injectable({
  providedIn: 'root'
})
export class ProfileService {
  private url = '/api/v1';
  constructor(private http: HttpClient) { }

  public getUser(id: number) : Observable<User>{
    return this.http.get<User>(`${this.url}/users/${id}`);
  }

  public postUser(user: User) : Observable<User>{
    return this.http.post<User>(`${this.url}/users/`,user);
  }

  /*putImage(image: File) {
    const uploadData = new FormData();
    uploadData.append('myFile', image, "name");
    return this.http.put(`${url}/users`, uploadData);
  }*/

}

