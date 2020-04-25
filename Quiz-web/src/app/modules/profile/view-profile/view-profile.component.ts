import {Component, Injectable, OnInit} from '@angular/core';

import {User} from "../../core/models/User";
import {ProfileService} from "../../core/services/profile.service";
import {Role} from "../../core/models/role";



@Component({
  selector: 'app-profile',
  templateUrl: './view-profile.component.html',
  styleUrls: ['./view-profile.component.css']
})

export class ViewProfile implements OnInit {
  userData: User;
  role: String;
  isUser: boolean;
  isModerator: boolean;
  isAdmin: boolean;
  isSuperAdmin: boolean;

  constructor(
    private profileService: ProfileService
  ) {

  }

  ngOnInit(): void {
    this.getUser();
  }

  private getUser() {
    this.profileService.getUser().subscribe(data => {
      this.userData = data;
      this.role = this.userData.role;
      this.setCondition(this.role);
    });
  }

  private setCondition(role: String) {
    if (role == Role.USER) {
      this.isUser = true;
    } else if (role == Role.SUPER_ADMIN) {
      this.isSuperAdmin = true;
    } else if (role == Role.ADMIN) {
      this.isAdmin = true;
    } else if (role == Role.MODERATOR) {
      this.isModerator = true;
    }
  }


}
