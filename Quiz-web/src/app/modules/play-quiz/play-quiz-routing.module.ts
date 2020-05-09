import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {AuthGuardService as AuthGuard} from "../core/services/auth-guard.service";
import {SettingsComponent} from "./settings/settings.component";
// import {WaitingRoomComponent} from "./game/waiting-room/waiting-room.component";
// import {PlayQuestionComponent} from "./game/play-question/play-question.component";
// import {GameComponent} from "./game/game.component";
// import {ViewAnnouncementComponent} from "../announcement/view-announcement/view-announcement.component";


const routes: Routes = [
  {path: 'quiz/:quizId/gameSettings', component: SettingsComponent, canActivate: [AuthGuard]},
  // {path: 'quiz/:quizId/game/:gameId/play', component: GameComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PlayQuizRoutingModule {
}
