import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';


import {LoginComponent} from './login/login.component';
import {RegistrationComponent} from './registration/registration.component';
import {PassRecoveryComponent} from './pass-recovery/pass-recovery.component';
import {MailConfirmComponent} from './registration/mail-confirm/mail-confirm.component';
import {PassRecoveryConfirmComponent} from './pass-recovery/pass-recovery-confirm/pass-recovery-confirm.component';
import {HomeComponent} from "./home/home.component";
import {QuizzesComponent} from "./quizzes/quizzes.component";

const routes: Routes = [
  {path: "", pathMatch: "full", redirectTo: "home"},
  {path: "home", component: HomeComponent},
  {path: 'api/v1/quizzes', component: QuizzesComponent},
  {path: 'api/v1/login', component: LoginComponent},
  {path: 'api/v1/registration', component: RegistrationComponent},
  {path: 'api/v1/pass-recovery', component: PassRecoveryComponent},
  {path: 'api/v1/registration/:token', component: MailConfirmComponent},
  {path: 'api/v1/pass-recovery/:token', component: PassRecoveryConfirmComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
