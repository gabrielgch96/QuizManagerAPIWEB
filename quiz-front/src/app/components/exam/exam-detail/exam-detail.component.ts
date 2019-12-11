import { Component, OnInit } from '@angular/core';
import { Question } from 'src/app/datamodel/question';
import { Location } from '@angular/common';
import { FormGroup, Validators, FormBuilder, FormArray } from '@angular/forms';
import { ExamService } from 'src/app/services/exam.service';
import { ActivatedRoute } from '@angular/router';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { User } from 'src/app/datamodel/user';

@Component({
  selector: 'app-exam-detail',
  templateUrl: './exam-detail.component.html',
  styleUrls: ['./exam-detail.component.css']
})
export class ExamDetailComponent implements OnInit {

  examForm: FormGroup;
  private currentUser: User;

  constructor(private location: Location,
    private authService: AuthenticationService,
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private examService: ExamService) {
      this.authService.currentUser.subscribe(x => this.currentUser = x);
     }

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

  checkIfAdded(question: Question): boolean {

    for (let questionIn of this.exam_questions.controls) {
      if (questionIn.get('id').value == question.id)
        return true;
    }
    return false;
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
      })
    })
    this.exam_questions.push(questionForm);
  }

  removeOption(index: number) {
    this.exam_questions.removeAt(index);
  }

  goBack(): void {
    this.location.back();
  }

  getExam(): void {
    const id = +this.route.snapshot.paramMap.get('id');

    this.examService.getExam(id).subscribe(
      exam => {
        //this.question = question;
        this.id.setValue(exam.id);
        this.title.setValue(exam.title);
        exam.questions.forEach(question => {
          this.addQuestion(question);
        });
      });
  }

  updateExam(): void {
    if (this.examForm.valid) {
      let data = JSON.stringify(this.examForm.value);
      console.log(data);
      this.examService.updateExamWithQuestions(data).subscribe();
      this.goBack();
    }
    else {
      alert('please verify the fields!...At least one question is needed')
    }
  }

  onAdded(question: Question) {
    if (!this.checkIfAdded(question))
      this.addQuestion(question);
    else
      alert("Question already added");
  }

}
