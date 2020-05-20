import {Component, OnInit} from '@angular/core';
import {Chat} from "../../core/models/chat";
import {SecurityService} from "../../core/services/security.service";
import {ChatService} from "../../core/services/chat.service";
import {User} from "../../core/models/user";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-chat-list',
  templateUrl: './chat-list.component.html',
  styleUrls: ['./chat-list.component.css']
})
export class ChatListComponent implements OnInit {
  id: number;
  public creation: boolean = false;
  public chats: Array<Chat>;
  public chat: Chat = new Chat();

  constructor(private chatService: ChatService,
              private securityService: SecurityService,
              public translate: TranslateService) {
  }

  ngOnInit(): void {
    this.id = this.securityService.getCurrentId();
    this.chatService.getUserChats(this.id)
      .subscribe(
        chats => {
          this.chats = chats;
        },
        err => {
          console.log(err);
        })
  }

  createChat() {
    this.chat.users.push({id : this.id} as User);
    this.chatService.createChat(this.chat, this.id)
      .subscribe(
        data => {
          this.creation = false;
          window.location.reload();
        },
        err => {
          console.log(err);
        }
      )
  }
}
