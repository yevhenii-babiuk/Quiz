import {Component, OnInit} from '@angular/core';
import {Achievement} from "../../core/models/achievement";
import {
  AchievementCharacteristic,
  AchievementCondition,
  ConditionOperator
} from "../../core/models/achievementCondition";
import {Router} from "@angular/router";
import {AchievementService} from "../../core/services/achievement.service";
import {AlertService} from "../../core/services/alert.service";

@Component({
  selector: 'app-create-achievement',
  templateUrl: './create-achievement.component.html',
  styleUrls: ['./create-achievement.component.css']
})
export class CreateAchievementComponent implements OnInit {

  achievement: Achievement = new Achievement();
  characteristics: AchievementCharacteristic[]=[];
  operators = ConditionOperator;
  operatorName: any;
  conditions: AchievementCondition[] = [new AchievementCondition];
  message: string = "";
  isInvalid: boolean = false;

  constructor(private achievementService: AchievementService,
              private alertService: AlertService,
              private router: Router) {
    this.operatorName = Object.keys(ConditionOperator).filter(x => !(parseInt(x) >= 0));
  }

  ngOnInit() {
    this.getCharacteristics();
  }

  addCondition() {
    this.conditions.push(new AchievementCondition);
  }

  removeCondition(i: number) {
    this.conditions.splice(i, 1);
  }

  isValid(): boolean {
    if (this.achievement.name == null || this.achievement.name.length == 0) {
      this.message = "Please enter name of achievement";
      return false
    }
    if (this.achievement.description == null || this.achievement.description.length == 0) {
      this.message = "Please enter description of achievement";
      return false
    }
    if (this.achievement.conditions.length < 1) {
      this.message = "Please add one or more condition";
      return false
    }
    for (let i = 0; i < this.achievement.conditions.length; i++) {
      let condition = this.achievement.conditions[i];
      if (condition.characteristicId==null) {
        this.message = `Please choose characteristic of your condition ${i + 1}`;
        return false
      }
      if (condition.operator == null ) {
        this.message = `Please choose operator of your condition ${i + 1} `;
        return false
      }
      if (condition.value == null || condition.value == 0) {
        this.message = `Please enter value of your condition ${i + 1} `;
        return false
      }
    }
    return true;
  }

  send() {
    this.achievement.conditions=this.conditions;
    if (!this.isValid()) {
      this.isInvalid = true;
    } else {
      this.achievementService.sendAchievement(this.achievement).subscribe(
        data => {
          if (data){
            this.alertService.success('Adding successful', false);
            this.isInvalid = false;
            this.achievement.name="";
            this.achievement.description="";
            this.conditions = [new AchievementCondition];
          } else {
            this.alertService.error('Adding is not successful', false);
          }
        },
        error => {
          this.alertService.error('Error while adding achievement!');
          this.message = `cant add achievement`;
          console.log(error);
        });
    }
  }

  getCharacteristics() {
    this.achievementService.getCharacteristics().subscribe(characteristics => {
        this.characteristics = characteristics;
      },
      err => {
        console.log(err);
      });
  }
}
