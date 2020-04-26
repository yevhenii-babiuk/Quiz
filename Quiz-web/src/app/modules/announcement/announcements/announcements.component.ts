import {Component, HostListener, OnInit} from '@angular/core';
import {Announcement} from "../../core/models/announcement";
import {AnnouncementService} from "../../core/services/announcement.service";

@Component({
  selector: 'app-announcements',
  templateUrl: './announcements.component.html',
  styleUrls: ['./announcements.component.css']
})

export class AnnouncementsComponent implements OnInit {
  announcements: Announcement[] = [];
  isWaiting: boolean = false;


  constructor(private announcementService: AnnouncementService,) {
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
    this.announcementService.getAnnouncements(this.announcements.length)
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
