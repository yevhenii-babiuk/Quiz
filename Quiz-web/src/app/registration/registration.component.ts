import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { AuthenticationService } from '../authentication.service';

import { User } from '../models/user';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  constructor(
    private authenticationService: AuthenticationService,
    private router: Router
  ) { }

  ngOnInit(): void {
  }

  register(login: string, firstname: string, lastname: string,
            email: string, password: string, confirmPassword: string){

    if (password !== confirmPassword) { return; }

    let regUser: User = {
      firstName: firstname,
      secondName: lastname,
      login: login,
      mail: email,
      password: password
    };

    this.authenticationService.register(regUser)
    .subscribe(
               data => {
                   this.router.navigate(['/login']);
               },
               error => {
                   console.log("Error while registration");
               });
  }

}
