import {Component, Injectable, OnInit} from '@angular/core';

import {User} from "../../core/models/user";
import {ProfileService} from "../../core/services/profile.service";
import {Role} from "../../core/models/role";
import {RoleService} from "../../core/services/role.service";
import {switchAll} from "rxjs/operators";
import {IdService} from "../../core/services/id.service";
import {formatNumber} from "@angular/common";


@Component({
  selector: 'app-profile',
  templateUrl: './view-profile.component.html',
  styleUrls: ['./view-profile.component.css']
})

export class ViewProfile implements OnInit {
  userData: User;
  id
  role: Role;
  roleEnum= Role;
  constructor(
    private profileService: ProfileService,
    private roleService: RoleService,
    private idService: IdService
  ) {

  }

  ngOnInit(): void {
    this.getUser();
  }

  private getUser() {
    this.id = this.idService.getCurrentId();
    console.log(this.id);
    this.profileService.getUser(this.id).subscribe(data => {
      console.log(data)
      this.userData = data;
      this.role = this.roleService.getCurrentRole();

    });
  }
}





