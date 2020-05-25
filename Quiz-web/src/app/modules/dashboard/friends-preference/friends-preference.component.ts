import { Component, OnInit } from '@angular/core';
import {DashboardService} from "../../core/services/dashboard.service";
import {Statistics} from "../../core/models/statistics";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-friends-preference',
  templateUrl: './friends-preference.component.html',
  styleUrls: ['./friends-preference.component.css']
})
export class FriendsPreferenceComponent implements OnInit {

  stat: Statistics[] = [];
  options: any;
  echartsInstance: any;

  constructor(private dashboardService: DashboardService,
              public translate: TranslateService) {
  }

  getPreferences() {
    this.dashboardService.getFriendsPreferences().subscribe(statistics => {
        this.stat = statistics;
        this.plotGraph();
      },
      err => {
        console.log(err);
      });
  }

  plotGraph() {
    this.options = {
      backgroundColor: '#ffffff',

      title: {
        text: 'Friends preferences',
        left: 'center',
        top: 20,
        textStyle: {
          color: 'rgba(42,35,35,0.01)'
        }
      },

      tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : {c} ({d}%)'
      },

      visualMap: {
        show: false,
        min: 80,
        max: 600,
        inRange: {
          colorLightness: [0, 1]
        }
      },
      series: [
        {
          name: 'Category',
          type: 'pie',
          radius: '55%',
          center: ['50%', '50%'],
          data:  this.stat.sort(function (a, b) {
            return a.value - b.value;
          }),
          roseType: 'radius',
          label: {
            normal: {
              textStyle: {
                color: 'rgb(172,66,78)'
              }
            }
          },
          labelLine: {
            normal: {
              lineStyle: {
                color: 'rgba(184,57,57,0.91)'
              },
              smooth: 0.2,
              length: 10,
              length2: 20
            }
          },
          itemStyle: {
            normal: {
              color: '#c23531',
              shadowBlur: 200,
              shadowColor: 'rgb(232,182,9)'
            }
          },

          animationType: 'scale',
          animationEasing: 'elasticOut',
          animationDelay: function (idx) {
            return Math.random() * 200;
          }
        }
      ]
    };
  }

  ngOnInit() {
    this.getPreferences();
  }

  onChartInit(e: any) {
    this.echartsInstance = e;
  }
}
