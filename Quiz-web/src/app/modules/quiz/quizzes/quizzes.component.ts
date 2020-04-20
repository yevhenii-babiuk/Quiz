import {Component, OnInit, HostListener} from '@angular/core';
import {QuizzesService} from "../../core/services/quizzes.service";
import {Quiz} from "../../../models/quiz";

@Component({
  selector: 'app-quizzes',
  templateUrl: './quizzes.component.html'
})
export class QuizzesComponent implements OnInit {
  quizzes: Quiz[] = [];
  isWaiting: boolean = false;


  constructor(
    private quizzesService: QuizzesService,
  ) {
  }

  @HostListener("window:scroll", ["$event"])
  onWindowScroll() {

    if (document.documentElement.scrollHeight - document.documentElement.scrollTop -
      document.documentElement.clientHeight < 40) {
      this.getNew();
    }

  }

  getNew(): void {
    if (this.isWaiting){
      return;
    }
    this.isWaiting=true;
    this.quizzesService.getQuizzes(this.quizzes.length)
      .subscribe(
        quizzes => {
          if (quizzes.length == 0) {
            return;
          }
          this.isWaiting=false;
          this.quizzes = this.quizzes.concat(quizzes);
        },
        err => {
          console.log(err);
        })

  }

  ngOnInit(): void {
    this.getNew();
  }

}
