import {Injectable} from '@angular/core';
import decode from 'jwt-decode';
import {Role} from "../models/role";

@Injectable({providedIn: 'root'})
export class IdService {

  private id: number;
  constructor () {
  }

  public getCurrentId() {
    const token = sessionStorage.getItem('token').substr(7);
    const tokenPayload = decode(token);
    return this.id = tokenPayload.userId;

  }
}
