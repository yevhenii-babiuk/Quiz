import {Component, Injectable, OnInit} from '@angular/core';
import {User} from "../../core/models/user";
import {ProfileService} from "../../core/services/profile.service";
import {Role} from "../../core/models/role";
import {SecurityService} from "../../core/services/security.service";


@Component({
  selector: 'app-profile',
  templateUrl: './view-profile.component.html',
  styleUrls: ['./view-profile.component.scss']
})

export class ViewProfile implements OnInit {
  userData: User;
  id: number;
  role: Role;
  roleEnum = Role;

  constructor(
    private profileService: ProfileService,
    private securityService: SecurityService
  ) {
  }

  ngOnInit(): void {
    this.getUser();
  }

  private getUser() {
    this.id = this.securityService.getCurrentId();
    console.log(this.id);
    this.profileService.getUser(this.id).subscribe(data => {
      this.userData = data;
      this.role = this.securityService.getCurrentRole();
    });
  }
}
