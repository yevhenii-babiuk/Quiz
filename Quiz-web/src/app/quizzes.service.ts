import {Injectable} from '@angular/core';

import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {catchError} from "rxjs/operators";

import {Quiz} from './models/quiz'
import {logger} from "codelyzer/util/logger";

@Injectable({
  providedIn: 'root'
})
export class QuizzesService {
  private url = '/api/v1';

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient) {
  }

  getQuizzes(currentCount: number): Observable<Quiz[]> {
    return this.http.get<Quiz[]>(`${this.url}/quizzes/${currentCount.toString(10)}`)
      .pipe(
        catchError(this.handleError<Quiz[]>([]))
      );

  }

  private handleError<T>(result?: T) {
    return (error: any): Observable<T> => {

      console.error(error); // log to console instead

      return of(result as T);
    };
  }
}
