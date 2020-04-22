import {Component, OnInit} from '@angular/core';
import {Category} from "../../core/models/category";
import {QuizzesService} from "../../core/services/quizzes.service";
import {ImageSnippet} from "../../core/models/imageSnippet"
import {Question} from "../../core/models/question";
import {Quiz} from "../../core/models/quiz";
import {Imaged} from "../../core/models/imaged";
import {QuestionType} from "../../core/models/questionType";
import {QuestionOptions} from "../../core/models/questionOptions";
import {Tag} from "../../core/models/tag";
import {ActivatedRoute} from "@angular/router";


@Component({
  selector: 'app-create-quiz',
  templateUrl: './update-quiz.component.html',
  styleUrls: ['update-quiz.component.scss']
})
export class UpdateQuizComponent implements OnInit {
  categories: Category[];
  quiz: Quiz;

  constructor(
    private quizzesService: QuizzesService,
    private route: ActivatedRoute) {
    this.getCategories();
    const id = this.route.snapshot.paramMap.get('quizId');
    console.log(id);
    if (id) {
      this.quizzesService.getById(id).subscribe(
        data => {
          this.quiz = data;
          this.setSelectOnQuiz();
        }, err => {
          console.log(err);
          this.createNewQuiz();
        });
    } else {
      this.createNewQuiz();
    }

  }

  getCategories() {
    this.quizzesService.getCategories().subscribe(categories => {
        this.categories = categories;
      },
      err => {
        console.log(err);
      });
  }

  createNewQuiz() {
    this.quiz = new Quiz();
    this.setOptions(this.quiz.questions[0]);
  }

  setSelectOnQuiz() {
    for (let i = 0; i < this.quiz.questions.length; i++) {
      console.log("b == "+(this.quiz.questions[i].type==QuestionType.TRUE_FALSE));

      if(this.quiz.questions[i].type==QuestionType.SELECT_OPTION){
        this.quiz.questions[i].type = 0;
      }else if (this.quiz.questions[i].type==QuestionType.SELECT_SEQUENCE){
        this.quiz.questions[i].type = 1;
      }else if(this.quiz.questions[i].type==QuestionType.TRUE_FALSE){
        this.quiz.questions[i].type = 2;
      }else if(this.quiz.questions[i].type==QuestionType.ENTER_ANSWER){
        this.quiz.questions[i].type = 3;
      }


      console.log("a"+this.quiz.questions[i].type);
    }
    //console.log("on setSelectOnQuiz");
    // (<HTMLInputElement>document.getElementById("categories")).value=this.quiz.category.name;
  }


  ngOnInit(): void {
  }

  processFile(imageInput: any, imaged: Imaged) {
    const file: File = imageInput.files[0];
    const reader = new FileReader();

    reader.addEventListener('load', (event: any) => {
      console.log("in reader.addEventListener")
      imaged.selectedFile = new ImageSnippet(event.target.result);
      this.quizzesService.putImage(file).subscribe(
        id => {
          console.log("id=" + id);
          if (typeof id === "number") {
            imaged.imageId = id;
          }
        },
        error => {
          console.log(error);
        });
    });

    reader.readAsDataURL(file);
  }

  setQuestionOptions(question: Question, count: number) {
    question.options = [];
    for (let i = 0; i < count; i++) {
      question.options.push(new QuestionOptions(i + 1));
    }
  }

  setCorrectAnswer(question: Question, value: boolean) {
    question.options[0].isCorrect = value
  }

  setCategory(categoryStr: string) {
    console.log("on change ctegory");
    this.quiz.category = this.categories.find((item) => item.name == categoryStr);
    console.log(this.quiz.category.name);
  }

  setOptions(question: Question) {
    if (question.type == QuestionType.SELECT_OPTION || question.type == QuestionType.SELECT_SEQUENCE) {
      this.setQuestionOptions(question, 4);
    } else {
      this.setQuestionOptions(question, 1);
    }
    if (question.type == QuestionType.SELECT_OPTION) {
      question.options[0].isCorrect = true;
    }
  }

  onChangeTypeQuestion(question: Question, str: string) {
    question.type = parseInt(str);
    console.log("changed");
    this.setOptions(question);
  }

  addQuestion() {
    let question = new Question(this.quiz.questions[this.quiz.questions.length - 1].id + 1);
    this.setOptions(question);
    this.quiz.questions = this.quiz.questions.concat(question);
  }

  setTags() {
    let str = (<HTMLInputElement>document.getElementById("tags")).value.split(" ");
    this.quiz.tags = [];
    for (let i = 0; i < str.length; i++) {
      this.quiz.tags.push(new Tag(str[i]));
      console.log("Tag" + i + ": " + str[i]);
    }
  }

  send() {
    this.quizzesService.sendQuiz(this.quiz).subscribe(
      get => {
        console.log("id=" + get);
      },
      error => {
        console.log(error);
      });
  }

}
