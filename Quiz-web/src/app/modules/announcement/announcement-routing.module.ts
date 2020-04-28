import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ViewAnnouncementComponent} from "./view-announcement/view-announcement.component";
import {UpdateAnnouncementComponent} from "./update-announcement/update-announcement.component";
import {AnnouncementsComponent} from "./announcements/announcements.component";


const routes: Routes = [
  {path: 'announcement/:announcementId', component: ViewAnnouncementComponent},
  {path: 'announcement/:announcementId/edit', component: UpdateAnnouncementComponent},
  {path: 'createAnnouncement', component: UpdateAnnouncementComponent},
  {path: 'announcements', component: AnnouncementsComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AnnouncementRoutingModule { }
