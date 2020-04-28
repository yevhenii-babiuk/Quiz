import {Component, OnInit} from '@angular/core';
import {Announcement} from "../../core/models/announcement";
import {ActivatedRoute, Router} from "@angular/router";
import {AnnouncementService} from "../../core/services/announcement.service";
import {Imaged} from "../../core/models/imaged";
import {ImageSnippet} from "../../core/models/imageSnippet";

@Component({
  selector: 'app-update-announcement',
  templateUrl: './update-announcement.component.html',
  styleUrls: ['../../quiz/update-quiz/update-quiz.component.html']
})
export class UpdateAnnouncementComponent implements OnInit {

  announcement: Announcement;

  constructor(private announcementService: AnnouncementService,
              private route: ActivatedRoute) {
    const id = this.route.snapshot.paramMap.get('announcementId');
    console.log(id);
    if (id) {
      this.announcementService.getById(id).subscribe(
        data => {
          this.announcement = data;
        }, err => {
          console.log(err);
          this.announcement = new Announcement();
        });
    } else {
      this.announcement = new Announcement();
    }

  }

  ngOnInit(): void {
  }

  processFile(imageInput: any, imaged: Imaged) {
    const file: File = imageInput.files[0];
    const reader = new FileReader();

    reader.addEventListener('load', (event: any) => {
      console.log("in reader.addEventListener (announcement)")
      imaged.selectedFile = new ImageSnippet(event.target.result);
      this.announcementService.putImage(file).subscribe(
        id => {
          console.log("id = " + id);
          if (typeof id === "number") {
            imaged.imageId = id;
          }
        },
        error => {
          console.log(error);
        });
    });
    reader.readAsDataURL(file);
  }

  send() {
    if (this.announcement.id) {
      this.announcementService.updateAnnouncement(this.announcement).subscribe(
        get => {
          console.log("id = " + get);
        },
        error => {
          console.log(error);
        });
    } else {
      this.announcement.isPublished = false;
      this.announcementService.sendAnnouncement(this.announcement).subscribe(
        get => {
          console.log("id = " + get);
        },
        error => {
          console.log(error);
        });
    }
  }
}
