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
  user: User = new User();
  confirmPassword: string = "";

  constructor(
    private authenticationService: AuthenticationService,
    private alertService: AlertService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
  }

  registerUser(): void {

    if (!this.user.login || !this.user.firstName || !this.user.secondName || !this.user.mail) {
      this.alertService.error('alert.fieldEmpty');
      return;
    }

    this.authenticationService.register(this.user)
      .subscribe(
        data => {
          if (data) {
            this.alertService.success('alert.registrationSuccessful', true);
            this.router.navigate(['login']);
          } else {
            this.alertService.error('alert.registrationNoSuccessful', false);
          }
        },
        error => {
          this.alertService.error('alert.errorRegistration');
          console.log(error);
        });
  }

}
