import { Injectable, EventEmitter } from '@angular/core';
import { User } from '../datamodel/user';
import { Observable, of, BehaviorSubject } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private usersUrl: string = 'http://localhost:8080/quiz-rest-api/rest/users/';
  //private currentUsers = new BehaviorSubject<User[]>([]);
  //userEvent: EventEmitter<User> = new EventEmitter();

  //getCurrentUsers = this.currentUsers.asObservable();
/*
  setCurrentUsers(users: User[]){
    console.log(users);
    this.currentUsers.next(users);
  }
*/
  httpOptions = {
    headers: new HttpHeaders({ 'ContentType': 'text/plain' })
  };

  constructor(private http: HttpClient) { }

  getUser(id: number): Observable<User> {
    const url = `${this.usersUrl}${id}`;
    return this.http.get<User>(url).pipe(
      //tap(_ => this.log(`fetched hero id =${id}`)),
      catchError(this.handleError<User>(`getUser id=${id}`))
    );
  }

  createUser(user: User): Observable<User>{
    return this.http.post<User>(this.usersUrl, user).pipe(
      catchError(this.handleError<User>('createUser'))
    );
  }

  updateUser(user: User): Observable<User>{
    const url = this.usersUrl+"update";
    return this.http.put<User>(url, user).pipe(
      catchError(this.handleError<User>('updateUser'))
    )
  }

  getAllUsers(): Observable<User[]> {
    const url = this.usersUrl+"search";
    return this.http.get<User[]>(url).pipe(
      //tap(_ => this.log(`fetched hero id =${id}`)),
      catchError(this.handleError<User[]>('getAllUsers'))
    );
  }

  searchUser(term: string): Observable<User[]> {
    if(!term.trim()){
      return of([]);
    }
    return this.http.get<User[]>(`${this.usersUrl}search?ulast=${term}&ufirst=${term}`).pipe(
      catchError(this.handleError<User[]>('getAllUsers'))
    );
  }
  
  deleteUser(user: User | number): Observable<User>{
    const id = typeof user === 'number' ? user : user.id;
    const url = `${this.usersUrl}${id}`;
    return this.http.delete<User>(url).pipe(
      catchError(this.handleError<User>('deleteUser'))
    );

  }

  validateUser (email: string, password: string): Observable<User[]> {
    let data = {
      "email": email,
      "password": password
    };
    return this.http.post(`${this.usersUrl}login`,data) as Observable<User[]>;
  }

  /**
* Handle Http operation that failed.
* Let the app continue.
* @param operation - name of the operation that failed
* @param result - optional value to return as the observable result
*/
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      //this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}
