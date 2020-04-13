import { Component, OnInit } from '@angular/core';

import { AuthenticationService } from '../authentication.service';
import { AlertService } from '../alert.service';

@Component({
  selector: 'app-pass-recovery',
  template: `
  <h3>Password recovery</h3>
  <div>Please, enter your email to reset the password</div>
  <div>
    <input type="email" placeholder="email" #email>
  </div>
  <button (click)="reset(email.value)">
    Recover
  </button>
  `,
  styleUrls: ['./pass-recovery.component.css']
})
export class PassRecoveryComponent implements OnInit {

  constructor(
    private authenticationService: AuthenticationService,
    private alertService: AlertService,
  ) { }

  ngOnInit(): void {
  }

  reset(email: string){
    email = email.trim();
    if (!email) {
      this.alertService.error("Enter the email!");
      return;
    }

    this.authenticationService.resetPass(email)
    .subscribe(
      data => {
        this.alertService.success('Reset is successful. Check your email box.', true);
      },
      error => {
        this.alertService.error("Error while reset!");
        console.log(error);
      });
  }
}
