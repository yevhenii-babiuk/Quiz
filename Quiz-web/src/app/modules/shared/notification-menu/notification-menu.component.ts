import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "../../core/services/authentication.service";

import {socket} from "../../../../environments/environment.prod";
import * as Stomp from "@stomp/stompjs";
import * as SockJS from 'sockjs-client';
import {WebsocketEvent} from "../../core/models/websocketEvent";
import {SecurityService} from "../../core/services/security.service";
import {NotificationService} from "../../core/services/notification.service";
import {NotificationDto} from "../../core/models/notificationDto";
import {NotificationFilters} from "../../core/models/notificationFilters";

@Component({
  selector: 'app-notification-menu',
  templateUrl: './notification-menu.component.html',
  styleUrls: ['./notification-menu.component.css']
})

export class NotificationMenuComponent implements OnInit {

  public stompClient;
  notifications = [];
  unviewedNotificationCount = 0;
  receivedEvent: WebsocketEvent;
  userId: number;
  notificationFilters: NotificationFilters;

  newsChanel = true;
  playChanel: false;
  quizChanel: boolean;
  invitationChanel: boolean;

  showNotification: boolean;
  showSettings: boolean;
  newsSubscription: any;

  constructor(public securityService: SecurityService,
              public authService: AuthenticationService,
              private notificationService: NotificationService) {

  }

  initializeWebSocketConnection() {
    let ws = new SockJS(socket);
    this.stompClient = Stomp.Stomp.over(ws);
    let that = this;
    this.stompClient.connect({}, function () {
      that.stompClient.subscribe("/notification" + that.securityService.getCurrentId(), async (message) => {
        if (message.body) {
          that.receivedEvent = JSON.parse(message.body);
          that.notifications.push(that.receivedEvent.notification);
          that.unviewedNotificationCount++;
        }
      });
    }, this);
  }

  setViewed(notification: NotificationDto) {
    if (!this.notifications[this.notifications.indexOf(notification)].isViewed) {
      this.notifications[this.notifications.indexOf(notification)].isViewed = true;
      this.notificationService.updateNotificationView(this.notifications[this.notifications.indexOf(notification)].isViewed).subscribe();
      if (this.unviewedNotificationCount > 0) {
        this.unviewedNotificationCount--;
      }
    }
  }

  deleteNotification(linkAction: string, id: number) {
    this.notifications.splice(this.notifications.indexOf(linkAction));
    this.notificationService.deleteNotificationById(id).subscribe();
  }

  deleteAllNotification() {
    this.notifications = [];
    this.unviewedNotificationCount = 0;
    this.notificationService.deleteAllByUserId(this.securityService.getCurrentId()).subscribe();
  }

  openSettings(state: boolean) {
    this.showSettings = state;
  }

  openNotification(state: boolean) {
    this.showNotification = state;
  }

  getNotificationFromDB() {
    this.notificationService.getNotificationsByUserId(this.securityService.getCurrentId()).subscribe(
      notifications => {
        this.notifications = this.notifications.concat(notifications);
        for (let i = 0; i < this.notifications.length; i++) {
          if (!this.notifications[i].isViewed) {
            this.unviewedNotificationCount++;
          }
        }
      },
      err => {
        console.log(err);
      });
  }

  updateSettings(notificationFilters: NotificationFilters) {
    this.notificationService.updateSettings(notificationFilters).subscribe();
  }

  ngOnInit(): void {
    if (this.authService.isAuthenticated()) {
      this.initializeWebSocketConnection();
      this.getNotificationFromDB();
      this.notificationService.getSettingsByUserId(this.securityService.getCurrentId()).subscribe(
        notificationFilters => {
          this.notificationFilters = notificationFilters;
          console.log("notification settings: " + this.notificationFilters.newAnnouncement);
        },
        err => {
          console.log(err);
        });
    }

  }

  disconnect() {
    this.stompClient.disconnect();
    console.log("Disconnected");
  }

}
