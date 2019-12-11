import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormArray } from '@angular/forms';
import { Question } from 'src/app/datamodel/question';
import { Option } from 'src/app/datamodel/option';
import { ExamService } from 'src/app/services/exam.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Exam } from 'src/app/datamodel/exam';
import { Answer } from 'src/app/datamodel/answer';
import { SubmissionService } from 'src/app/services/submission.service';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { User } from 'src/app/datamodel/user';

@Component({
  selector: 'app-take-exam',
  templateUrl: './take-exam.component.html',
  styleUrls: ['./take-exam.component.css']
})
export class TakeExamComponent implements OnInit {

  private currentUser: User;

  constructor(private fb: FormBuilder,
    private authService: AuthenticationService,
    private route: ActivatedRoute,
    private router: Router,
    private examService: ExamService,
    private submissionService: SubmissionService) { 
      this.authService.currentUser.subscribe(x => this.currentUser = x);
    }

  examForm: FormGroup;
  examMine: Exam;

  ngOnInit() {
    this.examForm = this.fb.group({
      exam: this.fb.group({
        id: ['', Validators.required],
        title: ['', Validators.required]
      }),
      exam_questions: this.fb.array([], [Validators.required])
    });
    this.getExam();
  }

  getExam(): void {
    const id = +this.route.snapshot.paramMap.get('id');

    this.examService.getExam(id).subscribe(
      exam => {
        //this.question = question;
        this.examMine = exam;
        console.log(this.examMine);
        /*this.examMine.questions.forEach(question => {
          question.options.forEach(option => {
            this.addOption(option);
          });
        });*/
        this.id.setValue(exam.id);
        this.title.setValue(exam.title);
        exam.questions.forEach(question => {
          this.addQuestion(question);
        });
      });
  }

  submitExam():void{
    let answers: Answer[] = [];
    let data = JSON.parse(JSON.stringify(this.examForm.value));
    let exam:Exam = data.exam as Exam;
    data.exam_questions.forEach(question => {
      question.options = question.options.filter(option => option.is_selected == true);
      let answer:Answer = new Answer();
      question.options.forEach(option => {
        let opt: Option = new Option(option.text, option.is_right);
        opt.id = option.id;
        opt.question = question as Question;
        answer.selectedOptions.push(opt);
        delete option['is_selected'];
      });
      answer.question = question as Question;
      //answer.exam = data.exam as Exam;
      answers.push(answer);
    });
    //console.log(data.exam_questions);
    console.log(answers)
    let dataInWrapper ={
      "exam": exam,
      "user": this.currentUser,
      "answers": answers
    };
    let x = JSON.stringify(dataInWrapper);
    console.log(x);
    this.submissionService.createSubmission(x).subscribe();
    this.router.navigate(['/submissions']);
  }

  get title() {
    return this.examForm.get('exam').get('title');
  }

  get id(){
    return this.examForm.get('exam').get('id');
  }

  get exam_questions() {
    return this.examForm.get('exam_questions') as FormArray;
  }

  addQuestion(question: Question) {
      let questionForm: FormGroup = this.fb.group({
        id: [question.id],
        content: [question.content, Validators.required],
        topic: this.fb.group({
          id: [question.topic.id],
          name: [question.topic.name, Validators.required]
        }),
        options: this.fb.array([])
      });

      (question.options).forEach(option => {
        let optionForm: FormGroup = this.fb.group({
          id: [option.id, Validators.required],
          text: [option.text, Validators.required],
          is_right: [option.is_right],
          is_selected: [false]
        });
        (questionForm.get('options') as FormArray).push(optionForm);
      });

      this.exam_questions.push(questionForm);
    }
}
