import { Component, OnInit } from '@angular/core';
import {DashboardService} from "../../core/services/dashboard.service";
import {AdminStatistics} from "../../core/models/adminStatistics";

@Component({
  selector: 'app-quizzes-status',
  templateUrl: './quizzes-status.component.html',
  styleUrls: ['./quizzes-status.component.css']
})
export class QuizzesStatusComponent implements OnInit {

  stat: AdminStatistics[] = [];
  data: any;

  getStatus() {
    this.dashboardService.getByStatus().subscribe(statistics => {
        this.stat = statistics;
        this.plotGraph();
      },
      err => {
        console.log(err);
      });
  }

  plotGraph() {

    let label:Array<Date> = [];
    let created:Array<number> = [];
    let published:Array<number> = [];

    this.stat.forEach(element=>label.push(element.date))
    this.stat.forEach(element=>created.push(element.createdCount))
    this.stat.forEach(element=>published.push(element.publishedCount))

    this.data = {
      labels: label,
      datasets: [
        {
          label: 'Created quizzes',
          backgroundColor: '#42A5F5',
          borderColor: '#1E88E5',
          data: created
        },
        {
          label: 'Published quizzes',
          backgroundColor: '#9CCC65',
          borderColor: '#7CB342',
          data: published
        }
      ]
    }
  }

  constructor(private dashboardService: DashboardService) {
  }

  ngOnInit(): void {
    this.getStatus();
  }

}
