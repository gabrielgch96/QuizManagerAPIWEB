import { Component, OnInit } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { Question } from 'src/app/datamodel/question';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';
import { QuestionsService } from 'src/app/services/questions.service';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-question-search',
  templateUrl: './question-search.component.html',
  styleUrls: ['./question-search.component.css']
})
export class QuestionSearchComponent implements OnInit {

  questionsSearch$: Observable<Question[]>;
  private searchTerms = new Subject<string>();
  
  constructor(private authService: AuthenticationService,
    private questionService: QuestionsService) { }

  ngOnInit() {

    this.questionsSearch$ = this.searchTerms.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap((term: string) => this.questionService.searchQuestion(term)),
    );
  }

  search(term: string): void {
    this.searchTerms.next(term);
  }

}
