import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AnnouncementService} from "../../core/services/announcement.service";
import {Announcement} from "../../core/models/announcement";

@Component({
  selector: 'app-view-announcement',
  templateUrl: './view-announcement.component.html',
  styleUrls: ['./view-announcement.component.css']
})
export class ViewAnnouncementComponent implements OnInit {

  announcement: Announcement;

  constructor(
    private announcementService: AnnouncementService,
    private route: ActivatedRoute,
    private redirect: Router) {
    const id = this.route.snapshot.paramMap.get('announcementId');
    console.log(id);
    if (id) {
      this.announcementService.getById(id).subscribe(
        data => {
          this.announcement = data;
        }, err => {
          console.log(err);
          redirect.navigate(['home']);
        });
    } else {
      redirect.navigate(['home']);
    }

  }

  ngOnInit(): void {
  }


}
