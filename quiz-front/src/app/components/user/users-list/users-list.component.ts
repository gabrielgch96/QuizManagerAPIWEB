import { Component, OnInit } from '@angular/core';
import { User } from '../../../datamodel/user';
import { UserService } from 'src/app/services/user.service';
import { Subscription, Observable, Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.css']
})
export class UsersListComponent implements OnInit {

  users$: User[];
  usersSearch$: Observable<User[]>;
  private searchTerms = new Subject<string>();
  private currentUser: User;

  constructor(
    private authService: AuthenticationService,
    private userService: UserService
  ) { this.users$ = [];
    this.authService.currentUser.subscribe(x => this.currentUser = x);}

  ngOnInit() {
    
    this.getAllUsers();
    //this.userService.getCurrentUsers.subscribe(users=>this.users = users);
    this.usersSearch$ = this.searchTerms.pipe(
      // wait 300ms after each keystroke before considering the term
      debounceTime(300),

      // ignore new term if same as previous term
      distinctUntilChanged(),

      // switch to new search observable each time the term changes
      switchMap((term: string) => this.userService.searchUser(term)),
    );
  }

  // Push a search term into the observable stream.
  search(term: string): void {
    this.searchTerms.next(term);
  }

  getAllUsers(): void{
    this.userService.getAllUsers()
      .subscribe(users => this.users$ = users);
  }

  deleteUser(userToDelete: User): void{
    this.users$ = this.users$.filter(user => user != userToDelete);
    this.userService.deleteUser(userToDelete).subscribe();
  }

}
