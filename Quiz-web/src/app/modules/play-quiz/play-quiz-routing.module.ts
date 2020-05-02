import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {AuthGuardService as AuthGuard} from "../core/services/auth-guard.service";
import {SettingsComponent} from "./settings/settings.component";
import {WaitingRoomComponent} from "./waiting-room/waiting-room.component";


const routes: Routes = [
  {path: 'settings', component: SettingsComponent},
 // {path: 'waitingRoom', component: WaitingRoomComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PlayQuizRoutingModule {
}
