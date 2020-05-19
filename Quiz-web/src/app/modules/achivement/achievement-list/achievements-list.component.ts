import {Component, Input, OnInit} from '@angular/core';
import {Achievement} from "../../core/models/achievement";
import {AchievementService} from "../../core/services/achievement.service";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-achievements-list',
  templateUrl: './achievements-list.component.html',
  styleUrls: ['./achievements-list.component.scss']
})
export class AchievementsListComponent implements OnInit {
  cards: Achievement[];
  slides: any = [[]];
  @Input() currentId:number;

  constructor(private achievementService: AchievementService,
              public translate: TranslateService) {
  }

  chunk(arr, chunkSize) {
    let R = [];
    for (let i = 0, len = arr.length; i < len; i += chunkSize) {
      R.push(arr.slice(i, i + chunkSize));
    }
    return R;
  }

  ngOnInit() {
    this.getCharacteristics();
  }

  getCharacteristics() {
    this.achievementService.getUserAchievements(this.currentId).subscribe(achivement => {
        this.cards = achivement;
        this.slides = this.chunk(this.cards, 4);
      },
      err => {
        console.log(err);
      });
  }
}
