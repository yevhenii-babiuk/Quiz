import {Component, OnInit} from '@angular/core';
import {ProfileService} from "../../core/services/profile.service";
import {Role} from "../../core/models/role";
import {Router} from "@angular/router";
import {SecurityService} from "../../core/services/security.service";
import {AuthenticationService} from "../../core/services/authentication.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  id: number;
  role: Role;


  constructor(
    private profileService: ProfileService,
    private securityService: SecurityService,
    private redirect: Router,
    public authService: AuthenticationService
  ) {
    this.role = this.securityService.getCurrentRole();
  }

  ngOnInit(): void {
  }

  search(event: any) {
    this.redirect.navigate(['Quizzes?quizName=' + event.target.value]);
  }
  logout(){
    this.authService.logOut();
    this.redirect.navigate(['home']);
  }
}
