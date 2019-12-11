import { Injectable } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Submission } from '../datamodel/submission';
import { User } from '../datamodel/user';

@Injectable({
  providedIn: 'root'
})
export class SubmissionService {

  private submissionUrl: string = 'http://localhost:8080/quiz-rest-api/rest/submissions/';
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  getSubmission(id: number): Observable<Submission> {
    const url = `${this.submissionUrl}${id}`;
    return this.http.get<Submission>(url).pipe(
      catchError(this.handleError<Submission>(`getExam id=${id}`))
    );
  }

  createSubmission(dataInWrapper: string): Observable<Submission>{
    return this.http.post<Submission>(this.submissionUrl, dataInWrapper, this.httpOptions).pipe(
      catchError(this.handleError<Submission>('createSubmission'))
    );
  }

  getUserSubmissions(user: User): Observable<Submission[]> {
    return this.http.get<Submission[]>(`${this.submissionUrl}search?user_id=${user.id}`).pipe(
      //tap(_ => this.log(`fetched hero id =${id}`)),
      catchError(this.handleError<Submission[]>('getUserSubmissions'))
    );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      console.error(error);
      return of(result as T);
    };
  }
}
