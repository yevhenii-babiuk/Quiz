import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import { RouterModule } from '@angular/router';

import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {AuthenticationModule} from './modules/authentication/authentication.module';
import {CoreModule} from './modules/core/core.module';
import {SharedModule} from './modules/shared/shared.module';
import {AuthRoutingModule} from './modules/authentication/auth-routing.module';
import {PagesRoutingModule} from './modules/core/pages/pages-routing.module';
import {QuizRoutingModule} from './modules/quiz/quiz-routing.module'
import {QuizModule} from './modules/quiz/quiz.module';
import {ProfileModule} from './modules/profile/profile.module';
import {ProfileRoutingModule} from './modules/profile/profile-routing.module';
import {AnnouncementModule} from "./modules/announcement/announcement.module";
import {AnnouncementRoutingModule} from "./modules/announcement/announcement-routing.module";
@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    RouterModule,
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    AuthenticationModule,
    CoreModule,
    SharedModule,
    AuthRoutingModule,
    PagesRoutingModule,
    QuizRoutingModule,
    QuizModule,
    ProfileRoutingModule,
    ProfileModule
    QuizModule,
    AnnouncementModule,
    AnnouncementRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})

export class AppModule {
}
