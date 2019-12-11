import { Injectable } from '@angular/core';
import { Question } from '../datamodel/question';
import { Option } from '../datamodel/option';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { throwError, Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class QuestionsService {

  private questionsUrl: string = 'http://localhost:8080/quiz-rest-api/rest/questions/';
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  getQuestion(id: number): Observable<Question> {
    const url = `${this.questionsUrl}${id}`;
    return this.http.get<Question>(url).pipe(
      //tap(_ => this.log(`fetched hero id =${id}`)),
      catchError(this.handleError<Question>(`getQuestion id=${id}`))
    );
  }

  getAllQuestions(): Observable<Question[]> {
    const url = this.questionsUrl + "search";
    return this.http.get<Question[]>(url).pipe(
      //tap(_ => this.log(`fetched hero id =${id}`)),
      catchError(this.handleError<Question[]>('getAllQuestions'))
    );
  }

  createQuestion(question: Question): Observable<Question> {
    return this.http.post<Question>(this.questionsUrl, question).pipe(
      catchError(this.handleError<Question>('createQuestion'))
    );
  }

  getRandom(): Observable<Question[]>{
    return this.http.get<Question[]>(this.questionsUrl+"random").pipe(
      catchError(this.handleError<Question[]>('getRandom'))
    )
  }

  createQuestionWithOptions(data: string): Observable<any> {
    return this.http.post<Question>(this.questionsUrl, data, this.httpOptions).pipe(
      catchError(this.handleError<Question>('createQuestionWithOptions'))
    );
  }

  searchQuestion(term: string): Observable<Question[]> {
    if (!term.trim()) {
      return of([]);
    }
    return this.http.get<Question[]>(`${this.questionsUrl}search?qContent=${term}`).pipe(
      //tap(_ => this.log(`fetched hero id =${id}`)),
      catchError(this.handleError<Question[]>('searchQuestion'))
    );
  }

  updateQuestion(question: Question): Observable<Question> {
    const url = this.questionsUrl + "update";
    return this.http.put<Question>(url, question).pipe(
      catchError(this.handleError<Question>('updateQuestion'))
    )
  }

  updateQuestionWithOptions(data:string): Observable<Question> {
    const url = this.questionsUrl + "update";
    return this.http.put<Question>(url, data, this.httpOptions).pipe(
      catchError(this.handleError<Question>('updateQuestion'))
    )
  }

  deleteQuestion(question: Question | number): Observable<Question> {
    const id = typeof question === 'number' ? question : question.id;
    const url = `${this.questionsUrl}${id}`;
    return this.http.delete<Question>(url).pipe(
      catchError(this.handleError<Question>('deleteQuestion'))
    );

  }

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
  /*
    private handleError(error: HttpErrorResponse) {
      if (error.error instanceof ErrorEvent) {
        // A client-side or network error occurred. Handle it accordingly.
        console.error('An error occurred:', error.error.message);
      } else {
        // The backend returned an unsuccessful response code.
        // The response body may contain clues as to what went wrong,
        console.error(
          `Backend returned code ${error.status}, ` +
          `body was: ${error.error}`);
      }
      // return an observable with a user-facing error message
      return throwError(
        'Something bad happened; please try again later.');
    };*/
}
