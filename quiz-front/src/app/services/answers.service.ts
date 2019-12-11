import { Injectable } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { Answer } from '../datamodel/answer';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AnswersService {

  private answersUrl: string = 'http://localhost:8080/quiz-rest-api/rest/submissions/';
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  createAnswer(answer: Answer): Observable<Answer>{
    return this.http.post<Answer>(this.answersUrl, answer).pipe(
      catchError(this.handleError<Answer>('createAnswer'))
    );
  }

  getAnswer(id: number): Observable<Answer> {
    const url = `${this.answersUrl}${id}`;
    return this.http.get<Answer>(url).pipe(
      catchError(this.handleError<Answer>(`getAnswer id=${id}`))
    );
  }

  updateAnswer(answer: Answer): Observable<Answer>{
    const url = this.answersUrl+"update";
    return this.http.put<Answer>(url, answer).pipe(
      catchError(this.handleError<Answer>('updateAnswer'))
    )
  }

  getAllAnswers(): Observable<Answer[]> {
    const url = this.answersUrl+"search";
    return this.http.get<Answer[]>(url).pipe(
      catchError(this.handleError<Answer[]>('getAllAnswers'))
    );
  }

  searchAnswer(term: string): Observable<Answer[]> {
    if(!term.trim()){
      return of([]);
    }
    return this.http.get<Answer[]>(`${this.answersUrl}search?title=${term}`).pipe(
      catchError(this.handleError<Answer[]>('searchanswers'))
    );
  }

  deleteAnswer(answer: Answer | number): Observable<Answer>{
    const id = typeof answer === 'number' ? answer : answer.id;
    const url = `${this.answersUrl}${id}`;
    return this.http.delete<Answer>(url).pipe(
      catchError(this.handleError<Answer>('deleteanswer'))
    );

  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      console.error(error);
      return of(result as T);
    };
  }
}