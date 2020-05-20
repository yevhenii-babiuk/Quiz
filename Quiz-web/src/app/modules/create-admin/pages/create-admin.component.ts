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
      console.log(this.user.role);
    } else {
      this.user.role = 'MODERATOR';
      this.roles = ['MODERATOR'];
    }
    console.log(this.roles);
  }

  selectOption() {
    console.log(this.user.role)
    //getted from event
    // console.log(id);
    //getted from binding
    // console.log(this.selected)
  }

  createAdmin(): void {

    if (!this.user.login || !this.user.firstName || !this.user.secondName || !this.user.mail || !this.user.role) {
      this.alertService.error('Fields should not be empty!');
      return;
    }

    console.log(this.user);


    this.authenticationService.createAdmin(this.user)
      .subscribe(
        data => {
          if (data) {
            this.alertService.success('Created successfully', true);
            this.router.navigate(['profile']);
          } else {
            this.alertService.error('Couldn\'t create', false);
          }
        },
        error => {
          this.alertService.error('Error while creation!');
          console.log(error);
        });
  }

}
