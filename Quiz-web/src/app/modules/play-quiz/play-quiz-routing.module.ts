import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {AuthGuardService as AuthGuard} from "../core/services/auth-guard.service";
import {SettingsComponent} from "./settings/settings.component";
import {WaitingRoomComponent} from "./waiting-room/waiting-room.component";
import {PlayQuestionComponent} from "./play-question/play-question.component";


const routes: Routes = [
  {path: 'quiz/:quizId/game/:gameId/settings', component: SettingsComponent},
  {path: 'quiz/:quizId/game/:gameId/waitingRoom', component: WaitingRoomComponent},
  {path: 'quiz/:quizId/game/:gameId/play', component: PlayQuestionComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PlayQuizRoutingModule {
}
