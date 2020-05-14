import {Injectable} from '@angular/core';
import {
  Router,
  CanActivate, ActivatedRouteSnapshot,
} from '@angular/router';
import {AuthenticationService} from './authentication.service';
import {SecurityService} from "./security.service";
import decode from 'jwt-decode';

@Injectable()
export class RoleGuardService implements CanActivate {
  constructor(public auth: AuthenticationService, public secur: SecurityService, public router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot): boolean {
    // this will be passed from the route config
    // on the data property
    const expectedRole = route.data.expectedRole;
    const token = localStorage.getItem('token');
    // decode the token to get its payload
    const tokenPayload = decode(token);
    if (!this.auth.isAuthenticated()) {
      this.router.navigate(['login']);
      return false;
    }
    if (tokenPayload.role !== expectedRole) {
      this.router.navigate(['profile']);
      return false;
    }
    return true;
  }
}
