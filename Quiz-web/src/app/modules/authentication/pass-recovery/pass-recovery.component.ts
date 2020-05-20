import {Component, OnInit} from '@angular/core';

import {AuthenticationService} from '../../core/services/authentication.service';
import {AlertService} from '../../core/services/alert.service';
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-pass-recovery',
  templateUrl: './pass-recovery.component.html',
  styleUrls: ['./pass-recovery.component.css']
})
export class PassRecoveryComponent implements OnInit {

  constructor(
    public translate: TranslateService,
    private authenticationService: AuthenticationService,
    private alertService: AlertService,
  ) {
  }

  ngOnInit(): void {
  }

  reset(email: string) {
    email = email.trim();
    if (!email) {
      this.alertService.error('Enter the email!');
      return;
    }

    this.authenticationService.resetPass(email)
      .subscribe(
        data => {
          this.alertService.success('Reset is successful. Check your email box.', true);
        },
        error => {
          this.alertService.error('Error while reset!');
          console.log(error);
        });
  }
}
