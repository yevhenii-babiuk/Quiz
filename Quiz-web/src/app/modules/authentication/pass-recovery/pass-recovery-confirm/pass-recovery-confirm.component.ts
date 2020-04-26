import { Component, OnInit } from '@angular/core';

import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import { Location } from '@angular/common';

import { AuthenticationService } from '../../../core/services/authentication.service';
import { AlertService } from '../../../core/services/alert.service';

@Component({
  selector: 'app-pass-recovery-confirm',
  templateUrl: './pass-recovery-confirm.component.html',
  styleUrls: ['./pass-recovery-confirm.component.css']
})
export class PassRecoveryConfirmComponent implements OnInit {

  isConfirmed: boolean;
  token: string;

  constructor(
      private authenticationService: AuthenticationService,
      private alertService: AlertService,
      private route: ActivatedRoute,
      private location: Location,
      private router: Router
  ) { }

  ngOnInit() {
   this.confirm();
 }

 confirm(): void {
    this.token = this.route.snapshot.paramMap.get('token');
    this.authenticationService.confirmResetPass(this.token)
   .subscribe(
     isConfirmed => { this.isConfirmed = isConfirmed; },
     error => {
       this.isConfirmed = false;
       console.log(error);
     }
   );
 }

 createNewPass(password: string) {
   this.authenticationService.createNewPass(this.token, password)
   .subscribe(
              data => {
                  this.router.navigate(['/login']).then();
              },
              error => {
                  this.alertService.error('Error while applying new pass');
                  console.log(error);
              });
 }

}
