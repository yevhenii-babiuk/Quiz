import {Component, OnInit} from '@angular/core';
import {QuizzesService} from "../../core/services/quizzes.service";
import {Quiz} from "../../core/models/quiz";
import {ActivatedRoute, Router} from "@angular/router";
import {SecurityService} from "../../core/services/security.service";
import {COLORS} from '../../../../environments/environment.prod';
import {TranslateService} from '@ngx-translate/core';
import {QuizStatus} from "../../core/models/quizStatus";


@Component({
  selector: 'app-create-quiz',
  templateUrl: './view-quiz.component.html',
  styleUrls: ['view-quiz.component.scss']
})
export class ViewQuizComponent implements OnInit {

  quiz: Quiz;
  COLORS: any = COLORS;

  constructor(
    public securityService: SecurityService,
    private quizzesService: QuizzesService,
    private route: ActivatedRoute,
    public translate: TranslateService,
    private redirect: Router) {
    console.log(this.securityService.getCurrentId())
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

  isActivated():boolean{
    return this.quiz.status==QuizStatus.ACTIVATED;
  }

  isDeactivated():boolean{
    return this.quiz.status==QuizStatus.DEACTIVATED;
  }

  setActivated(){
    this.setStatus(QuizStatus.ACTIVATED)
  }

  setDeactivated(){
    this.setStatus(QuizStatus.DEACTIVATED)
  }

  setStatus(status: QuizStatus){
    this.quiz.status=status;
    this.quizzesService.updateQuizStatus(this.quiz).subscribe();
  }


}
