import {Component, Input, OnInit} from '@angular/core';
import {GameResults} from "../../core/models/gameResults";
import {Question} from "../../core/models/question";
import {Answer} from "../../core/models/answer";
import {ActivatedRoute, Router} from "@angular/router";
import {SecurityService} from "../../core/services/security.service";
import {PlayGameService} from "../../core/services/play-game.service";
import {Game} from "../../core/models/game";
import {User} from "../../core/models/user";

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent implements OnInit {

  //players: String[] = [];

  public question: Question;
  public gameResults: GameResults;

  public currentUser:User;
  isWaiting: boolean = true;

  constructor(private route: ActivatedRoute,
              private redirect: Router,
              private securityService: SecurityService,
              private playGameService: PlayGameService) {
    console.log("parCon");
    let userId = securityService.getCurrentId();

    this.playGameService.sendJoinedUser(userId, this.route.snapshot.paramMap.get('gameId')).subscribe(
      user => {
        console.log("userJoined");
        this.currentUser = user;
     //   this.players.push(this.currentUser.login);
      }, err => {
        console.log(err);
        this.redirect.navigate(['home']);
      });


  }

  ngOnInit(): void {
  }

  processAnswer(answer: Answer) {
    console.log(answer);
  }

  startGame() {

  }
}
