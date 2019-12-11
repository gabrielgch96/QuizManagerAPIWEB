import { Component, OnInit } from '@angular/core';
import { User } from '../../../datamodel/user';
import { UserService } from 'src/app/services/user.service';
import { Location } from '@angular/common';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.css']
})
export class UserFormComponent implements OnInit {

  private userModel = new User("John", "Doe", "example@gmail.com","bazau",false);
  private users: User[];
  private currentUser: User;

  constructor(private authService: AuthenticationService,
    private userService: UserService,
    private location: Location) { 
      this.authService.currentUser.subscribe(x => this.currentUser = x);
    }

  ngOnInit() {
    //this.userService.getAllUsers().subscribe(users => this.users = users);
  }

  goBack(): void {
    this.location.back();
  }

  createUser(){

    this.userService.createUser(this.userModel).subscribe();
    //this.userService.setCurrentUsers([this.userModel]);
    this.goBack();
  }

}
