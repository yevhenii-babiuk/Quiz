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

  registerUser(login: string, firstName: string, secondName: string, mail: string, password: string, confirmPassword: string): void {

    if (!login || !firstName || !secondName || !mail) {
      this.alertService.error('Fields should not be empty!');
      return;
    }

    if (password !== confirmPassword) {
      this.alertService.error('Passwords don\'t match!');
      return;
    }

    this.authenticationService.register({firstName, secondName, login, mail, password} as User)
      .subscribe(
        data => {
          if (data){
            this.alertService.success('Registration successful', true);
            this.router.navigate(['login']);
          } else {
            this.alertService.error('Registration is not successful', false);
          }
        },
        error => {
          this.alertService.error('Error while registration!');
          console.log(error);
        });
  }

}
