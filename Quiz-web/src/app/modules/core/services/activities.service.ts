import {Injectable} from '@angular/core';

import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {catchError} from "rxjs/operators";

import {url} from "../../../../environments/environment.prod";
import {Activity} from "../models/activity";
import {Announcement} from "../models/announcement";
import {Message} from "../models/message";

@Injectable({
  providedIn: 'root'
})

export class ActivitiesService {

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient) {
  }

  getFilterActivities(id: number, categoryFilter: boolean[]): Observable<Activity[]> {
    return this.http.get<Activity[]>(`${url}/activities?id=${id}&categoryFilter=${categoryFilter}`)
      .pipe(
        catchError(this.handleError<Activity[]>([]))
      );
  }

  getFilterActivitiesPage(id: number, categoryFilter: boolean[], currentCount: number): Observable<Activity[]> {
    return this.http.get<Activity[]>(`${url}/activities/filter/pages?id=${id}&categoryFilter=${categoryFilter}&pageNumber=${currentCount/10}`)
      .pipe(
        catchError(this.handleError<Activity[]>([]))
      );
  }

  getActivitiesByUserId(id: number): Observable<Activity[]> {
    return this.http.get<Activity[]>(`${url}/activities/${id}`)
      .pipe(
        catchError(this.handleError<Activity[]>([]))
      );
  }

  getActivitiesPageByUserId(userId: number, currentCount: number): Observable<Activity[]> {
    return this.http.get<Activity[]>(`${url}/activities/${userId}/pages?pageNumber=${currentCount/10}`)
      .pipe(
        catchError(this.handleError<Activity[]>([]))
      );
  }

  private handleError<T>(result?: T) {
    return (error: any): Observable<T> => {

      console.error(error);

      return of(result as T);
    };
  }
}
