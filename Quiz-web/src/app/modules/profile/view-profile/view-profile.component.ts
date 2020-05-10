import {Component, OnInit} from '@angular/core';
import {User} from "../../core/models/user";
import {ProfileService} from "../../core/services/profile.service";
import {Role} from "../../core/models/role";
import {SecurityService} from "../../core/services/security.service";
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

  constructor(
    private route: ActivatedRoute,
    private profileService: ProfileService,
    private securityService: SecurityService
  ) {
  }

  ngOnInit(): void {
    this.id = Number(this.route.snapshot.paramMap.get('userId'));
    if (this.id) this.isOwn = false;
    else  {
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
    });
  }
}
