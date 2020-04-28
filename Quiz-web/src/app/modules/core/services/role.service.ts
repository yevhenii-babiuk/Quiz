import {Injectable} from '@angular/core';
import decode from 'jwt-decode';
import {Role} from "../models/role";

@Injectable({providedIn: 'root'})
export class RoleService {

  private role: Role;
  constructor () {
  }

  public getCurrentRole() {
    const token = sessionStorage.getItem('token');
    const tokenPayload = decode(token);

    return this.role = tokenPayload.role;

  }
}
