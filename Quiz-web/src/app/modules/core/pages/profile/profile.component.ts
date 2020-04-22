import {Component, Injectable, OnInit, Output} from '@angular/core';

import {User} from "../../../../models/user";
import {ProfileService} from "../../services/profile.service";



@Injectable()
@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})

@Injectable()
export class ProfileComponent implements OnInit {
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
