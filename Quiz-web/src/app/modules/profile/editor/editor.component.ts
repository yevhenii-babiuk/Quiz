import {Component, Injectable, OnInit, Output} from '@angular/core';
import {User} from "../../core/models/user";
import {ProfileService} from "../../core/services/profile.service";
import {AlertService} from "../../core/services/alert.service";
import {SecurityService} from "../../core/services/security.service";

@Component({
  selector: 'app-editor',
  templateUrl: './editor.component.html',
  styleUrls: ['./editor.component.css']
})

export class EditorComponent implements OnInit {

  id: number;
  userData: User;
  login: String;
  role: String;

  constructor(
    private profileService: ProfileService,
    private alertService: AlertService,
    private securityService: SecurityService
  ) {
  }

  ngOnInit(): void {
    this.getUser();
  }

  private getUser() {
    this.id = this.securityService.getCurrentId();
    this.role = this.securityService.getCurrentRole();
    this.profileService.getUser(this.id).subscribe(data => {
      this.userData = data;
      this.userData.id = this.id;


    });
  }

  edit(id: number, firstname: string, secondname: string, email: string, profile: string, password: string, confirmPassword: string) {
    if (password != confirmPassword) {
      this.alertService.error('passwords don`t match');
      return;
    }
    if (firstname == "" || secondname == "" || email == "" || password == "" || confirmPassword == "") {
      this.alertService.error('fill all required fields');
      return;
    }
    let editedUser: User = {
      id: id,
      firstName:firstname,
      secondName:secondname,
      login:this.userData.login,
      mail:email,
      password:password,
      profile:profile,
      score:this.userData.score,
      role:this.userData.role,
      registrationDate:this.userData.registrationDate,
      imageId: this.userData.imageId,
      image: this.userData.image,
      status: this.userData.status
    };

    this.profileService.updateUser(editedUser).subscribe(data => {
      editedUser = data;
    });
    this.alertService.success('You have successfully edited your profile');
  }

}
