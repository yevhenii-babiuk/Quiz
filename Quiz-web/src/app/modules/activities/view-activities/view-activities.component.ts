import {Component, HostListener, Input, OnInit} from '@angular/core';
import {Role} from "../../core/models/role";
import {SecurityService} from "../../core/services/security.service";
import {ActivitiesService} from "../../core/services/activities.service";
import {Activity} from "../../core/models/activity";
import {FriendActivityType} from "../../core/models/friendActivityType";
import {Image} from "../../core/models/image";
import {GameDto} from "../../core/models/gameDto";


@Component({
  selector: 'app-view-activities',
  templateUrl: './view-activities.component.html',
  styleUrls: ['./view-activities.component.css']
})
export class ViewActivitiesComponent implements OnInit {
  category = FriendActivityType;
  activityCategories = [
    {
      id: '1',
      value: 'Додані друзі',
      selected: false
    },
    {
      id: '2',
      value: 'Додані до списку улюблених вікторини',
      selected: false
    },
    {
      id: '3',
      value: 'Створені вікторини',
      selected: false
    },
    {
      id: '4',
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
    this.activities = [];
    let resultSelected = [];
    this.userId = this.securityService.getCurrentId();
    this.activityCategories.forEach(function (value) {
      resultSelected.push(value.selected);
    });

    if (!resultSelected[0] && !resultSelected[1] && !resultSelected[2] && !resultSelected[3]) {
      this.getActivities();
      return;
    }

    this.activitiesService.getFilterActivities(this.userId, resultSelected)
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
