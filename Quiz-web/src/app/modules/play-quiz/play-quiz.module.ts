import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SettingsComponent} from './settings/settings.component';
import {RouterModule} from "@angular/router";
import {FormsModule} from "@angular/forms";
import {PlayQuizRoutingModule} from "./play-quiz-routing.module";
import { WaitingRoomComponent } from './waiting-room/waiting-room.component';
import { PlayQuestionComponent } from './play-question/play-question.component';


@NgModule({
  declarations: [
    SettingsComponent,
    WaitingRoomComponent,
    PlayQuestionComponent
  ],
  imports: [
    CommonModule,
    PlayQuizRoutingModule,
    RouterModule,
    FormsModule
  ]
})
export class PlayQuizModule {
}
