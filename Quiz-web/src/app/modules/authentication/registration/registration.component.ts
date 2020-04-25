import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

import {AuthenticationService} from '../../core/services/authentication.service';
import {AlertService} from '../../core/services/alert.service';

import {User} from '../../core/models/user';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  constructor(
    private authenticationService: AuthenticationService,
    private alertService: AlertService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
  }

  registerUser(login: string, firstname: string, lastname: string, email: string, password: string, confirmPassword: string): void {

    if (!login || !firstname || !lastname || !email) {
      this.alertService.error('Fields should not be empty!');
      return;
    }

    if (password !== confirmPassword) {
      this.alertService.error('Passwords don\'t match!');
      return;
    }

    const regUser: User = {
      firstName: firstname,
      secondName: lastname,
      login: login,
      mail: email,
      password: password,
      profile: null,
      score: null,
      role: null
    };

    this.authenticationService.register(regUser)
      .subscribe(
        data => {
          this.alertService.success('Registration successful', true);
          this.router.navigate(['login']);
        },
        error => {
          this.alertService.error('Error while registration!');
          console.log(error);
        });
  }

}
