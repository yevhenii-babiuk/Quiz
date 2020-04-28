import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { AuthenticationService } from '../../core/services/authentication.service';
import { AlertService } from '../../core/services/alert.service';

import { User } from '../../core/models/user';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(
    private authenticationService: AuthenticationService,
    private alertService: AlertService,
    private router: Router
  ) { }

  ngOnInit(): void {
  }

  onLogin(login: string, password: string) {
    login = login.trim();
    if (!login) {
      this.alertService.error('Login is empty!');
      return;
    }

    this.authenticationService.login({login, password} as User)
    .subscribe(
               data => {
                 //this.router.navigate(['/']).then();
                 this.router.navigate(['profile']).then();
               },
               error => {
                   this.alertService.error('Error while login');
                   console.log(error);
               });
  }

}
