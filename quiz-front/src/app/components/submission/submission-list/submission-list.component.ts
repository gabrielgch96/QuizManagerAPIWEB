import { Component, OnInit } from '@angular/core';
import { SubmissionService } from 'src/app/services/submission.service';
import { Submission } from 'src/app/datamodel/submission';
import { User } from 'src/app/datamodel/user';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-submission-list',
  templateUrl: './submission-list.component.html',
  styleUrls: ['./submission-list.component.css']
})
export class SubmissionListComponent implements OnInit {
  
  private currentUser: User;
  submissions$: Submission[];

  constructor(private authService: AuthenticationService,
    private submissionService: SubmissionService) { 
      this.authService.currentUser.subscribe(x => this.currentUser = x);
    }

  ngOnInit() {
    this.getUserSubmissions();
  }


  getUserSubmissions(): void {
    this.submissionService.getUserSubmissions(this.currentUser)
      .subscribe(submissions => this.submissions$ = submissions);
  }

}
