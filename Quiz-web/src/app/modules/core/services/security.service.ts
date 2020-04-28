import {Injectable} from '@angular/core';
import decode from 'jwt-decode';
import {Role} from "../models/role";

@Injectable({providedIn: 'root'})
export class SecurityService {

  private role: Role;
  private id: number;
  constructor () {
  }

  public getCurrentId() {
    const token = sessionStorage.getItem('token');
    const tokenPayload = decode(token);
    return this.id = tokenPayload.userId;
  }

  public getCurrentRole() {
    const token = sessionStorage.getItem('token');
    const tokenPayload = decode(token);

    return this.role = tokenPayload.role;

  }
}
