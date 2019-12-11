import { Component, OnInit } from '@angular/core';
import { Submission } from 'src/app/datamodel/submission';
import { SubmissionService } from 'src/app/services/submission.service';
import { ActivatedRoute } from '@angular/router';
import { Option } from 'src/app/datamodel/option';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { User } from 'src/app/datamodel/user';

@Component({
  selector: 'app-submission-detail',
  templateUrl: './submission-detail.component.html',
  styleUrls: ['./submission-detail.component.css']
})
export class SubmissionDetailComponent implements OnInit {

  submission: Submission;
  selectedOptions: Option[];
  private currentUser: User;

  constructor(private authService: AuthenticationService,
    private submissionService: SubmissionService,
    private route: ActivatedRoute) { 
      this.selectedOptions = [];
      this.authService.currentUser.subscribe(x => this.currentUser = x);
    }

  ngOnInit() {
    this.getSubmission();
  }

  checkIfSelected(option:Option): boolean{
    let b:boolean =this.selectedOptions.some((opt) => opt.id == option.id)
    return b;
  }

  getSubmission(): void{
    const id = +this.route.snapshot.paramMap.get('id');
    this.submissionService.getSubmission(id).subscribe(
      submission => {
        this.submission = submission;
        submission.answers.forEach(answer => {
          answer.selectedOptions.forEach(option => {
            this.selectedOptions.push(option);
          });
        });
  });
  }

}
