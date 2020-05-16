import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule} from "@angular/router";
import {SharedModule} from "../shared/shared.module";
import { ChatListComponent } from './chat-list/chat-list.component';
import { ChatAreaComponent } from './chat-area/chat-area.component';


@NgModule({
  declarations: [ChatListComponent, ChatAreaComponent],
  imports: [
    CommonModule,
    RouterModule,
    SharedModule
  ]
})
export class ChatModule { }
