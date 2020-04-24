import {Component, Input, OnInit} from '@angular/core';
import {User} from "../../../models/user";
import {ProfileService} from "../../core/services/profile.service";
import {Role} from "../../../models/role";

@Component({
  selector: 'app-sidenav',
  templateUrl: 'sidenav.component.html',
  styleUrls: ['./sidenav.component.css']
})
export class SidenavComponent implements OnInit {
  role: String;
  isUser: boolean;
  isAdmin: boolean;
  notProfile: boolean;

  constructor(
    private profileService: ProfileService
  ) {

  }

  ngOnInit(): void {
    this.setCondition(null);
    //this.getUser();

  }

  private getUser() {
    this.profileService.getUser().subscribe(data => {
      this.setCondition(data.role);
    });
  }

  private setCondition(role: String) {
    if (role == null) {
      this.notProfile = true;
    } else if (role == Role.USER) {
      this.isUser = true;
    } else if (role == Role.ADMIN || role == Role.MODERATOR || role == Role.SUPER_ADMIN) {
      this.isAdmin = true;
    }
  }


}
