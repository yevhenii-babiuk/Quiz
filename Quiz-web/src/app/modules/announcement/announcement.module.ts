import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AnnouncementRoutingModule } from './announcement-routing.module';
import { ViewAnnouncementComponent } from './view-announcement/view-announcement.component';
import {RouterModule} from "@angular/router";
import {FormsModule} from "@angular/forms";
import { UpdateAnnouncementComponent } from './update-announcement/update-announcement.component';
import { AnnouncementsComponent } from './announcements/announcements.component';


@NgModule({
  declarations: [ViewAnnouncementComponent, UpdateAnnouncementComponent, AnnouncementsComponent],
  imports: [
    CommonModule,
    AnnouncementRoutingModule,
    RouterModule,
    FormsModule
  ]
})
export class AnnouncementModule { }
