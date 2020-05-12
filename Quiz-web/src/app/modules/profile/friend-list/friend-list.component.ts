import {Component, OnInit} from '@angular/core';
import {User} from "../../core/models/user";
import {ProfileService} from "../../core/services/profile.service";
import {SecurityService} from "../../core/services/security.service";

@Component({
  selector: 'app-friend-list',
  templateUrl: './friend-list.component.html',
  styleUrls: ['../user-list/user-list.component.css']
})
export class FriendListComponent implements OnInit {

  users: User[] = [];
  allUsers: boolean;
  private keyUpTimeout: any;
  id: number;

  constructor(private profileService: ProfileService,
              private securityService: SecurityService) {
  }


  ngOnInit(): void {
    this.id = this.securityService.getCurrentId();
    this.profileService.getFriends(this.id)
      .subscribe(
        users => {
          this.users = users;
        },
        err => {
          console.log(err);
        })
  }

}
