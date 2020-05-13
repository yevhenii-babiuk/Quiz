import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "../../core/services/authentication.service";

import {socket} from "../../../../environments/environment.prod";
import * as Stomp from "@stomp/stompjs";
import * as SockJS from 'sockjs-client';
import {WebsocketEvent} from "../../core/models/websocketEvent";
import {SecurityService} from "../../core/services/security.service";

@Component({
  selector: 'app-notification-menu',
  templateUrl: './notification-menu.component.html',
  styleUrls: ['./notification-menu.component.css']
})

export class NotificationMenuComponent implements OnInit {

  public stompClient;
  notifications = [];
  notificationCount = 0;
  unviewedNotificationCount = 0;
  receivedEvent: WebsocketEvent;
  userId = this.securityService.getCurrentId();

  newsChanel = true;
  playChanel: false;
  quizChanel: boolean;
  invitationChanel: boolean;

  showNotification: boolean;
  showSettings: boolean;
  newsSubscription: any;
  constructor( public securityService: SecurityService,
               public authService: AuthenticationService) {
    }

  initializeWebSocketConnection() {
    let ws = new SockJS(socket);
    this.stompClient = Stomp.Stomp.over(ws);
    let that = this;
    this.stompClient.connect({}, function () {
      that.stompClient.subscribe("/notification"+ that.userId, async (message) => {
        if (message.body) {
          that.receivedEvent = JSON.parse(message.body);
          that.notifications.push(that.receivedEvent.notification);
          that.notificationCount++;
          that.unviewedNotificationCount++;
          console.log(that.notifications[that.notificationCount]);
        }
      });
    } ,this);
  }

  turnOffNews(flag: boolean) {
    if(flag == false) {
      console.log(flag);
      this.newsSubscription.unsubscribe(this.stompClient.sessionId);
    } else {
      this.initializeWebSocketConnection();
    }
  }


  setViewed(linkAction: string) {
    // if(this.notifications[this.notifications.indexOf(linkAction)].isViewed == false) {
         //this.notifications[this.notifications.indexOf(linkAction)].isViewed =  true;
    //   if(this.unviewedNotificationCount > 0) {
    //     this.unviewedNotificationCount--;
    //   }
    // }

    if(this.unviewedNotificationCount > 0) {
      this.unviewedNotificationCount--;
    }
  }

  deleteNotification(linkAction: string) {
    this.notifications.splice( this.notifications.indexOf(linkAction));
    this.notificationCount--;
  }
  deleteAllNotification() {
    this.notifications = [];
    this.notificationCount = 0;
  }
  openSettings(state: boolean) {
    this.showSettings = state;
  }
  openNotification(state: boolean) {
    this.showNotification = state;
  }
  ngOnInit(): void {
    this.initializeWebSocketConnection();
  }

  disconnect() {
    this.stompClient.disconnect();
    console.log("Disconnected");
  }

  sendMessage(message : string) {
    console.log(message);
    this.stompClient.send("/notification/notify" , {}, message);
  }
}
