import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { of, Observable } from 'rxjs';
import { Exam } from '../datamodel/exam';
import { catchError, map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ExamService {

  private examsUrl: string = 'http://localhost:8080/quiz-rest-api/rest/exams/';
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  getExam(id: number): Observable<Exam> {
    const url = `${this.examsUrl}${id}`;
    return this.http.get<Exam>(url).pipe(
      //tap(_ => this.log(`fetched hero id =${id}`)),
      catchError(this.handleError<Exam>(`getExam id=${id}`))
    );
  }

  createExam(exam: Exam): Observable<Exam>{
    return this.http.post<Exam>(this.examsUrl, exam).pipe(
      catchError(this.handleError<Exam>('createExam'))
    );
  }

  createExamWithQuestions(data: string): Observable<Exam>{
    return this.http.post<Exam>(this.examsUrl, data,this.httpOptions).pipe(
      catchError(this.handleError<Exam>('createExam'))
    );
  }

  updateExam(exam: Exam): Observable<Exam>{
    const url = this.examsUrl+"update";
    return this.http.put<Exam>(url, exam).pipe(
      catchError(this.handleError<Exam>('updateExam'))
    )
  }

  updateExamWithQuestions(data: string): Observable<Exam>{
    return this.http.put<Exam>(this.examsUrl+"update", data,this.httpOptions).pipe(
      catchError(this.handleError<Exam>('createExam'))
    );
  }

  getAllExams(): Observable<Exam[]> {
    const url = this.examsUrl+"search";
    return this.http.get<Exam[]>(url).pipe(
      catchError(this.handleError<Exam[]>('getAllExams'))
    );
  }

  searchExam(term: string): Observable<Exam[]> {
    if(!term.trim()){
      return of([]);
    }
    return this.http.get<Exam[]>(`${this.examsUrl}search?title=${term}`).pipe(
      //tap(_ => this.log(`fetched hero id =${id}`)),
      catchError(this.handleError<Exam[]>('searchExams'))
    );
  }

  deleteExam(exam: Exam | number): Observable<Exam>{
    const id = typeof exam === 'number' ? exam : exam.id;
    const url = `${this.examsUrl}${id}`;
    return this.http.delete<Exam>(url).pipe(
      catchError(this.handleError<Exam>('deleteExam'))
    );

  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      console.error(error);
      return of(result as T);
    };
  }
}
