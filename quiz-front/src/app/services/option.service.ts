import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Option } from '../datamodel/option';
import { HttpClient } from '@angular/common/http';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class OptionService {

  private optionsUrl: string = 'http://localhost:8080/quiz-rest-api/rest/options/';

  constructor(private http: HttpClient) { }

  getOption(id: number): Observable<Option> {
    const url = `${this.optionsUrl}${id}`;
    return this.http.get<Option>(url).pipe(
      catchError(this.handleError<Option>(`getOption id=${id}`))
    );
  }

  createOption(option: Option): Observable<Option>{
    return this.http.post<Option>(this.optionsUrl, option).pipe(
      catchError(this.handleError<Option>('createOption'))
    );
  }

  updateOption(option: Option): Observable<Option>{
    const url = this.optionsUrl+"update";
    return this.http.put<Option>(url, option).pipe(
      catchError(this.handleError<Option>('updateOption'))
    )
  }

  searchOption(questionId: number): Observable<Option[]> {
    return this.http.get<Option[]>(`${this.optionsUrl}search?qId=${questionId}`).pipe(
      catchError(this.handleError<Option[]>('searchOption'))
    );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      console.error(error);
      return of(result as T);
    };
  }
}
