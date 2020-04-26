import {Injectable} from '@angular/core';

import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {catchError} from "rxjs/operators";

import {Quiz} from '../models/quiz'
import {Category} from '../models/category'
import {url} from "../../../../environments/environment.prod";
import {Tag} from "../models/tag";
@Injectable({
  providedIn: 'root'
})
export class QuizzesService {

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient) {
  }

  getQuizzes(params: string, currentCount: number): Observable<Quiz[]> {
    return this.http.get<Quiz[]>(`${url}/quizzes${params}&count=${currentCount}`)
      .pipe(
        catchError(this.handleError<Quiz[]>([]))
      );
  }

  getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(`${url}/categories`)
      .pipe(
        catchError(this.handleError<Quiz[]>([]))
      );
  }
  getTags(): Observable<Tag[]> {
    return this.http.get<Tag[]>(`${url}/tags`)
      .pipe(
        catchError(this.handleError<Tag[]>([]))
      );
  }

  putImage(image: File) {
    const uploadData = new FormData();
    uploadData.append('myFile', image, "name");
    return this.http.put(`${url}/image`, uploadData);
  }

  sendQuiz(quiz: Quiz){
    return this.http.put<string>(`${url}/quiz/`, quiz, this.httpOptions);
  }

  getById(id: string){
    return this.http.get<Quiz>(`${url}/quiz/${id}`).pipe(
      catchError(this.handleError<Quiz>())
    );
  }

  private handleError<T>(result?: T) {
    return (error: any): Observable<T> => {

      console.error(error); // log to console instead

      return of(result as T);
    };
  }
}
