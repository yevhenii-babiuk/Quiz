import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  template: `
    <!--The content below is only a placeholder and can be replaced.-->
    <div style="text-align:center" class="content">
      <h1>
        Welcome to {{title}}!
      </h1>
      <nav>
        <a routerLink="api/v1/login">Login</a>
        <a routerLink="/api/v1/registration">Registration</a>
      </nav>
    <router-outlet></router-outlet>
        <alert></alert>
  `,
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Qucat';
}
