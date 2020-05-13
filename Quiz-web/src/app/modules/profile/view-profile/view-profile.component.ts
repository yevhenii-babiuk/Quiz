import {Component, OnInit} from '@angular/core';
import {User} from "../../core/models/user";
import {ProfileService} from "../../core/services/profile.service";
import {Role} from "../../core/models/role";
import {SecurityService} from "../../core/services/security.service";
import {Imaged} from "../../core/models/imaged";
import {Image} from "../../core/models/image";
import {ActivatedRoute} from "@angular/router";


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
  isOwn: boolean;
  isFriend: boolean;
  visitorId: number;

  constructor(
    private route: ActivatedRoute,
    private profileService: ProfileService,
    private securityService: SecurityService
  ) {
  }

  ngOnInit(): void {
    console.log(this.securityService.getCurrentId())
    //const id = this.route.snapshot.paramMap.get('userId');
    //console.log(id);
    /*if (id) {
      this.profileService.getById(id).subscribe(
        data => {
          this.userData = data;
          this.role = this.userData.role;
          this.userData.imageId = -1;
        }, err => {
          console.log(err);
          // redirect.navigate(['quizzes']);
        });*/
    //} else {
      this.getUser();
    //}

    /*let image: Image = {
      id: -1,
      // src:"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"
      src: ""
    }
    let editedUser: User = {
      id: 1,
      firstName: "firstname",
      secondName: "secondname",
      login: "login",
      mail: "email",
      password: "password",
      profile: "profile",
      score: 0,
      role: this.roleEnum.USER,
      imageId: -1,
      image: image
    };
    this.userData = editedUser;*/
    console.log("imadeId " + this.userData.imageId);
    this.id = Number(this.route.snapshot.paramMap.get('userId'));
    if (this.id) {
      this.visitorId = this.securityService.getCurrentId();
      if (this.visitorId != this.id) this.isOwn = false;
      else this.isOwn = true;
      this.profileService.checkFriendship(this.id, this.visitorId).subscribe(data => {
        this.isFriend = data;
        console.log(data);
      });
    } else {
      this.id = this.securityService.getCurrentId();
      this.isOwn = true;
    }
    this.getUser();
  }

  private getUser() {
    console.log(this.id);
    this.profileService.getUser(this.id).subscribe(data => {
      this.userData = data;
      this.role = this.securityService.getCurrentRole();
      this.userData.imageId = -1;
    });
  }

  processFile(imageInput: any, imaged: Imaged) {
    const file: File = imageInput.files[0];
    const reader = new FileReader();

    reader.addEventListener('load', (event: any) => {
      imaged.image.src = event.target.result.substring(23);
      this.profileService.putImage(file).subscribe(
        id => {
          console.log("id=" + id);
          if (typeof id === "number") {
            imaged.imageId = id;
          }
        },
        error => {
          imaged.image.src = null;
          console.log(error);
        });
    });

    reader.readAsDataURL(file);
  }

  friendship() {
    if (this.isFriend)
      this.profileService.removeFriend(this.visitorId, this.id).subscribe(data => {
        this.isFriend = false;
      });
    else
      this.profileService.addFriend(this.visitorId, this.id).subscribe(data => {
        this.isFriend = true
      });
  }
}
