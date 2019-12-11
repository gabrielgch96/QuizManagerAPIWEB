import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { of, Observable } from 'rxjs';
import { Topic } from '../datamodel/topic';
import { catchError, map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class TopicService {

  private topicsUrl: string = 'http://localhost:8080/quiz-rest-api/rest/topics/';

  constructor(private http: HttpClient) { }

  getTopic(id: number): Observable<Topic> {
    const url = `${this.topicsUrl}${id}`;
    return this.http.get<Topic>(url).pipe(
      //tap(_ => this.log(`fetched hero id =${id}`)),
      catchError(this.handleError<Topic>(`getTopic id=${id}`))
    );
  }

  createTopic(topic: Topic): Observable<Topic>{
    return this.http.post<Topic>(this.topicsUrl, topic).pipe(
      catchError(this.handleError<Topic>('createTopic'))
    );
  }

  updateTopic(topic: Topic): Observable<Topic>{
    const url = this.topicsUrl+"update";
    return this.http.put<Topic>(url, topic).pipe(
      catchError(this.handleError<Topic>('updateTopic'))
    )
  }

  getAllTopics(): Observable<Topic[]> {
    const url = this.topicsUrl+"search";
    return this.http.get<Topic[]>(url).pipe(
      //tap(_ => this.log(`fetched hero id =${id}`)),
      catchError(this.handleError<Topic[]>('getAllTopics'))
    );
  }

  searchTopic(term: string): Observable<Topic[]> {
    if(!term.trim()){
      return of([]);
    }
    return this.http.get<Topic[]>(`${this.topicsUrl}search?topic_name=${term}`).pipe(
      //tap(_ => this.log(`fetched hero id =${id}`)),
      catchError(this.handleError<Topic[]>('searchTopics'))
    );
  }
  
  deleteTopic(topic: Topic | number): Observable<Topic>{
    const id = typeof topic === 'number' ? topic : topic.id;
    const url = `${this.topicsUrl}${id}`;
    return this.http.delete<Topic>(url).pipe(
      catchError(this.handleError<Topic>('deleteTopic'))
    );

  }
  /**
* Handle Http operation that failed.
* Let the app continue.
* @param operation - name of the operation that failed
* @param result - optional value to return as the observable result
*/
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      console.error(error);
      return of(result as T);
    };
  }
}
