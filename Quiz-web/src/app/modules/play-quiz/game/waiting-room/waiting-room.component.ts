import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {SecurityService} from "../../../core/services/security.service";
import {PlayGameService} from "../../../core/services/play-game.service";
import {User} from "../../../core/models/user";


@Component({
  selector: 'app-waiting-room',
  templateUrl: './waiting-room.component.html',
  styleUrls: ['./waiting-room.component.css']
})
export class WaitingRoomComponent implements OnInit {

  @Input() players: String[];

  @Output()
  public startGame: EventEmitter<any> = new EventEmitter();

  @Input()
  currentUser: User;

  hostId: number = 0;
  gameId: string = this.route.snapshot.paramMap.get('gameId');

  constructor(private route: ActivatedRoute,
              private redirect: Router,
              private securityService: SecurityService,
              private playGameService: PlayGameService) {

  }

  ngOnInit(): void {
  }

  copy(divElement) {
    divElement.select();
    document.execCommand('copy');
    divElement.setSelectionRange(0, 0);
  }
}
