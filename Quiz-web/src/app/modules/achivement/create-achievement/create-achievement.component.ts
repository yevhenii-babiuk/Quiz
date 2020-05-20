import {Component, OnInit} from '@angular/core';
import {Achievement} from "../../core/models/achievement";
import {
  AchievementCharacteristic,
  AchievementCondition,
  ConditionOperator
} from "../../core/models/achievementCondition";
import {AchievementService} from "../../core/services/achievement.service";
import {AlertService} from "../../core/services/alert.service";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-create-achievement',
  templateUrl: './create-achievement.component.html',
  styleUrls: ['./create-achievement.component.css']
})
export class CreateAchievementComponent implements OnInit {

  achievement: Achievement = new Achievement();
  characteristics: AchievementCharacteristic[] = [];
  operators = ConditionOperator;
  operatorName: any;
  conditions: AchievementCondition[] = [new AchievementCondition];
  message: string = "";
  isInvalid: boolean = false;
  number: number;

  constructor(private achievementService: AchievementService,
              private alertService: AlertService,
              public translate: TranslateService) {
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
      this.message = "NAME";
      this.number = 0;
      return false
    }
    if (this.achievement.description == null || this.achievement.description.length == 0) {
      this.message = "DESCRIPTION";
      this.number = 0;
      return false
    }
    if (this.achievement.conditions.length < 1) {
      this.message = "CONDITION";
      this.number = 0;
      return false
    }
    for (let i = 0; i < this.achievement.conditions.length; i++) {
      let condition = this.achievement.conditions[i];
      if (condition.characteristicId == null) {
        this.message = `CHARACTERISTIC`;
        this.number = i + 1;
        return false
      }
      if (condition.operator == null) {
        this.message = `OPERATOR`;
        this.number = i + 1;
        return false
      }
      if (condition.value == null || condition.value == 0) {
        this.message = `VALUE`;
        this.number = i + 1;
        return false
      }
    }
    return true;
  }

  send() {
    this.achievement.conditions = this.conditions;
    if (!this.isValid()) {
      this.isInvalid = true;
    } else {
      this.achievementService.sendAchievement(this.achievement).subscribe(
        data => {
          if (data) {
            this.alertService.success('DASHBOARD.ACHIEVEMENT_VALIDATION.SUCCESSFUL', false);
            this.isInvalid = false;
            this.achievement.name = "";
            this.achievement.description = "";
            this.conditions = [new AchievementCondition];
          } else {
            this.alertService.error('DASHBOARD.ACHIEVEMENT_VALIDATION.ERRORED', false);
          }
        },
        error => {
          this.alertService.error('DASHBOARD.ACHIEVEMENT_VALIDATION.ERRORED');
          this.message = `DASHBOARD.ACHIEVEMENT_VALIDATION.ERRORED`;
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
