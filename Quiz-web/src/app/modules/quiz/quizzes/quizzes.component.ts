import {Component, OnInit, HostListener} from '@angular/core';
import {QuizzesService} from "../../core/services/quizzes.service";
import {Quiz} from "../../core/models/quiz";
import {Tag} from "../../core/models/tag";
import {KeywordFilter} from "./vertical-filter-bar/keyword-filter/keyword-filter.model";
import {CheckboxFilter} from "./vertical-filter-bar/checkbox-filter/checkbox-filter.model";
import {FBFilter} from "./vertical-filter-bar/fb-filter.interface";
import {ActivatedRoute, Router} from "@angular/router";
import {DateFilter} from "./vertical-filter-bar/date-filter/date-filter.model";
import {Category} from "../../core/models/category";
/*import {DateFilter} from "./vertical-filter-bar/date-filter/date-filter.model";*/

@Component({
  selector: 'app-quizzes',
  templateUrl: './quizzes.component.html'
})
export class QuizzesComponent implements OnInit {
  quizzes: Quiz[] = [new Quiz(), new Quiz(), new Quiz(), new Quiz(), new Quiz()];
  tags: string[] = [];
  categories: string[] = [];
  isWaiting: boolean = false;

  params: string;


  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private quizzesService: QuizzesService
  ) {
  }

  public manufacturers: string[] = ['Apple', 'Dell', 'Acer', 'HP', 'Lenovo', 'Asus', 'Alienware', 'Cooler Master', 'Fujitsu', 'IBM', 'LG Eletronics', 'MSI', 'Nvidia', 'Panasonic', 'Samsung']
  public conditions: string[] = ['New', 'Refurbished', 'Used']
  public displaySizes: string[] = ['17 Inches and Above', '16 to 16.9 Inches', '15 to 15.9 Inches', '14 to 14.9 Inches', '13 to 13.9 Inches', '12 to 12.9 Inches', '12 Inches and Under']
  public ramSize: string[] = ['32GB and Above', '16GB', '12GB', '8GB', '6GB', '4GB', '3GB', '2GB and Under']

  verticalBarFilters: FBFilter[] = [
    new KeywordFilter('quizName', 'Quiz name'),
    new KeywordFilter('authorName', 'Author name'),
    new CheckboxFilter('category', 'Categories', this.categories),
    /*new RangeFilter('price', 'Price', 0, 10000, true),*/
    new DateFilter("Date", "date",  new Date(2020,3,20), new Date()),
    new CheckboxFilter('tag', 'Tags', this.tags),
  ]


  @HostListener("window:scroll", ["$event"])
  onWindowScroll() {

    if (document.documentElement.scrollHeight - document.documentElement.scrollTop -
      document.documentElement.clientHeight < 40) {
      this.getQuizzes();
    }

  }

  getQuizzes(): void {
    if (this.isWaiting){
      return;
    }
    this.isWaiting=true;
    this.quizzesService.getQuizzes(this.params, this.quizzes.length)
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

  getTags(): void {
    this.quizzesService.getTags()
      .subscribe(
        tags => {
          tags.forEach(function (value) {
            console.log(value.name);
            this.push(value.name);
          }, this.tags);

        },
        err => {
          console.log(err);
        })
  }
  getCategories(): void {
    this.quizzesService.getCategories()
      .subscribe(
        category => {
          category.forEach(function (value) {
            console.log(value.name);
            this.push(value.name);
          }, this.categories);

        },
        err => {
          console.log(err);
        })
  }

  ngOnInit(): void {
    console.log("here1");
    this.route.queryParams.subscribe(params => {
      console.log(this.router.url.substring(this.router.url.indexOf("?")+1));
      this.quizzes=[];
      this.params="?";
      if (this.router.url.indexOf("?"))
        this.params+=this.router.url.substring(this.router.url.indexOf("?")+1);
      this.getQuizzes();

    });

    this.getTags();
    this.getCategories();
  }

}
