import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

import {AuthenticationService} from '../../core/services/authentication.service';
import {AlertService} from '../../core/services/alert.service';

import {User} from '../../core/models/user';
import {TranslateService} from "@ngx-translate/core";
import {SecurityService} from "../../core/services/security.service";

@Component({
  selector: 'app-create-admin',
  templateUrl: './create-admin.component.html',
  styleUrls: ['./create-admin.component.css']
})
export class CreateAdminComponent implements OnInit {
  user: User = new User();
  confirmPassword: string = "";
  roles: string[] = ['ADMIN', 'MODERATOR'];


  constructor(
    private authenticationService: AuthenticationService,
    private alertService: AlertService,
    private router: Router,
    public translate: TranslateService,
    public secur: SecurityService
  ) {
  }

  ngOnInit(): void {
    if (this.secur.getCurrentRole() == 'SUPER_ADMIN') {
      this.user.role = 'ADMIN';
    } else {
      this.user.role = 'MODERATOR';
      this.roles = ['MODERATOR'];
    }
  }

  createAdmin(): void {

    if (!this.user.login || !this.user.firstName || !this.user.secondName || !this.user.mail || !this.user.role) {
      this.alertService.error('alert.fieldEmpty');
      return;
    }

    console.log(this.user);


    this.authenticationService.createAdmin(this.user)
      .subscribe(
        data => {
          if (data) {
            this.alertService.success('alert.adminCreatedSuccessful', true);
            this.router.navigate(['profile']);
          } else {
            this.alertService.error('alert.adminCreatedNoSuccessful', false);
          }
        },
        error => {
          this.alertService.error('alert.errorCreatedAdmin');
          console.log(error);
        });
  }

}
