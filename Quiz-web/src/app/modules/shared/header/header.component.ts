import {Component, OnInit} from '@angular/core';
import {ProfileService} from "../../core/services/profile.service";
import {Role} from "../../core/models/role";
import {Router} from "@angular/router";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  isProfile: boolean;
  notProfile: boolean;
  constructor(
    private profileService: ProfileService,private redirect: Router
  ) {

  }

  ngOnInit(): void {
    this.setCondition(null);
   // this.getUser();
  }

  private getUser() {
    this.profileService.getUser().subscribe(data => {
      this.setCondition(data.role);
    });
  }

  private setCondition(role: String) {
    if (role == null) {
      this.notProfile = true;
    } else if (role == Role.USER || role == Role.ADMIN|| role == Role.MODERATOR || role == Role.SUPER_ADMIN) {
      this.isProfile = true;
    }
  }


  search(event:any) {
    this.redirect.navigate(['quizzes?quizName=*'+event.target.value+'*']);
  }
}
