import {Component, HostListener, OnInit} from '@angular/core';
import {Announcement} from "../../core/models/announcement";
import {AnnouncementService} from "../../core/services/announcement.service";
import {SecurityService} from "../../core/services/security.service";
import {Role} from "../../core/models/role";

@Component({
  selector: 'app-announcements',
  templateUrl: './announcements.component.html',
  styleUrls: ['./announcements.component.css']
})

export class AnnouncementsComponent implements OnInit {
  announcements: Announcement[] = [];
  isWaiting: boolean = false;
  role: Role;
  isPublished:boolean;

  constructor(private announcementService: AnnouncementService,
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
    this.isPublished = !(this.role && this.role != Role.USER);
    this.announcementService.getAnnouncementsByRole(this.announcements.length, this.isPublished)
      .subscribe(
        announcements => {
          if (announcements.length == 0) {
            return;
          }
          this.isWaiting = false;
          this.announcements = this.announcements.concat(announcements);
        },
        err => {
          console.log(err);
        })

  }

  ngOnInit(): void {
    this.getNew();
  }

}
