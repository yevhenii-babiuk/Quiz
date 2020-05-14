import {Component, HostListener, Input, OnInit} from '@angular/core';
import {Role} from "../../core/models/role";
import {SecurityService} from "../../core/services/security.service";
import {ActivitiesService} from "../../core/services/activities.service";
import {Activity} from "../../core/models/activity";
import {FriendActivityType} from "../../core/models/friendActivityType";
import {Image} from "../../core/models/image";


@Component({
  selector: 'app-view-activities',
  templateUrl: './view-activities.component.html',
  styleUrls: ['./view-activities.component.css']
})
export class ViewActivitiesComponent implements OnInit {
  category = FriendActivityType;
  activityCategories = [
    {
      value: 'Додані друзі',
      selected: false
    },
    {
      value: 'Додані до списку улюблених вікторини',
      selected: false
    },
    {
      value: 'Створені вікторини',
      selected: false
    },
    {
      value: 'Досягнення',
      selected: false
    }
  ]

  activities: Activity[] = [];
  userId: number;
  role: Role;


  constructor(private activitiesService: ActivitiesService,
              private securityService: SecurityService,
  ) {

  }

  getActivities(): void {
    this.userId = this.securityService.getCurrentId();
    console.log(this.userId);
    this.activitiesService.getActivitiesByUserId(this.userId)
      .subscribe(
        activities => {
          if (activities.length == 0) {
            return;
          }

          this.activities = this.activities.concat(activities);
        },
        err => {
          console.log(err);
        })

  }


  public getFilteredActivities() {
    let resultSelected=[];
    this.userId = this.securityService.getCurrentId();
    this.activityCategories.forEach(function (value) {
      resultSelected.push(value.selected);
    });

    this.activities = [];
    this.activitiesService.getFilterActivities(this.userId,resultSelected)
      .subscribe(
        activities => {
          if (activities.length == 0) {
            return;
          }

          this.activities = this.activities.concat(activities);
        },
        err => {
          console.log(err);
        })
  }

  ngOnInit(): void {
     this.getActivities();
  }

}
