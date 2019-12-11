import { Component, OnInit, Input, Output } from '@angular/core';
import { QuestionsService } from 'src/app/services/questions.service';
import { Question } from 'src/app/datamodel/question';
import { Observable, Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';
import { EventEmitter } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { User } from 'src/app/datamodel/user';

@Component({
  selector: 'app-question-list',
  templateUrl: './question-list.component.html',
  styleUrls: ['./question-list.component.css']
})
export class QuestionListComponent implements OnInit {

  private currentUser: User;

  constructor(private authService: AuthenticationService,
    private questionService: QuestionsService) {
      this.authService.currentUser.subscribe(x => this.currentUser = x);
     }

  @Input() modifiable:boolean;
  @Output() added: EventEmitter<Question> = new EventEmitter<Question>();
  questionList$ : Question[];
  questionsSearch$: Observable<Question[]>;
  private searchTerms = new Subject<string>();

  ngOnInit() {
    this.getAllQuestions();
    this.questionsSearch$ = this.searchTerms.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap((term: string) => this.questionService.searchQuestion(term)),
    );
  }

  addQuestionToExam(question:Question) : void{
    this.added.emit(question);
  }

  search(term: string): void {
    this.searchTerms.next(term);
  }

  getAllQuestions(): void{
    this.questionService.getAllQuestions()
      .subscribe(questions => this.questionList$ = questions);
  }

  deleteQuestion(questiontoDelete: Question): void{
    this.questionList$ = this.questionList$.filter(question => question != questiontoDelete);
    this.questionService.deleteQuestion(questiontoDelete).subscribe();
  }

}
