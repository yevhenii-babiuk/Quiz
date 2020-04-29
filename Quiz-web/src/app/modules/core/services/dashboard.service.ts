import {Injectable} from '@angular/core';

import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {catchError} from "rxjs/operators";

import {User} from '../models/user'
import {url} from "../../../../environments/environment.prod";
import {SecurityService} from "./security.service";
import {Statistics} from "../models/statistics";

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(
    private http: HttpClient,
    private securityService: SecurityService) {
  }

  getTopUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${url}/profile/dashboard/top`)
      .pipe(
        catchError(this.handleError<User[]>([]))
      );
  }

  getQuizzesCorrectAnswerPercent(): Observable<Statistics[]> {
    let id=this.securityService.getCurrentId();
    return this.http.get<Statistics[]>(`${url}/profile/dashboard/${id}/quizzes/played/percent`)
      .pipe(
        catchError(this.handleError<Statistics[]>([]))
      );
  }

  getFriendsPreferences(): Observable<Statistics[]> {
    let id=this.securityService.getCurrentId();
    return this.http.get<Statistics[]>(`${url}/profile/dashboard/${id}/friends/preferences`)
      .pipe(
        catchError(this.handleError<Statistics[]>([]))
      );
  }

  private handleError<T>(result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    };
  }
}
