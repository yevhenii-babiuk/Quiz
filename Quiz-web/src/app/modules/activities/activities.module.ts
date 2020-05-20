import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


import {RouterModule} from "@angular/router";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ViewActivitiesComponent} from "./view-activities/view-activities.component";
import {ActivitiesRoutingModule} from "./activities-routing.module";
import {TranslateModule} from "@ngx-translate/core";

@NgModule({
  declarations: [
    ViewActivitiesComponent
    ],
  imports: [
    CommonModule,
    ActivitiesRoutingModule,
    RouterModule,
    FormsModule,
    ReactiveFormsModule,
    TranslateModule
  ]
})
export class ActivitiesModule { }
