import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Question} from '../../../core/models/question';

import {timer} from 'rxjs';
import {Answer} from "../../../core/models/answer";
import {QuestionType} from "../../../core/models/questionType";
import {QuestionOptions} from "../../../core/models/questionOptions";

@Component({
  selector: 'app-play-question',
  templateUrl: './play-question.component.html',
  styleUrls: ['./play-question.component.css']
})
export class PlayQuestionComponent implements OnInit {
  public isSend: boolean = false;

  public subscribeTimer: number;

  @Input()
  timeLeft: number;

  @Input()
  question: Question;

  @Output()
  public sendAnswer: EventEmitter<any> = new EventEmitter();

  answer: Answer = new Answer();

  answerText = '';

  constructor() {

    timer(1000, 1000).subscribe(val => {
      if (this.subscribeTimer != 0) {
        this.subscribeTimer = this.timeLeft - val;
      } else {
        if (!this.isSend) {
          this.nextQuestion();
          this.isSend = true;
        }
      }
    });
  }

  ngOnInit(): void {
    let key = 'endTime' + this.question.id;

    if (!localStorage.getItem(key)) {
      localStorage.setItem(key, '' + (new Date().getTime() + this.timeLeft * 1000));
    } else {
      this.timeLeft = Math.round((+localStorage.getItem(key) - new Date().getTime()) / 1000);
    }
  }

  nextQuestion() {
    if (this.question.type == 'ENTER_ANSWER') {
      this.answer.fullAnswer = this.answerText;
    }

    this.answer.questionId = this.question.id;
    this.isSend = true;

    console.log(this.answer);

    this.sendAnswer.emit(this.answer)
  }

  setOption(optId: number) {
    if (this.answer.options.indexOf(optId) == -1) {
      this.answer.options.push(optId);
    } else {
      this.answer.options.splice(this.answer.options.indexOf(optId), 1);
    }

    console.log(this.answer.options);
  }

  setTrueFalseOption(value: boolean) {
    this.answer.trueFalse = value;
  }

  setSequence(opId: number, seqId: number) {
    if (this.answer.sequence.has(opId)) {
      this.answer.sequence.delete(opId);
    }
    this.answer.sequence.set(opId, seqId);
  }

}
