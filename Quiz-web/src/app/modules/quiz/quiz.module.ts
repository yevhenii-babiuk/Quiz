import 'zone.js/dist/zone';
import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule} from '@angular/router';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatFormFieldModule} from '@angular/material/form-field';
import {SliderModule} from 'primeng/slider';

import {QuizzesComponent} from './quizzes/quizzes.component';
import {UpdateQuizComponent} from './update-quiz/update-quiz.component';
import {ViewQuizComponent} from './view-quiz/view-quiz.component';
import {QuizRoutingModule} from './quiz-routing.module';
import {MatChipsModule} from "@angular/material/chips";
import {MatIconModule} from "@angular/material/icon";
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import {MatInputModule} from "@angular/material/input";
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ChipsAutocompleteExample} from './update-quiz/app/chips-autocomplete-example';



import {CheckboxFilterComponent} from './quizzes/vertical-filter-bar/checkbox-filter/checkbox-filter.component';
import {KeywordFilterComponent} from './quizzes/vertical-filter-bar/keyword-filter/keyword-filter.component';
import {DateFilterComponent} from './quizzes/vertical-filter-bar/date-filter/date-filter.component';
/*
import {DateFilterComponent} from './quizzes/vertical-filter-bar/date-filter/date-filter.component';
*/
import {VerticalFilterBarComponent} from './quizzes/vertical-filter-bar/vertical-filter-bar.component';
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatNativeDateModule} from '@angular/material/core';

@NgModule({
  declarations: [
    QuizzesComponent,
    UpdateQuizComponent,
    ViewQuizComponent,
    ChipsAutocompleteExample,
    CheckboxFilterComponent,
    KeywordFilterComponent,
    DateFilterComponent,
/*    DateFilterComponent,*/
    VerticalFilterBarComponent,
  ],
  imports: [
    CommonModule,
    QuizRoutingModule,
    RouterModule,
    FormsModule,
    ReactiveFormsModule,
    MatChipsModule,
    MatIconModule,
    MatAutocompleteModule,
    MatInputModule,
    BrowserAnimationsModule,
    MatFormFieldModule,
    SliderModule,
    MatDatepickerModule,
    MatNativeDateModule,
  ]
})
export class QuizModule {
}
