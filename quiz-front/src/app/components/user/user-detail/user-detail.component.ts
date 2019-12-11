import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { UserService } from 'src/app/services/user.service';
import { ActivatedRoute } from '@angular/router';
import { User } from 'src/app/datamodel/user';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css']
})
export class UserDetailComponent implements OnInit {

  user: User;
  private currentUser: User;

  constructor(private route: ActivatedRoute,
    private authService: AuthenticationService,
    private userService: UserService,
    private location: Location) { 
      this.authService.currentUser.subscribe(x => this.currentUser = x);
    }

  ngOnInit() {
    this.getUser();
  }

  getUser(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.userService.getUser(id).subscribe(
      user => this.user = user
    );
  }

  goBack(): void{
    this.location.back();
  }

  
  updateUser(){
    this.userService.updateUser(this.user).subscribe(
      user => this.user = user
    );
    this.goBack();
  }

}
