import { Component, OnInit } from '@angular/core';
import { graphic } from 'echarts';
import {DashboardService} from "../../core/services/dashboard.service";
import {Statistics} from "../../core/models/statistics";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-correct-answers-percent',
  templateUrl: './correct-answers-percent.component.html',
  styleUrls: ['./correct-answers-percent.component.css']
})

export class CorrectAnswersPercentComponent implements OnInit {

  stat: Statistics[] = [];
  options: any;
  detectEventChanges = true;

  constructor(private dashboardService: DashboardService,
              public translate: TranslateService) {
  }

  ngOnInit(): void{
    this.getDate()
  }

  getDate() {
    this.dashboardService.getQuizzesCorrectAnswerPercent().subscribe(statistics => {
        this.stat = statistics;
        this.plotGraph();
      },
      err => {
        console.log(err);
      });
  }

  plotGraph(){
    let dataAxis: Array<string> = [];
    this.stat.forEach(element=>dataAxis.push(element.name))
    let dataCount: Array<number> = [];
    this.stat.forEach(element=>dataCount.push(element.value))
    const yMax = 1;
    const dataShadow = [];

    for (let i = 0; i < dataCount.length; i++) {
      dataShadow.push(yMax);
    }
    this.options = {
      title: {
        text: 'Percents of correct answers'
      },
      xAxis: {
        data: dataAxis,
        axisLabel: {
          inside: true,
          textStyle: {
            color: '#fff'
          }
        },
        axisTick: {
          show: false
        },
        axisLine: {
          show: false
        },
        z: 10
      },
      yAxis: {
        axisLine: {
          show: false
        },
        axisTick: {
          show: false
        },
        axisLabel: {
          textStyle: {
            color: '#999'
          }
        }
      },
      dataZoom: [
        {
          type: 'inside'
        }
      ],
      series: [
        { // For shadow
          type: 'bar',
          itemStyle: {
            normal: { color: 'rgba(0,0,0,0.05)' }
          },
          barGap: '-100%',
          barCategoryGap: '40%',
          data: dataShadow,
          animation: false
        },
        {
          type: 'bar',
          itemStyle: {
            normal: {
              color: new graphic.LinearGradient(
                0, 0, 0, 1,
                [
                  { offset: 0, color: '#83bff6' },
                  { offset: 0.5, color: '#188df0' },
                  { offset: 1, color: '#188df0' }
                ]
              )
            },
            emphasis: {
              color: new graphic.LinearGradient(
                0, 0, 0, 1,
                [
                  { offset: 0, color: '#2378f7' },
                  { offset: 0.7, color: '#2378f7' },
                  { offset: 1, color: '#83bff6' }
                ]
              )
            }
          },
          data: dataCount
        }
      ]
    };
  }

  onChartEvent(event: any, type: string) {
    console.log('chart event:', type, event);
  }
}
