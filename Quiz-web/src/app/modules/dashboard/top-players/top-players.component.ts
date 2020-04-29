import {Component, OnInit} from '@angular/core';
import {DashboardService} from "../../core/services/dashboard.service";
import {User} from "../../core/models/user";

@Component({
  selector: 'app-top-players',
  templateUrl: './top-players.component.html',
  styleUrls: ['./top-players.component.css']
})
export class TopPlayersComponent implements OnInit {

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
