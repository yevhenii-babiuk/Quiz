import {Component, Injectable, OnInit, Output} from '@angular/core';
import {User} from "../../../models/user";

import {AlertService} from "../../core/services/alert.service";
import {ProfileService} from "../../core/services/profile.service";



@Component({
  selector: 'app-editor',
  templateUrl: './editor.component.html',
  styleUrls: ['./editor.component.css']
})

export class EditorComponent implements OnInit {
  userData : User;
  login:String;
  role:String;

  constructor(
    private profileService: ProfileService,
    private alertService: AlertService
  ) {
  }

  ngOnInit(): void {
    this.getUser();
  }
  private getUser() {
    this.profileService.getUser().subscribe(data => {
      this.userData = data;
      this.role=this.userData.role;

    });
  }
  edit(firstname: string, secondname: string, email: string, profile:string, password: string, confirmPassword: string){
    if (password!=confirmPassword) {
      this.alertService.error('passwords don`t match');
      return;
    }
    let editUser: User = {
      userId:this.userData.userId,
      firstName:firstname,
      secondName:secondname,
      login:this.userData.login,
      mail:email,
      password:password,
      profile:profile,
      registrationDate:this.userData.registrationDate,
      score:this.userData.score,
      status:this.userData.status,
      role:this.userData.role
    };
    this.profileService.postUser(editUser).subscribe(data => {
      editUser=data;
    });
  }

}
