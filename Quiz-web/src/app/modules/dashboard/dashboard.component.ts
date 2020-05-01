import { Component, OnInit } from '@angular/core';
import {Role} from "../core/models/role";
import {SecurityService} from "../core/services/security.service";

@Component({
  selector: 'app-dashboard',
  templateUrl:'./dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  roleEnum= Role;
  role: Role;

  constructor(private securityService : SecurityService) { }

  ngOnInit(): void {
    this.role = this.securityService.getCurrentRole();
  }

}
