import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { AuthenticationService } from '../authentication.service';

import { User } from '../models/user';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(
    private authenticationService: AuthenticationService,
    private router: Router
  ) { }

  ngOnInit(): void {
  }

  onLogin(login: string, password: string) {
    login = login.trim();
    if (!login) { return; }

    this.authenticationService.login({login, password} as User)
    .subscribe(
               data => {
                   this.router.navigate(['/']);
               },
               error => {
                   console.log("Error while login");
               });
  }

}
