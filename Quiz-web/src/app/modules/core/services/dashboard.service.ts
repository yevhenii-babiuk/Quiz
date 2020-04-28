import {Injectable} from '@angular/core';

import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {catchError} from "rxjs/operators";

import {User} from '../models/user'
import {url} from "../../../../environments/environment.prod";
@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient) {
  }

  getTopUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${url}/dashboard/top`)
      .pipe(
        catchError(this.handleError<User[]>([]))
      );
  }

  private handleError<T>(result?: T) {
    return (error: any): Observable<T> => {

      console.error(error); // log to console instead

      return of(result as T);
    };
  }
}
