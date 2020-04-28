import {Component, OnInit} from '@angular/core';
import {ProfileService} from "../../core/services/profile.service";
import {Role} from "../../core/models/role";
import {Router} from "@angular/router";
import {IdService} from "../../core/services/id.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  id: number;
  isProfile: boolean;
  notProfile: boolean;

  constructor(
    private profileService: ProfileService,
    private idService: IdService,
    private redirect: Router
  ) {

  }

  ngOnInit(): void {
    this.setCondition(null);
    // this.getUser();
  }

  private getUser() {
    this.id = this.idService.getCurrentId();
    this.profileService.getUser(this.id).subscribe(data => {
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
