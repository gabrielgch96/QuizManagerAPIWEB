import { Component, OnInit } from '@angular/core';
import { Exam } from 'src/app/datamodel/exam';
import { Observable, Subject } from 'rxjs';
import { ExamService } from 'src/app/services/exam.service';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { User } from 'src/app/datamodel/user';
import { distinctUntilChanged, switchMap, debounceTime } from 'rxjs/operators';

@Component({
  selector: 'app-exams-list',
  templateUrl: './exams-list.component.html',
  styleUrls: ['./exams-list.component.css']
})
export class ExamsListComponent implements OnInit {

  examList$: Exam[];
  examsSearch$: Observable<Exam[]>;
  private searchTerms = new Subject<string>();
  private currentUser: User;

  constructor(private authService: AuthenticationService,
    private examService: ExamService) { 
      this.authService.currentUser.subscribe(x => this.currentUser = x);
    }

  ngOnInit() {
    this.getAllExams();
    this.examsSearch$ = this.searchTerms.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap((term: string) => this.examService.searchExam(term)),
    );
  }

  search(term: string): void {
    this.searchTerms.next(term);
  }

  getAllExams(): void {
    this.examService.getAllExams()
      .subscribe(exams => this.examList$ = exams);
  }
  
  deleteExam(examToDelete: Exam): void {
    this.examList$ = this.examList$.filter(exam => exam != examToDelete);
    this.examService.deleteExam(examToDelete).subscribe();
  }

}
