import {Component, HostListener, OnInit} from '@angular/core';
import {Role} from "../../core/models/role";
import {SecurityService} from "../../core/services/security.service";
import {ProfileService} from "../../core/services/profile.service";
import {User} from "../../core/models/user";

@Component({
  selector: 'app-userlist',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  filter:string;
  users: User[] = [];
  isWaiting: boolean = false;
  role: Role;
  allUsers: boolean;
  private keyUpTimeout: any;

  constructor(private profileService: ProfileService,
              private securityService: SecurityService) {
    this.role = this.securityService.getCurrentRole();
  }

  @HostListener("window:scroll", ["$event"])
  onWindowScroll() {
    if (document.documentElement.scrollHeight - document.documentElement.scrollTop -
      document.documentElement.clientHeight < 40) {
      this.getNew();
    }
  }

  getNew(): void {
    if (this.isWaiting) {
      return;
    }
    this.isWaiting = true;
    this.allUsers =  (this.role != Role.USER);
    this.profileService.getUsers(this.users.length, this.allUsers)
      .subscribe(
        users => {
          if (users.length == 0) {
            return;
          }
          this.isWaiting = false;
          this.users = this.users.concat(users);
        },
        err => {
          console.log(err);
        })

  }

  ngOnInit(): void {
    this.getNew();
  }


  onKeyUp(event: Event) {
    event.stopPropagation();
    clearTimeout(this.keyUpTimeout);
    this.keyUpTimeout = setTimeout(() => {
      //this.profileService.getUsers()
    }, 450);
  }

  onChange(event: Event) {
    clearTimeout(this.keyUpTimeout);
    event.stopPropagation();
   // this.change.emit(this.model.filterValue)
  }
}
