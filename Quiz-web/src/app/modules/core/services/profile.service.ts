import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, of} from "rxjs";
import {User} from "../models/user";
//import {url} from "../../../../environments/environment.prod";
import {countOnPage, url} from "../../../../environments/environment.prod";
import {catchError} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class ProfileService {
  private url = '/api/v1';
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient) {
  }

  public getUser(id: number): Observable<User> {
    return this.http.get<User>(`${this.url}/users/${id}`);
  }

  public updateUser(user: User): Observable<User> {
    return this.http.put<User>(`${this.url}/users/`, user);
  }

  public getUsers(length: number, allUsers: boolean) {
    return this.http.get<User[]>(`${url}/users?pageNumber=${Math.floor((length + 1) / 10)}&allUsers=${allUsers}`)
      .pipe(
        catchError(this.handleError<User[]>([]))
      );
  }

  getFilterUsers(length: number, allUsers: boolean, filter: string) {
    return this.http.get<User[]>(`${url}/users?pageNumber=${Math.floor((length + 1) / 10)}&allUsers=${allUsers}&filter=${filter}`)
      .pipe(
        catchError(this.handleError<User[]>([]))
      );
  }

  private handleError<T>(result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    };
  }

  addFriend(visitorId: number, id: number) {
    return this.http.post<string>(`${url}/users/${id}/addFriend`, visitorId, this.httpOptions);
  }

  removeFriend(visitorId: number, id: number) {
    return this.http.post<string>(`${url}/users/${id}/removeFriend`, visitorId, this.httpOptions);
  }

  getFriends(id: number) {
    return this.http.get<User[]>(`${url}/users/${id}/friends`)
      .pipe(
        catchError(this.handleError<User[]>([]))
      );
  }

  putImage(image: File) {
    const uploadData = new FormData();
    uploadData.append('myFile', image, "name");
    return this.http.put(`${url}/image/profile`, uploadData);
  }

  checkFriendship(id: number, visitorId: number) {
    return this.http.get<boolean>(`${url}/users/${id}/checkFriend/${visitorId}`)
      .pipe(
        catchError(this.handleError<boolean>(false))
      );
  }
}

