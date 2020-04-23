import {Component, Injectable, OnInit} from '@angular/core';

import {User} from "../../../models/user";
import {ProfileService} from "../../core/services/profile.service";



@Component({
  selector: 'app-profile',
  templateUrl: './view-profile.component.html',
  styleUrls: ['./view-profile.component.css']
})


export class ViewProfileComponent implements OnInit {
  userData :  User;
  role:String;
  isUser:boolean=false;
  isModerator:boolean=false;
  isAdmin:boolean=false;
  isSuperAdmin:boolean=false;
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
      this.role=this.userData.role.toLowerCase();
      this.setCondition(this.role);
    });
  }
  private setCondition(role:String){
    if(role=="user"){
      this.isUser=true;
    }
    else if(role=="super_admin"){
      this.isSuperAdmin=true;
    }
    else if(role=="admin"){
      this.isAdmin=true;
    }
    else if(role=="moderator"){
      this.isModerator=true;
    }
  }


}
