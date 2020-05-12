import {AchievementsListComponent} from "./achievement-list/achievements-list.component";

import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {CardsModule, CarouselModule} from "angular-bootstrap-md";
import {CreateAchievementComponent} from "./create-achievement/create-achievement.component";
import {FormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    AchievementsListComponent,
    CreateAchievementComponent
  ],
  exports: [
    AchievementsListComponent
  ],
  imports: [
    CommonModule,
    CarouselModule,
    CardsModule,
    FormsModule
  ]
})
export class AchievementModule {
}
