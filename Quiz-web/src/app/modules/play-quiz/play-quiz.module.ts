import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SettingsComponent} from './settings/settings.component';
import {RouterModule} from "@angular/router";
import {FormsModule} from "@angular/forms";
import {PlayQuizRoutingModule} from "./play-quiz-routing.module";
import { WaitingRoomComponent } from './waiting-room/waiting-room.component';
import { PlayQuestionComponent } from './play-question/play-question.component';
import {MatSortModule} from "@angular/material/sort";
import {TimerPipe} from "./play-question/timer-pipe";


@NgModule({
  declarations: [
    SettingsComponent,
    WaitingRoomComponent,
    PlayQuestionComponent,
    TimerPipe
  ],
    imports: [
        CommonModule,
        PlayQuizRoutingModule,
        RouterModule,
        FormsModule,
        MatSortModule
    ]
})
export class PlayQuizModule {
}
