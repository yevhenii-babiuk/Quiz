import {Injectable} from '@angular/core';

import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {catchError} from "rxjs/operators";

import {url} from "../../../../environments/environment.prod";
import {Game} from "../models/game";
import Str = echarts.EChartOption.Tooltip.Position.Str;
import {UserDto} from "../models/userDto";

@Injectable({
  providedIn: 'root'
})
export class PlayGameService {

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient) {
  }

  /* getQuizzes(params: string, currentCount: number): Observable<Quiz[]> {
     return this.http.get<Quiz[]>(`${url}/quizzes${params}&pageNumber=${currentCount / countOnPage}&countOnPage=${countOnPage}`)
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
   }*/

  sendGame(game: Game) {
    return this.http.post<string>(`${url}/game/`, game, this.httpOptions);
  }

  sendJoinedUser(userId: number, gameId: string) {
    return this.http.post<UserDto>(`${url}/game/${gameId}/joinedUser`, userId, this.httpOptions);
  }

  /*getById(id: string) {
    return this.http.get<Quiz>(`${url}/quiz/${id}`).pipe(
      catchError(this.handleError<Quiz>())
    );
  }*/

  private handleError<T>(result?: T) {
    return (error: any): Observable<T> => {

      console.error(error); // log to console instead

      return of(result as T);
    };
  }

  getJoinedPlayers(gameId: string) : Observable<String[]> {
    return this.http.get<String[]>(`${url}/game/${gameId}/joinedUser`)
      .pipe(
        catchError(this.handleError<String[]>([]))
      );
  }

  getGame(gameId: string) {
    return this.http.get<Game>(`${url}/game/${gameId}`)
      .pipe(
        catchError(this.handleError<Game>(null))
      );
  }
}
