import {Component, OnInit} from '@angular/core';
import {QuizzesService} from "../../core/services/quizzes.service";
import {Quiz} from "../../core/models/quiz";
import {ActivatedRoute, Route, Router} from "@angular/router";


@Component({
  selector: 'app-create-quiz',
  templateUrl: './view-quiz.component.html',
  styleUrls: ['view-quiz.component.scss']
})
export class ViewQuizComponent implements OnInit {

  quiz: Quiz;

  constructor(
    private quizzesService: QuizzesService,
    private route: ActivatedRoute,
    private redirect: Router) {
    const id = this.route.snapshot.paramMap.get('quizId');
    console.log(id);
    if (id) {
      this.quizzesService.getById(id).subscribe(
        data => {
          this.quiz = data;
        }, err => {
          console.log(err);
          redirect.navigate(['quizzes']);
        });
    } else {

    }

  }

  ngOnInit(): void {
  }




}
