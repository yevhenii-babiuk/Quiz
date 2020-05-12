import {Component, HostListener, Input, OnInit} from '@angular/core';
import {Role} from "../../core/models/role";
import {SecurityService} from "../../core/services/security.service";
import {ActivitiesService} from "../../core/services/activities.service";
import {Activity} from "../../core/models/activity";


@Component({
  selector: 'app-view-activities',
  templateUrl: './view-activities.component.html',
  styleUrls: ['./view-activities.component.css']
})
export class ViewActivitiesComponent implements OnInit {
  activityCategories = [
    {
      value: 'Досягнення',
      selected: false
    },
    {
      value: 'Нові створені вікторини',
      selected: false
    },
    {
      value: 'Додані до списку улюблених вікторини',
      selected: false
    },
    {
      value: 'Додані друзі',
      selected: false
    }
  ]

  activities: Activity[] = [];
  userId: number;
  role: Role;

  todayDate: Date = new Date();


  constructor(private activitiesService: ActivitiesService,
              private securityService: SecurityService,
  ) {

  }

  getActivities(): void {
    let activity: Activity = {
      id: 1,
      friendId: 43,
      friendLogin: "login1",
      achievementName: "achievement",
      quizId: 0,
      quizName: null,
      quizCategoryName: null,
      markedFavourite: false,
      friendOfFriendId: null,
      friendOfFriendLogin: null,
      activityDate: this.todayDate,
      imageId: -1,
      image: null
    };
    let activity2: Activity = {
      id: 2,
      friendId: 43,
      friendLogin: "login1",
      achievementName: null,
      quizId: 15,
      quizName: "quizName",
      quizCategoryName: "quizCategoryName",
      markedFavourite: false,
      friendOfFriendId: null,
      friendOfFriendLogin: null,
      activityDate: this.todayDate,
      imageId: -1,
      image: null
    };
    let activity3: Activity = {
      id: 3,
      friendId: 43,
      friendLogin: "login1",
      achievementName: null,
      quizId: 15,
      quizName: "quizName",
      quizCategoryName: "quizCategoryName",
      markedFavourite: true,
      friendOfFriendId: null,
      friendOfFriendLogin: null,
      activityDate: this.todayDate,
      imageId: -1,
      image: null
    };
    let activity4: Activity = {
      id: 4,
      friendId: 43,
      friendLogin: "login1",
      achievementName: null,
      quizId: 0,
      quizName: null,
      quizCategoryName: null,
      markedFavourite: false,
      friendOfFriendId: 67,
      friendOfFriendLogin: "friendLogin",
      activityDate: this.todayDate,
      imageId: -1,
      image: null
    };
    this.activities[0] = activity;
    this.activities[1] = activity2;
    this.activities[2] = activity3;
    this.activities[3] = activity4;

    this.userId = this.securityService.getCurrentId();
    console.log(this.userId);
    /*this.activitiesService.getActivitiesByUserId(this.userId)
      .subscribe(
        activities => {
          if (activities.length == 0) {
            return;
          }

          this.activities = this.activities.concat(activities);
        },
        err => {
          console.log(err);
        })*/

  }


  public getSelected() {
    let result = this.activityCategories.filter((ch) => {
      return ch.selected
    })
      .map((ch) => {
        return ch.value
      });
    console.log(result);
    this.activities=[];
    this.activitiesService.getFilterActivities(result)
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
    console.log("done")
  }

  ngOnInit(): void {
    this.role = this.securityService.getCurrentRole();
    this.getActivities();
  }

}
