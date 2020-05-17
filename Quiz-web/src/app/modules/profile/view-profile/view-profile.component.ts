import {Component, OnInit} from '@angular/core';
import {User} from "../../core/models/user";
import {ProfileService} from "../../core/services/profile.service";
import {Role} from "../../core/models/role";
import {SecurityService} from "../../core/services/security.service";
import {Imaged} from "../../core/models/imaged";
import {ActivatedRoute} from "@angular/router";
import {AchievementService} from "../../core/services/achievement.service";
import {Status} from "../../core/models/Status";
import {AlertService} from "../../core/services/alert.service";


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
  isActivated: boolean;
  statusEnum = Status;

  constructor(
    private route: ActivatedRoute,
    private profileService: ProfileService,
    private securityService: SecurityService,
    private achievementService: AchievementService,
    private alertService: AlertService
  ) {
  }

  ngOnInit(): void {
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
      this.isActivate();
    });
  }

  processFile(imageInput: any, imaged: Imaged) {
    //this.id=this.securityService.getCurrentId();
    const file: File = imageInput.files[0];
    const reader = new FileReader();

    reader.addEventListener('load', (event: any) => {
      imaged.image.src = event.target.result.substring(23);
      this.profileService.putImage(this.id, file).subscribe(
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

  recalculateAchievement() {
    this.achievementService.recalculateAchievements().subscribe(
      data => {
        if (data) {
          this.alertService.success('Recalculation is successful', false);
        } else {
          this.alertService.error('Recalculation is not successful', false);
        }
      },
      error => {
        this.alertService.error('Error while recalculation!');
        console.log(error);
      });
  }

  isActivate() {
    if (this.userData.status &&
      this.userData.status == this.statusEnum.ACTIVATED) {
      this.isActivated = true;
    } else {
      this.isActivated = false;
    }
  }

  changeStatus() {
    this.isActivated = !this.isActivated;
    let status: Status;
    if (this.isActivated) {
      status = Status.ACTIVATED;
    } else {
      status = Status.UNACTIVATED;
    }
    this.userData.status = status;
    this.profileService.changeStatus(this.userData.id, status)
      .subscribe(
        data => {
          if (data) {
            this.alertService.success('Status was successfully changed', false);
          } else {
            this.alertService.error('Status was successfully changed', false);
          }
        },
        error => {
          this.alertService.error('Error while changing status!');
          console.log(error);
        });
  }


}
