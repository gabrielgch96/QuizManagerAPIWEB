import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../services/authentication.service';
import { User } from '../datamodel/user';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  private currentUser: User;

  constructor(private router: Router,
    private authService: AuthenticationService) {
      this.authService.currentUser.subscribe(x => this.currentUser = x);
     }

  ngOnInit() {
  }

  logout(){
    this.authService.logoutUser();
    this.router.navigate(['/login']);
  }

}
