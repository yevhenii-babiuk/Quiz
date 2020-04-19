import { Component, OnInit } from '@angular/core';

import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { AuthenticationService } from '../../../core/services/authentication.service';

@Component({
  selector: 'app-mail-confirm',
  templateUrl: './mail-confirm.component.html',
  styleUrls: ['./mail-confirm.component.css']
})
export class MailConfirmComponent implements OnInit {
  isConfirmed: boolean;

  constructor(
      private authenticationService: AuthenticationService,
      private route: ActivatedRoute,
      private location: Location
  ) { }

  ngOnInit() {
   this.confirm();
 }

 confirm(): void {
   const token = this.route.snapshot.paramMap.get('token');
   this.authenticationService.confirmMail(token)
   .subscribe(
     isConfirmed => { this.isConfirmed = isConfirmed },
     error => {
       this.isConfirmed = false;
       console.log(error);
     }
   );
 }

}
