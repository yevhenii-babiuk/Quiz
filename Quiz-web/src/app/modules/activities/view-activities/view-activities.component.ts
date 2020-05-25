import {Component, ElementRef, HostListener, Input, OnInit, ViewChild} from '@angular/core';
import {Role} from "../../core/models/role";
import {SecurityService} from "../../core/services/security.service";
import {ActivitiesService} from "../../core/services/activities.service";
import {Activity} from "../../core/models/activity";
import {FriendActivityType} from "../../core/models/friendActivityType";
import {TranslateService} from "@ngx-translate/core";


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
      activityType: 'addFriend',
      selected: false
    },
    {
      id: '2',
      activityType: 'favouriteQuizzes',
      selected: false
    },
    {
      id: '3',
      activityType: 'createdQuizzes',
      selected: false
    },
    {
      id: '4',
      activityType: 'achievements',
      selected: false
    }
  ];

  activities: Activity[] = [];
  userId: number;
  role: Role;
  isWaiting: boolean;
  isFiltering: boolean;

  @HostListener("window:scroll", ["$event"])
  onWindowScroll() {

    if (document.documentElement.scrollHeight - document.documentElement.scrollTop -
      document.documentElement.clientHeight < 40) {
      if (this.isFiltering) {
        this.getFilteredActivities()
      } else {
        this.getActivities();
      }
    }

  }

  constructor(private activitiesService: ActivitiesService,
              private securityService: SecurityService,
              public translate: TranslateService,
  ) {
  }


  getActivities(): void {
    this.userId = this.securityService.getCurrentId();
    this.isFiltering = false;
    if (this.isWaiting) {
      return;
    }
    this.isWaiting = true;
    this.activitiesService.getActivitiesPageByUserId(this.userId, this.activities.length)
      .subscribe(
        activities => {
          if (activities.length == 10) {
            this.isWaiting = false;
          }
          this.activities = this.activities.concat(activities);
        },
        err => {
          console.log(err);
        })

  }

  clearActivities(): void {
    this.isWaiting = false;
    this.activities = [];
  }

  getFilteredActivities(): void {
    this.isFiltering = true;
    let resultSelected = [];
    this.userId = this.securityService.getCurrentId();
    this.activityCategories.forEach(function (value) {
      resultSelected.push(value.selected);
    });

    if (!resultSelected[0] && !resultSelected[1] && !resultSelected[2] && !resultSelected[3]) {
      this.isWaiting = false;
      this.getActivities();
      return;
    }
    if (this.isWaiting) {
      return;
    }
    this.isWaiting = true;
    this.activitiesService.getFilterActivitiesPage(this.userId, resultSelected, this.activities.length)
      .subscribe(
        activities => {
          if (activities.length == 10) {
            this.isWaiting = false;
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
