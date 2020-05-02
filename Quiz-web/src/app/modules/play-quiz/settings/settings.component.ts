import {Component, OnInit} from '@angular/core';
import {WebsocketService} from "../../core/services/websocket.service";
import {SettingsModel} from "./settings.model";

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {
  settingsModel: SettingsModel;

  message: string = "";
  isInvalid: boolean = false;

  constructor() {//private wsService: WebsocketService) {
    this.settingsModel = new SettingsModel();
    /* this.wsService.on<String>('messages')
       .subscribe((messages: String) => {
         console.log(messages);
       });*/
  }

  ngOnInit(): void {
  }

  isValid(): boolean {
    if (this.settingsModel.isTimeForAnswerChosen && this.settingsModel.timeForAnswer
      && this.settingsModel.timeForAnswer < 61 && this.settingsModel.timeForAnswer > 4) {
      return true;
    } else {
      this.isInvalid = true;
      this.message = "Час на відповідь маєбути заповненим та знаходитись в межах від 5 до 60"
    }
  }

  submit() {
    if (this.isValid()) {
      this.isInvalid = false;
    }
  }
}
