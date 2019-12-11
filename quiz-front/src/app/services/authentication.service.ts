import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../datamodel/user';
import { Observable, BehaviorSubject } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private usersUrl: string = 'http://localhost:8080/quiz-rest-api/rest/users/';
  private currentUserBS: BehaviorSubject<User>;
  public currentUser: Observable<User>;

  constructor(private http: HttpClient) {
    this.currentUserBS = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserBS.asObservable();
  }

  public get currentUserValue() {
    return this.currentUserBS.value;
  }

  loginUser(email: string, password: string): Observable<User> {
    let data = {
      "email": email,
      "password": password
    };
    return this.http.post<User>(`${this.usersUrl}login`, data).pipe(
      map(user => {
        localStorage.setItem('currentUser', JSON.stringify(user));
        this.currentUserBS.next(user);
        return user;
      }));
  }

  logoutUser(){
    localStorage.removeItem('currentUser');
    this.currentUserBS.next(null);
  }


}
