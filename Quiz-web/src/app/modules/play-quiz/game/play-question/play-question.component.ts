import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Question} from '../../../core/models/question';
import {Users, UserDto} from "../../../core/models/gameResults";
import {Sort} from "@angular/material/sort";

import {timer} from 'rxjs';
import {Answer} from "../../../core/models/answer";

@Component({
  selector: 'app-play-question',
  templateUrl: './play-question.component.html',
  styleUrls: ['./play-question.component.css']
})
export class PlayQuestionComponent implements OnInit {
  public isSend: boolean = false;

  public subscribeTimer: number;
  public timeLeft: number = 15;

  @Input()
  question: Question;

  @Output()
  public sendAnswer: EventEmitter<any> = new EventEmitter();

  answer: Answer = new Answer();

  map = new Map();
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
   /* this.answer = {
      userId: 2,
      result: '',
      time: this.timeLeft
    };*/
  }

  nextQuestion() {
    if (this.question.type == 'SELECT_SEQUENCE') {
      for (let key of this.map.keys()) {
        this.answer.answer += key.toString() + '-' + this.map.get(key).toString() + ' ';
      }
      this.answer.answer.trim();
    }

    if (this.question.type == 'ENTER_ANSWER') {
      this.answer.answer = this.answerText;
    }

    this.answer.questionId=this.question.id;
    this.answer.answer = this.question.type.toString() + ':' + this.answer.answer;
    this.answer.time = this.subscribeTimer;
    this.isSend = true;

    this.sendAnswer.emit(this.answer)
  }

  setOption(value: string) {
    this.answer.answer = value;
  }

  setSequence(opId: number, seqId: number) {
    if (this.map.has(opId)) {
      this.map.delete(opId);
    }
    this.map.set(opId, seqId);
  }


}
