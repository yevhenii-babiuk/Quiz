import {Component, Input, OnInit} from '@angular/core';

import {User} from "../../core/models/user";
import {ProfileService} from "../../core/services/profile.service";

@Component({
  selector: 'app-sideprofile',
  templateUrl: 'sideprofile.component.html',
  styleUrls: ['./sideprofile.component.css']
})
export class SideprofileComponent implements OnInit {
  userData :  User;
  isUser: boolean=false;
  isAdmin: boolean=false;
  role:String;
  constructor(
    private profileService: ProfileService
  ) {
  }

  ngOnInit(): void {
    this.getRole();
  }

  private getRole() {
    this.profileService.getUser().subscribe(data => {
      this.userData = data;
      this.role=this.userData.role.toLowerCase();
      this.setSide(this.role);
    });
  }
  private setSide(role:String){
    if(role=="user"){
      this.isUser=true;
    }
    else if(role=="admin" || role=="moderator" || role=="super_admin"){
      this.isAdmin=true;
    }
  }

}
