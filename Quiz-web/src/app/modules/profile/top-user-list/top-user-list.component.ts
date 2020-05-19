import {Component, HostListener, OnInit} from '@angular/core';
import {Role} from "../../core/models/role";
import {SecurityService} from "../../core/services/security.service";
import {ProfileService} from "../../core/services/profile.service";
import {User} from "../../core/models/user";
import {TranslateService} from "@ngx-translate/core";
import {DashboardService} from "../../core/services/dashboard.service";

@Component({
  selector: 'app-topuserlist',
  templateUrl: './top-user-list.component.html',
  styleUrls: ['./top-user-list.component.css']
})
export class TopUserListComponent implements OnInit {

  users: User[] = [];

  constructor(
    private dashboardService: DashboardService){
  }

  getUsers() {
    this.dashboardService.getTopUsers().subscribe(users => {
        this.users = users;
      },
      err => {
        console.log(err);
      });
  }

  ngOnInit(): void {
    this.getUsers()
  }

}
