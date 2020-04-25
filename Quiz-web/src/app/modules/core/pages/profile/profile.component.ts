import {Component, Injectable, OnInit, Output} from '@angular/core';


import {ProfileService} from "../../services/profile.service";
import {Role} from "../../models/role";
import {User} from "../../models/user";
import {decode} from 'jwt-decode';


@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})

export class ProfileComponent implements OnInit {
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
      const token = sessionStorage.getItem('token');
      const tokenPayload = decode(token);
      this.role = tokenPayload.role;
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
